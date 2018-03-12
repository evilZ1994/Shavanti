package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class LaifudaoJoke extends BaseBean{
    /**
     * title :
     * content :
     * poster :
     * url : http://www.laifudao.com/wangwen/76046.htm
     */

    private String title;
    private String content;
    private String poster;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LaifudaoJoke && getUrl() != null && getUrl().equals(((LaifudaoJoke)obj).getUrl());
    }
}
