package cn.gongyuhua.gyh.tochina;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LaunchActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);
        try {
            Thread.sleep(1000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        // Build a intent refers to MainActivity
        intent = new Intent(this, MainActivity.class);

        // start a new thread to delay 2000 ms and start new intent
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    // Delay 2000 ms to show the launcher screen
                    // It could be a ad picture or something
                    Thread.sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                // Start MainActivity and finish Splash activity
                startActivity(intent);
                finish();
            }
        });
        thread.start();

    }
}
