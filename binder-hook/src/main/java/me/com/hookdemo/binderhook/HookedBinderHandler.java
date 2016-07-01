package me.com.hookdemo.binderhook;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HookedBinderHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    private Object mBase;

    public HookedBinderHandler(IBinder base, Class<?> subClass) {
        try {
            Method asInterfaceMethod = subClass.getDeclaredMethod("asInterface", IBinder.class);
            mBase = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hook failed!");
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        // 把剪切版的内容替换为 "you are hooked"
        if ("getPrimaryClip".equals(method.getName())) {
            Log.d(TAG, "hook getPrimaryClip");
            return ClipData.newPlainText(null, "you are hooked");
        }

        // 欺骗系统,使之认为剪切版上一直有内容
        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }

        return method.invoke(mBase, objects);
    }
}
