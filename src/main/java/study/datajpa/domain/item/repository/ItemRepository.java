package study.datajpa.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.domain.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
