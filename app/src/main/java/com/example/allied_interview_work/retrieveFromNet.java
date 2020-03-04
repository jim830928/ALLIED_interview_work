package com.example.allied_interview_work;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class retrieveFromNet {
    static String retrieve(String urlString){
        String content = "";
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(urlString);
            httpConn = (HttpURLConnection)url.openConnection();
            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.d("TAG", "---into-----urlConnection---success--");
                InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
                int i;
                while((i = isr.read()) != -1)
                {
                    content = content + (char)i;
                }
                isr.close();
                httpConn.disconnect();
                return content;
            }else
            {
                Log.d("TAG", "---into-----urlConnection---fail--");
                return "failed";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
