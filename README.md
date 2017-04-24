# PayCenter
对接多个支付渠道，由于各个支付渠道参数请求参数，返回参数无话八门，无法做到统一处理；改项目抽象统一支付，屏蔽渠道之间的支付差距，方便快速统一接入支付渠道；

## 项目结构说明

 - pay-center-core  支付骨架
 - pay-center-union 银联支付渠道实现,依赖pay-center-core
 - pay-center-baofu 宝付支付渠道实现，依赖pay-center-core
 - pay-center-api 支付门面，统一支付参数，路由到制定支付渠道
 - pay-center-test 测试
 - pay-center-design 设计文档素材
## 设计
### 类图
![class](https://github.com/qyuhy/PayCenter/tree/master/pay-center-design/images/class_structure.png)

