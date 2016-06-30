package me.com.hookdemo.binderhook;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BinderProxyHookHandler implements InvocationHandler {

    private static final String TAG = "BinderProxyHookHandler";

    private IBinder mBase;
    private Class<?> mStub;
    private Class<?> mIInterface;

    public BinderProxyHookHandler(IBinder base) {
        mBase = base;
        try {
            mStub = Class.forName("android.content.IClipboard$Stub");
            mIInterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        if ("queryLocalInterface".equals(method.getName())) {
            Log.d(TAG, "queryLocalInterface , return a proxy");
            return Proxy.newProxyInstance(o.getClass().getClassLoader(),
                    new Class[]{mIInterface},
                    new BinderHookHandler(mBase, mStub));
        }

        return method.invoke(mBase, objects);
    }
}
