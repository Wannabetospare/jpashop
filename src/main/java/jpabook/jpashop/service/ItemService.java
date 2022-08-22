package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 서비스
@Service
@Transactional(readOnly = true) // 읽기만 하는 읽기 전용 ( 저장 안됨 )
@RequiredArgsConstructor // 생성자 생략
public class ItemService {

    private final ItemRepository itemRepository;


    // 트랜잭션 어노테이션으로 저장을 한다.(위에서 읽기 전용으로 설정해서 여기만 어노테이션을 달아서 저장한다.)
    // 이름 - 아이템을 저장
    // 매개변수 - 아이템을 매개변수로 받는다.
    // 동작 - 아이템 리퍼지토리에 매개변수로 받은 아이템을 저장한다.
    // 반환값 - x
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 트랜잭션 어노테이션으로 저장을 한다.(위에서 읽기 전용으로 설정해서 여기만 어노테이션을 달아서 저장한다.)
    // 이름 - 아이템을 업데이트하는 메서드
    // 매개변수 - 아이템 id, 이름, 가격, 재고(수량)을 매개변수로 받는다.
    // 동작 - 아이템 리퍼지토리에서 매개변수로 받은 아이템 id 를 통해 아이템을 하나 찾아서, 아이템을 하나 생성하고, 생성된 아이템에 매개변수 값들을 넣어준다.
    // 반환값 - x
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }


    // 이름 - 아이템들을 찾는 메서드
    // 매개변수 - x
    // 동작 - 아이템 리퍼지토리에서 아이템을 전부 찾는다
    // 반환값 - 동작값으로 전부 찾은 아이템값을 아이템 클래스 리스트형으로 반환한다.
    public List<Item> findItems(){

        return itemRepository.findAll();

    }

    // 이름 - 아이템 하나를 찾는 메서드
    // 매개변수 - 아이템 id 를 매개변수로 받는다.
    // 동작 - 아이템 리퍼지토리에서 매개변수로 받은 id 를 통해서 아이템을 하나 찾는다.
    // 반환값 - 동작으로 찾은 아이템 하나를 반환한다.
    public Item findOne(Long itemId){

        return itemRepository.findOne(itemId);

    }
}
