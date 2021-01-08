package ru.itis.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.demo.models.*;
import ru.itis.demo.repositories.GoodRepository;
import ru.itis.demo.repositories.OrderRepository;
import ru.itis.demo.repositories.UserRepository;
import ru.itis.demo.services.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class OrderTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        when(orderService.changeStatusToDelivered(1L)).thenReturn(deliveredOrder());
        when(orderService.changeStatusToReceived(1L)).thenReturn(receivedOrder());
    }

    @Test
    public void deliveredOrderTest() throws Exception {
        mockMvc.perform(put("/orders/1/delivered")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(deliveredOrder().getStatus().name()))
                .andDo(document("delivered_order", responseFields(
                        fieldWithPath("status").description("Состояние заказа"),
                        fieldWithPath("_links.received.href").description("Ссылки на доступные действия")
                )));
    }

    @Test
    public void receivedOrderTest() throws Exception {
        mockMvc.perform(put("/orders/1/received")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(receivedOrder().getStatus().name()))
                .andDo(document("received_order", responseFields(
                        fieldWithPath("status").description("Состояние заказа")
                )));
    }

    private Order deliveredOrder() {
        Order order = getOrder();
        order.setStatus(Status.DELIVERED);
        return order;
    }

    private Order receivedOrder() {
        Order order = getOrder();
        order.setStatus(Status.RECEIVED);
        return order;
    }

    private Order getOrder(){
        Good good1 = Good.builder()
                .name("Колонка")
                .price(18990.00)
                .build();
        Good good2 = Good.builder()
                .name("Ноутбук")
                .price(97000.00)
                .build();
        Good good3 = Good.builder()
                .name("Кактус")
                .price(750.50)
                .build();

        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);

        User user1 = User.builder()
                .firstName("Adelya")
                .lastName("Garieva")
                .role(Role.ADMIN)
                .build();

        return Order.builder()
                .goods(goods)
                .user(user1)
                .status(Status.NOT_DELIVERED)
                .build();
    }
}
