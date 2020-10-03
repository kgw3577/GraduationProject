package com.Project.Closet.codi.recoCodi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.util.Codi;
import com.Project.Closet.util.ColorArrange;
import com.Project.Closet.util.Utils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class activity_recommendCodi extends AppCompatActivity implements Page_recommended_codi.FragListener {

    private static final String TAG = "recommend";


    ArrayList<ClothesVO> clothesList;
    List<Codi> codiList;
    ColorArrange settingArrange;
    boolean settingColor[] = new boolean[2];

    ArrayList<ClothesVO>[] parts;
    Set<Integer>[] colorsOfPart;

    final int MAIN=0;
    final int SUB=1;

    int setting_main_color;
    int setting_sub_color;

    int main_part;
    int sub_part;
    int other_parts[];


    //뷰페이저 선언
    private TabLayout tabLayout;
    private recommPagerAdapter pagerAdapter;
    private ViewPager viewPager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_codi_recommend_all);
        clothesList = getIntent().getExtras().getParcelableArrayList("clothesList");
        setting_main_color = getIntent().getExtras().getInt("main_color");
        setting_sub_color = getIntent().getExtras().getInt("sub_color");

        if(setting_main_color !=-1 && setting_sub_color !=-1)
            settingArrange = new ColorArrange(setting_main_color, setting_sub_color);
        else if(setting_main_color !=-1)
            settingColor[MAIN]=true;
        else if(setting_sub_color !=-1)
            settingColor[SUB]=true;


        codiList = new ArrayList<>();

        //각 파트 별로 나눔
        parts = new ArrayList[7];
        for (int i=0; i<7; i++){
            parts[i] = new ArrayList<>();
        }
        for (ClothesVO clo : clothesList){
            switch(clo.getKind()){
                case "상의" :
                    parts[Utils.Kind.TOP].add(clo);
                    break;
                case "하의" :
                    parts[Utils.Kind.BOTTOM].add(clo);
                    break;
                case "한벌옷" :
                    parts[Utils.Kind.SUIT].add(clo);
                    break;
                case "외투" :
                    parts[Utils.Kind.OUTER].add(clo);
                    break;
                case "신발" :
                    parts[Utils.Kind.SHOES].add(clo);
                    break;
                case "가방" :
                    parts[Utils.Kind.BAG].add(clo);
                    break;
                case "액세서리" :
                    parts[Utils.Kind.ACCESSORY].add(clo);
                    break;
            }
        }


        /*각 파트 별로 컬러 목록을 만듬*/
        colorsOfPart = new Set[7]; //set : 중복x
        for (int i=0; i<7; i++){
            colorsOfPart[i] = new HashSet<>();
        }
        //모든 파트에 대해 (i는 파트 번호)
        for(int i=0; i<7; i++){
            //해당 파트의 옷들 중에서
            for(ClothesVO clo : parts[i]){
                //해당 파트 색 목록에 <- 해당 옷 색깔 저장. set이므로 중복 x
                colorsOfPart[i].add(Utils.colorNumMap.get(clo.getColor()));
            }
        }


        //main 파트와 sub 파트를 결정하는 모드 설정
        mode mode = new mode();
        List<Integer> modes;

        if(settingArrange==null){

            if(settingColor[MAIN]){
                modes = mode.setModes(MAIN);
            }
            else if(settingColor[SUB]){
                modes = mode.setModes(SUB);
            }
            else{
                modes = mode.setModes(parts[Utils.Kind.TOP].size(),
                        parts[Utils.Kind.BOTTOM].size(),
                        parts[Utils.Kind.SUIT].size(),
                        parts[Utils.Kind.OUTER].size(),
                        parts[Utils.Kind.SHOES].size(),
                        parts[Utils.Kind.BAG].size(),
                        parts[Utils.Kind.ACCESSORY].size()
                );
            }

        }else{
            modes = mode.setModes();
        }

        if(modes.size()==0){
            Toast.makeText(this, "만들 수 있는 코디가 없습니다. 더 많은 옷을 추가해보세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }




        for(int modeNum : modes){ //모든 가능한 모드에 대해 다음을 실행
            mode.setParts(modeNum);
            Log.d(TAG, "모드"+modeNum+" 메인파트 아이템"+parts[main_part].size()+"개 서브파트 아이템"+parts[sub_part].size()+"개");


            List<ColorArrange> colorArrangeList = new ArrayList<>();

            if(settingArrange==null){ //미리 설정된 배색 조합이 없으면
                /*main 파트와 sub 파트의 배색 점수 계산*/
                //main 파트와 sub 파트 내의 모든 색 조합에 대하여 다음을 수행
                for(int main_color : colorsOfPart[main_part]){

                    if(settingColor[MAIN]) //사용자가 설정한 메인 컬러가 있으면 메인 컬러를 고정
                        main_color=setting_main_color;

                    for(int sub_color : colorsOfPart[sub_part]){

                        if(settingColor[SUB]) //사용자가 설정한 서브 컬러가 있으면 서브 컬러를 고정
                            sub_color=setting_sub_color;

                        //해당 컬러들에 대한 배색 클래스를 만듬
                        ColorArrange colorArrange = new ColorArrange(main_color,sub_color);
                        Log.d(TAG, Utils.getKey(Utils.colorNumMap, main_color) + " " + Utils.getKey(Utils.colorNumMap, sub_color)+ " 배색 생성");
                        //기타 파트 중 메인-서브 컬러와 같은 계열의 색이 존재하는지 체크하고 없으면 balance 점수를 -20 함
                        for(int part : other_parts){
                            if(colorsOfPart[part].size()==0) { //해당 파트의 옷이 아예 존재하지 않으면 balance 점수를 깎지 않음.
                                Log.d(TAG, Utils.getKey(Utils.colorNumMap, main_color) + " " + Utils.getKey(Utils.colorNumMap, sub_color)
                                        + " 파트" + part + " 존재하지 않음.");
                                continue;
                            }
                            int flag=0;
                            for(int color : colorArrange.getOther_colors()) {
                                if(colorsOfPart[part].contains(color)){
                                    flag=1;
                                    Log.d(TAG, Utils.getKey(Utils.colorNumMap,main_color)+" "+Utils.getKey(Utils.colorNumMap,sub_color)
                                            +" "+part+" "+Utils.getKey(Utils.colorNumMap,color));
                                    break;
                                }
                            }
                            if(flag==0){
                                colorArrange.balance_score-=20;
                                Log.d(TAG, Utils.getKey(Utils.colorNumMap,main_color)+" "+Utils.getKey(Utils.colorNumMap,sub_color)
                                        +" 파트"+part+" 해당 없음.");
                            }

                        }
                        colorArrange.total_score = colorArrange.arrange_score + colorArrange.balance_score; //총점 계산
                        Log.d(TAG, Utils.getKey(Utils.colorNumMap,main_color)+" "+Utils.getKey(Utils.colorNumMap,sub_color)
                                +" 배색 점수 "+colorArrange.arrange_score+" 조화 점수 "+colorArrange.balance_score);

                        colorArrangeList.add(colorArrange);

                        if(settingColor[SUB]) //사용자가 설정한 메인 컬러가 있으면 루프를 한번만 돌림
                            break;

                    }
                    if(settingColor[MAIN]) //사용자가 설정한 메인 컬러가 있으면 루프를 한번만 돌림
                        break;
                }

                //배색 리스트 총점 순으로 정렬
                Collections.sort(colorArrangeList);
                //출력 확인용.
                for(ColorArrange cA : colorArrangeList){
                    cA.describe();
                }

            }
            else{ //미리 설정된 배색조합이 있으면
                colorArrangeList.add(settingArrange);
                //ColorArrange reversedArrange = new ColorArrange(settingArrange.getSub_color(),settingArrange.getMain_color()); //반대 조합 추가
                //colorArrangeList.add(reversedArrange);
            }



            int numColorArrange = colorArrangeList.size();
            int numAllRepeat = 1;
            int numRepeat = 1;
            if(numColorArrange<10&&numColorArrange!=0){
                numAllRepeat =1; //반복하지 않기
                numRepeat = 10/numColorArrange;
            }

            for(int i=0; i<numAllRepeat; i++){ //중복값을 대비해 전체 과정을 최대 2번 되풀이

                for(ColorArrange colorArrange : colorArrangeList){ //모든 배색조합에 대해
                    for(int j=0; j<numRepeat; j++){ //해당 색조합을 반복횟수만큼 코디를 만듬

                        Codi codi = new Codi(colorArrange); //코디 만들기
                        int main_color = colorArrange.getMain_color();
                        int sub_color = colorArrange.getSub_color();
                        Set<Integer> other_colors = colorArrange.getOther_colors();
                        List<ClothesVO> tempList = new ArrayList<>();
                        int cloIndex;

                        for(ClothesVO clothes: parts[main_part]){ //코디의 main 파트 결정
                            if(Utils.colorNumMap.get(clothes.getColor())==main_color) //해당 컬러를 가진 해당 파트 찾기
                                tempList.add(clothes);
                        }
                        if(tempList.size()==0) //해당 컬러를 가진 패당 파트가 없으면
                            break; //해당 색조합 코디 종료

                        cloIndex = new Random().nextInt(tempList.size()); //랜덤으로 옷 선택
                        codi.setPart(main_part,tempList.get(cloIndex)); //코디의 해당 파트에 선택된 옷 set
                        Log.d(TAG, "메인 파트 선택 : "+Utils.getKey(Utils.colorNumMap,main_color)+" "+tempList.get(cloIndex).getDetailCategory());
                        tempList.clear();


                        for(ClothesVO clothes: parts[sub_part]){ //코디의 sub 파트 결정
                            if(Utils.colorNumMap.get(clothes.getColor())==sub_color) //해당 컬러를 가진 해당 파트 찾기
                                tempList.add(clothes);
                        }
                        if(tempList.size()==0) //해당 컬러를 가진 패당 파트가 없으면
                            break; //해당 색조합 코디 종료

                        cloIndex = new Random().nextInt(tempList.size());
                        codi.setPart(sub_part,tempList.get(cloIndex));
                        Log.d(TAG, "서브 파트 선택 : "+Utils.getKey(Utils.colorNumMap,sub_color)+" "+tempList.get(cloIndex).getDetailCategory());
                        tempList.clear();

                        int balance_score = 50;
                        for(int part : other_parts){ //코디의 나머지 파트 결정
                            for(ClothesVO clothes: parts[part]){ //해당 파트의 옷에서
                                for(int color : other_colors){ //모든 기타 컬러에 대해
                                    if(Utils.colorNumMap.get(clothes.getColor())==color) //해당 컬러를 가진 해당 파트 찾기
                                        tempList.add(clothes);
                                }
                            }
                            if(tempList.size()!=0){
                                //해당 컬러의 해당 파트가 있을 경우 그 중에서 랜덤 선택
                                cloIndex = new Random().nextInt(tempList.size());
                                codi.setPart(part,tempList.get(cloIndex));
                                Log.d(TAG, part+"기타 파트 선택 : "+tempList.get(cloIndex).getColor()+" "+tempList.get(cloIndex).getDetailCategory());
                                tempList.clear();
                            }else if(parts[part].size()!=0){
                                //해당 컬러가 없을 경우 무채색 계열 선택
                                Set<Integer> achromatic_colors = new HashSet<>();
                                achromatic_colors.addAll(Utils.ColorNum.achromatic_colors);
                                for(ClothesVO clothes: parts[part]){ //해당 파트의 옷에서
                                    for(int color : achromatic_colors){ //모든 무채색 컬러에 대해
                                        if(Utils.colorNumMap.get(clothes.getColor())==color) //해당 컬러를 가진 해당 파트 찾기
                                            tempList.add(clothes);
                                    }
                                }
                                if(tempList.size()!=0) {
                                    //해당 컬러의 해당 파트가 있을 경우 그 중에서 랜덤 선택
                                    cloIndex = new Random().nextInt(tempList.size());
                                    codi.setPart(part, tempList.get(cloIndex));
                                    balance_score-=10;
                                    Log.d(TAG, part + "무채색 기타 파트 선택 : " + tempList.get(cloIndex).getColor() + " " + tempList.get(cloIndex).getDetailCategory());
                                    tempList.clear();
                                }else{
                                    //무채색 계열도 없을 경우 해당 파트 중에서 랜덤 선택
                                    cloIndex = new Random().nextInt(parts[part].size());
                                    codi.setPart(part,parts[part].get(cloIndex));
                                    balance_score-=20;
                                    Log.d(TAG, part+"(안 어울림)기타 파트 선택 : "+parts[part].get(cloIndex).getColor()+" "+parts[part].get(cloIndex).getDetailCategory());
                                }
                            }
                        }
                        //나머지 파트 선택후 밸런스 점수 갱신
                        codi.getColorArrange().setBalance_score(balance_score);
                        codi.getColorArrange().setTotal_score(colorArrange.arrange_score+balance_score);

                        if(!codiList.contains(codi)){
                            codiList.add(codi); //코디 생성 과정이 모두 끝난 후 리스트에 추가

                        }else
                            Log.d(TAG,"중복 제거함");
                        Log.d(TAG,i+" "+j+"번 코디 생성 완료");
                    }
                }
            }



        }





        //코디 리스트 총점 순으로 정렬
        Collections.sort(codiList);
        Log.d(TAG, "코디 리스트 개수 :"+codiList.size());


        //헤더 메뉴 아이콘 받아오기
        ImageView ivRevert = (ImageView) findViewById(R.id.iv_revert);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);


        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivRevert.setOnClickListener(onClickListener);
        tvDone.setOnClickListener(onClickListener);


        //탭 설정
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        for(int i=0; i<codiList.size();i++){ //생성된 코디 개수만큼 탭 생성
            tabLayout.addTab(tabLayout.newTab().setText(i+1+""));
        }

        //하단 뷰페이저 어댑터 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        //viewPager.setOffscreenPageLimit(2); //캐싱을 해놓을 프래그먼트 개수
//        viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
        pagerAdapter = new recommPagerAdapter(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        for(Codi codi : codiList){ //생성된 코디 개수만큼 페이지 생성
            pagerAdapter.addItem(Page_recommended_codi.newInstance(codi));
        }

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }



    //프래그먼트에서 데이터 받아오기
    public void ReceivedData(Object data){

    }

    class BtnOnClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {


            switch (view.getId()) {
                case R.id.iv_revert :
                    //refresh로 수정할 것
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
                case R.id.tv_done : //완료 버튼
                    finish_ok();
                    break;
            }

        }
    }



    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class mode{

        final int TOP_BOTTOM = 0;
        final int TOP_OUTER = 1;
        final int OUTER_BOTTOM = 2;
        final int SUIT_BAG = 3;
        final int SUIT_SHOES = 4;
        final int SUIT_ACCESSORY=5;
        final int SUIT_OUTER = 6;

        public List<Integer> setModes(int numTop, int numBottom, int numSuit, int numOuter, int numShoes, int numBag, int numAccessory){
            List<Integer> modes = new ArrayList<>();
            int randomIndex;

            List<Integer> modesT = new ArrayList<>();
            if (numTop != 0 && numBottom != 0) {
                modesT.add(TOP_BOTTOM);
            }
            if (numTop != 0 && numOuter != 0) {
                modesT.add(TOP_OUTER);
            }
            if (numBottom != 0 && numOuter != 0     &&numTop!=0) {
                modesT.add(OUTER_BOTTOM);
            }
            if (modesT.size() != 0) {
                randomIndex = new Random().nextInt(modesT.size());
                modes.add(modesT.get(randomIndex));
            }

            List<Integer> modesS = new ArrayList<>();
            if(numSuit!=0&&numBag!=0){
                modesS.add(SUIT_BAG);
            }
            if(numSuit!=0&&numShoes!=0){
                modesS.add(SUIT_SHOES);
            }
            if(numSuit!=0&&numAccessory!=0){
                modesS.add(SUIT_ACCESSORY);
            }
            if(numSuit!=0&&numOuter!=0){
                modesS.add(SUIT_OUTER);
            }
            if(modesS.size()!=0){
                randomIndex = new Random().nextInt(modesS.size());
                modes.add(modesS.get(randomIndex));
            }

            return modes;
        }

        public List<Integer> setModes(){
            List<Integer> modes = new ArrayList<>();
            int randomIndex;

            int main_color = settingArrange.getMain_color();
            int sub_color = settingArrange.getSub_color();

            List<Integer> modesT = new ArrayList<>();
            if(colorsOfPart[Utils.Kind.TOP].contains(main_color) &&
                    colorsOfPart[Utils.Kind.BOTTOM].contains(sub_color))
                modesT.add(TOP_BOTTOM);
            if(colorsOfPart[Utils.Kind.TOP].contains(main_color) &&
                    colorsOfPart[Utils.Kind.OUTER].contains(sub_color))
                modesT.add(TOP_OUTER);
            if(colorsOfPart[Utils.Kind.OUTER].contains(main_color) &&
                    colorsOfPart[Utils.Kind.BOTTOM].contains(sub_color) &&
                    parts[Utils.Kind.TOP].size()!=0)
                modesT.add(OUTER_BOTTOM);
            if (modesT.size() != 0) {
                randomIndex = new Random().nextInt(modesT.size());
                modes.add(modesT.get(randomIndex));
            }

            List<Integer> modesS = new ArrayList<>();
            if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                    colorsOfPart[Utils.Kind.BAG].contains(sub_color))
                modesS.add(SUIT_BAG);
            if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                    colorsOfPart[Utils.Kind.SHOES].contains(sub_color))
                modesS.add(SUIT_SHOES);
            if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                    colorsOfPart[Utils.Kind.ACCESSORY].contains(sub_color))
                modesS.add(SUIT_ACCESSORY);
            if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                    colorsOfPart[Utils.Kind.OUTER].contains(sub_color))
                modesS.add(SUIT_OUTER);
            if(modesS.size()!=0){
                randomIndex = new Random().nextInt(modesS.size());
                modes.add(modesS.get(randomIndex));
            }


            return modes;
        }





        public List<Integer> setModes(int onlyMode){
            List<Integer> modes = new ArrayList<>();
            int randomIndex;

            int numTop = parts[Utils.Kind.TOP].size();
            int numBottom = parts[Utils.Kind.BOTTOM].size();
            int numSuit = parts[Utils.Kind.SUIT].size();
            int numOuter = parts[Utils.Kind.OUTER].size();
            int numShoes = parts[Utils.Kind.SHOES].size();
            int numBag = parts[Utils.Kind.BAG].size();
            int numAccessory = parts[Utils.Kind.ACCESSORY].size();

            if(onlyMode==MAIN){
                int main_color=setting_main_color;

                List<Integer> modesT = new ArrayList<>();
                if(colorsOfPart[Utils.Kind.TOP].contains(main_color) &&
                        numBottom != 0)
                    modesT.add(TOP_BOTTOM);
                if(colorsOfPart[Utils.Kind.TOP].contains(main_color) &&
                        numOuter != 0)
                    modesT.add(TOP_OUTER);
                if(colorsOfPart[Utils.Kind.OUTER].contains(main_color) &&
                        numBottom != 0 && numTop!=0)
                    modesT.add(OUTER_BOTTOM);
                if (modesT.size() != 0) {
                    randomIndex = new Random().nextInt(modesT.size());
                    modes.add(modesT.get(randomIndex));
                }

                List<Integer> modesS = new ArrayList<>();
                if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                        numBag!=0)
                    modesS.add(SUIT_BAG);
                if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                        numShoes!=0)
                    modesS.add(SUIT_SHOES);
                if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                        numAccessory!=0)
                    modesS.add(SUIT_ACCESSORY);
                if(colorsOfPart[Utils.Kind.SUIT].contains(main_color) &&
                        numOuter!=0)
                    modesS.add(SUIT_OUTER);
                if(modesS.size()!=0){
                    randomIndex = new Random().nextInt(modesS.size());
                    modes.add(modesS.get(randomIndex));
                }

            }else if(onlyMode==SUB){
                int sub_color=setting_sub_color;

                List<Integer> modesT = new ArrayList<>();
                if(numTop != 0 &&
                        colorsOfPart[Utils.Kind.BOTTOM].contains(sub_color))
                    modesT.add(TOP_BOTTOM);
                if(numTop != 0 &&
                        colorsOfPart[Utils.Kind.OUTER].contains(sub_color))
                    modesT.add(TOP_OUTER);
                if(numBottom != 0 &&
                        colorsOfPart[Utils.Kind.BOTTOM].contains(sub_color)
                        && numTop!=0)
                    modesT.add(OUTER_BOTTOM);
                if (modesT.size() != 0) {
                    randomIndex = new Random().nextInt(modesT.size());
                    modes.add(modesT.get(randomIndex));
                }


                List<Integer> modesS = new ArrayList<>();
                if(numSuit!=0 &&
                        colorsOfPart[Utils.Kind.BAG].contains(sub_color))
                    modesS.add(SUIT_BAG);
                if(numSuit!=0 &&
                        colorsOfPart[Utils.Kind.SHOES].contains(sub_color))
                    modesS.add(SUIT_SHOES);
                if(numSuit!=0 &&
                        colorsOfPart[Utils.Kind.ACCESSORY].contains(sub_color))
                    modesS.add(SUIT_ACCESSORY);
                if(numSuit!=0 &&
                        colorsOfPart[Utils.Kind.OUTER].contains(sub_color))
                    modesS.add(SUIT_OUTER);
                if(modesS.size()!=0){
                    randomIndex = new Random().nextInt(modesS.size());
                    modes.add(modesS.get(randomIndex));
                }

            }

            return modes;
        }





        public void setParts(int modeNum){
            switch(modeNum){
                case TOP_BOTTOM :
                    main_part = Utils.Kind.TOP;
                    sub_part = Utils.Kind.BOTTOM;
                    other_parts = new int[]{Utils.Kind.OUTER, Utils.Kind.SHOES, Utils.Kind.BAG, Utils.Kind.ACCESSORY};
                    break;
                case TOP_OUTER :
                    main_part = Utils.Kind.TOP;
                    sub_part = Utils.Kind.OUTER;
                    other_parts = new int[]{Utils.Kind.BOTTOM, Utils.Kind.SHOES, Utils.Kind.BAG, Utils.Kind.ACCESSORY};
                    break;
                case OUTER_BOTTOM :
                    main_part = Utils.Kind.OUTER;
                    sub_part = Utils.Kind.BOTTOM;
                    other_parts = new int[]{Utils.Kind.TOP, Utils.Kind.SHOES, Utils.Kind.BAG, Utils.Kind.ACCESSORY};
                    break;
                case SUIT_BAG :
                    main_part = Utils.Kind.SUIT;
                    sub_part = Utils.Kind.BAG;
                    other_parts = new int[]{Utils.Kind.SHOES, Utils.Kind.OUTER, Utils.Kind.ACCESSORY};
                    break;
                case SUIT_SHOES :
                    main_part = Utils.Kind.SUIT;
                    sub_part = Utils.Kind.SHOES;
                    other_parts = new int[]{Utils.Kind.BAG , Utils.Kind.OUTER, Utils.Kind.ACCESSORY};
                    break;
                case SUIT_ACCESSORY :
                    main_part = Utils.Kind.SUIT;
                    sub_part = Utils.Kind.ACCESSORY;
                    other_parts = new int[]{Utils.Kind.BAG , Utils.Kind.OUTER, Utils.Kind.SHOES };
                    break;
                case SUIT_OUTER :
                    main_part = Utils.Kind.SUIT;
                    sub_part = Utils.Kind.OUTER;
                    other_parts = new int[]{Utils.Kind.BAG , Utils.Kind.ACCESSORY , Utils.Kind.SHOES };
                    break;
            }
        }
    }

    public void finish_cancel(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void finish_ok(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}