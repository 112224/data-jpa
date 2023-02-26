package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.member.entity.Member;
import study.datajpa.domain.member.repository.MemberRepository;


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
}