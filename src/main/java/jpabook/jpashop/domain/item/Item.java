package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글테이블 전략
@DiscriminatorColumn(name = "dtype") // 상위 클래스에서 하위클래스를 구부할때 사용
@Getter
@Setter
public abstract class Item {

    // 기본키 매핑
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name; // 이름

    private int price; // 가격
    private int stockQuantity; // 재고(수량)

    // 다대다 연관관계로서 mappedBy 가 존재하므로, 주인이 아니다.
    @ManyToMany(mappedBy = "items") // 읽기전용, 주인아님
    private List<Category> categories = new ArrayList<>();


    // == 비즈니스 로직 == //

    // 이름 - 재고(수량)을 더하는 메서드
    // 매개변수 - 수량을 매개변수로 받는다.
    // 동작 - 매개변수로 받은 수량을 재고수량에 더하기한다.
    // 반환값 - x
    public void addStock(int quantity) {

        this.stockQuantity += quantity;

    }


    // 이름 - 재고(수량)을 빼는 메서드
    // 매개변수 - 수량을 매개변수로 받는다.
    // 동작 - 남은재고를 원래 재고에서 매개변수 수량을 뺀값으로 설정한다, 재고가 0보다 작으면 재고가 없음을 오류를 사용해서 표시함
    // 반환값 - x
    public void removStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
