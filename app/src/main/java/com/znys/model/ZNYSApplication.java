package com.znys.model;

import android.app.Activity;
import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by mushroom on 2015/6/5.
 */
public class ZNYSApplication extends Application {

    private List<Activity> activitys = null;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getApplicationContext());
        imageLoader.init(config);
    }

    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }
    }

    /**
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activitys.contains(activity)) {
            activity.finish();
            activitys.remove(activity);
        }
    }

    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
