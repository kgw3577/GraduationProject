package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.HTTP.VO.CodiVO;
import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.HTTP.VO.FollowVO;
import com.Project.Closet.HTTP.VO.UserVO;
import com.Project.Closet.HTTP.VO.UserspaceVO;

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

        /*피드*/
        // 최신순 피드 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("social/feed/newest")
        Call<List<FeedVO>> showNewestFeed(@Query("page") String page, @Query("pageSize") String pageSize);
        // 팔로우 피드 받아오기
        @GET("social/feed/following/{userID}")
        Call<List<FeedVO>> showFollowingFeed(@Path("userID") String userID, @Query("page") String page, @Query("pageSize") String pageSize);
        // 피드 한 개 받아오기
        @GET("social/feed/{boardNo}")
        Call<FeedVO> selectOneFeed(@Path("boardNo") String boardNo);

        //해당 게시물 코멘트 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("social/comment/{boardNo}")
        Call<List<CommentFeedVO>> showCommentInBoard(@Path("boardNo") String boardNo, @Query("page") String page, @Query("pageSize") String pageSize);

        /*팔로우*/
        //팔로우 검색
        @Headers("Content-Type: application/json")
        @GET("social/follow/search")
        Call<List<FollowVO>> searchFollow(@Body FollowVO followFilter);
        //팔로우 실행(revert)
        @Headers("Content-Type: application/json")
        @POST("social/follow/execute")
        Call<String> executeFollow(@Body FollowVO followInfo); //응답 : following, not_following (현재 상태), fail(실패)

        /*유저스페이스*/
        @GET("social/space/{userID}")
        Call<UserspaceVO> showUserSpace(@Path("userID") String userID, @Query("myID") String myID);

    }
}
