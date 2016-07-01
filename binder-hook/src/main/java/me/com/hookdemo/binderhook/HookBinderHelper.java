package me.com.hookdemo.binderhook;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class HookBinderHelper {

    @SuppressWarnings("unchecked")
    public static void hookClipboardService() throws Exception {
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method getService = serviceManager.getDeclaredMethod("getService", String.class);

        //get the raw Binder Proxy
        IBinder rawBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);
        //create hooked Binder Proxy
        IBinder hookedBinderProxy = (IBinder) Proxy.newProxyInstance(
                serviceManager.getClassLoader(),
                new Class<?>[]{IBinder.class},
                new HookedBinderProxyHandler(rawBinder));

        //replace the Binder Proxy
        Field cacheField = serviceManager.getDeclaredField("sCache");
        cacheField.setAccessible(true);
        Map<String, IBinder> cache = (Map) cacheField.get(null);
        cache.put(Context.CLIPBOARD_SERVICE, hookedBinderProxy);
    }
}
