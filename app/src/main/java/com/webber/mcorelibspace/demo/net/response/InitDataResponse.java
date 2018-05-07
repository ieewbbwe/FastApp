package com.webber.mcorelibspace.demo.net.response;

import java.util.List;

/**
 * Created by picher on 2018/5/7.
 * Describe：
 */

public class InitDataResponse {


    /**
     * message :
     * response : [{"id":11,"id_core":"001","parent_id":"","name":"多媒体类型","update_time":"2017-11-03T17:47:13.206387+08:00","other":"一级分类"},{"id":12,"id_core":"001001","parent_id":"001","name":"视屏","update_time":"2017-11-03T17:48:19.028001+08:00","other":"二级分类"},{"id":13,"id_core":"001002","parent_id":"001","name":"图片","update_time":"2017-11-03T17:48:34.591862+08:00","other":"二级分类"},{"id":14,"id_core":"002","parent_id":"","name":"产品类型","update_time":"2017-11-03T17:48:59.168925+08:00","other":"一级分类"},{"id":15,"id_core":"002001","parent_id":"002","name":"吃-酒家","update_time":"2018-01-15T11:33:48.073428+08:00","other":"二级分类"},{"id":16,"id_core":"002001001","parent_id":"002001","name":"菜","update_time":"2017-11-03T17:55:29.938587+08:00","other":"三级分类"},{"id":17,"id_core":"002002","parent_id":"002","name":"玩-景点","update_time":"2017-11-03T17:55:51.531751+08:00","other":"二级分类"},{"id":18,"id_core":"002002001","parent_id":"002002","name":"风景","update_time":"2017-11-03T17:56:13.808174+08:00","other":"三级分类"},{"id":19,"id_core":"002003","parent_id":"002","name":"住-宾馆","update_time":"2017-11-03T17:56:49.048235+08:00","other":"二级分类"},{"id":20,"id_core":"002003001","parent_id":"002003","name":"房型","update_time":"2017-11-03T17:57:11.322664+08:00","other":"三级分类"},{"id":21,"id_core":"003","parent_id":"","name":"筛选参数","update_time":"2017-11-03T17:57:37.156893+08:00","other":"一级分类"},{"id":22,"id_core":"003001","parent_id":"003","name":"区域","update_time":"2017-11-03T17:57:59.813810+08:00","other":"二级分类"},{"id":23,"id_core":"003001001","parent_id":"003001","name":"西陵区","update_time":"2017-11-03T17:58:36.115501+08:00","other":"三级分类"},{"id":24,"id_core":"003001002","parent_id":"003001","name":"伍家岗区","update_time":"2017-11-03T17:58:56.096480+08:00","other":"三级分类"},{"id":25,"id_core":"003001003","parent_id":"003001","name":"点军区","update_time":"2017-11-03T17:59:50.084146+08:00","other":"三级分类"},{"id":26,"id_core":"003001004","parent_id":"003001","name":"猇亭区","update_time":"2017-11-03T18:00:07.199526+08:00","other":"三级分类"},{"id":27,"id_core":"003001005","parent_id":"003001","name":"夷陵区","update_time":"2017-11-03T18:00:32.958488+08:00","other":"三级分类"},{"id":28,"id_core":"003001006","parent_id":"003001","name":"秭归县","update_time":"2017-11-03T18:01:07.510459+08:00","other":"三级分类"},{"id":29,"id_core":"003001007","parent_id":"003001","name":"远安县","update_time":"2017-11-03T18:01:26.307087+08:00","other":"三级分类"},{"id":30,"id_core":"003001008","parent_id":"003001","name":"兴山县","update_time":"2017-11-03T18:01:45.950561+08:00","other":"三级分类"},{"id":31,"id_core":"003001009","parent_id":"003001","name":"长阳自治县","update_time":"2017-11-03T18:02:02.229262+08:00","other":"三级分类"},{"id":32,"id_core":"003001010","parent_id":"003001","name":"五峰自治县","update_time":"2017-11-03T18:02:21.621016+08:00","other":"三级分类"},{"id":33,"id_core":"003001011","parent_id":"003001","name":"宜都市","update_time":"2017-11-03T18:02:37.917110+08:00","other":"三级分类"},{"id":34,"id_core":"003001012","parent_id":"003001","name":"当阳市","update_time":"2017-11-03T18:02:59.126983+08:00","other":"三级分类"},{"id":35,"id_core":"003001013","parent_id":"003001","name":"枝江市","update_time":"2017-11-03T18:03:17.084029+08:00","other":"三级分类"},{"id":36,"id_core":"003002","parent_id":"003","name":"星级","update_time":"2017-11-03T18:03:55.271831+08:00","other":"二级分类"},{"id":37,"id_core":"003002001","parent_id":"003002","name":"特色民宿","update_time":"2017-11-03T18:04:22.428522+08:00","other":"三级分类"},{"id":38,"id_core":"003002002","parent_id":"003002","name":"经济型","update_time":"2017-11-03T18:04:45.963301+08:00","other":"三级分类"},{"id":39,"id_core":"003002003","parent_id":"003002","name":"三星级","update_time":"2017-11-03T18:05:08.697758+08:00","other":"三级分类"},{"id":40,"id_core":"003002004","parent_id":"003002","name":"四星级","update_time":"2017-11-03T18:05:26.287553+08:00","other":"三级分类"},{"id":41,"id_core":"003002005","parent_id":"003002","name":"五星级","update_time":"2017-11-03T18:05:45.531483+08:00","other":"三级分类"},{"id":42,"id_core":"003003","parent_id":"003","name":"价格","update_time":"2017-11-03T18:06:02.346978+08:00","other":"二级分类"},{"id":43,"id_core":"003003001","parent_id":"003003","name":"100元以下","update_time":"2017-11-03T18:06:20.282321+08:00","other":"三级分类"},{"id":44,"id_core":"003003002","parent_id":"003003","name":"101~300元","update_time":"2017-11-03T18:06:34.851364+08:00","other":"三级分类"},{"id":45,"id_core":"003003003","parent_id":"003003","name":"301~500元","update_time":"2017-11-03T18:06:52.379924+08:00","other":"三级分类"},{"id":46,"id_core":"003003004","parent_id":"003003","name":"501~1000元","update_time":"2017-11-03T18:07:09.107273+08:00","other":"三级分类"},{"id":47,"id_core":"003003005","parent_id":"003003","name":"1000元以上","update_time":"2017-11-03T18:07:36.837565+08:00","other":"三级分类"},{"id":48,"id_core":"003004","parent_id":"003","name":"景观类型","update_time":"2017-11-03T18:07:53.710682+08:00","other":"二级分类"},{"id":49,"id_core":"003004001","parent_id":"003004","name":"自然景观","update_time":"2017-11-03T18:08:14.407654+08:00","other":"三级分类"},{"id":50,"id_core":"003004002","parent_id":"003004","name":"人文景观","update_time":"2017-11-03T18:08:31.696000+08:00","other":"三级分类"},{"id":51,"id_core":"003005","parent_id":"003","name":"酒家类型","update_time":"2017-11-03T18:08:49.677290+08:00","other":"二级分类"},{"id":52,"id_core":"003005001","parent_id":"003005","name":"农家菜馆","update_time":"2017-11-03T18:09:04.629070+08:00","other":"三级分类"},{"id":53,"id_core":"003005002","parent_id":"003005","name":"特色菜馆","update_time":"2017-11-03T18:11:04.454535+08:00","other":"三级分类"},{"id":54,"id_core":"003005003","parent_id":"003005","name":"日韩料理","update_time":"2017-11-03T18:09:51.486120+08:00","other":"三级分类"},{"id":55,"id_core":"003005004","parent_id":"003005","name":"西餐甜品","update_time":"2017-11-03T18:10:27.400510+08:00","other":"三级分类"},{"id":56,"id_core":"003005005","parent_id":"003005","name":"火锅","update_time":"2017-11-03T18:10:44.493577+08:00","other":"三级分类"},{"id":58,"id_core":"002004","parent_id":"002","name":"购物-商超","update_time":"2018-01-15T11:34:10.739087+08:00","other":"二级分类"},{"id":59,"id_core":"002005","parent_id":"002","name":"娱乐-潮范","update_time":"2018-01-15T11:34:59.214481+08:00","other":"二级分类"},{"id":60,"id_core":"003006","parent_id":"003","name":"购物类型","update_time":"2018-04-29T01:27:20.674000+08:00","other":""},{"id":61,"id_core":"003006001","parent_id":"003006","name":"购物天堂","update_time":"2018-04-29T01:27:48.001000+08:00","other":""},{"id":62,"id_core":"003006002","parent_id":"003006","name":"商超一体","update_time":"2018-04-29T01:28:02.335000+08:00","other":""},{"id":63,"id_core":"003006003","parent_id":"003006","name":"家装市场","update_time":"2018-04-29T01:28:17.464000+08:00","other":""},{"id":64,"id_core":"003006004","parent_id":"003006","name":"汽车之家","update_time":"2018-04-29T01:28:30.945000+08:00","other":""},{"id":65,"id_core":"003007","parent_id":"003","name":"娱乐类型","update_time":"2018-04-29T01:29:12.895000+08:00","other":""},{"id":66,"id_core":"003007001","parent_id":"003007","name":"KTV","update_time":"2018-04-29T01:29:21.704000+08:00","other":""},{"id":67,"id_core":"003007002","parent_id":"003007","name":"酒吧","update_time":"2018-04-29T01:29:32.609000+08:00","other":""},{"id":68,"id_core":"003007003","parent_id":"003007","name":"VR体验馆","update_time":"2018-04-29T01:29:44.649000+08:00","other":""},{"id":69,"id_core":"003007004","parent_id":"003007","name":"轰趴馆","update_time":"2018-04-29T01:30:04.692000+08:00","other":""},{"id":70,"id_core":"003007005","parent_id":"003007","name":"剧场剧院","update_time":"2018-04-29T01:30:25.534000+08:00","other":""},{"id":71,"id_core":"003007006","parent_id":"003007","name":"新鲜玩意","update_time":"2018-04-29T01:30:39.831000+08:00","other":""}]
     * statusCode : 200
     */

    private String message;
    private int statusCode;
    private List<ResponseBean> response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 11
         * id_core : 001
         * parent_id :
         * name : 多媒体类型
         * update_time : 2017-11-03T17:47:13.206387+08:00
         * other : 一级分类
         */

        private int id;
        private String id_core;
        private String parent_id;
        private String name;
        private String update_time;
        private String other;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getId_core() {
            return id_core;
        }

        public void setId_core(String id_core) {
            this.id_core = id_core;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}
