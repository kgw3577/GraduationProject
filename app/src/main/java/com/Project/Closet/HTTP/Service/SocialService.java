package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.HTTP.VO.CodiVO;
import com.Project.Closet.HTTP.VO.FeedVO;

import java.util.HashMap;
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

public class SocialService extends APIAdapter {
//웹서버로 전송하는 어댑터

    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static SocialAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (SocialAPI) retrofit(context, SocialAPI.class);
    }
    public interface SocialAPI{

        // 최신순 피드 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("social/feed/share")
        Call<List<FeedVO>> showAllFeed(@Query("page") String page, @Query("pageSize") String pageSize);

        //내 게시글 리스트 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("social/comment/{boardNo}")
        Call<List<FeedVO>> showCommentInBoard(@Path("boardNo") String boardNo, @Query("page") String page, @Query("pageSize") String pageSize);

    }
}
