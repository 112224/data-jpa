package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.member.entity.Member;
import study.datajpa.domain.member.repository.MemberRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void testMember() {

        Member member = new Member("hoon");

        Member save = memberRepository.save(member);
        Member findMember = memberRepository.findById(save.getId()).get();

        assertThat(save.getId()).isEqualTo(findMember.getId());
        assertThat(save.getUserName()).isEqualTo(findMember.getUserName());
        assertThat(save).isEqualTo(findMember);
    }

    @Test
    public void testBasicCRUD() throws Exception {
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        List<Member> all = memberRepository.findAll();

        //then

//        단건조회
        assertThat(member1).isEqualTo(findMember1);
        assertThat(member2).isEqualTo(findMember2);

//        전체조회
        assertThat(all.size()).isEqualTo(2);

//        카운트
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

//        삭제
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long deletedCnt = memberRepository.count();
        assertThat(deletedCnt).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() throws Exception {
        //given
        Member member = new Member("AAA", 10);
        Member member1 = new Member("AAA", 20);

        memberRepository.save(member);
        memberRepository.save(member1);

        //when
        List<Member> ret = memberRepository.findByUserNameAndAgeIsGreaterThan("AAA", 15);


        //then
        assertThat(member1).isEqualTo(ret.get(0));
    }

    @Test
    public void testNamedQuery() throws Exception {
        //given
        Member member = new Member("AAA", 10);
        Member member1 = new Member("BBB", 20);

        memberRepository.save(member);
        memberRepository.save(member1);
        //when

        List<Member> ret = memberRepository.findByUserName("AAA");

        //then
        Member findMember = ret.get(0);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void testFindUser() throws Exception {
        //given
        Member member = new Member("AAA", 10);
        Member member1 = new Member("AAA", 20);

        memberRepository.save(member);
        memberRepository.save(member1);

        //when
        List<Member> ret = memberRepository.findUser("AAA", 15);


        //then
        assertThat(member1).isEqualTo(ret.get(0));
    }
}