package com.Project.Closet.codi.recoCodi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.CodiService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.R;
import com.Project.Closet.util.Codi;
import com.Project.Closet.util.ColorArrange;
import com.Project.Closet.util.Utils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Page_recommended_codi extends Fragment {

    Codi codi;
    //activity_recommendCodi activity;

    //파트별 뷰 선언
    ImageView ivTop; //상의 파트
    ImageView ivBottom; //하의 파트
    ImageView ivSuit;   //한벌옷 파트
    ImageView ivOuter;  //외투 파트
    ImageView ivShoes;  //신발 파트
    ImageView ivBag;    //가방 파트
    ImageView ivAccessory; //액세서리4 파트

    //이미지 저장 관련
    Bitmap savedCodi;
    Bitmap resizedImage;
    String path;


    public static Page_recommended_codi newInstance(Codi codi) {

        Bundle args = new Bundle();
        args.putParcelable("codi",codi);

        Page_recommended_codi fragment = new Page_recommended_codi();
        fragment.setArguments(args);
        return fragment;
    }

    public interface FragListener{
        void ReceivedData(Object data);
    }
    private FragListener mFragListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof FragListener){
            mFragListener = (FragListener) getActivity();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            codi = args.getParcelable("codi");
        }

        //activity = (activity_recommendCodi) getActivity();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_codi_recommend_page, container, false);
        //이미지뷰 불러오기
        ivTop = (ImageView) view.findViewById(R.id.iv_top);
        ivBottom = (ImageView) view.findViewById(R.id.iv_bottom);
        ivSuit = (ImageView) view.findViewById(R.id.iv_suit);
        ivOuter= (ImageView) view.findViewById(R.id.iv_outer);
        ivShoes= (ImageView) view.findViewById(R.id.iv_shoes);
        ivBag= (ImageView) view.findViewById(R.id.iv_bag);
        ivAccessory = (ImageView) view.findViewById(R.id.iv_acc4);

        if(codi.getTop().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getTop().getFilePath()).into(ivTop);
        else
            ivTop.setVisibility(View.GONE);

        if(codi.getBottom().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getBottom().getFilePath()).into(ivBottom);
        else
            ivBottom.setVisibility(View.GONE);

        if(codi.getSuit().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getSuit().getFilePath()).into(ivSuit);
        else
            ivSuit.setVisibility(View.GONE);

        if(codi.getOuter().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getOuter().getFilePath()).into(ivOuter);
        else
            ivOuter.setVisibility(View.GONE);

        if(codi.getShoes().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getShoes().getFilePath()).into(ivShoes);
        else
            ivShoes.setVisibility(View.GONE);

        if(codi.getBag().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getBag().getFilePath()).into(ivBag);
        else
            ivBag.setVisibility(View.GONE);

        if(codi.getAccessory().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getAccessory().getFilePath()).into(ivAccessory);
        else
            ivAccessory.setVisibility(View.GONE);


        ColorArrange colorArrange = codi.getColorArrange();

        TextView tv_main_color = view.findViewById(R.id.tv_main_color);
        TextView tv_sub_color = view.findViewById(R.id.tv_sub_color);
        CircleImageView civ_main_color = view.findViewById(R.id.civ_main_color);
        CircleImageView civ_sub_color = view.findViewById(R.id.civ_sub_color);

        String main_color = Utils.getKey(Utils.colorNumMap,colorArrange.getMain_color());
        String sub_color = Utils.getKey(Utils.colorNumMap,colorArrange.getSub_color());

        String main_colorInt = Utils.colorIntMap.get(main_color);
        String sub_colorInt = Utils.colorIntMap.get(sub_color);


        tv_main_color.setText(main_color);
        tv_sub_color.setText(sub_color);
        civ_main_color.setColorFilter(Color.parseColor(main_colorInt));
        civ_sub_color.setColorFilter(Color.parseColor(sub_colorInt));

        //TextView arrange_tip = view.findViewById(R.id.arrange_tip);
//        arrange_tip.setText(Utils.getKey(Utils.colorMap,colorArrange.getMain_color())+" "
//                +Utils.getKey(Utils.colorMap,colorArrange.getSub_color())
//                +"\n"+"배색 점수 : "+colorArrange.arrange_score
//                +"\n"+"조화 점수 : "+colorArrange.balance_score
//                +"\n"+"총점 : "+colorArrange.total_score
//                );


        ImageView iv_save = view.findViewById(R.id.iv_save);
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //코디 화면 레이아웃 받아오기
                RelativeLayout layout_codi_image = (RelativeLayout) view.findViewById(R.id.layout_makecodi);

                //현재 코디 이미지 캡쳐
                layout_codi_image.setDrawingCacheEnabled(false); //캐시를 지운다
                layout_codi_image.setDrawingCacheEnabled(true); //캐시를 얻는다
                layout_codi_image.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                savedCodi = layout_codi_image.getDrawingCache(); //캡쳐를 비트맵으로 저장

                //크기 줄여주기 (메모리 부족 오류 방지)
                double height=savedCodi.getHeight();
                double width=savedCodi.getWidth();
                resizedImage = Bitmap.createScaledBitmap(savedCodi, (int)Global.bitmapWidth, (int)(height/(width/Global.bitmapWidth)), true);
                if (resizedImage != null) {
                    upload();
                    //Intent intent = new Intent(activity_addCodi.this, activity_sendCodi.class);
                    //intent.putExtra("codiImage", resizedImage) ;
                    //startActivity(intent);
                }
            }
        });

        return view;
    }


    String upload(){
        String res="";

        //임시 파일로 저장하기
        final Context context = getContext();
        String filename = "myTemp";
        File tempFile = null;
        try {
            tempFile = File.createTempFile(filename, null, context.getCacheDir());
            FileOutputStream out = new FileOutputStream(tempFile);
            resizedImage.compress(Bitmap.CompressFormat.JPEG, 100 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close();
            path = tempFile.getAbsolutePath(); //임시 파일 경로
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            res = new UploadTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        try {
            if (res.contains("ok")) {
                Toast.makeText(getContext(), "코디북에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                //activity.finish_ok();
            } else if (res.contains("fail")) {
                Toast.makeText(getContext(), "실패하였습니다.", Toast.LENGTH_SHORT).show();
                //activity.finish_cancel();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            //activity.finish_cancel();
        }
        return res;
    }



    public class UploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody;
            MultipartBody.Part body;
            File file = new File(path);
            LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
            List<MultipartBody.Part> arrBody = new ArrayList<>();


            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("userID", RequestBody.create(MediaType.parse("text/plain"), MySharedPreferences.getInstanceOf(getContext()).getUserID()));
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default"));
            body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);


            Call<String> stringCall = CodiService.getRetrofit(getActivity()).addCodi(mapRequestBody, arrBody);
            try {
                return stringCall.execute().body(); //웹서버에 이미지 보내고 응답 받기
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
