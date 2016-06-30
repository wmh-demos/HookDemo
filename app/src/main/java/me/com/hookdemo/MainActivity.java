package me.com.hookdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.com.hookdemo.binderhook.BinderHookHelper;
import me.com.hookdemo.hook.HookHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BinderHookHelper.hookClipboardService();
        } catch (Exception e) {
            Log.e(TAG, "hook exception", e);
        }

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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://www.baidu.com"));

            // 注意这里使用的ApplicationContext 启动的Activity
            // 因为Activity对象的startActivity使用的并不是ContextImpl的mInstrumentation
            // 而是自己的mInstrumentation, 如果你需要这样, 可以自己Hook
            // 比较简单, 直接替换这个Activity的此字段即可.
            getApplicationContext().startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
