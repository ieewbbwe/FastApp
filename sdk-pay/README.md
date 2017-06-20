#使用文档
库中引入了支付宝支付，微信支付两种支付方式。
1. 在PayConstants 中配置好WX_APP_ID，申请地址https://open.weixin.qq.com/
2. 与公司后端协商，定义生成orderInfo的接口地址，在PayUrlMgr中配置
3. 可以在PayApi中修改订单接口的请求参数，RewardAliResp和RewardWxResp分别是支付宝订单信息，微信订单信息的响应参数
4. WXPayEntryActivity这里做了修改，可以同时处理支付宝和微信的回调，并根据支付code做出响应
5. 注意请按照需要自行添加 notifyService() 通知后端支付结果
6. 使用方法new PayHelper().aliPrePay()

还有问题的，请看官网接入文档：
支付宝支付：https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.CoadnK&treeId=204&articleId=105296&docType=1
微信支付：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5