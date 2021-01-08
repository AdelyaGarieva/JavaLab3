package ru.itis.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.demo.models.Order;
import ru.itis.demo.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order changeStatusToDelivered(Long orderID) {
        Order order = orderRepository.findById(orderID).orElseThrow(IllegalArgumentException::new);
        order.changeStatusToDelivered();
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order changeStatusToReceived(Long orderID) {
        Order order = orderRepository.findById(orderID).orElseThrow(IllegalArgumentException::new);
        order.changeStatusToReceived();
        orderRepository.save(order);
        return order;
    }
}
