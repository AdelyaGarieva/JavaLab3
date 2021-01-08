package ru.itis.demo.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.demo.controllers.OrderController;
import ru.itis.demo.models.Order;
import ru.itis.demo.models.Status;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Order>> {
    @Override
    public EntityModel<Order> process(EntityModel<Order> model) {
        Order order = model.getContent();

        if (order != null && order.getStatus().equals(Status.NOT_DELIVERED)) {
            model.add(linkTo(methodOn(OrderController.class)
                    .delivery(order.getId())).withRel("delivered"));
        }
        if (order != null && order.getStatus().equals(Status.DELIVERED)) {
            model.add(linkTo(methodOn(OrderController.class)
                    .atThePostOffice(order.getId())).withRel("received"));
        }

        return model;
    }
}
