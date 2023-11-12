package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")  // 컬럼이름을 테이블 명에 id로 해둠
    private Long id;

    @ManyToOne  // Order랑 Member는 다대일 관계
    @JoinColumn(name = "member_id")  // member_id 로 조인함. FK 아이디가 되는 부분
    // 연관관계 주인 설정 필요 ) 값이 변경이 되었을 때 FK를 변경해주는 것이 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id") // 일대일 관계에서 연관관계 주인은 Order!
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
