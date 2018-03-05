package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/3/1.
 */

public class DateTitle extends BaseBean{
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DateTitle && ((DateTitle)obj).date.equals(this.date);
    }
}
