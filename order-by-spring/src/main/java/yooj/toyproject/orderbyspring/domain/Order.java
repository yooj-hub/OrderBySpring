package yooj.toyproject.orderbyspring.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yooj.toyproject.orderbyspring.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Address address;

    public Order(Member member, OrderStatus status) {
        this.member = member;
        this.status = status;
        this.address = member.getAddress();
    }

    public Order(Member member, OrderStatus status, Address address) {
        this.member = member;
        this.status = status;
        this.address = address;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getMember(), order.getMember()) && getStatus() == order.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMember(), getStatus());
    }
}
