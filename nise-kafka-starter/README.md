# 基础介绍
> 该项目主要用做快速引入`kafka`的来使用，配置如下
```yaml
nise:
  kafka:
    enabled: true             #是否启用kafka   true:是   false:否
    useDelay: false           #是否启用延时队列   true:是   false:否
    autoCreateTopic: false    #是否自动创建TOPIC   true:是   false:否
    retryCount: 5             #重试次数   0代表不重试
    bootstrapServer: kafka1:9092,kafka2:9092,kafka3:9092    #后台服务访问地址
```

