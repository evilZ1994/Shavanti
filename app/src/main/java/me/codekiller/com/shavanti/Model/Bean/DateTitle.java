package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/3/1.
 */

public class DateTitle extends BaseBean{


    @Override
    public boolean equals(Object obj) {
        return obj instanceof DateTitle && ((DateTitle)obj).getKeyDate()!=null && getKeyDate()!=null && ((DateTitle)obj).getKeyDate().equals(getKeyDate());
    }
}
