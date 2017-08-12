package cn.gongyuhua.gyh.tochina;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class DstContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dst_content);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
        setTitle("");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
