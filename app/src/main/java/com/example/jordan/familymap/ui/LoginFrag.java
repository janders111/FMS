package com.example.jordan.familymap.ui;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.async.FillAllDataTask;
import com.example.jordan.familymap.async.LoginTask;
import com.example.jordan.familymap.async.RegisterTask;
import com.example.jordan.familymap.model.MainModel;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginRequest;
import ReqAndResponses.LoginResponse;
import ReqAndResponses.PersonRequest;
import ReqAndResponses.RegisterResponse;
import model.Person;
import model.User;


public class LoginFrag extends Fragment implements com.example.jordan.familymap.async.RegisterTask.registerResultInterface, com.example.jordan.familymap.async.LoginTask.loginResultInterface, com.example.jordan.familymap.async.FillAllDataTask.fillDataInterface{
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mServerHost;
    private EditText mPort;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastname;
    private EditText mEmail;
    private RadioGroup mGender;
    private Boolean male = false;
    private Boolean female = false;

    public LoginFrag() {
        MainModel.setLf(this);
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //for activities
        super.onCreate(savedInstanceState);
    }

    public void checkRequiredFields() {
        if(
                mServerHost.getText().toString().length() > 0 &&
                mPort.getText().toString().length() > 0 &&
                mUsername.getText().toString().length() > 0 &&
                mPassword.getText().toString().length() > 0 &&
                mFirstName.getText().toString().length() > 0 &&
                mLastname.getText().toString().length() > 0 &&
                mEmail.getText().toString().length() > 0 &&
                (male || female)
                ){
            mRegisterButton.setEnabled(true);
        } else {
            mRegisterButton.setEnabled(false);
        }
        if(     mServerHost.getText().toString().length() > 0 &&
                mPort.getText().toString().length() > 0 &&
                mUsername.getText().toString().length() > 0 &&
                mPassword.getText().toString().length() > 0
                ){
            mLoginButton.setEnabled(true);
        } else {
            mLoginButton.setEnabled(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mServerHost = (EditText) v.findViewById(R.id.host);
        mPort = (EditText) v.findViewById(R.id.port);
        mUsername = (EditText) v.findViewById(R.id.userName);
        mPassword = (EditText) v.findViewById(R.id.password);
        mFirstName = (EditText) v.findViewById(R.id.firstName);
        mLastname = (EditText) v.findViewById(R.id.lastName);
        mEmail = (EditText) v.findViewById(R.id.email);
        mLoginButton = (Button) v.findViewById(R.id.signIn);
        mRegisterButton = (Button) v.findViewById(R.id.register);
        mGender = (RadioGroup) v.findViewById(R.id.gender);

        TextWatcher EnableDisableButtonsWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRequiredFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        };

        mServerHost.addTextChangedListener(EnableDisableButtonsWatcher);
        mPort.addTextChangedListener(EnableDisableButtonsWatcher);
        mUsername.addTextChangedListener(EnableDisableButtonsWatcher);
        mPassword.addTextChangedListener(EnableDisableButtonsWatcher);
        mFirstName.addTextChangedListener(EnableDisableButtonsWatcher);
        mLastname.addTextChangedListener(EnableDisableButtonsWatcher);
        mEmail.addTextChangedListener(EnableDisableButtonsWatcher);
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male:
                        male = true;
                        female = false;
                        checkRequiredFields();
                        break;
                    case R.id.female:
                        male = false;
                        female = true;
                        checkRequiredFields();
                        break;
                }
            }
        });
        checkRequiredFields();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProxyServer.setServerHost(mServerHost.getText().toString());
                ProxyServer.setServerPort(mPort.getText().toString());
                LoginRequest req = new LoginRequest(mUsername.getText().toString(), mPassword.getText().toString());
                requestLogin(req);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProxyServer.setServerHost(mServerHost.getText().toString());
                ProxyServer.setServerPort(mPort.getText().toString());
                User req = new User(mUsername.getText().toString(),
                        mPassword.getText().toString(),
                        mEmail.getText().toString(),
                        mFirstName.getText().toString(),
                        mLastname.getText().toString(),
                        (male ? "m":"f"),
                        null);
                requestRegister(req);
            }
        });
        return v;
    }

    public void requestRegister(User req) {//These two lines are in their own function so the context is right for using "this"
        RegisterTask task = new RegisterTask(this);
        task.execute(req);
    }

    public void requestLogin(LoginRequest req) {//These two lines are in their own function so the context is right for using "this"
        LoginTask task = new LoginTask(this);
        task.execute(req);
    }

    public void requestFillAllData(LoginResponse res) {//These two lines are in their own function so the context is right for using "this"
        FillAllDataTask task = new FillAllDataTask(this);
        task.execute(res);
    }

    @Override //interface function that gives this class the data after registerTask is called
    public void setRegisterInterfaceResult(RegisterResponse regResponse) {
        if(regResponse == null) {
            toast("Connection error");
        }
        else if(!regResponse.valid()) {
            toast("Registration error.");
        }
        else {
            finishLogin(regResponse.castToLoginResponse());
        }
    }

    @Override
    public void setLoginInterfaceResult(LoginResponse loginResponse) {
        if(loginResponse == null) {
            toast("Connection error");
        }
        else if(!loginResponse.valid()) {
            toast("Login error.");
        }
        else {
            finishLogin(loginResponse);
        }
    }

    public void finishLogin(LoginResponse lr) {
        toast("Welcome " + lr.getUserName());
        MainModel.setPassword(mPassword.getText().toString());
        //MainModel.fillAllData(lr);
        requestFillAllData(lr);
    }

    @Override
    public void fillDataInterfaceResult(String result) {
        ((MainActivity) getActivity()).switchToMapFrag();
    }

    public void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

}