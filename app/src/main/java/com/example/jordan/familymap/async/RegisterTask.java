package com.example.jordan.familymap.async;

import android.content.Context;
import android.os.AsyncTask;
import ProxyServer.ProxyServer;
import ReqAndResponses.PersonRequest;
import ReqAndResponses.RegisterResponse;
import model.User;

public class RegisterTask extends AsyncTask<User, Integer , RegisterResponse> {
    RegisterResponse response;
    registerResultInterface context;

    public RegisterTask(registerResultInterface context) {
        this.context = context;
    }

    @Override
    protected RegisterResponse doInBackground(User... requests) {
        return ProxyServer.register(requests[0]);
    }

    protected void onPostExecute(RegisterResponse result) {
        context.setRegisterInterfaceResult(result);
    }

    public interface registerResultInterface {
        public void setRegisterInterfaceResult(RegisterResponse result);
    }
}