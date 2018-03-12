package me.codekiller.com.shavanti.Model.Bean;

import java.util.List;

/**
 * Created by Lollipop on 2018/3/1.
 */

public class JuheNews {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"db40f42758d569313d9e3abd20c3f50f","title":"云霄飞车故障急刹车 35人被困40米高空1小时","date":"2018-03-01 10:39","category":"头条","author_name":"看看新闻网","url":"http://mini.eastday.com/mobile/180301103911362.html","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_2_mwpm_03200403.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"uniquekey":"db40f42758d569313d9e3abd20c3f50f","title":"云霄飞车故障急刹车 35人被困40米高空1小时","date":"2018-03-01 10:39","category":"头条","author_name":"看看新闻网","url":"http://mini.eastday.com/mobile/180301103911362.html","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_2_mwpm_03200403.jpg"}]
         */

        private String stat;
        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean extends BaseBean{
            /**
             * uniquekey : db40f42758d569313d9e3abd20c3f50f
             * title : 云霄飞车故障急刹车 35人被困40米高空1小时
             * date : 2018-03-01 10:39
             * category : 头条
             * author_name : 看看新闻网
             * url : http://mini.eastday.com/mobile/180301103911362.html
             * thumbnail_pic_s : http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_3_mwpm_03200403.jpg
             * thumbnail_pic_s02 : http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_1_mwpm_03200403.jpg
             * thumbnail_pic_s03 : http://04.imgmini.eastday.com/mobile/20180301/20180301103911_e0da1225500d94379c05a1a23c07f20b_2_mwpm_03200403.jpg
             */

            private String uniquekey;
            private String title;
            private String date;
            private String category;
            private String author_name;
            private String url;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof DataBean && getUrl() != null && getUrl().equals(((DataBean)obj).getUrl());
            }
        }
    }
}
