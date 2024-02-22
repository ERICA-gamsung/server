package com.erica.gamsung.menu.controller;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.menu.service.MenuListResponse;
import com.erica.gamsung.menu.service.MenuService;
import com.erica.gamsung.menu.service.PutMenuRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {
    final private MenuService menuService;
    @GetMapping(path="get/{userId}")
    public List<MenuListResponse> getMenuList(@PathVariable("userId") Long userId){
        return menuService.getMenu(userId);
    }
    @PutMapping(path = "put/{userId}")
    public List<PutMenuRequest> putMenu(@RequestBody List<Menu> putMenuRequest, @PathVariable("userId") Long userId){
        return menuService.putMenu(userId,putMenuRequest);
    }
}