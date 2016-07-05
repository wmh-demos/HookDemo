package me.com.ams_pms_hook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mButton) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.baidu.com"));
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        try {
            HookHelper.hookActivityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.attachBaseContext(newBase);
    }
}
