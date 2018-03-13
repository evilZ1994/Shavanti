package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/3/12.
 */

public class OnePic extends BaseBean {
    private String url;
    private String imgUrl;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OnePic && getUrl() != null && getUrl().equals(((OnePic)obj).getUrl());
    }
}
