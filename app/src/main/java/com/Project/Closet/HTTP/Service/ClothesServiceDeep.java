package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapterDeep;
import com.Project.Closet.HTTP.VO.ClothesVO;

import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public class ClothesServiceDeep extends APIAdapterDeep {
//딥러닝 서버로 전송하는 어댑터

    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static ClothesAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (ClothesAPI) retrofit(context, ClothesAPI.class);
    }
    public interface ClothesAPI{

        //딥러닝 서버 실험용 사진 전송
        @Multipart
        @POST("test")
        Call<String> addClothesDeep(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);
        //response는 true/false

    }
}
