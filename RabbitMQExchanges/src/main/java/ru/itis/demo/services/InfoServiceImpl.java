package ru.itis.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.demo.dto.UserDTO;
import ru.itis.demo.models.User;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class InfoServiceImpl implements InfoService {
    private static final String EXCHANGE_NAME = "docs_direct_exchange";
//    private static final String SALE_QUEUE_BINDING_KEY = "sale";
//    private static final String SALE_QUEUE_NAME = "sale_queue";
//    private static final String WORK_QUEUE_BINDING_KEY = "work";
//    private static final String WORK_QUEUE_NAME = "work_queue";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void sendToExchange(UserDTO userDTO) {
        // создаем фабрику подключений
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            Connection connection = connectionFactory.newConnection();
            // создаем канал
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//            channel.queueBind(SALE_QUEUE_NAME, EXCHANGE_NAME, SALE_QUEUE_BINDING_KEY);
//            channel.queueBind(WORK_QUEUE_NAME, EXCHANGE_NAME, WORK_QUEUE_BINDING_KEY);
            User user = User.toDomain(userDTO);
            channel.basicPublish(EXCHANGE_NAME, "", null, objectMapper.writeValueAsBytes(user));
            connection.close();
        } catch (TimeoutException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
