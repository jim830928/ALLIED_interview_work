package com.example.allied_interview_work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String dataset = "F-C0032-001";
    private String AuthorizationCode = "CWB-BE02C8E4-C27E-4F4B-99BE-C3201BEF724F";
    private String conditions[] = new String[]{"elementName=MinT", "format=json", "locationName=臺北市"};
    private String JSONdata;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check whether you have entered this app only once or not
        WelcomeBack_implement();
        // Add items from Net to recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        new Thread(runnable).start();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        //Print content into app page from some sub-thread.
        @Override
        public void handleMessage(Message msg) {
            Log.d("content", JSONdata);
            String[][] testData = rearrangeJSONData(JSONdata);
            if(testData.length == 0){
                // If rearrange failed, print error message
                Log.d("error", "JSONData rearrange failed");
            }else{
                CustomRListAdapter adapter = new CustomRListAdapter(testData);
                // Write data into recyclerview
                recyclerView.setAdapter(adapter);
            }
        }
    };
    Runnable runnable = new Runnable() {
        // Add sub-thread to retrieve contents from internet.
        @Override
        public void run() {
            String urlString = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/" + dataset
                    + "?Authorization=" + AuthorizationCode;
            for(String condition : conditions){
                urlString += "&" + condition;
            }
            Log.d("url", urlString);
            JSONdata = retrieveFromNet.retrieve(urlString);
            handler.sendEmptyMessage(0);
        }
    };
    String[][] rearrangeJSONData(String JSONdata){
        // Rearrange a String of JSON datas and turn them into 2D String array
        String[][] output = new String[0][];
        try {
            String[][] outputTmp;
            JSONObject obj = new JSONObject(JSONdata).getJSONObject("records");
            JSONArray dataArray = obj.getJSONArray("location").getJSONObject(0)
                                .getJSONArray("weatherElement").getJSONObject(0)
                                .getJSONArray("time");
            outputTmp = new String[dataArray.length()][];
            for(int i=0; i<outputTmp.length; i++){
                outputTmp[i] = new String[4];
                JSONObject objTmp = dataArray.getJSONObject(i);
                outputTmp[i][0] = objTmp.getString("startTime");
                outputTmp[i][1] = objTmp.getString("endTime");
                outputTmp[i][2] = objTmp.getJSONObject("parameter").getString("parameterName");
                outputTmp[i][3] = objTmp.getJSONObject("parameter").getString("parameterUnit");
            }
            output = outputTmp;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return output;
        }
    }
    private void WelcomeBack_implement(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        long wentTimes = prefs.getLong("wentTimes", 0);
        if(wentTimes > 0)
            Toast.makeText(this, "歡迎回來，這是您第" + (wentTimes + 1) + "開啟該App。", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("wentTimes", wentTimes+1);
        editor.apply();
    }
}
