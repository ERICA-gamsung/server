package com.erica.gamsung.menu.repository;
import com.erica.gamsung.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{
    List<Menu> findByUserId(Long userId);
}