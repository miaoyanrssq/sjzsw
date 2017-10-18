package com.zjrb.sjzsw.entity;

import java.util.List;

/**
 * Created by jinzifu on 2017/10/17.
 * Email:jinzifu123@163.com
 * 类描述:
 */

public class GirlList {
    private List<NewslistBean> newslist;

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2016-12-04 21:00
         * title : 粉弯玉足 baby嘉茵 纯纯学生装娇滴护士制服诱惑
         * description : 美女写真
         * picUrl : http://m.xxxiao.com/wp-content/uploads/sites/3/2016/10/m.xxxiao.com_0d30d85aff7811792723bc5305820c25-683x1024.jpg
         * url : http://m.xxxiao.com/80515
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
