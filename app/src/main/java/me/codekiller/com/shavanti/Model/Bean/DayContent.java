package me.codekiller.com.shavanti.Model.Bean;

import java.util.List;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class DayContent {
    private String date;
    private List<BaseBean> beans;

    public List<BaseBean> getBeans() {
        return beans;
    }

    public void setBeans(List<BaseBean> beans) {
        this.beans = beans;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()){
            return false;
        }else {
            return this.getDate().equals(((DayContent)obj).getDate());
        }
    }
}
