package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자가 아닌 생성메서드를 이용해야함을 알리는 것
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")  // 컬럼이름을 테이블 명에 id로 해둠
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Order랑 Member는 다대일 관계
    @JoinColumn(name = "member_id")  // member_id 로 조인함. FK 아이디가 되는 부분
    // 연관관계 주인 설정 필요 ) 값이 변경이 되었을 때 FK를 변경해주는 것이 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // CascadeType.ALl: Order를 persist 하면 OrderItem 도 persist
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL) // 다른 곳에서 Delivery 를 쓴다면 cascade를 막 사용하면 안됨. 차라리 각각의 레포를 파서 persist 해야한다.
    @JoinColumn(name = "delivery_id") // 일대일 관계에서 연관관계 주인은 Order!
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ==연관관계 메서드 == //
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    // ==생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        // Enum 클래스 정의
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // == 비즈니스 로직 == //
    /*
    * 주문 취소
    * */
    public void cancel(){
        // 배송 완료된 상품 주문취소 예외 반환
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");

        }
        this.setStatus(OrderStatus.CANCEl);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    // == 조회 로직 ==//
    /*
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

    }

}
