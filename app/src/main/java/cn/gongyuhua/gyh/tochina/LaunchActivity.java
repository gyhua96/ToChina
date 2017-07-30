package cn.gongyuhua.gyh.tochina;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * In this activity we can show some ads here
 */
public class LaunchActivity extends AppCompatActivity {
    private Intent intent;
    private File file;
    private Thread thread;
    private boolean skiped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get new ad photo
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        setContentView(R.layout.activity_launch);
        ImageView splash = (ImageView) findViewById(R.id.splash_img);
        splash.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        intent = new Intent(this, MainActivity.class);
        final Button skip = (Button) findViewById(R.id.skip_splash);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skiped = true;
                startActivity(intent);
                finish();
            }
        });
        String path = getCacheDir().toString();
        String filename = "splash.jpg";
        file = new File(path, filename);
        if (!file.exists()) {
            Log.d("splash", "file not exist.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] data = readInputStream(getRequest("http://gongyuhua.cn/tochina/splash.png"));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                                data.length);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

                        String imagePath = file.getAbsolutePath();
                        Log.i("splash", "imagePath : file.getAbsolutePath() = " + imagePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            splash.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                splash.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        Log.d("cache", getCacheDir().toString());
        // Build a intent refers to MainActivity


        // start a new thread to delay 2000 ms and start new intent
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Delay 2000 ms to show the launcher screen
                    // It could be a ad picture or something
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Start MainActivity and finish Splash activity
                if (!skiped) {
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();

    }

    public static InputStream getRequest(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000); // 5秒
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;

    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

}
