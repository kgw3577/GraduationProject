package com.Project.Closet.codi.recoCodi;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.weather.vo.ApiInterface;
import com.Project.Closet.HTTP.weather.vo.Repo;
import com.Project.Closet.R;
import com.Project.Closet.closet.closet_activities.activity_closet_DB;
import com.Project.Closet.codi.weather.PermissionActivity;
import com.Project.Closet.util.MySpinnerAdapter;
import com.Project.Closet.util.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import petrov.kristiyan.colorpicker.ColorPicker;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_recoCodi_setting extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "setting";
    int RECO_CODI = 255;
    Call<List<ClothesVO>> cloListCall;
    List<ClothesVO> clothesList;

    ScrollView scrollView;

    List<CheckBox> checkBoxes;
    CheckBox cb_top;
    CheckBox cb_bottom;
    CheckBox cb_suit;
    CheckBox cb_outer;
    CheckBox cb_shoes;
    CheckBox cb_bag;
    CheckBox cb_accessory;

    RadioButton rb_man;


    TextView tv_main_color;
    TextView tv_sub_color;
    CircleImageView civ_main_color;
    CircleImageView civ_sub_color;

    int main_color_num=-1;
    int sub_color_num=-1;

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
    //
    List<String> categories;
    List<String> detail_categories;




    private static final int FROM_CLOSET = 1009;
    final int MAIN_COLOR = 0;
    final int SUB_COLOR = 1;

    SlidingDrawer slidingDrawer;
    LinearLayout drawer_content;

    //TextView tv_add_image;
    TextView tv_from_closet;
    TextView tv_cancel;

    ArrayList<ImageView> list_childClothes;
    ArrayList<TextView> list_tv_childClothes;
    int[] child_clothes_no;
    ArrayList<Integer> index_resourceID;
    int selected_clo_index;


    ImageView child1;
    ImageView child2;
    ImageView child3;
    ImageView child4;
    ImageView child5;
    ImageView child6;
    ImageView child7;
    //
    TextView tv_child1;
    TextView tv_child2;
    TextView tv_child3;
    TextView tv_child4;
    TextView tv_child5;
    TextView tv_child6;
    TextView tv_child7;


    //날씨 관련
    boolean weatherApplied;
    final double TEMPER_NULL = 10000;
    double temperature = TEMPER_NULL;
    final static String TEMPER_CODE = "℃";
    String[] recommendedDCate;

    RadioButton rb_weather_none;
    RadioButton rb_weather_now;
    RadioButton rb_weather_temper;
    RadioButton rb_weather_season;

    LinearLayout ll_now_weather;
    TextView tv_now_location;
    TextView tv_now_temperature;
    FlowLayout fl_setting_temperature;
    FlowLayout fl_setting_season;


    CheckBox temper0;
    CheckBox temper1;
    CheckBox temper2;
    CheckBox temper3;
    CheckBox temper4;
    CheckBox temper5;
    CheckBox temper6;
    CheckBox temper7;
    List<CheckBox> weatherCheckBoxes;


    private LocationManager lm;
    Location my_location;
    private static final int REQUEST_PERMISSION = 1024;
    String API_KEY = "f73fa03d36a8a1b6c8acdca0ea6d229a";
    public Address addr;
    boolean isNowApplied;

    double longitude; //경도
    double latitude; //위도





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_codi_setting);


        TextView header_title = findViewById(R.id.header_title);
        header_title.setText("코디 추천 설정");

        scrollView = findViewById(R.id.scrollView);

        //체크박스 관련
        cb_top = findViewById(R.id.cb_top_bottom);
        cb_bottom = cb_top;
        cb_suit = findViewById(R.id.cb_suit);
        cb_outer = findViewById(R.id.cb_outer);
        cb_bag = findViewById(R.id.cb_bag);
        cb_shoes = findViewById(R.id.cb_shoes);
        cb_accessory = findViewById(R.id.cb_accessory);
        checkBoxes = new ArrayList<>();
        checkBoxes.add(cb_top);
        checkBoxes.add(cb_bottom);
        checkBoxes.add(cb_suit);
        checkBoxes.add(cb_outer);
        checkBoxes.add(cb_shoes);
        checkBoxes.add(cb_bag);
        checkBoxes.add(cb_accessory);

        //컬러 관련 변수
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

        rb_man = findViewById(R.id.radioButton3);


        /*포함 카테고리 - 스피너 설정*/

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
                    Log.d(TAG, "onItemSelected: "+top);
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
                                    top_detail = (String) spinner_top_detail.getSelectedItem();
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
                else{
                    top = "";
                    Log.d(TAG, "onItemSelected: "+top);
                }
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
                                    bottom_detail = (String) spinner_bottom_detail.getSelectedItem();
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


        /*포함 옷 관련*/

        //드로워 관련
        slidingDrawer = findViewById(R.id.sliding_drawer);
        drawer_content = findViewById(R.id.drawer_content);

        tv_from_closet= findViewById(R.id.tv_from_closet);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_from_closet.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        drawer_content.setOnClickListener(this);

        //옷 관련
        child1 = findViewById(R.id.child1);
        child2 = findViewById(R.id.child2);
        child3 = findViewById(R.id.child3);
        child4 = findViewById(R.id.child4);
        child5 = findViewById(R.id.child5);
        child6 = findViewById(R.id.child6);
        child7 = findViewById(R.id.child7);
        tv_child1 = findViewById(R.id.tv_child1);
        tv_child2 = findViewById(R.id.tv_child2);
        tv_child3 = findViewById(R.id.tv_child3);
        tv_child4 = findViewById(R.id.tv_child4);
        tv_child5 = findViewById(R.id.tv_child5);
        tv_child6 = findViewById(R.id.tv_child6);
        tv_child7 = findViewById(R.id.tv_child7);

        list_childClothes = new ArrayList<ImageView>(Arrays.asList(child1, child2, child3, child4, child5, child6, child7));
        list_tv_childClothes = new ArrayList<TextView>(Arrays.asList(tv_child1, tv_child2, tv_child3, tv_child4, tv_child5, tv_child6, tv_child7));
        child_clothes_no =new int[7]; //옷 no 저장
        index_resourceID = new ArrayList<Integer>(Arrays.asList(R.id.child1, R.id.child2, R.id.child3, R.id.child4, R.id.child5, R.id.child6, R.id.child7));

        for(ImageView v : list_childClothes){
            v.setOnClickListener(this);
        }

        //날씨 관련
        rb_weather_none = findViewById(R.id.rb_weather_none);
        rb_weather_now = findViewById(R.id.rb_weather_now);
        rb_weather_temper = findViewById(R.id.rb_weather_temper);
        rb_weather_season = findViewById(R.id.rb_weather_season);
        rb_weather_none.setOnClickListener(this);
        rb_weather_now.setOnClickListener(this);
        rb_weather_temper.setOnClickListener(this);
        rb_weather_season.setOnClickListener(this);

        ll_now_weather = findViewById(R.id.ll_now_weather);
        tv_now_location = findViewById(R.id.tv_now_location);
        tv_now_temperature = findViewById(R.id.tv_now_temperature);
        fl_setting_temperature = findViewById(R.id.fl_setting_temperature);
        fl_setting_season = findViewById(R.id.fl_setting_season);







        //설정 완료 버튼
        ScalableLayout sl_ok = findViewById(R.id.sl_ok);
        sl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //날씨 설정
                if(!applyWeather())
                    return;


                try {
                    clothesList = new networkTask().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(clothesList==null)
                    return;

                //선택되지 않은 파트(코디 구성) 제거
                deactivateParts();

                categories = new ArrayList<>(Arrays.asList(top,bottom,suit,outer,shoes,bag,accessory));
                for(int i=0; i<7; i++){
                    if(!categories.get(i).isEmpty() && child_clothes_no[i]!=0){
                        String kind = Utils.getKey(Utils.Kind.kindNumMap,i);
                        Toast.makeText(activity_recoCodi_setting.this, "<"+kind+">"+" 파트의 '이 카테고리 포함'과 '이 옷 포함' 설정이 중복됩니다." +
                                "\n파트 당 하나만 선택해 주세요.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //특정 카테고리 포함
                if(!restrictCategory())
                    return;

                //특정 옷 포함
                if(!restrictClothes())
                    return;

                //성별 설정
                setGender();


                Intent intent = new Intent(activity_recoCodi_setting.this, activity_recommendCodi.class);
                intent.putParcelableArrayListExtra("clothesList",(ArrayList<ClothesVO>) clothesList);
                intent.putExtra("main_color", main_color_num);  //배색 조합 정보 보내기(default 값:-1)
                intent.putExtra("sub_color", sub_color_num);
                intent.putExtra("temperature", temperature);
                startActivityForResult(intent, RECO_CODI);
            }
        });
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
            try {
                if(!weatherApplied){
                    ClothesVO clothesFilter = new ClothesVO();
                    clothesFilter.setLocation("private");
                    cloListCall = ClothesService.getRetrofit(getApplicationContext()).searchClothesNoPage(clothesFilter, userID);
                }
                else{
                    if(recommendedDCate==null){
                        Toast.makeText(activity_recoCodi_setting.this, "날씨 설정 과정에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    HashMap map = new HashMap();
                    map.put("location","private");
                    map.put("list",recommendedDCate);
                    cloListCall = ClothesService.getRetrofit(getApplicationContext()).searchClothesByListNoPage(
                            map, userID, "detailCategory");
                }
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



    private void scrollDown(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
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
                        int colorNum = Utils.colorNumMap.get(colorName);

                        switch(numColor){
                            case MAIN_COLOR:
                                main_color_num = colorNum;
                                tv_main_color.setText(colorName);
                                civ_main_color.setColorFilter(Color.parseColor(colorIntStr));
                                break;
                            case SUB_COLOR :
                                sub_color_num = colorNum;
                                tv_sub_color.setText(colorName);
                                civ_sub_color.setColorFilter(Color.parseColor(colorIntStr));
                                break;
                        }
                    }

                    @Override
                    public void onCancel() {
                        switch(numColor){
                            case MAIN_COLOR:
                                main_color_num = -1;
                                tv_main_color.setText("미설정");
                                civ_main_color.setColorFilter(Color.parseColor("#dddddd"));
                                break;
                            case SUB_COLOR :
                                sub_color_num = -1;
                                tv_sub_color.setText("미설정");
                                civ_sub_color.setColorFilter(Color.parseColor("#dddddd"));
                                break;
                        }
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

    void deactivateParts(){

        for(int kindNum=0; kindNum<7; kindNum++){
            if(!checkBoxes.get(kindNum).isChecked()){ //체크되지 않은 파트
                String Kind = Utils.getKey(Utils.Kind.kindNumMap,kindNum); //이름을 찾아서
                Iterator<ClothesVO> iter = clothesList.iterator();
                while (iter.hasNext()) { //제거
                    ClothesVO clothes = iter.next();
                    if(Kind.equals(clothes.getKind()))
                        iter.remove();
                }
//
//                for(ClothesVO clothes : clothesList){ //제거
//                    if(Kind.equals(clothes.getKind()))
//                        clothesList.remove(clothes);
//                }
            }
        }
    }

    boolean restrictCategory(){

        //categories = new ArrayList<>(Arrays.asList(top,bottom,suit,outer,shoes,bag,accessory));


        if((!top.isEmpty()||!bottom.isEmpty())&&!suit.isEmpty()){
            Toast.makeText(this, "상의/하의와 한벌옷은 동시에 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }


            detail_categories = new ArrayList<>(Arrays.asList(top_detail,bottom_detail,suit_detail,
                outer_detail,shoes_detail,bag_detail,accessory_detail));

        for (int kindNum=0; kindNum<7; kindNum++){
            String category = categories.get(kindNum); //해당 종류(ex.상의)에 설정된 카테고리명 받아옴
            String kind = Utils.getKey(Utils.Kind.kindNumMap,kindNum); //종류명 받아옴

            if(!category.isEmpty()){ //카테고리가 설정 되어있다면
                String detail_category = detail_categories.get(kindNum); //디테일 카테고리 받아옴
                Iterator<ClothesVO> cloListIter = clothesList.iterator();
                int remain_items=0;

                if(detail_category.isEmpty()){ //카테고리만 설정되어 있다면
                    while (cloListIter.hasNext()) {
                        ClothesVO clothes = cloListIter.next();
                        String cloKind = clothes.getKind();
                        if(cloKind.equals(kind)){
                            if(!clothes.getCategory().equals(category))
                                cloListIter.remove(); //해당 종류에 해당 카테고리가 아닌 옷들을 제거
                            else
                                remain_items+=1; //제거되지 않은 옷
                        }

                        if(kindNum == Utils.Kind.TOP || kindNum == Utils.Kind.BOTTOM){
                            if("한벌옷".equals(cloKind))
                                cloListIter.remove();
                        }else if(kindNum == Utils.Kind.SUIT){
                            if("상의".equals(cloKind) || "하의".equals(cloKind))
                                cloListIter.remove(); //제거
                        }
                    }
                }else{ //디테일 카테고리도 설정되어 있다면
                    while (cloListIter.hasNext()) {
                        ClothesVO clothes = cloListIter.next();
                        String cloKind = clothes.getKind();
                        if(cloKind.equals(kind)){
                            if(!clothes.getDetailCategory().equals(detail_category))
                                cloListIter.remove();//해당 종류에 해당 세부 카테고리가 아닌 옷들을 제거
                            else
                                remain_items+=1; //제거되지 않은 옷
                        }

                        if(kindNum == Utils.Kind.TOP || kindNum == Utils.Kind.BOTTOM){
                            if("한벌옷".equals(cloKind))
                                cloListIter.remove();
                        }else if(kindNum == Utils.Kind.SUIT){
                            if("상의".equals(cloKind) || "하의".equals(cloKind))
                                cloListIter.remove(); //제거
                        }
                    }
                }

                if(remain_items==0){
                    if(!detail_category.isEmpty()){
                        category = detail_category;
                    }

//                    if(recommendedDCate!=null ){
//                        List<String>recommendedDCateArray = Arrays.asList(recommendedDCate);
//                        if(!recommendedDCateArray.contains(category)){
//                            Toast.makeText(this, "해당 날씨에 맞지 않는 <"+category+"> 카테고리가 설정에 포함되어 있습니다.", Toast.LENGTH_LONG).show();
//                            return false;
//                        }
//                    }

                    Toast.makeText(this, "설정한 <"+category+"> 카테고리의 옷이 없거나 해당 날씨에 맞지 않습니다. \n더 많은 옷을 추가해보세요.", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }

        return true;
    }

    boolean restrictClothes(){

        if((child_clothes_no[Utils.Kind.TOP]!=0 || child_clothes_no[Utils.Kind.BOTTOM]!=0)
                && child_clothes_no[Utils.Kind.SUIT]!=0){
            Toast.makeText(this, "상의/하의와 한벌옷은 동시에 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int kindNum=0; kindNum<7; kindNum++){
            int cloNo=child_clothes_no[kindNum];
            String kind = Utils.getKey(Utils.Kind.kindNumMap, kindNum);
            int remain_item=0;
            if(cloNo!=0){
                Iterator<ClothesVO> cloListIter = clothesList.iterator();
                while (cloListIter.hasNext()) {
                    ClothesVO clothes = cloListIter.next();
                    String cloKind = clothes.getKind();
                    if(cloKind.equals(kind)){
                        if(clothes.getCloNo()!=cloNo)
                            cloListIter.remove();
                        else
                            remain_item+=1; //제거되지 않은 옷
                    }
                    if(kindNum == Utils.Kind.TOP || kindNum == Utils.Kind.BOTTOM){
                        if("한벌옷".equals(cloKind))
                            cloListIter.remove();
                    }else if(kindNum == Utils.Kind.SUIT){
                        if("상의".equals(cloKind) || "하의".equals(cloKind))
                            cloListIter.remove(); //제거
                    }
                }
                if(remain_item==0){
                    Toast.makeText(this, "설정에 중복된 요소가 존재합니다.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Integer resourceID = v.getId();
        switch(v.getId()){
            case R.id.child1 :
            case R.id.child2 :
            case R.id.child3 :
            case R.id.child4 :
            case R.id.child5 :
            case R.id.child6 :
            case R.id.child7 :
                selected_clo_index= index_resourceID.indexOf(resourceID);
                slidingDrawer.open();
                break;
            case R.id.drawer_content :
                slidingDrawer.close();
                break;
            case R.id.tv_from_closet :
                //share와 똑같이. mode 만들고 result 받아오기
                intent = new Intent(this, activity_closet_DB.class);
                intent.putExtra("mode","select_my");
                intent.putExtra("selected_kindNum_str",Integer.toString(selected_clo_index));
                startActivityForResult(intent, FROM_CLOSET);
                slidingDrawer.close();
                break;
            case R.id.tv_cancel :
                resetCurrentItem();
                break;
            case R.id.rb_weather_none :
                setWeatherNone();
                break;
            case R.id.rb_weather_now :
                setNowTemper();
                break;
            case R.id.rb_weather_temper :
                ll_now_weather.setVisibility(View.GONE);
                fl_setting_temperature.setVisibility(View.VISIBLE);
                fl_setting_season.setVisibility(View.GONE);
                scrollDown();
                break;
            case R.id.rb_weather_season :
                ll_now_weather.setVisibility(View.GONE);
                fl_setting_temperature.setVisibility(View.GONE);
                fl_setting_season.setVisibility(View.VISIBLE);
                scrollDown();
                break;
        }
    }

    void resetCurrentItem(){
        //이미지 리셋
        list_childClothes.get(selected_clo_index).setImageResource(R.drawable.hanger_gray_small);
        //옷 번호 리셋
        child_clothes_no[selected_clo_index] = 0;
        //텍스트 보이기
        list_tv_childClothes.get(selected_clo_index).setVisibility(View.VISIBLE);
        slidingDrawer.close();
    }

    void setGender(){
        if (rb_man.isChecked()){
            Iterator<ClothesVO> cloListIter = clothesList.iterator();
            while (cloListIter.hasNext()) {
                ClothesVO clothes = cloListIter.next();
                String category = clothes.getCategory();
                if("스커트".equals(category)){
                    cloListIter.remove();
                }
                else if("원피스".equals(category)){
                    cloListIter.remove();
                }
            }
        }
    }

    void setWeatherNone(){
        ll_now_weather.setVisibility(View.GONE);
        fl_setting_temperature.setVisibility(View.GONE);
        fl_setting_season.setVisibility(View.GONE);
    }

    void setNowTemper() {
        startFindLocation();
        rb_weather_now.setChecked(true);
        ll_now_weather.setVisibility(View.VISIBLE);
        fl_setting_temperature.setVisibility(View.GONE);
        fl_setting_season.setVisibility(View.GONE);
        scrollDown();
    }

    boolean applyWeather(){
        temperature = TEMPER_NULL;
        recommendedDCate=null;
        weatherApplied=false;

        if(rb_weather_none.isChecked()){ //적용 안함
            return true; //적용 안함 시 스킵
        }else if(rb_weather_now.isChecked()){ //현재 기온

            if(!isNowApplied){
                Toast.makeText(this, "사용자의 위치를 수신하지 못했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }

            //설정된 기온을 읽어들임
            String temperStr = tv_now_temperature.getText().toString();
            if(!temperStr.isEmpty()){
                int idx = temperStr.indexOf(TEMPER_CODE); //℃
                temperStr = temperStr.substring(0, idx);
                temperature = Double.parseDouble(temperStr);
            }
            else{
                Toast.makeText(this, "기온이 바르게 설정되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            //기온 별 옷차림 목록 받아옴
            if(temperature!=TEMPER_NULL){ //사용자가 온도 설정시
                if(temperature<6)
                    recommendedDCate = getResources().getStringArray(R.array.to5);
                else if(temperature>=6 && temperature<10)
                    recommendedDCate = getResources().getStringArray(R.array.fr6to9);
                else if(temperature>=10 && temperature<12)
                    recommendedDCate = getResources().getStringArray(R.array.fr10to11);
                else if(temperature>=12 && temperature<17)
                    recommendedDCate = getResources().getStringArray(R.array.fr12to16);
                else if(temperature>=17 && temperature<19)
                    recommendedDCate = getResources().getStringArray(R.array.fr17to19);
                else if(temperature>=19 && temperature<22)
                    recommendedDCate = getResources().getStringArray(R.array.fr20to22);
                else if(temperature>=23 && temperature<26)
                    recommendedDCate = getResources().getStringArray(R.array.fr23to26);
                else if(temperature>=27)
                    recommendedDCate = getResources().getStringArray(R.array.fr27);
            }
        }else{ //기온 or 날씨 설정

            temper0= findViewById(R.id.temper0);
            temper1= findViewById(R.id.temper1);
            temper2= findViewById(R.id.temper2);
            temper3= findViewById(R.id.temper3);
            temper4= findViewById(R.id.temper4);
            temper5= findViewById(R.id.temper5);
            temper6= findViewById(R.id.temper6);
            temper7= findViewById(R.id.temper7);

            weatherCheckBoxes= new ArrayList<>();
            weatherCheckBoxes.add(temper0);
            weatherCheckBoxes.add(temper1);
            weatherCheckBoxes.add(temper2);
            weatherCheckBoxes.add(temper3);
            weatherCheckBoxes.add(temper4);
            weatherCheckBoxes.add(temper5);
            weatherCheckBoxes.add(temper6);
            weatherCheckBoxes.add(temper7);


            if(rb_weather_temper.isChecked()){ //기온 설정
                if(!loadCheckedTemperature())
                    return false;
            }else if(rb_weather_season.isChecked()){ //계절 설정
                if(!checkSeason()) //계절별로 기온 체크
                    return false;
                if(!loadCheckedTemperature()) //체크된 기온으로부터 옷차림 불러오기
                    return false;
            }
        }

        if(recommendedDCate!=null){
            weatherApplied=true;
            return true;
        }
        else{
            Toast.makeText(this, "기온별 옷차림 적용에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean loadCheckedTemperature(){

        HashSet<String> stringHashSet = new HashSet<>();
        int checkedSize=0;
        int checkedTemper=10000;
        for(int temperNum=0; temperNum<8; temperNum++) {
            if (weatherCheckBoxes.get(temperNum).isChecked()){
                checkedSize+=1;
                switch(temperNum){
                    case 0:
                        checkedTemper=5;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.to5)));
                        break;
                    case 1:
                        checkedTemper=6;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr6to9)));
                        break;
                    case 2:
                        checkedTemper=10;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr10to11)));
                        break;
                    case 3:
                        checkedTemper=12;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr12to16)));
                        break;
                    case 4:
                        checkedTemper=17;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr17to19)));
                        break;
                    case 5:
                        checkedTemper=20;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr20to22)));
                        break;
                    case 6:
                        checkedTemper=23;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr23to26)));
                        break;
                    case 7:
                        checkedTemper=27;
                        stringHashSet.addAll(Arrays.asList(getResources().getStringArray(R.array.fr27)));
                        break;
                }
            }
        }

        if(checkedSize==0){
            Toast.makeText(this, "적어도 하나의 기온을 선택해야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(checkedSize==1){
            temperature = checkedTemper;
        }

        if(stringHashSet.size()!=0)
            recommendedDCate = (String[])stringHashSet.toArray(new String[0]);

        if(recommendedDCate!=null && recommendedDCate.length!=0)
            return true;
        else{
            Toast.makeText(this, "기온별 옷차림을 불러올 수 없었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean checkSeason(){
        RadioButton spring= findViewById(R.id.spring);
        RadioButton summer= findViewById(R.id.summer);
        RadioButton fall= findViewById(R.id.fall);
        RadioButton winter= findViewById(R.id.winter);
        List<RadioButton> seasonRadioButtons= new ArrayList<>();
        seasonRadioButtons.add(spring);
        seasonRadioButtons.add(summer);
        seasonRadioButtons.add(fall);
        seasonRadioButtons.add(winter);

        int checkedSize=0;
        for(int seasonNum=0; seasonNum<4; seasonNum++) {
            if (seasonRadioButtons.get(seasonNum).isChecked()){
                checkedSize+=1;
                switch(seasonNum){
                    case 0:
                        setTemperCheckedTrue(new int[]{2,3,4});
                        setTemperCheckedFalse(new int[]{0,1,5,6,7});
                        break;
                    case 1:
                        setTemperCheckedTrue(new int[]{5,6,7});
                        setTemperCheckedFalse(new int[]{0,1,2,3,4});
                        break;
                    case 2:
                        setTemperCheckedTrue(new int[]{1,2,3});
                        setTemperCheckedFalse(new int[]{0,4,5,6,7});
                        break;
                    case 3:
                        setTemperCheckedTrue(new int[]{0, 1});
                        setTemperCheckedFalse(new int[]{2,3,4,5,6,7});
                        break;
                }
            }
        }

        if(checkedSize==0){
            Toast.makeText(this, "적어도 하나의 계절을 선택해야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    void setTemperCheckedTrue(int[] indexes){
        for (int index : indexes){
            CheckBox cb =weatherCheckBoxes.get(index);
            cb.setChecked(true);
        }
    }

    void setTemperCheckedFalse(int[] indexes){
        for (int index : indexes){
            CheckBox cb =weatherCheckBoxes.get(index);
            cb.setChecked(false);
        }
    }


    /**
     * 사용자의 위치를 수신
     */
    private void startFindLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, PermissionActivity.class);
            startActivityForResult(intent,REQUEST_PERMISSION);
        }
        else {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            assert lm != null;
            lm.removeUpdates( mLocationListener );
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    10, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    10, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);

        }
    }

    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            my_location=location;
            lm.removeUpdates(this);  //위치 정보 수신 끄기
            longitude = location.getLongitude(); //경도
            latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            Log.d("test", "위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy);

            if(latitude==0 || longitude == 0){
                Toast.makeText(activity_recoCodi_setting.this, "받아온 위치 정보에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                getAddress(getBaseContext(), latitude, longitude);//나라 도 시 구 동 번지

                //String do_ = addr.getAdminArea(); //도
                String si = addr.getLocality(); //시
                String gu = addr.getThoroughfare(); //구
                String gu_str=getCompleteWord(gu,"은","는");

                tv_now_location.setText("지금 "+si + " " + gu_str+" : "); //지금 시흥시 정왕동은 :

                try {
                    temperature = new weatherTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, latitude, longitude).get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int temperInt = (int) Math.round(temperature); //소수점 반올림
                tv_now_temperature.setText(temperInt + TEMPER_CODE);//24℃
                isNowApplied = true;

            }
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };



    /**
     * 위도,경도로 주소구하기
     * @param lat
     * @param lng
     * @return 주소
     */
    public String getAddress(Context mContext, double lat, double lng) {
        String addressStr= null;
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
            //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            address = geocoder.getFromLocation(lat, lng, 1);
            if(address.size()==0){
                Toast.makeText(mContext,  "현재 위치를 확인 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
            else addr = address.get(0);

            if (address.size() > 0) {
                // 주소 받아오기
                String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                addressStr  = currentLocationAddress;
            }
        } catch (IOException e) {
            System.out.println("주소를 가져올 수 없습니다.");
            e.printStackTrace();
            return null;
        }
        return addressStr;
    }




    public class weatherTask extends AsyncTask<Double, Void, Double> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected Double doInBackground(Double... params)  {

            Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface service = client.create(ApiInterface.class);
            Call<Repo> call = service.repo(API_KEY, params[0], params[1]); //lat,lon
            Repo repo = null;
            try {
                repo = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "날씨 정보 받아오기 실패", Toast.LENGTH_SHORT).show();
                return TEMPER_NULL;
            }
            assert repo != null;
            return repo.getMain().getTemp()- 273.15; //기온 return

        }

        @Override
        protected void onPostExecute(Double res) {
            super.onPostExecute(res);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == RECO_CODI && resultCode == RESULT_OK){
            setResult(RESULT_OK, intent);
            finish();
        }
        else if(requestCode == FROM_CLOSET && resultCode == RESULT_OK){
            //데이터 받아오기
            Bundle extras = intent.getExtras();
            String cloNo = extras.getString("cloNo");
            boolean isExist=false;

            for (int i=0;i<7;i++){
                if(i==selected_clo_index)
                    continue;
                if(Integer.parseInt(cloNo)==child_clothes_no[i]){
                    isExist=true;
                    Toast.makeText(this, "중복 아이템입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            if(!isExist){
                byte[] byteArray = intent.getByteArrayExtra("image");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                //이미지 설정
                list_childClothes.get(selected_clo_index).setImageBitmap(bitmap);
                //텍스트 지우기
                list_tv_childClothes.get(selected_clo_index).setVisibility(View.INVISIBLE);
                //옷 번호 저장
                child_clothes_no[selected_clo_index] = Integer.parseInt(cloNo);
            }
        }else if(requestCode == REQUEST_PERMISSION && resultCode == RESULT_OK){
            setNowTemper();
        }else if(requestCode == REQUEST_PERMISSION && resultCode == RESULT_CANCELED){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                setNowTemper();
            }else{
                rb_weather_none.setChecked(true);
                Toast.makeText(this, "위치 정보 권한 승인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                setWeatherNone();
            }
        }

    }

    public final String getCompleteWord(String name, String firstValue, String secondValue) {

        char lastName = name.charAt(name.length() - 1);

        // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if (lastName < 0xAC00 || lastName > 0xD7A3) {
            return name;
        }

        String selectedValue = (lastName - 0xAC00) % 28 > 0 ? firstValue : secondValue;
        return name+selectedValue;
    }


    @Override
    public void onBackPressed() {
        if (slidingDrawer.isOpened()) {
            slidingDrawer.close();
        }else{
            super.onBackPressed();
        }
    }



}