package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


// 리퍼지토리는 JPA 를 직접 사용한다 ( = 엔티티 매니저를 사용한다 )
@Repository
@RequiredArgsConstructor // 생성자 생략 기능
public class ItemRepository {

    private final EntityManager em;

    // 이름 - 아이템 저장 메서드
    // 매개변수 - 아이템을 매개변수로 받는다.
    // 동작 - 아이템의 id 값을 받아와 없으면 저장하고, 있으면 병합한다.(=덮으쓰기 같은 느낌)
    // 반환값 - x
    public void save(Item item){
        if (item.getId() == null){
            em.persist(item);
        }else {
            em.merge(item);
        }
    }

    // 이름 - 아이템 하나 찾기
    // 매개변수 - id 를 매개변수로 받는다.
    // 동작 - 매개변수로 받은 id 를 사용해서, 조회할 엔티티 타입을 아이템을 찾아서
    // 반환값 - 동작값이 곧 반환값으로 아이템을 반환한다.
    public Item findOne(Long id) {

        return em.find(Item.class, id);

    }

    // 이름 - 아이템을 전부 찾는 메서드
    // 매개변수 - x
    // 동작 - 엔티티 매니저로 제이쿼리를 만들어서 아이템 클래스를 리스트로 뽑아 가져온다.
    // 반환값 - 동작으로 가져온 아이템 값들을 리스트형으로 반환한다.\

    public List<Item> findAll(){

        return em.createQuery("select i from Item i", Item.class).getResultList();

    }
}
