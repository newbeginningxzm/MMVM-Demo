package com.bupt.paragon.learningvolley;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Paragon on 2016/5/17.
 */
public class VolleySingleton extends Application {
    private RequestQueue mRequestQueue ;
    private static  Context mContext;
    private static VolleySingleton INSTANCE;
    private static class VolleySingletonHolder{
       public static final RequestQueue mRequestQueue= Volley.newRequestQueue(mContext,null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        INSTANCE=this;
    }

    public static VolleySingleton getInstance(){
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        return VolleySingletonHolder.mRequestQueue;
    }

    public <T> boolean addToRequestQueue(Request<T> request){
        if(request!=null){
            mRequestQueue.add(request);
            return true;
        }
        return false;
    }

}
