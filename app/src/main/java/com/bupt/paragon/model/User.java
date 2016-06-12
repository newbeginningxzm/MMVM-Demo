package com.bupt.paragon.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.*;
import android.databinding.tool.expr.BracketExpr;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bupt.paragon.learningvolley.BR;

import java.lang.ref.WeakReference;

/**
 * Created by Paragon on 2016/6/11.
 */
public class User implements Observable{
    private String userName;
    private Drawable icon;
    private boolean mIsFriend;
    private WeakReference<Context> mContext;
    private PropertyChangeRegistry mRegistry;
    public User(Context context){
        mContext=new WeakReference<Context>(context);
        mRegistry=new PropertyChangeRegistry();
    }

    @Bindable
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        if(icon!=null){
            this.icon=icon;
            notifyPropertyChanged(BR.icon);
            Log.e("Users", "Notify Property Changed:Icon!");
        }
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName=userName;
        notifyPropertyChanged(BR.userName);
        Log.e("Users","Notify Property Changed:userName!");
    }

    public boolean isFriend(){
        boolean temp=mIsFriend;
        mIsFriend=!mIsFriend;
        Log.e("Users", "Is Friend:" + mIsFriend);
        return temp;
    }

    public void onClick(View view){
        if(isFriend()){
            onClickFriend();
        }else{
            onClickEnemy();
        }
    }


    @BindingAdapter("")
    public void onClickFriend(){
        Toast.makeText(mContext.get(),"Click Friend!",Toast.LENGTH_SHORT).show();
    }

    public void onClickEnemy(){
        Toast.makeText(mContext.get(),"Click Enemy!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mRegistry.remove(callback);
    }

    private void notifyPropertyChanged(int propertyID){
        mRegistry.notifyChange(this,propertyID);
    }

    public void onClick(User user){
        if(user.isFriend()){
            onClickFriend();
        }else{
            onClickEnemy();
        }
    }

}
