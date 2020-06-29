package com.Project.Closet.codi.addCodi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.Closet.R;

public class activity_addCodi extends AppCompatActivity implements Page_allClothes.FragListener, Page_top.FragListener, Page_bottom.FragListener
    , Page_suit.FragListener, Page_outer.FragListener, Page_shoes.FragListener, Page_bag.FragListener, Page_accessory.FragListener {

    class Category {
        final static int ALL = 0;
        final static int TOP = 1;
        final static int BOTTOM = 2;
        final static int SUIT = 3;
        final static int OUTER = 4;
        final static int SHOES = 5;
        final static int BAG = 6;
        final static int ACCESSORY = 7;
    }

    //뷰페이저 선언
    private ViewPager viewPager;
    private PagerAdapter_addCodi pagerAdapter;

    //셀렉트바 선언
    LinearLayout selectbar;
    ImageView iv_selected;
    ImageView ivTop;
    ImageView ivBottom;
    ImageView ivSuit;
    ImageView ivOuter;
    ImageView ivShoes;
    ImageView ivBag;
    ImageView ivAccessory1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_codi);

        //헤더 메뉴 아이콘 받아오기
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        ImageView ivRandom = (ImageView) findViewById(R.id.iv_random);
        TextView tvPreview = (TextView) findViewById(R.id.tv_preview);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);

        //코디 화면 레이아웃과 이미지뷰 받아오기
        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);
        ivTop = (ImageView) findViewById(R.id.iv_top);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);
        ivSuit = (ImageView) findViewById(R.id.iv_suit);
        ivOuter= (ImageView) findViewById(R.id.iv_outer);
        ivShoes= (ImageView) findViewById(R.id.iv_shoes);
        ivBag= (ImageView) findViewById(R.id.iv_bag);
        ivAccessory1= (ImageView) findViewById(R.id.iv_acc1);
        selectbar = (LinearLayout) findViewById(R.id.selectbar);

        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivSearch.setOnClickListener(onClickListener);
        ivRandom.setOnClickListener(onClickListener);
        tvPreview.setOnClickListener(onClickListener);
        tvDone.setOnClickListener(onClickListener);
        ivTop.setOnClickListener(onClickListener);
        ivBottom.setOnClickListener(onClickListener);
        ivSuit.setOnClickListener(onClickListener);
        ivOuter.setOnClickListener(onClickListener);
        ivShoes.setOnClickListener(onClickListener);
        ivBag.setOnClickListener(onClickListener);
        ivAccessory1.setOnClickListener(onClickListener);

        //하단 뷰페이저 어댑터 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        viewPager.setOffscreenPageLimit(1); //캐싱을 해놓을 프래그먼트 개수
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        pagerAdapter = new PagerAdapter_addCodi(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        pagerAdapter.addItem(new Page_allClothes());
        pagerAdapter.addItem(new Page_top());
        pagerAdapter.addItem(new Page_bottom());
        pagerAdapter.addItem(new Page_suit());
        pagerAdapter.addItem(new Page_outer());
        pagerAdapter.addItem(new Page_shoes());
        pagerAdapter.addItem(new Page_bag());
        pagerAdapter.addItem(new Page_accessory());
        viewPager.setAdapter(pagerAdapter);


    }


    //프래그먼트에서 데이터 받아오기
    public void ReceivedData(Object data){
        if(iv_selected!=null)
            iv_selected.setImageBitmap((Bitmap)data);
        else
            Toast.makeText(getApplicationContext(), "추가할 부분을 선택해야 합니다!", Toast.LENGTH_LONG).show();
    }

    class BtnOnClickListener implements Button.OnClickListener {

        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        @Override
        public void onClick(View view) {

            Bitmap savedBitmap;

            switch (view.getId()) {
                case R.id.iv_search :
                    break ;
                case R.id.iv_random :
                    getApplication();
                    break ;
                case R.id.tv_preview : //미리보기 버튼 : 현재 코디 비트맵으로 저장한 후 보여주기
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedBitmap = layoutMakeCodi.getDrawingCache();
                    if (savedBitmap != null) {
                        ivBottom.setImageBitmap(savedBitmap.copy(savedBitmap.getConfig(), false));
                    }
                    break ;
                case R.id.tv_done :
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedBitmap = layoutMakeCodi.getDrawingCache();
                    break ;

                case R.id.iv_top :
                    iv_selected = ivTop;
                    viewPager.setCurrentItem(Category.TOP);
                    break;
                case R.id.iv_bottom :
                    iv_selected = ivBottom;
                    viewPager.setCurrentItem(Category.BOTTOM);
                    break;
                case R.id.iv_suit :
                    iv_selected = ivSuit;
                    viewPager.setCurrentItem(Category.SUIT);
                    break;
                case R.id.iv_outer :
                    iv_selected = ivOuter;
                    viewPager.setCurrentItem(Category.OUTER);
                    break;
                case R.id.iv_shoes :
                    iv_selected = ivShoes;
                    viewPager.setCurrentItem(Category.SHOES);
                    break;
                case R.id.iv_bag :
                    iv_selected = ivBag;
                    viewPager.setCurrentItem(Category.BAG);
                    break;
                case R.id.iv_acc1:
                    iv_selected = ivAccessory1;
                    viewPager.setCurrentItem(Category.ACCESSORY);
                    break;
            }

            if(iv_selected!=null)
                selectbar.setVisibility(View.VISIBLE);

        }
    }







}