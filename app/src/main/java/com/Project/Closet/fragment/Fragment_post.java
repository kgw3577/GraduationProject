package com.Project.Closet.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.BoardService;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.R;
import com.Project.Closet.share.activity_post;
import com.Project.Closet.share.fragment_share;
import com.Project.Closet.util.BoardListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Fragment_post extends Fragment {

    fragment_share parentFragment;

    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize=2;
    String pagesize="8";

    int page=0;
    RecyclerView rv_post;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
    
    //리사이클러뷰 어댑터
    BoardListAdapter boardListAdapter;
    Call<List<BoardVO>> boardListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    public static Fragment_post newInstance(String identifier, String size) {

        Bundle args = new Bundle();
        args.putString("identifier", identifier);  // 키값, 데이터
        args.putString("size", size);

        Fragment_post fragment = new Fragment_post();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentFragment = ((fragment_share)getParentFragment());


        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
            size = args.getString("size");

            switch (size){
                case "small":
                    gridsize = 4; //스몰 그리드 4x5
                    pagesize="26"; //스몰 페이지 사이즈 25
                    break;
                case "medium":
                    gridsize = 3; //미디엄 그리드 3x4
                    pagesize="18"; //미디엄 페이지 사이즈 17
                    break;
                case "large":
                    gridsize = 2; //라지 그리드 2x3
                    pagesize="8"; //라지 페이지 사이즈 7
                    break;
            }
        }


        //리사이클러뷰 어댑터 초기화
        boardListAdapter = new BoardListAdapter(boardList); //추후 수정

        boardListAdapter.setOnItemClickListener(new BoardListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getContext(), activity_post.class);
                startActivity(intent);

                /*
                BoardVO boardInfo = null;
                try {
                    boardInfo = new InfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.toString(boardList.get(position).getNo())).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                parentFragment.setInfo(boardInfo);
                */

            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //현재 페이지수와 함께 웹서버에 옷 데이터 요청
        new networkTask().execute(Integer.toString(page));

        //리사이클러 뷰 설정하기
        View view = inflater.inflate(R.layout.layout_share, container, false);
        rv_post = (RecyclerView) view.findViewById(R.id.post_list);
        rv_post.setLayoutManager(new GridLayoutManager(getContext(), gridsize)); //그리드 사이즈 설정
        rv_post.setAdapter(boardListAdapter);
        rv_post.setNestedScrollingEnabled(true);


        rv_post.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!rv_post.canScrollVertically(-1)) {
                    //스크롤이 최상단이면 데이터를 갱신한다
                    //page = 0;
                    //new networkTask().execute(Integer.toString(page));
                    //Log.e("test","데이터 갱신");
                }
                else if (!rv_post.canScrollVertically(1)) {
                    //스크롤이 최하단이면 웹서버에 다음 페이지 옷 데이터 요청
                    new networkTask().execute(Integer.toString(++page));
                    Log.e("test","페이지 수 증가");
                }
                else {
                }
            }
        });

        return view;
    }





    public class networkTask extends AsyncTask<String, Void, List<BoardVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected List<BoardVO> doInBackground(String... params) {

            if(identifier==null)
                boardListCall = BoardService.getRetrofit(getActivity()).allBoard(params[0], pagesize);
            else{
                switch(identifier){
                    case "all" : //모든 게시글 조회
                        boardListCall = BoardService.getRetrofit(getActivity()).allBoard(params[0], pagesize);
                        break;
                    case "clothes" : //옷 게시글 조회
                        boardListCall = BoardService.getRetrofit(getActivity()).allBoard_Clothes(params[0], pagesize);
                        break;
                    case "codi" : //코디 게시글 조회
                        boardListCall = BoardService.getRetrofit(getActivity()).allBoard_Codi(params[0], pagesize);
                        break;
                    case "my" : //내 게시글 조회
                        boardListCall = BoardService.getRetrofit(getActivity()).myAllBoard(params[0], pagesize);
                        break;
                    default : //해당 유저 게시글 조회 -> 해당하는 case가 없을 경우 identifier가 userID임.
                        boardListCall = BoardService.getRetrofit(getActivity()).usersAllBoard(identifier, params[0], pagesize);
                        break;
                }
            }
            //인자 params[0]은 page.

            try {
                return boardListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<BoardVO> boardlist) {
            super.onPostExecute(boardlist);
            if(boardlist!=null) {
                for(BoardVO e:boardlist) {
                    //게시글 데이터를 받아온 후 이미지 url 리스트를 갱신
                    ImageUrlList.add(new String(Global.baseURL+e.getFilePath()));
                    boardList.add(e);
                    Log.e("item", e.getSubject());
                }
                boardListAdapter.notifyDataSetChanged();
            }
        }
    }

/*
    public class InfoTask extends AsyncTask<String, Void, BoardVO> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected BoardVO doInBackground(String... boardNo) {

            Call<BoardVO> boardVOCall = ClothesService.getRetrofit(getContext()).infoClothes(boardNo[0]);
            try {
                return boardVOCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(BoardVO c) {
            super.onPostExecute(c);
        }
    }
*/




    @Override
    public void onResume() {
        super.onResume();
    }

    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }


}
