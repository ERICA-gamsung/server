package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.menu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    public List<MenuListResponse> getMenu(Long userId){
        List<Menu> menu = menuRepository.findByUserId(userId);
        List<MenuListResponse> menuListResponses = menu.stream().map(MenuListResponse::new).toList();
        if (menuListResponses.size()<=0)
            throw new RuntimeException("menulist가 존재하지 않습니다. userid를 확인해주세요.");
        return menuListResponses;
    }
    @PostConstruct
    public void init() {
        // 데이터가 이미 존재하면 바로 리턴
        if (menuRepository.count() > 0) {
            return;
        }

        // 더미 데이터 작성
        Menu menu1 = new Menu(
                1L,
                -1L,
                "김치찌개",
                5000
        );
        Menu menu2 = new Menu(
                2L,
                -1L,
                "된장찌개",
                1000
        );
        Menu menu3 = new Menu(
                3L,
                -2L,
                "초밥",
                300
        );
        // 더미 데이터 저장
        menuRepository.saveAll(List.of(menu1,menu2,menu3));
        }
}