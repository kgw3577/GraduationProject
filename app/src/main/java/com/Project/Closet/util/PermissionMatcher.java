package com.Project.Closet.util;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PermissionMatcher{
    public static PermissionMatcher match(AppCompatActivity act, int code, String[] permissions, int[] grantResults){
        return new PermissionMatcher(act, code, permissions, grantResults);
    }

    public interface MatcherConsumer<T>{
        void accept(T v);
    }
    final private AppCompatActivity act;
    final private int code;
    final private Map<String, Boolean> permissions = new HashMap<>(), matchers;
    final private Set<String> denied = new HashSet<>(), never = new HashSet<>();
    private int noGranted = 0;
    PermissionMatcher(AppCompatActivity a, int c, String[] p, int[] r){
        act = a;
        code = c;
        for(int i = 0; i < p.length; i++){
            String key = p[i];
            boolean isGranted = r[i] == PackageManager.PERMISSION_GRANTED;
            permissions.put(key, isGranted);
            if(!isGranted){
                noGranted++;
                if(ActivityCompat.shouldShowRequestPermissionRationale(act, key)) denied.add(key);
                else never.add(key);
            }
        }
        matchers = new HashMap<>(permissions);
        if(noGranted == 0) PermissionRequest.beforeRemove(c);
    }
    public PermissionMatcher is(@NonNull String permission, @NonNull MatcherConsumer<Boolean> f){
        if(matchers.containsKey(permission)){
            f.accept(matchers.get(permission));
            matchers.remove(permission);
        }
        return this;
    }
    public PermissionMatcher rest(@NonNull MatcherConsumer<Map<String, Boolean>> f){
        if(matchers.size() > 0) f.accept(matchers);
        return this;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public PermissionMatcher denied(@NonNull Function<Set<String>, Boolean> f){
        if(denied.size() > 0 && f.apply(denied)) PermissionRequest.before(code);
        return this;
    }
    public PermissionMatcher never(@NonNull MatcherConsumer<Set<String>> f){
        if(never.size() > 0) f.accept(never);
        return this;
    }
}
