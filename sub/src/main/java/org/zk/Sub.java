package org.zk;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订阅者
 * Created by zhangkang on 2016/12/29.
 */
public class Sub implements MessageListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(Sub.class);

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            try {
                LOGGER.info("哈，我接收到了消息：{}", txtMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void receive(String topic) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topic);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new Sub());
        LOGGER.info("订阅topic:{}", topic);
    }

    public static void main(String[] args) throws Exception {
        //Sub.receive("topicA");
        Sub.receive("topicB");
    }

}
