# 消息队列培训-第三课

Kafka进阶

## 大作业：具体场景下的Kafka接口设计实现

### 背景
请各位同学结合定序 & 撮合的业务特点, 练习 Producer + Consuer 的用法：
- 定序要求: 数据有序并且不丢失
- 撮合要求: 可重放, 顺序消费, 消息仅处理一次

### 消息格式
消息的内容格式为：// 见本仓库的代码
```
public class Order{
private Long id；
private Long ts;
private String symbol;
private Double price;

// 省略setter getter...
}
```

### 作业要求：结果分（12分）
1. `Producer`接口应该有序发送消息：2分
2. `Producer`接口应该不丢失消息：2分
3. `Consumer`接口应该顺序消费消息:2分
4. `Consumer`接口应该支持从offset重新接收消息：3分
5. `Consumer`接口应该支持消息的幂等处理：3分

### 提交方式：过程分（3分）
1. 注册一个github账号，已经有的话本步骤可以忽略，1分
2. fork本仓库到自己的github账号下，1分
3. git clone 自己的仓库地址，
4. 设计一个 `io.github.huobidev.自己姓名拼音.Producer` 接口，需满足以上要求，并尝试实现
5. 设计一个 `io.github.huobidev.自己姓名拼音.Consumer` 接口，需满足以上要求，并尝试实现
6. 提交以上代码，并提交一个Pull Request到本仓库，在钉钉群回复已经完成，1分

### 评分方式
1. 由`fenghong`、`guoyemeng`、`zhengye`、`qinjinwei`作为评委，评分后加到相应的小组。
2. 评审后的代码将会被合并到master，最终结果以master的合并情况为准。
3. 第四课时，我们会讲解这些内容，并结合流式改造的实际设计和实现进行分析讨论。

### 提交时间
请各位于 2019-5-4 中午12点之前提交代码，过期代码视为无效。
