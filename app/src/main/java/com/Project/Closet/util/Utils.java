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
    public String[] color_code;
    Context context;

    public static CropImage.ActivityBuilder CropImageSetting(){

        return CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(60)
                .setInitialCropWindowPaddingRatio(0.05f);
    }

    public void setColorUtil(Context context){
        this.context = context;
        color_name = context.getResources().getStringArray(R.array.Color);
        color_code = context.getResources().getStringArray(R.array.Color_Code);

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

    public static String convertKor(String korean){
        String english="";
        switch(korean){
            case "상의" :
                english = "top";
            case "하의" :
                english = "bottom";
            case "한벌옷" :
                english = "suit";
            case "외투" :
                english = "outer";
            case "신발" :
                english = "shoes";
            case "가방" :
                english = "top";
            case "액세서리" :
                english = "accessory";
        }
        if(!english.isEmpty())
            return english;
        else
            return korean;
    }

    public static String convertEng(String english){
        String korean="";
        switch(english){
            case "top" :
                korean = "상의";
            case "bottom" :
                korean = "하의";
            case "suit" :
                korean = "한벌옷";
            case "outer" :
                korean = "외투";
            case "shoes" :
                korean = "신발";
            case "bag" :
                korean = "가방";
            case "accessory" :
                korean = "액세서리";
        }
        if(!korean.isEmpty())
            return korean;
        else
            return english;
    }
}

