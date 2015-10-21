package com.mobile.countme.implementation.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Torgeir on 15.10.2015.
 */

public class HttpSenderThread extends Thread {
    private JSONObject obj;
    private String url;
    private LoginInfo info;
    private HttpPostKind postType;

    HttpSenderThread(JSONObject obj, String url, LoginInfo info, HttpPostKind postType){
        this.obj = obj;
        this.url = url;
        this.info = info;
        this.postType = postType;

    }


    public void run(){
        sendJSON();
    }

    //sendJSON is a delegation method that sends a jsonObject to an url
    private void sendJSON(){
        Log.d("SendJSON", "SendJSON started");
        HttpPost post = new HttpPost( url );
        StringEntity string;
        HttpResponse response;

        try {
            string = new StringEntity(obj.toString());
            string.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(string);
            response = new DefaultHttpClient().execute(post);
            Log.d("SendJSON", "JSON sent");
            switch(postType){

                case TRIP:
                    break;
                case ERROR:
                    String json_string1 = EntityUtils.toString(response.getEntity());
                    Log.d("Received errorresponse", json_string1);
                    synchronized (obj) {
                        obj.notifyAll();
                    }
                    break;
                case LOGIN:
                    String json_string = EntityUtils.toString(response.getEntity());
                    JSONObject receivedObject = new JSONObject(json_string);
                    synchronized (info) {
                        info.setUserID(receivedObject.getString("_id"));
                        info.setToken(receivedObject.getString("token"));
                        info.notifyAll();
                    }
                    Log.d("Received loginresponse", receivedObject.toString());
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        //If response is needed somewhere, figure out how to communicate with main thread.

    }
}
