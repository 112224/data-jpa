package study.datajpa.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.domain.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeIsGreaterThan(String userName, int age);
}
