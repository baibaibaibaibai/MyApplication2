
package com.example.android.myapplication.base;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getScreenManager() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        // 3524 modify baidan 1/7
        // if (activityStack.size() == 0)
        if (activityStack == null || activityStack.size() == 0)
            return null;
        Activity activity = activityStack.lastElement();
        return activity;
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void popAllActivityExceptOne() {
        List<Activity> activitys = new ArrayList<Activity>();
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            activitys.add(activity);
            popActivity(activity);
        }
        for (Activity activity : activitys) {
            activity.finish();
        }
    }

    public void popAllActivityExceptOne(int count) {
        List<Activity> activitys = new ArrayList<Activity>();
        for (int i = 0; i < count; i++) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            activitys.add(activity);
            popActivity(activity);
        }
        for (Activity activity : activitys) {
            activity.finish();
        }
    }

    private boolean isAppForeground = true;

    private Class<?> restartActivity;


    public void reStart() {
        if (!isAppForeground) {
            isAppForeground = true;
            Activity currentActivity = currentActivity();
            if (currentActivity == null) {
                return;
            }
            popAllActivityExceptOne();
            Intent intent = new Intent(currentActivity, restartActivity);
            intent.putExtra(Constant.REMOTE_LOGIN, true);
            currentActivity.startActivity(intent);
        }
    }
}
