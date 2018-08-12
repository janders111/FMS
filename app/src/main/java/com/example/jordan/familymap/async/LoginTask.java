package com.example.jordan.familymap.async;

import android.os.AsyncTask;
import com.example.jordan.familymap.ui.LoginFrag;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginRequest;
import ReqAndResponses.LoginResponse;
import ReqAndResponses.RegisterResponse;

public class LoginTask extends AsyncTask<LoginRequest, Integer , LoginResponse> {
    LoginResponse response;
    loginResultInterface context;

    public LoginTask(loginResultInterface context) {
        this.context = context;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... requests) {
        return ProxyServer.login(requests[0]);
    }

    protected void onPostExecute(LoginResponse result) {
        context.setLoginInterfaceResult(result);
    }

    public interface loginResultInterface {
        public void setLoginInterfaceResult(LoginResponse result);
    }
}
