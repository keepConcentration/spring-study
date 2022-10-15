package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // 스프링이 엔티티 매니저를 주입
    // EntityMananager를 EntityFactory에서 직접 주입받을 경우
    // @PersistenceUnit
    // spring data jpa 사용 시 @PersistenceContext 대신 @Autowired 사용 가능
    // sprind data jpa 없으면 @PersistenceContext 사용
    // @PersistenceContext
    private final EntityManager em;

    // generatedValue를 사용 시 persist를 한다해서 db에 바로 insert하지 않음
    // commit으로 flush될 때 insert함
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { // jpql : 엔티티 객체를 대상으로 쿼리 실행
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
