package consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import models.FilePDF;
import models.UserInfo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StudyLeavingConsumer {

    private final static String TITLE = "StudyLeaving";
    private final static String EXCHANGE_NAME = "documents";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // создаем временную очередь со случайным названием
            String queue = channel.queueDeclare().getQueue();

            // привязали очередь к EXCHANGE_NAME
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String infoString = new String(message.getBody());
                UserInfo userInfo = objectMapper.readValue(infoString, UserInfo.class);
                try {

                    FilePDF file = new FilePDF();
                    file.createPDF(TITLE, userInfo);
                    System.out.println("Finish create file!");
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (IOException e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
