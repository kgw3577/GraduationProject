package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.VO.UserVO;

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

public class ClothesService extends APIAdapter {
//웹서버로 전송하는 어댑터

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
        @POST("codi/add")
        Call<String> addClothes(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);
        //response는 true/false

        //모든 옷 정보 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("clothes/all")
        Call<List<ClothesVO>> myAllClothes(@Query("page") String page, @Query("pageSize") String pageSize);

        //특정 카테고리 목록 가져오기
        @GET("clothes/search")
        Call<List<ClothesVO>> chooseCategory(@Query("category") String category, @Query("page") String page, @Query("pageSize") String pageSize);

        //즐겨찾기한 옷 목록 가져오기
        @GET("clothes/search")
        Call<List<ClothesVO>> favoriteClothes(@Query("like") String like, @Query("page") String page, @Query("pageSize") String pageSize);

        //옷 정보 수정하기
        @Headers("Content-Type: application/json")
        @PUT("clothes/modify")
        Call<String> modifyClothes(@Body ClothesVO clothesFilter);

        //옷 삭제
        //id를 파라미터로 받아 API URL을 완성해서 DELETE 방식으로 요청
        @DELETE("clothes/delete/{cloNo}")
        Call<String> deleteClothes(@Path("cloNo") String cloNo);
        //response는 ok/fail

    }
}
