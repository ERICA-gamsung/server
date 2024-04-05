package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.menu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuListResponse> getMenu(Long userId){
        List<Menu> menu = menuRepository.findByUserId(userId);

        List<MenuListResponse> menuListResponses = menu.stream().map(MenuListResponse::new).toList();
        if (menuListResponses.size()<=0)
            throw new RuntimeException("menulist가 존재하지 않습니다. userid를 확인해주세요.");

        return menuListResponses;
    }
    public List<PutMenuRequest> putMenu(Long userId, List<PutMenuRequest> data){ //userId 잘못 됐을시 에러 처리 추가할 것
        List<Menu> menuList = menuRepository.findByUserId(userId);
        for(Menu x : menuList){ // 삭제 및 수정
            delOrMod(x,data,userId);
        }
        for (PutMenuRequest nowMenu : data) { //저장
            Menu newMenu = new Menu(nowMenu.id(),userId,nowMenu.name(),nowMenu.price());
            menuRepository.save(newMenu);
        }
        return menuRepository.findByUserId(userId).stream().map(PutMenuRequest::new).toList();
    }
    public void delOrMod(Menu menu,List<PutMenuRequest> data,Long userId){ //삭제 및 수정 함수
        for(PutMenuRequest x  : data){
            if(Objects.equals(menu.getId(), x.id())) {
                Menu newMenu = new Menu(x.id(),userId,x.name(),x.price());
                menuRepository.save(newMenu);
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