package me.codekiller.com.shavanti.Model.Bean;

/**
 * Created by Lollipop on 2018/2/28.
 */

public class LaifudaoPic extends BaseBean{
    /**
     * title : 莫名其妙弄个台阶还不让踩
     * thumburl : http://ww3.sinaimg.cn/large/bd759d6djw1ep7xtjiansj20c80b3wff.jpg
     * sourceurl : http://down.laifudao.com/images/tupian/2015211203522.jpg
     * height : 399
     * width : 440
     * class : 1
     * url : http://www.laifudao.com/tupian/41433.htm
     */

    private String title;
    private String thumburl;
    private String sourceurl;
    private int height;
    private int width;
    @com.google.gson.annotations.SerializedName("class")
    private String classX;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LaifudaoPic && getUrl() != null && getUrl().equals(((LaifudaoPic)obj).getUrl());
    }
}
