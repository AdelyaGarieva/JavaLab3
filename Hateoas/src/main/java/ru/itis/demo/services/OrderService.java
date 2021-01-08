package ru.itis.demo.services;

import ru.itis.demo.models.Order;

public interface OrderService {
    Order changeStatusToDelivered(Long orderID);
    Order changeStatusToReceived(Long orderID);
}
