package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.member.dto.MemberDto;
import study.datajpa.domain.member.entity.Member;
import study.datajpa.domain.member.repository.*;
import study.datajpa.domain.team.entity.Team;
import study.datajpa.domain.team.respository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @Test
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

    @Test
    public void testFindUserName() throws Exception {
        //given
        Member member = new Member("AAA", 10);
        Member member1 = new Member("BBB", 20);
        Member member2 = new Member("hoon", 20);

        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<String> userNameList = memberRepository.findUserNameList();

        //then
        for (String s : userNameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void testFindMemberDto() throws Exception {
        //given
        Team team = new Team("teamA");

        Member member = new Member("hoon", 29);

        teamRepository.save(team);

        member.changeTeam(team);
        memberRepository.save(member);
        //when

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        //then
        MemberDto findMemberDto = memberDto.get(0);
        assertThat(findMemberDto.getId()).isEqualTo(member.getId());
        assertThat(findMemberDto.getTeamName()).isEqualTo(member.getTeam().getTeamName());
        assertThat(findMemberDto.getUserName()).isEqualTo(member.getUserName());
    }

    @Test
    public void testFindByNames() throws Exception {
        //given
        Member member = new Member("hoon", 29);
        Member member1 = new Member("dana", 29);
        Member member2 = new Member("hh", 29);

        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<String> names = new ArrayList<>();
        names.add("hoon");
        names.add("dana");
        names.add("hh");
        List<Member> byNames = memberRepository.findByNames(names);

        //then
        for (Member byName : byNames) {
            System.out.println("byName = " + byName);
        }
    }

    @Test
    public void testReturnType() throws Exception {
        //given
        Member member = new Member("hoon", 29);
        //when
        Optional<Member> hoon = memberRepository.findOptionalByUserName("hoon");

        //then
        System.out.println("hoon = " + hoon);
    }

    @Test
    public void pageTest() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));


        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));
        //when
        Page<Member> memberPage = memberRepository.findByAge(age, pageRequest);

        //then
        List<Member> content = memberPage.getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(memberPage.getTotalElements()).isEqualTo(5);
        assertThat(memberPage.getNumber()).isEqualTo(0);
        assertThat(memberPage.getTotalPages()).isEqualTo(2);
        assertThat(memberPage.isFirst()).isTrue();
        assertThat(memberPage.hasNext()).isTrue();

    }

    @Test
    public void testBulkUpdate() throws Exception {
        //given
        memberRepository.save(new Member("member1", 11));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        memberRepository.save(new Member("member4", 50));
        memberRepository.save(new Member("member5", 21));
        //when

        int result = memberRepository.bulkAgePlus(20);

        //then
        assertThat(result).isEqualTo(4);
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void testFindMemberLazy() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("hoon", 10, teamA);
        Member member2 = new Member("dana", 20, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findFetchByUserName("hoon");

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getTeamName() = " + member.getTeam().getTeamName());

        }
        //then
    }

    @Test
    public void testQueryHint() throws Exception {
        //given
        Member member = new Member("hoon", 29);
        memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        Member member1 = memberRepository.findReadOnlyByUserName(member.getUserName());
        member1.changeUserName("dana");

        em.flush();
        //then
    }

    @Test
    public void testLockQuery() throws Exception {
        //given
        Member member = new Member("hoon", 29);
        memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        List<Member> member1 = memberRepository.findLockByUserName(member.getUserName());

        em.flush();
        //then
    }

    @Test
    public void testFindMemberCustom() throws Exception {
        //given
        memberRepository.save(new Member("hoon", 29));
        //when
        List<Member> memberCustom = memberRepository.findMemberCustom();
        //then
        for (Member member : memberCustom) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void specBasic() throws Exception {
        //given
        Team team = new Team("teamA");
        em.persist(team);

        Member member1 = new Member("m1", 10, team);
        Member member2 = new Member("m2", 10, team);

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        //when

        Specification<Member> spec = MemberSpec.userName("m1").and(MemberSpec.teamName("teamA"));
        List<Member> members = memberRepository.findAll(spec);

        //then

        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    public void queryByExample() throws Exception {
        Team team = new Team("teamA");
        em.persist(team);

        Member member1 = new Member("m1", 10, team);
        Member member2 = new Member("m2", 10, team);

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        //when
        //Probe
        Member member = new Member("m1");
        member.changeTeam(team);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");
        Example<Member> example = Example.of(member, matcher);

        List<Member> members = memberRepository.findAll(example);

        //then

        assertThat(members.get(0).getUserName()).isEqualTo("m1");
    }

    @Test
    public void projections() throws Exception {
        //given
        Team team = new Team("teamA");
        em.persist(team);

        Member member1 = new Member("m1", 10, team);
        Member member2 = new Member("m2", 10, team);

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();
        //when

        List<NestedClosedProjections> result = memberRepository.findProjectionsByUserName("m1", NestedClosedProjections.class);
        for (NestedClosedProjections userNameOnly : result) {
            System.out.println("userNameOnly = " + userNameOnly);
        }

        //then
    }
}