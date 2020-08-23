package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.VO.CodiVO;

import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CodiService extends APIAdapter {
//웹서버로 전송하는 어댑터

    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static CodiAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (CodiAPI) retrofit(context, CodiAPI.class);
    }
    public interface CodiAPI{

        //코디 추가
        //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
        @Multipart
        @POST("codi/add")
        Call<String> addCodi(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);
        //response는 true/false

        //데이터로 코디 추가
        @Headers("Content-Type: application/json")
        @POST("codi/add/data")
        Call<String> addCodiFrData(@Body CodiVO codiInfo);


        //모든 코디 정보 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("codi/share")
        Call<List<CodiVO>> myAllCodi(@Query("userID") String userID, @Query("page") String page, @Query("pageSize") String pageSize);

        @Headers("Content-Type: application/json")
        @PUT("codi/search")
        Call<List<CodiVO>> searchCodi(@Body CodiVO codiFilter, @Query("userID") String userID, @Query("page") String page, @Query("pageSize") String pageSize);

        //코디 정보 수정하기
        @Headers("Content-Type: application/json")
        @PUT("codi/modify")
        Call<String> modifyCodi(@Body CodiVO codiFilter);

        //코디 삭제
        //codiNo 파라미터로 받아 API URL을 완성해서 DELETE 방식으로 요청
        @DELETE("codi/delete/{codiNo}")
        Call<String> deleteCodi(@Path("codiNo") String codiNo);
        //response는 ok/fail

    }
}
