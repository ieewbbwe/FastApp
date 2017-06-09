#使用文档
1. 修改assets/ShareSDK.XML 中各平对应的key
2. 修改Manifest 中<data android:scheme="tencent100371282" /> 为腾讯平台的id
3. 使用SocialShareHelper，对ShareSdk的简单封装
```
   socialComponent= new SocialShareHelper.Builder(this)
                 .setContent("应用描述信息")
                 .setTitle("推荐给您一款好用的APP，快来试试吧！")
                 .setTitleUrl("应用下载地址")
                 .setImageUrl("图片地址").create();
   socialComponent.show();
```

其余还有问题的，可以去ShareSdk官网上查阅集成手册。
http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/

注意：使用时请参照需要去掉一些无用lib,减少包大小