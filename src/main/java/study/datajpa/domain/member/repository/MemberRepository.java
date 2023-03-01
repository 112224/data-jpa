package study.datajpa.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeIsGreaterThan(String userName, int age);

    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("userName") String userName);
}
