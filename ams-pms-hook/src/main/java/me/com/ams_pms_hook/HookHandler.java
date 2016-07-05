package me.com.ams_pms_hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HookHandler implements InvocationHandler {

    private static final String TAG = "HookHandler";

    private Object mBase;

    public HookHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.d(TAG, "You are hooked!");
        Log.d(TAG, "method : " + method.getName() + " , args : " + Arrays.toString(objects));

        return method.invoke(mBase, objects);
    }
}
