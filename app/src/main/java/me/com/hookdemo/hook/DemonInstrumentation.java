package me.com.hookdemo.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

public class DemonInstrumentation extends Instrumentation {

    private static final String TAG = "DemonInstrumentation";

    Instrumentation mBase;

    public DemonInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token,
            Activity target, Intent intent, int requestCode, Bundle options) {

        Toast.makeText(who, "DemonInstrumentation has hooked execStartActivity method!",
                Toast.LENGTH_SHORT).show();

        Log.d(TAG, "DemonInstrumentation has hooked execStartActivity method!");

        Log.d(TAG, "\n执行了startActivity, 参数如下: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who, contextThread, token,
                    target, intent, requestCode, options);
        } catch (Exception e) {
            Log.e(TAG, "reflect method execStartActivity exception ", e);
        }
        return null;
    }
}
