package com.Project.Closet.util;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class PermissionRequest{

    public interface BeforeRequest{
        void call(Set<String> permissions, BeforeResolver resolver);
    }
    static public class BeforeResolver{
        final private PermissionRequest checker;
        BeforeResolver(PermissionRequest c){checker = c;}
        public void ok(){checker.ok();}
    }

    static final private Map<Integer, PermissionRequest> instances = new HashMap<>();
    static void before(int code){
        if(!instances.containsKey(code)) return;
        instances.get(code).request(code);
    }
    static void beforeRemove(int code){
        if(instances.containsKey(code)) instances.remove(code);
    }

    static public PermissionRequest builder(@NonNull AppCompatActivity act){
        return builder(act, false);
    }
    static public PermissionRequest builder(@NonNull AppCompatActivity act, boolean isClear){
        if(isClear && instances.containsKey(code)) instances.remove(code);
        return new PermissionRequest(act);
    }

    final private AppCompatActivity act;
    final private Set<String> permissions = new ConcurrentSkipListSet<>();
    private BeforeRequest beforeRequest;
    private static int code;

    PermissionRequest(@NonNull AppCompatActivity a){act = a;}
    public PermissionRequest permissions(String...ps){
        for(String p:ps) permissions.add(p);
        return this;
    }
    public PermissionRequest beforeRequest(BeforeRequest before){
        beforeRequest = before;
        return this;
    }
    public void request(){request(0);}
    public void request(int c){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        for(String p : permissions){
            if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(act, p)){
                permissions.remove(p);
            }
        }
        if(permissions.size() == 0) return;
        code = c;
        if(beforeRequest != null) beforeRequest.call(permissions, new BeforeResolver(this));
        else ok();
    }
    void ok(){
        instances.put(code, this);
        ActivityCompat.requestPermissions(act, permissions.toArray(new String[0]), code);
    }
}
