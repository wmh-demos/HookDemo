package me.com.ams_pms_hook;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class HookHelper {

    public static void hookActivityManager() throws Exception {
        Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

        Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);

        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        Object rawIActivityManager = mInstanceField.get(gDefault);

        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerInterface},
                new HookHandler(rawIActivityManager));
        mInstanceField.set(gDefault, proxy);
    }
}
