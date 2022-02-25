package com.github.nise.kafka.consumer;

/**
 * 对应用户配置的消息监听
 *
 * @author JiangZhe  2/20/22.
 */
public interface MessageListener {


  /**
   * 消费群组
   *
   * @return 消费群组
   */
    String getGroupId();

    /**
     * 消息主题，表示只接受给定主题下的消息
     *
     * @return 消息主题
     */
    String getTopic();

    /**
     * 处理接收到的消息
     *
     * @param message 收到的消息
     */
    void process(ConsumeMessage message);
}
