package com.guoguang.framework.event;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.guoguang.rxbus.RxBus;

import io.reactivex.Observable;

/**
 * Created by jereme on 4/14/18.
 */

public class ActivityLifecycleEvent {
    public final static Observable<ActivityLifecycleEvent> observable = RxBus.getInstance().register(ActivityLifecycleEvent.class, ActivityLifecycleEvent.class);

    public final static int onActivityCreated = 0;
    public final static int onActivityStarted = 1;
    public final static int onActivityResumed = 2;
    public final static int onActivityPaused = 3;
    public final static int onActivityStopped = 4;
    public final static int onActivitySaveInstanceState = 5;
    public final static int onActivityDestroyed = 6;

    private int type;
    private Activity activity;
    private Bundle bundle;

    public ActivityLifecycleEvent(int type, Activity activity, @Nullable Bundle bundle) {
        this.type = type;
        this.activity = activity;
        this.bundle = bundle;
    }

    public int getType() {
        return type;
    }

    public Activity getActivity() {
        return activity;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
