package com.bupt.paragon.learningvolley;

import android.animation.ValueAnimator;
import android.app.IntentService;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bupt.paragon.learningvolley.databinding.MainBinding;
import com.bupt.paragon.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private SimpleDateFormat mFormat;
    private static final String TAG="learningvolley";
    private VolleySingleton volley;
    private RequestQueue mRequestQueue;
    private String url="http://www.mocky.io/v2/573aeffe0f00009e18dccbe7";
    private String imgurl;
    private User mUser;
    private List<String> list;
    private MainBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=MainBinding.inflate(getLayoutInflater());
        volley=VolleySingleton.getInstance();
        mRequestQueue=volley.getRequestQueue();
        mUser=new User(this);
        getInfo();
        mainBinding.setUser(mUser);
        mainBinding.setFirstName("Paragon");
    }

    private void getInfo(){
        JsonObjectRequest infoRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    imgurl = jsonObject.getString("id");
                    setImage();
                    String nameInfo=jsonObject.getString("first_name");
                    nameInfo=nameInfo==null||nameInfo.length()==0?"It is empty":nameInfo;
                    mUser.setUserName(nameInfo);
//                    mUser.setUserName(nameInfo);
                    Log.e(TAG, "Get Response！");
//                    name.setText(nameInfo);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        infoRequest.setShouldCache(false);
        mRequestQueue.add(infoRequest);
        Log.e(TAG, "send info request!");
    }

    private void setImage(){
        if(imgurl!=null&&imgurl.length()!=0){
            ImageLoader imgLoader=new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String s) {
                    return null;
                }

                @Override
                public void putBitmap(String s, Bitmap bitmap) {

                }
            });
            imgLoader.get(imgurl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
//                    imgView.setImageBitmap(imageContainer.getBitmap());
                    mUser.setIcon(new BitmapDrawable(imageContainer.getBitmap()));
                    Log.e(TAG, "Get Icon!");
//                    mainBinding.setUser(mUser);
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            },100,100);
        }
    }
}
