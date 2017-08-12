package cn.gongyuhua.gyh.tochina;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gyh on 17-7-27.
 */

public class HTTP {
    public static String HttpGet(String Url) {
        /*Usage:
        String str=HTTP.HttpGet(url);
        url is the endpoint.
         */
        HttpURLConnection connection = null;
        String str = null;
        try {
            URL url = new URL(Url);
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            str = "";
            String len;
            while ((len = br.readLine()) != null) {
                //while the data stream is not ended
                str = str + len;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        //the value of str is what you need
        Log.d("data", str);
        return str;

    }

    public static String HttpPost(String Url, String data) {
        /*Usage:
        String str=HTTP.HttpPost(url,data);
        url is the endpoint, and data is the data you want to post, format like "user=user&pwd=pwd".
         */
        HttpURLConnection urlConnection = null;
        String str = null;
        try {
            URL url = new URL(Url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);

            writer.flush();
            writer.close();
            out.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            str = "";
            String len;
            while ((len = br.readLine()) != null) {
                //while the data stream is not ended
                str = str + len;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return str;
    }
}
