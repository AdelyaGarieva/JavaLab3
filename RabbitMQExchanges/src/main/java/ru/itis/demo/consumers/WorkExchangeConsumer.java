package ru.itis.demo.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import ru.itis.demo.helpers.PdfGenerator;
import ru.itis.demo.models.User;

@SpringBootApplication
@ComponentScan({
        "ru.itis.demo.consumers",
        "ru.itis.demo.models",
        "ru.itis.demo.helpers"
})
public class WorkExchangeConsumer implements CommandLineRunner {
    private static final String EXCHANGE_NAME = "work";

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder(WorkExchangeConsumer.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, EXCHANGE_NAME, "");
        channel.basicConsume(queue, false, (tag, message) -> {
            User user = objectMapper.readValue(message.getBody(), User.class);
            pdfGenerator.generatePdf(user, "work");
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, tag -> {});
    }
}
