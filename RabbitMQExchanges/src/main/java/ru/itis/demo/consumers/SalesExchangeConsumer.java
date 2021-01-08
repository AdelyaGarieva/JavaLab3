package ru.itis.demo.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.itis.demo.helpers.PdfGenerator;
import ru.itis.demo.models.User;

public class SalesExchangeConsumer implements CommandLineRunner {
    private static final String LOSS_ROUTING_KEY = "sale.*";
    private static final String SALE_EXCHANGE = "sale_docs";

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SalesExchangeConsumer.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, SALE_EXCHANGE, LOSS_ROUTING_KEY);
        channel.basicConsume(queueName, false, (tag, message) -> {
            User user = objectMapper.readValue(message.getBody(), User.class);
            pdfGenerator.generatePdf(user,  message.getEnvelope().getRoutingKey());
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false );
        }, tag -> {});
    }
}
