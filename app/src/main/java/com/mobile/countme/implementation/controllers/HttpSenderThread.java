package com.mobile.countme.implementation.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/**
 * Created by Torgeir on 15.10.2015.
 */

public class HttpSenderThread extends Thread {
    private JSONObject obj;
    private String url;

    HttpSenderThread(JSONObject obj, String url){
        this.obj = obj;
        this.url = url;

    }

    public void run(){
        sendJSON();
    }

    //sendJSON is a delegation method that sends a jsonObject to an url
    private void sendJSON(){
        Log.d("SendJSON", "SendJSON started");
        HttpPost post = new HttpPost( url );
        StringEntity string = null;
        HttpResponse response;

        try {
            string = new StringEntity(obj.toString());
            string.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(string);
            response = new DefaultHttpClient().execute(post);
            Log.d("SendJSON", "JSON sent");
        }
        catch (Exception e){
            e.printStackTrace();

        }

        //If response is needed somewhere, figure out how to communicate with main thread.

    }
}
