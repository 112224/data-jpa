package study.datajpa.domain.team.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
