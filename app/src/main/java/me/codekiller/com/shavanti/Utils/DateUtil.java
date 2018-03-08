package me.codekiller.com.shavanti.Utils;

import android.content.Context;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import me.codekiller.com.shavanti.R;

/**
 * Created by Lollipop on 2018/2/27.
 */

public class DateUtil {

    public static long CountDays(){
        Calendar calStart = Calendar.getInstance();
        //月份从0开始计数，所以2月要设置为1
        calStart.set(2013, 1, 14, 0, 0, 0);

        Calendar calNow = Calendar.getInstance();

        return  (calNow.getTimeInMillis() - calStart.getTimeInMillis()) / (1000 * 24 * 60 * 60);
    }

    /**
     * yyyy年MM月dd日
     */
    public static String dateFormat(Date date, Context context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.date_format), Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取每天数据的刷新时间，早上8点
     * @return
     */
    public static long getLimitTime(){
        Calendar limitTime = Calendar.getInstance(TimeZone.getDefault());
        limitTime.set(limitTime.get(Calendar.YEAR), limitTime.get(Calendar.MONTH), limitTime.get(Calendar.DATE), 8, 0);

        return limitTime.getTimeInMillis();
    }
}
