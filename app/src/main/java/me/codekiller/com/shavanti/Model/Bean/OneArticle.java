package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/3/13.
 */

public class OneArticle extends BaseBean {
    private String title;
    private String description;
    private String article;
    private String author;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OneArticle && getUrl() != null && getUrl().equals(((OneArticle) obj).getUrl());
    }
}
