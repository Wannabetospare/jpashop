package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


// 리퍼지토리는 JPA 를 직접 사용한다 ( = 엔티티 매니저를 사용한다 )
@Repository
@RequiredArgsConstructor // 생성자 생략, 생성자가 하나라면 @Autowired 가 자동으로 붙는다.
public class OrderRepository {

    private final EntityManager em;

    // 이름 - 주문 저장
    // 매개변수 - 오더
    // 동작 - 오더를 매개변수로 받아 엔티티 매니저에 저장한다.
    // 반환값 - x
    public void save(Order order) {

        em.persist(order);

    }

    // 이름 - 주문하나 찾기
    // 매개변수 - id 값을 매개변수로 받는다.
    // 동작 - 매개변수로 받은 id 값으로 오더 클래스를 찾아 반환한다.
    // 반환값 - 동작값으로 찾은 오더 클래스 값
    public Order findOne(Long id) {

        return em.find(Order.class, id);

    }


    // 이름 - 주문 전부 찾기
    // 매개변수 - 오더 검색 클래스
    // 동작 - 엔티티매니저의 쿼리를 사용해서 회원을 찾아 조인한다.
    // 반환값 - 오더 리스트
    public List<Order> findAll(OrderSearch orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER); //회원과 조인

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        // 동작 - 만약 오더 검색창이 비어있지 않다면, 오더 클래스의 상태를 검색하여 오더 상태를 찾아서 creteria 에 추가한다.
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        // 만약 오더검색에서 멤버의 이름으로 매개변수를 받아서 확인하고, 같은 이름이 있다면 criteria 에 이름을 추가한다.
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        // criteria 의 사이즈만큼 찾아서 최대 1000번까지 쿼리를 날려 리스트를 반환한다.
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}

