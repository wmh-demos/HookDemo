package me.com.hookdemo.binderhook;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class BinderHookHelper {

    @SuppressWarnings("unchecked")
    public static void hookClipboardService() throws Exception {
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method getService = serviceManager.getDeclaredMethod("getService", String.class);

        IBinder rawBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);
        IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                new Class<?>[]{IBinder.class},
                new BinderProxyHookHandler(rawBinder));

        Field cacheField = serviceManager.getDeclaredField("sCache");
        cacheField.setAccessible(true);
        Map<String, IBinder> cache = (Map) cacheField.get(null);
        cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
    }
}
