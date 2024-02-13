package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.menu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuListResponse> getMenu(Long userId){
        List<Menu> menu = menuRepository.findByUserId(userId);
        List<MenuListResponse> menuListResponses = new ArrayList<>(); // List 초기화 및 타입 명시
        for (Menu value : menu) {
            menuListResponses.add(new MenuListResponse(value));
        }
        if (menuListResponses.size()<=0)
            throw new RuntimeException();
        return menuListResponses;
    }
    public void putMenu(Long userId,List<Menu> data){
        List<Menu> menuList = menuRepository.findByUserId(userId);
        for(Menu x : menuList){ // 삭제 및 수정
            delOrMod(x,data,userId);
        }
        for (Menu nowMenu : data) { //저장
            nowMenu.setUserId(userId);
            menuRepository.save(nowMenu);
        }
    }
    public void delOrMod(Menu menu,List<Menu> data,Long userId){ //삭제 및 수정 함수
        for(Menu x  : data){
            if(Objects.equals(menu.getId(), x.getId())) {
                x.setUserId(userId);
                menuRepository.save(x);
                data.remove(x);
                return;
            }
        }
        menuRepository.delete(menu);
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