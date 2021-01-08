package producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import models.UserInfo;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    private final static String EXCHANGE_NAME = "documents";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        // создаем фабрику подключений
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // говорим, куда подключаться
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            // создаем канал
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

            Scanner scanner = new Scanner(System.in);
            UserInfo info = new UserInfo();

            System.out.println("Please, enter info:");
            System.out.println("Name:");
            info.setName(scanner.nextLine());

            System.out.println("Surname");
            info.setSurname(scanner.nextLine());

            System.out.println("Passport number");
            info.setPassportNumber(scanner.nextLine());

            System.out.println("Age");
            info.setAge(scanner.nextLine());

            System.out.println("Date of issue");
            info.setIssueDate(scanner.nextLine());

            ObjectMapper objectMapper = new ObjectMapper();
            String infoJson = objectMapper.writeValueAsString(info);

            channel.basicPublish(EXCHANGE_NAME, "", null, infoJson.getBytes());
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
