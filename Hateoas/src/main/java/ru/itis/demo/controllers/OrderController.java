package ru.itis.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.demo.services.OrderService;

@RepositoryRestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders/{order-id}/delivered", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> delivery(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                EntityModel.of(orderService.changeStatusToDelivered(orderId)));
    }

    @RequestMapping(value = "/orders/{order-id}/received", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> atThePostOffice(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(
                EntityModel.of(orderService.changeStatusToReceived(orderId)));
    }
}
