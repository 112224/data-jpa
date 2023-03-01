package study.datajpa.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.member.dto.MemberDto;
import study.datajpa.domain.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeIsGreaterThan(String userName, int age);

    List<Member> findByUserName(@Param("userName") String userName);

    @Query("select m from Member m where m.userName = :userName and m.age >= :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUserNameList();

    @Query("select new study.datajpa.domain.member.dto.MemberDto(m.id, m.userName, t.teamName) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();
}
