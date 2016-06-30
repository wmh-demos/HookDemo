package me.com.hookdemo.hook;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookHelper {

    /**
     * 使用反射的方式找到ActivityThread这个类
     * 将类中的Instrumentation对象替换成自己实现的
     */
    public static void attachContext() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        Instrumentation demonInstrumentation = new DemonInstrumentation(mInstrumentation);
        mInstrumentationField.set(currentActivityThread, demonInstrumentation);
    }
}
