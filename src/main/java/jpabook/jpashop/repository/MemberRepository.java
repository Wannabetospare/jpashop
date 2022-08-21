package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 멤버 저장소를 생성, 멤버와 관련된 DB 작업을 할때 사용됨.
// 리퍼지토리는 JPA 를 직접 사용한다 ( = 엔티티 매니저를 사용한다 )
@Repository
public class MemberRepository {

    // 엔티티 매니저 선언, @PersistenceContext를 선언하여 생성자를 선언하지 않아도 엔티티 매니저 사용가능 (표준)
    @PersistenceContext
    private EntityManager em;


    // 매개변수 - 멤버 값
    // 동작 - 엔티티 매니저를 통해 persist 메서드로 멤버를 엔티티 매니저에 저장
    // 반환값 - 매개변수로 받은 멤버의 id 값
    public Long save(Member member){

        em.persist(member);

        return member.getId();
    }

    // 매개변수 - id 값
    // 동작 - 엔티티 매니저의 find 메서드를 사용해서 매개변수 id 값을 통해 멤버를 찾아 반환한다.
    // 반환값 - 동작값으로 멤버를 반환
    public Member findOne(Long id){

        return em.find(Member.class,id);

    }


    // 매개변수 - x
    // 동작 - 엔티티 매니저의 제이쿼리로 멤버를 전부 찾아서 리스트형으로 반환한다.
    // 반환값 - 동작값으로 멤버 리스트형을 반환
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


    // 매개변수 - 문자타입으로 이름을 받음
    // 동작 - 엔티티 매니저의 제이쿼리로 멤버의 이름을 전부 찾아서 이름을 name 으로 넘기고, 넘겨진 값들을 리스트형으로 반환
    // 반환값 - 동작 값으로 멤버 리스트형형을 반환
    public List<Member> findByName(String name) {

        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
