package com.Project.Closet;

public class Global {
    //public static final String baseURL = "http://192.168.0.3:8080/";     //로컬 주소
    public static final String baseURL = "http://13.209.3.5:8080/"; //AWS 주소
    public static final String baseURLDeep = "http://34.64.198.139:5000/"; //딥러닝 주소

    private static Global instance = null;

    public static synchronized Global getInstance(){
        if(null == instance){
            instance = new Global();
        }
        return instance;
    }
}
