package com.beautyteam.smartkettle.network;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by asus on 29.10.2014.
 */

public class Network {
    public String urlConnectionPost(String strUrl, String urlParameters) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        int code = connection.getResponseCode();
        String str = "";
        if (code == 200) {
            InputStream in = connection.getInputStream();
            str = handleInputStream(in);
        }
        writer.close();
        connection.disconnect();
        return str;
    }

    public String urlConnectionGet( String strUrl) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        String str = "";
        if (code == 200) {
            InputStream in = connection.getInputStream();
            str = handleInputStream(in);
        }
        connection.disconnect();
        //str = URLEncoder.encode(str, "UTF-8");
        return str;
    }

    private String handleInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = "", line = "";
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        Log.e("", result);
        return result;
    }


}
