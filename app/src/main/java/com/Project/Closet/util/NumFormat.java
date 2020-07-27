package com.Project.Closet.util;

import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class NumFormat {

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 8;
        public static final int YEAR = 365;
    }

    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;

        Date dateRegTime = new Date(regTime);
        Date dateCurTime = new Date(curTime);

        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) { //7일까지는 표시
            msg = (diffTime) + "일 전";
        } else if (dateRegTime.getYear()==dateCurTime.getYear()) { //같은 년도면
            msg = new SimpleDateFormat("M월 d일").format(new Timestamp(regTime));
        } else { //다른 년도면
            msg = new SimpleDateFormat("yyyy년 M월 d일").format(new Timestamp(regTime));
        }


            /*
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            diffTime /= TIME_MAXIMUM.MONTH;
            msg = (diffTime) + "년 전";
        }
*/
        return msg;
    }







    private static class NUM_UNIT{
        public static final double K = 1000.0;
        public static final double K10 = 10000.0;
    }

    public static String formatNumString(int num) {

        double doubleNum = (double)num;
        System.out.println(doubleNum);

        DecimalFormat df = new DecimalFormat("0.#");
        df.setRoundingMode(RoundingMode.DOWN);

        String msg = null;
        if(doubleNum == 0.0){ //0일 경우 표시하지 않음
            msg = "";
        }
        else if (doubleNum < NUM_UNIT.K) {
            msg = num+"";
        } else if (( doubleNum /= NUM_UNIT.K) < NUM_UNIT.K10/NUM_UNIT.K) {
            msg =  df.format(doubleNum)+ "천"; //2.5천

        } else {
            doubleNum /= NUM_UNIT.K10/NUM_UNIT.K;
            msg =  df.format(doubleNum)+ "만"; //125.1만
        }

        return msg;
    }


}

