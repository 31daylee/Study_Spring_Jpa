package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 기본 생성자 -> 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는
    //protected 로 설정
    protected Address() {

    }
}
