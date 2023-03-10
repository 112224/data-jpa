package study.datajpa.domain.member.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.team.entity.Team;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        //given
        Team teamA = new Team("first");
        Team teamB = new Team("second");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("hoon1", 10, teamA);
        Member member2 = new Member("hoon2", 10, teamA);
        Member member3 = new Member("hoon3", 10, teamB);
        Member member4 = new Member("hoon4", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        //when

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member = " + member.getTeam());
        }
        //then
    }

    @Test
    @Rollback(value = false)
    public void testJpaEventBaseEntity() throws Exception {
        //given
        Member member = new Member("hoon", 29);

        em.persist(member);
        Thread.sleep(1000);

        member.changeUserName("dana");

        em.flush();
        em.clear();
        //when

        Member member1 = em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", member.getId())
                .getSingleResult();


        //then
        System.out.println("member1 = " + member1);
        System.out.println("member1.getCreatedDate() = " + member1.getCreatedDate());
        System.out.println("member1.getLastModifiedDate() = " + member1.getLastModifiedDate());
    }

}