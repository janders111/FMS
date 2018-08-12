package com.example.jordan.familymap.async;

import android.os.AsyncTask;
import android.widget.Toast;

import ProxyServer.ProxyServer;
import ReqAndResponses.PersonRequest;
import model.Person;

public class getPersonFromIDTask {
}


//public class getPersonFromIDTask extends AsyncTask<PersonRequest, Integer , String> {
//    Person response;
//    @Override
//    protected String doInBackground(PersonRequest... requests) {
//        response = ProxyServer.getPersonFromID(requests[0]);
//        if(response == null) {
//            return "getPersonFromIDTask failed";
//        }
//        if(response.getFirstName() == null) {
//            return "getPersonFromIDTask failed";
//        }
//        String name = response.getFirstName() + " " + response.getLastName();
//        System.out.println("Got the personID getPersonFromIDTask:" + name);
//        return name;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        //change to the map fragment
//        Toast.makeText(getActivity(), result,
//                Toast.LENGTH_LONG).show();
//        System.out.println(result);
//    }
//}