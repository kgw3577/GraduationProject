package com.Project.Closet.HTTP;

import android.content.Context;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.interceptor.AddCookiesInterceptor;
import com.Project.Closet.HTTP.interceptor.ReceivedCookiesInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * APIAdapter 클래스
 *
 * @autor devetude
 */
public class APIAdapter {
    /**
     * Retrofit 객체를 초기화하는 메소드
     *
     * @param context
     * @param serviceName
     * @return
     */
    protected static Object retrofit(Context context, Class<?> serviceName) {


        //통신로그를 확인하기 위한 부분
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * OkHttpClient 객체 생성 과정
         *
         * 1. OkHttpClient 객체 생성
         * 2. 세션 데이터의 획득을 위해 response 데이터 중 헤더 영역의 쿠키 값을 가로채기 위한 RecivedCookiesInterceptor 추가
         * 3. 서버로 데이터를 보내기 전 세션 데이터 삽입을 위해 AddCookiesInterceptor 추가
         * 4. OkHttpClient 빌드
         *
         * 주의) 가로채기 위한 메소드는 addInterceptor이고 삽입하기 위한 메소드는 addNetworkInterceptor
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();

        /**
         * Retrofit 객체 생성 과정
         *
         * 1. Retrofit 객체 생성
         * 2. base(api 서버) url 설정
         * 3. json 형식의 reponse 데이터의 파싱을 위해 Gson 추가
         * 3. 위에서 만든 OkHttpClient 객체를 추가
         * 4. Retrofit 빌드
         *
         * 주의) addConverterFactory를 추가하지 않을 경우 어플리케이션이 종료됨
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        /**
         * 서비스객체의 이름으로 Retrofit 객체 생성 및 반환
         *
         * ex) retrofit.create(SignService.class);
         */
        return retrofit.create(serviceName);
    }
}