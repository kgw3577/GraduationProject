package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
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

public class ClothesService extends APIAdapter {
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

        //옷 추가
        //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
        @Multipart
        @POST("clothes/add")
        Call<String> addClothes(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);
        //response는 true/false

        //모든 옷 정보 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("clothes/all")
        Call<List<ClothesVO>> myAllClothes(@Query("page") String page, @Query("pageSize") String pageSize);

        //상의 목록 가져오기
        @GET("clothes/category/top")
        Call<List<ClothesVO>> chooseTop(@Query("page") String page, @Query("pageSize") String pageSize);
        //하의 목록 가져오기
        @GET("clothes/category/bottom")
        Call<List<ClothesVO>> chooseBottom(@Query("page") String page, @Query("pageSize") String pageSize);
        //한벌옷 목록 가져오기
        @GET("clothes/category/suit")
        Call<List<ClothesVO>> chooseSuit(@Query("page") String page, @Query("pageSize") String pageSize);
        //외투 목록 가져오기
        @GET("clothes/category/outer")
        Call<List<ClothesVO>> chooseOuter(@Query("page") String page, @Query("pageSize") String pageSize);
        //신발 목록 가져오기
        @GET("clothes/category/shoes")
        Call<List<ClothesVO>> chooseShoes(@Query("page") String page, @Query("pageSize") String pageSize);
        //가방 목록 가져오기
        @GET("clothes/category/bag")
        Call<List<ClothesVO>> chooseBag(@Query("page") String page, @Query("pageSize") String pageSize);
        //악세서리 목록 가져오기
        @GET("clothes/category/accessory")
        Call<List<ClothesVO>> chooseAccessory(@Query("page") String page, @Query("pageSize") String pageSize);

        //즐겨찾기한 옷 목록 가져오기
        @GET("clothes/favorite")
        Call<List<ClothesVO>> favoriteClothes(@Query("page") String page, @Query("pageSize") String pageSize);

    }
}
