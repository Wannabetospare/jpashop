package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 멤버 저장소를 생성, 멤버와 관련된 DB 작업을 할때 사용됨.
@Repository
public class MemberRepository {

    // 엔티티 매니저 선언, @PersistenceContext를 선언하여 생성자를 선언하지 않아도 엔티티 매니저 사용가능
    @PersistenceContext
    private EntityManager em;

    // 매개변수로 멤버를 받아 엔티티 매니저에 저장하고, 매개변수의 id 값을 반환한다.
    public Long save(Member member){

        em.persist(member);

        return member.getId();
    }

    // id 값을 매개변수로받아 멤버를 하나찾고, 멤버를 반환
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }


    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
