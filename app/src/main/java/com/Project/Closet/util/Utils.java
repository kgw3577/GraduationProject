package com.Project.Closet.util;

import android.content.Context;

import com.Project.Closet.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public Map<String, String> mapColors;
    public String[] color_name;
    Context context;



    public static CropImage.ActivityBuilder CropImageSetting(){

        return CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMaxCropResultSize(40000,99999)
                .setMinCropResultSize(200,40)
                .setMinCropWindowSize(200,42)
                .setOutputCompressQuality(60)
                .setMaxZoom(3);
    }

    public void setColorUtil(Context context){
        this.context = context;
        color_name = context.getResources().getStringArray(R.array.Color);
        final String[] color_code = context.getResources().getStringArray(R.array.Color_Code);

        mapColors = new HashMap<>();

        for(int i=0; i<color_name.length; i++){
            mapColors.put(color_name[i], color_code[i]);
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        // 찾을 hashmap 과 주어진 단서 value
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
}

