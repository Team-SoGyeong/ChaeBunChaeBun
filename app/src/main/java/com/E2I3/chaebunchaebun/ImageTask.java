package com.E2I3.chaebunchaebun;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImageTask extends AsyncTask<String, Void, String> {
    String receiveMsg, str;
    String boundary = "^-----^";
    String LINE_FEED = "\r\n";
    String charset = "UTF-8";
    PrintWriter writer;

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
            conn.setRequestProperty("Content-Type",  "multipart/form-data;charset=utf-8;boundary=" + boundary);
            conn.setRequestProperty("Connection", "Keep-Alive");

            File image = new File(params[1]);
            OutputStream os = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"user\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(params[2]).append(LINE_FEED);
            writer.flush();

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"data\"; filename=\"" + image.getName() + "\"").append(LINE_FEED);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(image.getName())).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(image);
            byte[] byteBuffer = new byte[(int)image.length()];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(byteBuffer)) != -1) {
                os.write(byteBuffer, 0, bytesRead);
            }
            os.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();

            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

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
                return receiveMsg;
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
