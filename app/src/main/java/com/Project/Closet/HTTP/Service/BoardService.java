package com.Project.Closet.HTTP.Service;

import android.content.Context;

import com.Project.Closet.HTTP.APIAdapter;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.HTTP.VO.ClothesVO;

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

public class BoardService extends APIAdapter {
//웹서버로 전송하는 어댑터

    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static BoardAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (BoardAPI) retrofit(context, BoardAPI.class);
    }
    public interface BoardAPI{

        //게시글 추가
        //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
        @Multipart
        @POST("board/add")
        Call<String> addBoard(@PartMap() LinkedHashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> names);
        //response는 ok/fail

        //모든 게시글 리스트 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("board/share")
        Call<List<BoardVO>> allBoard(@Query("page") String page, @Query("pageSize") String pageSize);

        //내 게시글 리스트 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("board/my")
        Call<List<BoardVO>> myAllBoard(@Query("page") String page, @Query("pageSize") String pageSize);

        //유저 게시글 리스트 받아오기
        // Get방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
        // Bean객체를 생성하지 않고 JsonObject로 받을 수 있습니다.
        @GET("board/{userID}")
        Call<List<BoardVO>> usersAllBoard(@Path("userID") String userID, @Query("page") String page, @Query("pageSize") String pageSize);

        //내용으로 게시글 검색
        @GET("board/search")
        Call<List<BoardVO>> searchBoardByContents(@Query("contents") String contents, @Query("page") String page, @Query("pageSize") String pageSize);

        //게시글 정보 받아오기
        //boardNo를 파라미터로 받아 API URL을 완성해서 GET 방식으로 요청
        @GET("board/{boardNo}/info")
        Call<BoardVO> infoBoard(@Path("boardNo") String boardNo);

        //게시글 수정하기
        @Headers("Content-Type: application/json")
        @PUT("board/modify")
        Call<String> modifyBoard(@Body BoardVO boardFilter);
        //response는 ok/fail

        //게시글 삭제
        //boardNo 파라미터로 받아 API URL을 완성해서 DELETE 방식으로 요청
        @DELETE("board/delete/{boardNo}")
        Call<String> deleteBoard(@Path("boardNo") String boardNo);
        //response는 ok/fail

    }
}
