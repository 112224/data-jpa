package study.datajpa.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
