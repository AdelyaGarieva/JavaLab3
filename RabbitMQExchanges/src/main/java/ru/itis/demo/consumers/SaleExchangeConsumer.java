package ru.itis.demo.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.itis.demo.models.User;

public class SaleExchangeConsumer implements CommandLineRunner {
    private static final String SALE_QUEUE_NAME = "sale_queue";
    private static final String SALE_BINDING_KEY = "sale.sale";
    private static final String PR_SALE_BINDING_KEY = "sale.pr_sale";
    private static final String EXCHANGE_NAME = "sale_docs";

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SaleExchangeConsumer.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Connection exchangeConnection = connectionFactory.newConnection();
        Channel exchangeChannel = exchangeConnection.createChannel();
        exchangeChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.basicConsume(SALE_QUEUE_NAME, false, (tag, message) -> {
            User user = objectMapper.readValue(message.getBody(), User.class);
            exchangeChannel.basicPublish(EXCHANGE_NAME, SALE_BINDING_KEY, null, objectMapper.writeValueAsBytes(user));
            exchangeChannel.basicPublish(EXCHANGE_NAME, PR_SALE_BINDING_KEY, null, objectMapper.writeValueAsBytes(user));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, tag -> {
        });
    }
}
