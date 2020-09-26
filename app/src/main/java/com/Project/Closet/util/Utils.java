package com.Project.Closet.util;

import android.content.Context;

import com.Project.Closet.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public Map<String, String> mapColors;
    public String[] color_name;
    public String[] color_code;
    Context context;


    public static class Kind {
        //final static int ALL = 0;
        public final static int TOP = 0;
        public final static int BOTTOM = 1;
        public final static int SUIT = 2;
        public final static int OUTER = 3;
        public final static int SHOES = 4;
        public final static int BAG = 5;
        public final static int ACCESSORY = 6;
    }


    static int [][] score_rule= new int[][]{
        {50,10,	50,	40,	40,	40,	20,	20,	20,	20,	40,	40,	40,	40,	20,	50,	20,	20,	50},
        {50	,10	,30	,50	,50	,40	,40	,40	,20	,50	,50	,50	,40	,40	,50	,50	,50	,20	,40},
        {50	,30	,20	,20	,20	,20	,20	,20	,50	,10	,20	,50	,0	,0	,20	,20	,50	,20	,40},
        {50	,50	,20	,50	,50	,40	,50	,50	,20	,20	,20	,20	,40	,40	,20	,20	,20	,20	,20},
        {50	,50	,50	,50	,50	,40	,20	,20	,20	,50	,40	,20	,40	,40	,20	,20	,50	,20	,20},
        {40	,50	,20	,40	,40	,20	,20	,20	,20	,50	,40	,10	,20	,20	,20	,20	,20	,20	,20},
        {30	,30	,20	,20	,20	,20	,10	,40	,50	,10	,10	,50	,0	,0	,20	,20	,20	,20	,20},
        {50	,30	,20	,10	,10	,20	,40	,10	,50	,10	,10	,50	,0	,0	,20	,20	,20	,20	,20},
        {0	,20	,50	,20	,20	,20	,50	,50	,20	,20	,20	,50	,50	,50	,20	,20	,20	,20	,20},
        {40	,50	,50	,40	,40	,50	,40	,40	,50	,40	,50	,50	,0	,0	,50	,20	,50	,20	,20},
        {40	,50	,50	,40	,40	,50	,40	,40	,50	,10	,20	,10	,0	,0	,50	,20	,50	,20	,20},
        {30	,50	,50	,40	,40	,50	,50	,50	,50	,40	,40	,10	,40	,40	,50	,20	,50	,20	,20},
        {30	,20	,20	,20	,20	,10	,20	,20	,50	,20	,40	,50	,10	,10	,20	,20	,50	,20	,20},
        {50	,20	,20	,40	,40	,10	,20	,20	,50	,20	,40	,50	,10	,10	,20	,20	,50	,20	,20},
        {40	,10	,20	,40	,40	,20	,40	,40	,20	,40	,40	,40	,20	,20	,20	,20	,20	,20	,20},
        {40	,50	,20	,20	,20	,20	,20	,20	,20	,20	,40	,40	,10	,10	,20	,20	,20	,20	,20},
        {20	,30	,50	,50	,50	,30	,20	,20	,20	,50	,50	,50	,50	,50	,20	,20	,20	,20	,20},
        {40	,30	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,10},
        {50	,40	,40	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,20	,10	,40}
    };

    public static class Color {
        public final static int BLACK = 0;
        public final static int WHITE = 1;
        public final static int GRAY = 2;
        public final static int IVORY = 3;
        public final static int BEIGE = 4;
        public final static int PINK = 5;
        public final static int RED = 6;
        public final static int WINE = 7;
        public final static int PURPLE = 8;
        public final static int SKY_BLUE = 9;
        public final static int BLUE = 10;
        public final static int NAVY = 11;
        public final static int GREEN = 12;
        public final static int OLIVE_GREEN = 13;
        public final static int YELLOW = 14;
        public final static int ORANGE = 15;
        public final static int BROWN = 16;
        public final static int GOLD = 17;
        public final static int SILVER = 18;


        public final static List<Integer> achromatic_colors = Arrays.asList(BLACK,WHITE,GRAY); //무채색 계열

        public final static List<Integer> soft_colors = Arrays.asList(IVORY,BEIGE,BROWN,GOLD); //아이보리 계열
        public final static List<Integer> pink_colors = Arrays.asList(PINK,PURPLE); //핑크 계열
        public final static List<Integer> red_colors = Arrays.asList(RED,WINE); //레드 계열
        public final static List<Integer> blue_colors = Arrays.asList(SKY_BLUE,BLUE,NAVY); //블루 계열
        public final static List<Integer> green_colors = Arrays.asList(GREEN,OLIVE_GREEN); //그린 계열
        public final static List<Integer> yellow_colors = Arrays.asList(YELLOW,ORANGE); //옐로우 계열
        public final static List<Integer> black_colors = Arrays.asList(BLACK,WHITE,GRAY); //블랙 계열

    }

    public static final HashMap<String,Integer> colorMap = new HashMap<>();
    static {
        colorMap.put("블랙", Color.BLACK);
        colorMap.put("화이트",Color.WHITE);
        colorMap.put("그레이",Color.GRAY);
        colorMap.put("아이보리",Color.IVORY);
        colorMap.put("베이지",Color.BEIGE);
        colorMap.put("핑크",Color.PINK);
        colorMap.put("레드",Color.RED);
        colorMap.put("와인",Color.WINE);
        colorMap.put("퍼플",Color.PURPLE);
        colorMap.put("스카이블루",Color.SKY_BLUE);
        colorMap.put("블루",Color.BLUE);
        colorMap.put("네이비",Color.NAVY);
        colorMap.put("그린",Color.GREEN);
        colorMap.put("올리브그린",Color.OLIVE_GREEN);
        colorMap.put("옐로우",Color.YELLOW);
        colorMap.put("오렌지",Color.ORANGE);
        colorMap.put("브라운",Color.BROWN);
        colorMap.put("골드",Color.GOLD);
        colorMap.put("실버",Color.SILVER);
    }



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

