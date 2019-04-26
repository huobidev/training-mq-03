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

### 作业要求：结果分（11分+6分=17分）
1. `Producer`接口应该有序发送消息：2分
2. `Producer`接口应该不丢失消息：2分
3. `Consumer`接口应该顺序消费消息:2分
4. `Consumer`接口应该支持从offset重新接收消息：2分
5. `Consumer`接口应该支持消息的幂等处理，即根据id去重：3分
6. 如果接口对外暴露的使用方式，完全屏蔽了kafka的原生接口和类，额外加2分
7. 如果程序都正确，而且写了单元测试，额外加4分，（需要开发环境的kafka可以自己run一个，也可以找fenghong要一个现成的）

### 提交方式：过程分（3分）
1. 注册一个github账号，已经有的话本步骤可以忽略，1分
2. fork本仓库到自己的github账号下，1分
3. git clone 自己的仓库地址，
4. 设计一个 `io.github.huobidev.自己姓名拼音.Producer` 接口，需满足以上要求，并尝试实现
5. 设计一个 `io.github.huobidev.自己姓名拼音.Consumer` 接口，需满足以上要求，并尝试实现
6. 提交以上代码，并提交一个Pull Request到本仓库，在钉钉群回复已经完成，1分

### 评分方式
1. 由`fenghong`、`guoyemeng`、`zhengye`、`qinjinwei`作为评委，评分后加到相应的小组，理论上每个同学可以为自己小组加20分。
2. 评审后的代码将会被合并到master，最终结果以master的合并情况为准。
3. 第四课时，我们会讲解这些内容，并结合流式改造的实际设计和实现进行分析讨论。

### 提交时间与注意事项
- 请各位于 2019-5-4 中午12点之前提交代码，过期代码视为无效。
- 另外kafka的依赖，已经添加到了pom文件。
- 建议使用idea完成作业。
- 小组内可以讨论。

