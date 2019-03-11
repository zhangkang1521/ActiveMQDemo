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


    public static void main(String[] args) throws Exception{
        String url = "failover://tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("HelloWorldQueue");
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 5; i++) {
            TextMessage textMessage1 = session.createTextMessage("hello");
            producer.send(textMessage1);
        }

        session.close();
        connection.close();
    }
}
