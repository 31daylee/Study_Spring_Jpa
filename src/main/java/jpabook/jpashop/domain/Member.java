package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입으로 열었다는 것을 표시하는 것. Address 는 @Embeddable 어노테이션 사용
    private Address address;

    @OneToMany(mappedBy = "member") // member와 order는 일대다 관계
    // / 연관관계 주인이 아니에요 -> mappedBy 로 설정
    // 누구에 의해 매핑이 되었나요?-> Order테이블에 있는 member(변수이름)가 주인이에요
    private List<Order> orders = new ArrayList<>();








}
