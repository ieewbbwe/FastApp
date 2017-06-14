package com.webber.mcorelibspace.demo.net.response;

import com.android_mobile.net.response.BaseResponse;

import java.util.List;

/**
 * Created by mxh on 2016/11/24.
 * Describe：
 */

public class TopNewsResponse extends BaseResponse {

    public List<TopNewsItem> newslist;

    public class TopNewsItem {
        private String ctime;
        private String title;
        private String picUrl;
        private String url;
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
