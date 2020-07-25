package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.UserVO;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public class UserService extends APIAdapter {
    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static SignAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (SignAPI) retrofit(context, SignAPI.class);
    }
    public interface SignAPI {

        //함수 선언


        //1. 회원 정보 조회
        //Get방식, 주소가 고정되지 않는 상황에서는 @Path를 통해 주소를 다이나믹하게 넣을 수 있습니다.
        //Json형식에 맞게 Bean 객체를 만들어 두면 설정한 Parser가 자동으로 컨버팅해 돌려 줍니다.
        @GET("user/myInfo/{userID}")
        Call<UserVO> myInfo(@Path("userID") String userID);


        /**
         * 회원가입 메소드
         *
         * @param email
         * @param pwd
         * @param nickname
         * @return
         */
        //@FormUrlEncoded
        //URL encoding하여 보냅니다. --- application/x-www-form-urlencoded
        //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
        @FormUrlEncoded
        Call<UserVO> up(
                @Field("email") String email,
                @Field("pwd") String pwd,
                @Field("nickname") String nickname
        );


        //회원 가입
        @Headers("Content-Type: application/json")
        @POST("user/join")
        Call<String> join(@Body UserVO userVO);

        //3. 로그인
        //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
        @Headers("Content-Type: application/json")
        @POST("user/login")
        Call<String> login(@Body UserVO userVO);
        //Call<String> login(@Field("id") String id, @Field("pwd") String pwd);
        //response는 true/false

        //Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);


        //회원 정보 수정
        @Headers("Content-Type: application/json")
        @PUT("user/modify")
        Call<String> modify(@Body UserVO userVO);

        //프로필 사진 변경
        @Multipart
        @POST("user/modify/profile_image")
        Call<String> modifyProfileImage(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);


        //회원 정보 삭제
        //id를 파라미터로 받아 API URL을 완성해서 DELETE 방식으로 요청
        @DELETE("user/delete/{id}")
        Call<String> delete(@Path("id") String id);
        //response는 ok/fail


        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("project")
        Call<JsonObject> Project(@Query("email") String email);

        //Rxandroid 사용
        //Rxandroid : 안드로이드에서 Observer 패턴, Iterator 패턴을 사용 할 수 있게 하는 라이브러리
        @GET("project")
        Observable<JsonObject> Project();


    }
}

