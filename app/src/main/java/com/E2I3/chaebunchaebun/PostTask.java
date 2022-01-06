package com.E2I3.chaebunchaebun;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostTask extends AsyncTask<String, Void, String> {
    String receiveMsg, str;

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://3.37.243.188:8080/" + params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true); // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");

            OutputStream os = conn.getOutputStream();
            os.write(params[1].getBytes("UTF-8"));
            os.flush();
            os.close();

            //BufferedReader br = null;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg: ", receiveMsg);

                bufferedReader.close();
                return  receiveMsg;
            } else {
                InputStreamReader tmp = new InputStreamReader(conn.getErrorStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg: ", receiveMsg);

                bufferedReader.close();
                return receiveMsg;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
