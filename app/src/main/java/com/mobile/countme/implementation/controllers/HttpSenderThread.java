package com.mobile.countme.implementation.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

/**
 * Created by Torgeir on 15.10.2015.
 */

public class HttpSenderThread extends Thread {
    private JSONObject obj;
    private String url;
    private LoginInfo info;
    private HttpPostKind postType;

    HttpSenderThread(JSONObject obj, String url, LoginInfo info, HttpPostKind postType) {
        this.obj = obj;
        this.url = url;
        this.info = info;
        this.postType = postType;

    }


    public void run() {
        sendJSON();
    }

    //sendJSON is a delegation method that sends a jsonObject to an url
    private void sendJSON() {
        Log.d("SendJSON", "SendJSON started");
        HttpPost post = new HttpPost(url);
        StringEntity string;
        HttpResponse response;

        try {
            string = new StringEntity(obj.toString());
            string.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(string);
            response = new DefaultHttpClient().execute(post);
            Log.d("SendJSON", "JSON sent");
            String json_string;
            JSONObject receivedObject;
            switch (postType) {

                case TRIP:
                    String json_string2 = EntityUtils.toString(response.getEntity());
                    Log.d("Received tripresponse", json_string2);
                    break;
                case ERROR:
                    String json_string1 = EntityUtils.toString(response.getEntity());
                    Log.d("Received errorresponse", json_string1);

                    break;
                case LOGIN:
                    json_string = EntityUtils.toString(response.getEntity());
                    receivedObject = new JSONObject(json_string);
                    info.setUserID(receivedObject.getString("_id"));
                    info.setToken(receivedObject.getString("token"));


                    Log.d("Received loginresponse", receivedObject.toString());
                    break;
                case CREATEUSER:
                    json_string = EntityUtils.toString(response.getEntity());
                    receivedObject = new JSONObject(json_string);
                    info.setUsername(receivedObject.getString("username"));


                    Log.d("Create user response", json_string);
                    break;
                case UPDATEUSER:

                    json_string = EntityUtils.toString(response.getEntity());
                    //receivedObject = new JSONObject(json_string);
                    Log.d("Update user response", json_string);

            }
        } catch (JSONException e) {
            Log.d("Exception", "JSONException");
            e.printStackTrace();
        } catch (ClientProtocolException e) {

            Log.d("Exception", "ClientProtoclException");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {

            Log.d("Exception", "UnsupportedEncodingException");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            Log.d("Exception", "UnknownHostException");
            switch (postType) {
                case LOGIN:
                    info.resetLogin();

                    break;
                case CREATEUSER:
                    info.resetInfo();
                    break;

            }

            e.printStackTrace();
        } catch (IOException e) {

            Log.d("Exception", "IOException");
            e.printStackTrace();
        }
        synchronized (this){
            this.notifyAll();
        }


        //If response is needed somewhere, figure out how to communicate with main thread.

    }
}
