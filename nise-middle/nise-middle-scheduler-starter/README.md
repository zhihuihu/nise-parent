### 简介
> 该`scheduler`采用的是`spring`的`ThreadPoolTaskScheduler`线程池作为动态的定时调度器,

### `yml`配置
```yaml
nise:
  scheduler:
    enabled: true
```

### 正式代码
- 如果想自定义线程池处理
```java
@Configuration
public class SchedulerConfig {

    /**
     * 用户可以自己重新定义该线程池，只要名称相同即可
     * @param niseSchedulerProperties
     * @return
     */
    @Bean(name = "niseThreadPoolTaskScheduler")
    public ThreadPoolTaskScheduler niseThreadPoolTaskScheduler(NiseSchedulerProperties niseSchedulerProperties) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(niseSchedulerProperties.getPoolSize());
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(niseSchedulerProperties.getRemoveOnCancelPolicy());
        threadPoolTaskScheduler.setThreadNamePrefix(niseSchedulerProperties.getThreadName());
        return threadPoolTaskScheduler;
    }

}
```
- 新增删除定时任务
```java
@RestController
@RequestMapping(value = "task")
public class TaskController {

    @Autowired
    private ScheduledRegistrar scheduledRegistrar;

    @GetMapping(value = "test")
    public Object test(){
        scheduledRegistrar.addCronTask("test","0/10 * * * * ?",() -> {
            System.out.println(LocalDateTime.now() + "aaa");
            try{
                Thread.sleep(12000L);
            }catch (Exception ex){

            }
        });
        return "SUCCESS";
    }

    @GetMapping(value = "cancel")
    public Object cancel(String taskName){
        scheduledRegistrar.removeCronTask(taskName);
        return "SUCCESS";
    }

}
```
