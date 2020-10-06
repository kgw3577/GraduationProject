package com.Project.Closet.codi.recoCodi;

import android.annotation.SuppressLint;
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
import java.util.Random;
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

    @SuppressLint("SetTextI18n")
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


        /*메인/서브 컬러 정보*/
        TextView tv_main_color = view.findViewById(R.id.tv_main_color);
        TextView tv_sub_color = view.findViewById(R.id.tv_sub_color);
        CircleImageView civ_main_color = view.findViewById(R.id.civ_main_color);
        CircleImageView civ_sub_color = view.findViewById(R.id.civ_sub_color);

        String main_colorName = Utils.getKey(Utils.colorNumMap,colorArrange.getMain_color()); //"레드"
        String sub_colorName = Utils.getKey(Utils.colorNumMap,colorArrange.getSub_color());

        String main_colorInt = Utils.colorIntMap.get(main_colorName); //"#ff0000"
        String sub_colorInt = Utils.colorIntMap.get(sub_colorName);

        tv_main_color.setText(main_colorName);
        tv_sub_color.setText(sub_colorName);
        civ_main_color.setColorFilter(Color.parseColor(main_colorInt));
        civ_sub_color.setColorFilter(Color.parseColor(sub_colorInt));

        //TextView arrange_tip = view.findViewById(R.id.arrange_tip);
//        arrange_tip.setText(Utils.getKey(Utils.colorMap,colorArrange.getMain_color())+" "
//                +Utils.getKey(Utils.colorMap,colorArrange.getSub_color())
//                +"\n"+"배색 점수 : "+colorArrange.arrange_score
//                +"\n"+"조화 점수 : "+colorArrange.balance_score
//                +"\n"+"총점 : "+colorArrange.total_score
//                );


        //점수별 추천 문구
        TextView tv_comment = view.findViewById(R.id.tv_comment);
        switch ((int)colorArrange.total_score/10){
            case 10:
            case 9:
                tv_comment.setText("강력 추천!");
                break;
            case 8:
            case 7:
                tv_comment.setText("추천!");
                break;
            case 6:
            case 5:
                tv_comment.setText("무난해요");
                break;
            default:
                tv_comment.setText("비추...");
                break;
        }

        //분위기 문구
        TextView tv_mood = view.findViewById(R.id.tv_mood);
        tv_mood.setTextColor(Color.parseColor(main_colorInt));
        switch (main_colorName){
            case "네이비":
                tv_mood.setText("단정하면서 도시적인 세련미");
                break;
            case "블랙":
                tv_mood.setText("품위있고 권위적, 격조 높은 고급");
                break;
            case "오렌지":
                tv_mood.setText("개방적, 상큼함, 활기참");
                break;
            case "블루":
                tv_mood.setText("시원함, 젊음, 냉정");
                break;
            case "베이지":
                tv_mood.setText("부드러움, 따뜻함");
                break;
            case "화이트":
                tv_mood.setText("깨끗함, 단아함, 청순함");
                tv_mood.setShadowLayer(5,2,2,Color.parseColor("#444444"));
                break;
            default:
                tv_mood.setVisibility(View.GONE);
                break;
        }

        //날씨별 문구
        TextView tv_weather_tip = view.findViewById(R.id.tv_weather_tip);
        double temperature = codi.getTemperature();
        if(temperature==10000){
            tv_weather_tip.setVisibility(View.GONE);
        }
        else if(temperature<=5){
            tv_weather_tip.setText("롱패딩은 생존템!\n" +
                    "야상, 패딩, 목도리 등등 몸을 감싸는 옷을 있는대로 챙겨서 겹겹이 싸입고 나가는 것이 좋아요.\n" +
                    "멋보다는 건강을 챙기려면 다들 패딩을 필수로 챙겨가야겠네요 :)");
        }else if(temperature <= 9){
            tv_weather_tip.setText("여전히 추운 날씨, 하지만 멋 정도는 부릴 수 있어요!\n" +
                    "속옷을 따뜻하게 챙겨 입었다면 겉옷은 코트나 가죽자켓을 입어도 무방한 기온이랍니다 :D\n" +
                    "요즘은 ‘얼.죽.코’라는 말도 있는데, ‘얼어 죽어도 코트를 입는 사람’ 이라는 뜻이라고 해요.\n" +
                    "그래도 너무 추울 땐 감기에 걸릴 수도 있으니까 꼭 꼭 따뜻하게 입어주세요!");
        }else if(temperature <= 11){
            tv_weather_tip.setText("늦가을 즈음, 조금 쌀쌀한 날씨!\n" +
                    "간절기인 만큼 아우터를 들고 나가야 할까 말아야 할까 고민이 많이 되실 것 같은데요.\n" +
                    "이럴 땐 트렌치코트나 간절기 야상을 입는 것을 추천 드려요 :)\n" +
                    "또한 한창 이때 많이들 감기에 걸리시는데요ㅠ_ㅜ 쌀쌀한 추위에 대비할 수 있도록 얇은 옷을 여러 벌 껴입는 것도 추천드립니다!");
        }else if(temperature <= 16){
            tv_weather_tip.setText("대체로 따뜻하지만 바람이 불거나 해가 지면 확 추워지는 온도입니다.\n" +
                    "낮에 활동할 때에는 자켓, 셔츠, 가디건, 간절기 야상, 살색 스타킹을,\n" +
                    "추위를 잘타시는 분이나, 저녁에 활동할 때에는 아우터를 따로 챙겨나오는 것이 좋을 것 같습니다 :-)");
        }else if(temperature <= 19){
            tv_weather_tip.setText("새 학기가 시작될, 곧 다가올 따스한 봄 날씨의 기온입니다.\n" +
                    "이 땐 얇은 니트, 가디건, 후드티, 면바지, 슬랙스 어떤 옷이든 OK!\n" +
                    "한창 따뜻한 날씨이니까 마음껏 꾸미고 다니시면 될 것 같아요 :D\n" +
                    "햇살이 좋은 날엔 예쁜 원피스를 입는 것도 좋을 것 같네요!");
        }else if(temperature <= 22){
            tv_weather_tip.setText("여름이 가까워지면서 낮이 뜨겁고 밤은 조금 쌀쌀한 날이 많습니다.\n" +
                    "아우터 없이 긴팔티와 면바지를 입거나, 더위를 잘 타는 분은 반팔티를 입고 그 위에 후드집업이나 가디건과 같은 얇은 아우터를 걸치는 것을 추천드려요!");
        }else if(temperature <= 26){
            tv_weather_tip.setText("여름이 본격적으로 시작되는군요.\n" +
                    "푹푹 찌는 날씨의 옷차림은 당연히 반팔과 반바지가 필수! 선크림을 발라주는 것도 잊지 마세요!\n" +
                    "혹시 피부가 자외선에 예민하신 분들은 피부를 보호하기 위해 얇은 긴팔이나 면바지 등을 입는 것이 좋겠죠~?");
        }else {
            tv_weather_tip.setText("뜨거운 열기에 숨이 턱턱 막힙니다.\n" +
                    "이럴 땐 나시티를 입거나 반바지, 민소매 원피스 등 몸의 열기를 빨리 날려주는 옷을 입어야 해요!\n" +
                    "통풍이 잘되는 린넨 소재의 옷을 입는 것도 좋을 선택일 것 같아요 :)");
        }


        //저장 로직
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
