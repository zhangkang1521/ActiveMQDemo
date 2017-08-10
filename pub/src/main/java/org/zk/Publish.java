package org.zk;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发布者
 * Created by zhangkang on 2016/12/29.
 */
public class Publish {

    private static final Logger LOGGER = LoggerFactory.getLogger(Publish.class);

    public void send(String msg) {
        Connection connection = null;
        try {
            String url = "failover://tcp://192.168.226.129:61616";
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination_send = session.createQueue("q2");
//            Destination destination_send = session.createTopic("topicB");
            MessageProducer producer = session.createProducer(destination_send);
            TextMessage message = session.createTextMessage(msg);
            producer.send(message);
            LOGGER.info("发送消息, content:{}", msg);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        new Publish().send("topicA", "aaaaa");
        new Publish().send("b4");
    }
}
