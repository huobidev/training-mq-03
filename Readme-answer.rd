# 进入kafka安装目录


# 启动zookeeper
./bin/zookeeper-server-start.sh -daemon ./config/zookeeper.properties


# 启动kafka
./bin/kafka-server-start.sh -daemon ./config/server.properties



### 作业要求：结果分（11分+6分=17分）
1. `Producer`接口应该有序发送消息：2分
答：在producer的配置中，进行如下设置即可
```
// max.in.flight.requests.per.connection限制客户端在单个连接上能够发送的未响应请求的个数。
// 设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。
// 注意：设置此参数是保证了有序
props.put("max.in.flight.requests.per.connection",1);

```

2. `Producer`接口应该不丢失消息：2分
答：消息的不丢失不是绝对的，但是可以在producer的配置中，通过如下设置，最大程度降低消息丢失的可能性


```
    //所有follower都响应了才认为消息提交成功，最大的降低消息丢失的可能性
    props.put("acks", "all");
```
3. `Consumer`接口应该顺序消费消息:2分
答：同一topic的消息在同一个partition中是有序的，针对顺序消费是也是基于此完成的。

4. `Consumer`接口应该支持从offset重新接收消息：2分
答：`Consumer`接口应该支持从offset重新接收消息，需要在订阅主题（topic）的时候，增加一个实现了ConsumerRebalanceListener接口的参数，
并且在consumer.seek(partition,offset)的offset中完成偏移接收消息。

5. `Consumer`接口应该支持消息的幂等处理，即根据id去重：3分
答：'Consumer'拉取到的消息后，处理前先根据id去db中查询消息是否已经消费过，如果消费过则不再消费，如果没有消费，则进行消费，并在消费完成后，存到db中，供下次查询。

6. 如果接口对外暴露的使用方式，完全屏蔽了kafka的原生接口和类，额外加2分
7. 如果程序都正确，而且写了单元测试，额外加4分，（需要开发环境的kafka可以自己run一个，也可以找fenghong要一个现成的）
