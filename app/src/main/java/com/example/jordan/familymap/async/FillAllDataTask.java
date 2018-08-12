package com.example.jordan.familymap.async;

import android.os.AsyncTask;

import com.example.jordan.familymap.model.MainModel;
import com.example.jordan.familymap.ui.MainActivity;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginRequest;
import ReqAndResponses.LoginResponse;

public class FillAllDataTask extends AsyncTask<LoginResponse, Integer , String> {
    String error;
    FillAllDataTask.fillDataInterface context;

    public FillAllDataTask(FillAllDataTask.fillDataInterface context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(LoginResponse... loginResponses) {
        //return ProxyServer.login(loginResponses[0]);
        return MainModel.fillAllData(loginResponses[0]);
    }

    protected void onPostExecute(String result) {
        context.fillDataInterfaceResult(result);
    }

    public interface fillDataInterface {
        public void fillDataInterfaceResult(String result);
    }
}
