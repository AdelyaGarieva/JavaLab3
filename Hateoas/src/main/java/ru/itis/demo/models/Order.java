package ru.itis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Address address;

    @ManyToMany
    private List<Good> goods;

    private Status status;

    public void changeStatusToDelivered() {
        if (this.status.equals(Status.NOT_DELIVERED)) {
            this.status = Status.DELIVERED;
        } else {
            throw  new IllegalArgumentException();
        }
    }

    public void changeStatusToReceived() {
        if (this.status.equals(Status.DELIVERED)) {
            this.status = Status.RECEIVED;
        } else {
            throw  new IllegalArgumentException();
        }
    }
}
