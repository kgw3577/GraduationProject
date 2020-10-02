package com.Project.Closet.codi.recoCodi;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.activity_login;
import com.Project.Closet.codi.addCodi.Page_category;
import com.Project.Closet.codi.fragment_codi;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.util.MySpinnerAdapter;
import com.Project.Closet.util.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import petrov.kristiyan.colorpicker.ColorPicker;
import retrofit2.Call;

public class activity_recoCodi_setting extends AppCompatActivity {

    int RECO_CODI = 255;
    Call<List<ClothesVO>> cloListCall;
    List<ClothesVO> clothesList;


    TextView tv_main_color;
    TextView tv_sub_color;
    CircleImageView civ_main_color;
    CircleImageView civ_sub_color;

    String main_color_name;
    String sub_color_name;

    String top="";
    String bottom="";
    String suit="";
    String outer="";
    String bag="";
    String shoes="";
    String accessory="";
    //
    String top_detail="";
    String bottom_detail="";
    String suit_detail="";
    String outer_detail="";
    String bag_detail="";
    String shoes_detail="";
    String accessory_detail="";


    final int MAIN_COLOR = 0;
    final int SUB_COLOR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_codi_setting);

        TextView header_title = findViewById(R.id.header_title);
        header_title.setText("코디 추천 설정");



        LinearLayout ll_main_color = findViewById(R.id.ll_main_color);
        LinearLayout ll_sub_color = findViewById(R.id.ll_sub_color);
        tv_main_color = findViewById(R.id.tv_main_color);
        tv_sub_color = findViewById(R.id.tv_sub_color);
        civ_main_color = findViewById(R.id.civ_main_color);
        civ_sub_color = findViewById(R.id.civ_sub_color);


        //색 조합 선택
        ll_main_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(MAIN_COLOR);
            }
        });
        ll_sub_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(SUB_COLOR);
            }
        });


        /*필수 포함 카테고리 - 스피너 설정*/
        final Spinner spinner_top = (Spinner)findViewById(R.id.spinner_select_top);
        final Spinner spinner_bottom = (Spinner)findViewById(R.id.spinner_select_bottom);
        final Spinner spinner_suit = (Spinner)findViewById(R.id.spinner_select_suit);
        final Spinner spinner_outer = (Spinner)findViewById(R.id.spinner_select_outer);
        final Spinner spinner_bag = (Spinner)findViewById(R.id.spinner_select_bag);
        final Spinner spinner_shoes = (Spinner)findViewById(R.id.spinner_select_shoes);
        final Spinner spinner_accessory = (Spinner)findViewById(R.id.spinner_select_accessory);
        //
        final Spinner spinner_top_detail = (Spinner)findViewById(R.id.spinner_select_top_detail);
        final Spinner spinner_bottom_detail = (Spinner)findViewById(R.id.spinner_select_bottom_detail);
        final Spinner spinner_suit_detail = (Spinner)findViewById(R.id.spinner_select_suit_detail);
        final Spinner spinner_outer_detail = (Spinner)findViewById(R.id.spinner_select_outer_detail);
        final Spinner spinner_bag_detail = (Spinner)findViewById(R.id.spinner_select_bag_detail);
        final Spinner spinner_shoes_detail = (Spinner)findViewById(R.id.spinner_select_shoes_detail);
        final Spinner spinner_accessory_detail = (Spinner)findViewById(R.id.spinner_select_accessory_detail);

        //상의
        String[] itemArray = getResources().getStringArray(R.array.상의);
        List<String> items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("상의"); // Last item = Hint (제목)
        MySpinnerAdapter adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_top.setAdapter(adapter);
        spinner_top.setSelection(adapter.getCount());
        spinner_top.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_top.getSelectedItemPosition() != spinner_top.getCount()) { //hint 제외

                    if(spinner_top.getSelectedItemPosition()==spinner_top.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_top);
                        spinner_top_detail.setVisibility(View.INVISIBLE);
                        top_detail ="";
                        return;
                    }

                    String selected = (String) spinner_top.getSelectedItem();
                    top = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_top_detail.setAdapter(adapter);
                        spinner_top_detail.setSelection(adapter.getCount());
                        spinner_top_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_top_detail.getSelectedItemPosition() != spinner_top_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_top_detail.getSelectedItem();
                                    top_detail = selected;
                                }else
                                    top_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        spinner_top_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_top_detail.setVisibility(View.INVISIBLE);
                        top_detail ="";
                    }
                }
                else
                    top = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //하의
        itemArray = getResources().getStringArray(R.array.하의);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("하의"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bottom.setAdapter(adapter);
        spinner_bottom.setSelection(adapter.getCount());
        spinner_bottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_bottom.getSelectedItemPosition() != spinner_bottom.getCount()) { //hint 제외

                    if(spinner_bottom.getSelectedItemPosition()==spinner_bottom.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_bottom);
                        spinner_bottom_detail.setVisibility(View.INVISIBLE);
                        bottom_detail ="";
                        return;
                    }

                    String selected = (String) spinner_bottom.getSelectedItem();
                    bottom = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_bottom_detail.setAdapter(adapter);
                        spinner_bottom_detail.setSelection(adapter.getCount());
                        spinner_bottom_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_bottom_detail.getSelectedItemPosition() != spinner_bottom_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_bottom_detail.getSelectedItem();
                                    bottom_detail = selected;
                                }else
                                    bottom_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_bottom_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_bottom_detail.setVisibility(View.INVISIBLE);
                        bottom_detail ="";
                    }
                }else
                    bottom = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //한벌옷
        itemArray = getResources().getStringArray(R.array.한벌옷);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("한벌옷"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_suit.setAdapter(adapter);
        spinner_suit.setSelection(adapter.getCount());
        spinner_suit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_suit.getSelectedItemPosition() != spinner_suit.getCount()) { //hint 제외

                    if(spinner_suit.getSelectedItemPosition()==spinner_suit.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_suit);
                        spinner_suit_detail.setVisibility(View.INVISIBLE);
                        suit_detail ="";
                        return;
                    }

                    String selected = (String) spinner_suit.getSelectedItem();
                    suit = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_suit_detail.setAdapter(adapter);
                        spinner_suit_detail.setSelection(adapter.getCount());
                        spinner_suit_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_suit_detail.getSelectedItemPosition() != spinner_suit_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_suit_detail.getSelectedItem();
                                    suit_detail = selected;
                                }else
                                    suit_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_suit_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_suit_detail.setVisibility(View.INVISIBLE);
                        suit_detail ="";
                    }
                }else
                    suit = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //외투
        itemArray = getResources().getStringArray(R.array.외투);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("외투"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_outer.setAdapter(adapter);
        spinner_outer.setSelection(adapter.getCount());
        spinner_outer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_outer.getSelectedItemPosition() != spinner_outer.getCount()) { //hint 제외

                    if(spinner_outer.getSelectedItemPosition()==spinner_outer.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_outer);
                        spinner_outer_detail.setVisibility(View.INVISIBLE);
                        outer_detail ="";
                        return;
                    }
                    String selected = (String) spinner_outer.getSelectedItem();
                    outer = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_outer_detail.setAdapter(adapter);
                        spinner_outer_detail.setSelection(adapter.getCount());
                        spinner_outer_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_outer_detail.getSelectedItemPosition() != spinner_outer_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_outer_detail.getSelectedItem();
                                    outer_detail = selected;
                                }else
                                    outer_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_outer_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_outer_detail.setVisibility(View.INVISIBLE);
                        outer_detail ="";
                    }
                }else
                    outer = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //신발
        itemArray = getResources().getStringArray(R.array.신발);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("신발"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shoes.setAdapter(adapter);
        spinner_shoes.setSelection(adapter.getCount());
        spinner_shoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_shoes.getSelectedItemPosition() != spinner_shoes.getCount()) { //hint 제외

                    if(spinner_shoes.getSelectedItemPosition()==spinner_shoes.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_shoes);
                        spinner_shoes_detail.setVisibility(View.INVISIBLE);
                        shoes_detail ="";
                        return;
                    }
                    String selected = (String) spinner_shoes.getSelectedItem();
                    shoes = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_shoes_detail.setAdapter(adapter);
                        spinner_shoes_detail.setSelection(adapter.getCount());
                        spinner_shoes_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_shoes_detail.getSelectedItemPosition() != spinner_shoes_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_shoes_detail.getSelectedItem();
                                    shoes_detail = selected;
                                }else
                                    shoes_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_shoes_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_shoes_detail.setVisibility(View.INVISIBLE);
                        shoes_detail ="";
                    }
                }else
                    shoes = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //가방
        itemArray = getResources().getStringArray(R.array.가방);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("가방"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bag.setAdapter(adapter);
        spinner_bag.setSelection(adapter.getCount());
        spinner_bag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_bag.getSelectedItemPosition() != spinner_bag.getCount()) { //hint 제외

                    if(spinner_bag.getSelectedItemPosition()==spinner_bag.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_bag);
                        spinner_bag_detail.setVisibility(View.INVISIBLE);
                        bag_detail ="";
                        return;
                    }

                    String selected = (String) spinner_bag.getSelectedItem();
                    bag = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_bag_detail.setAdapter(adapter);
                        spinner_bag_detail.setSelection(adapter.getCount());
                        spinner_bag_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_bag_detail.getSelectedItemPosition() != spinner_bag_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_bag_detail.getSelectedItem();
                                    bag_detail = selected;
                                }else
                                    bag_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_bag_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_bag_detail.setVisibility(View.INVISIBLE);
                        bag_detail ="";
                    }
                }else
                    bag = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //액세서리
        itemArray = getResources().getStringArray(R.array.액세서리);
        items = new ArrayList<>(Arrays.asList(itemArray));
        items.add("(취소)");
        items.add("액세서리"); // Last item = Hint (제목)
        adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_accessory.setAdapter(adapter);
        spinner_accessory.setSelection(adapter.getCount());
        spinner_accessory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_accessory.getSelectedItemPosition() != spinner_accessory.getCount()) { //hint 제외

                    if(spinner_accessory.getSelectedItemPosition()==spinner_accessory.getCount()-1){ //취소 선택시
                        reset_spinner(spinner_accessory);
                        spinner_accessory_detail.setVisibility(View.INVISIBLE);
                        accessory_detail ="";
                        return;
                    }

                    String selected = (String) spinner_accessory.getSelectedItem();
                    accessory = selected;
                    List<String> items = setDetailCategory(selected);
                    if(!items.isEmpty()){
                        items.add("모두"); // Last item = Hint (제목)
                        MySpinnerAdapter adapter = new MySpinnerAdapter(activity_recoCodi_setting.this, android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_accessory_detail.setAdapter(adapter);
                        spinner_accessory_detail.setSelection(adapter.getCount());
                        spinner_accessory_detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(spinner_accessory_detail.getSelectedItemPosition() != spinner_accessory_detail.getCount()) { //hint 제외
                                    String selected = (String) spinner_accessory_detail.getSelectedItem();
                                    accessory_detail = selected;
                                }else
                                    accessory_detail ="";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        spinner_accessory_detail.setVisibility(View.VISIBLE);
                    }
                    else{
                        spinner_accessory_detail.setVisibility(View.INVISIBLE);
                        accessory_detail ="";
                    }
                }else
                    accessory = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //설정 완료 버튼
        ScalableLayout sl_ok = findViewById(R.id.sl_ok);
        sl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    clothesList = new networkTask().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //여기서 설정대로 수정 후 보내기


                Intent intent = new Intent(activity_recoCodi_setting.this, activity_recommendCodi.class);
                intent.putParcelableArrayListExtra("clothesList",(ArrayList<ClothesVO>) clothesList);
                startActivityForResult(intent, RECO_CODI);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RECO_CODI && resultCode == RESULT_OK){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }





    public class networkTask extends AsyncTask<String, Void, List<ClothesVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected List<ClothesVO> doInBackground(String... params) {
            String userID = MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID();
            ClothesVO clothesFilter = new ClothesVO();
            clothesFilter.setLocation("private");
            cloListCall = ClothesService.getRetrofit(getApplicationContext()).searchClothesNoPage(clothesFilter, userID);

            try {
                return cloListCall.execute().body();
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ClothesVO> clolist) {
            super.onPostExecute(clolist);
        }
    }


    public void openColorPicker(final int numColor) {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        final Utils colorUtil = new Utils();
        colorUtil.setColorUtil(getApplicationContext());

        for(int i=0; i<colorUtil.color_name.length; i++){
            colors.add(colorUtil.mapColors.get(colorUtil.color_name[i]));
        }

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int colorInt) {

                        String colorIntStr = String.format("#%06X", 0xFFFFFF & colorInt); //"#ff0000"
                        String colorName = Utils.getKey(colorUtil.mapColors,colorIntStr);

                        switch(numColor){
                            case MAIN_COLOR:
                                main_color_name = colorName;
                                tv_main_color.setText(colorName);
                                civ_main_color.setColorFilter(Color.parseColor(colorIntStr));
                                break;
                            case SUB_COLOR :
                                sub_color_name = colorName;
                                tv_sub_color.setText(colorName);
                                civ_sub_color.setColorFilter(Color.parseColor(colorIntStr));
                                break;
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }


    List<String> setDetailCategory(String category){

        String[] items;
        switch(category){
            case "반팔티셔츠" :
                items = getResources().getStringArray(R.array.반팔티셔츠);
                break;
            case "긴팔티셔츠" :
                items = getResources().getStringArray(R.array.긴팔티셔츠);
                break;
            case "셔츠" :
                items = getResources().getStringArray(R.array.셔츠);
                break;
            case "니트" :
                items = getResources().getStringArray(R.array.니트);
                break;
            case "후디" :
                items = getResources().getStringArray(R.array.후디);
                break;
            case "스커트" :
                items = getResources().getStringArray(R.array.스커트);
                break;
            case "원피스" :
                items = getResources().getStringArray(R.array.원피스);
                break;
            case "자켓" :
                items = getResources().getStringArray(R.array.자켓);
                break;
            case "점퍼" :
                items = getResources().getStringArray(R.array.점퍼);
                break;
            case "코트" :
                items = getResources().getStringArray(R.array.코트);
                break;
            case "패딩" :
                items = getResources().getStringArray(R.array.패딩);
                break;
            case "모자" :
                items = getResources().getStringArray(R.array.모자);
                break;
            case "상의 기타" :
                items = getResources().getStringArray(R.array.상의_기타);
                break;
            case "하의 기타" :
                items = getResources().getStringArray(R.array.하의_기타);
                break;
            case "한벌옷 기타" :
                items = getResources().getStringArray(R.array.한벌옷_기타);
                break;
            default:
                items = new String[]{};
        }

        if(items.length !=0){
            List<String> itemList = new ArrayList<>(Arrays.asList(items));
            return itemList;
        }else return Collections.emptyList();
    }

    void reset_spinner(Spinner spinner){
        spinner.setSelection(spinner.getCount());
    }

}