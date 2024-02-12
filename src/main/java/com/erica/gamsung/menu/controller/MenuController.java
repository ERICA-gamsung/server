package com.erica.gamsung.menu.controller;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.menu.service.MenuListResponse;
import com.erica.gamsung.menu.service.MenuService;
import com.erica.gamsung.menu.service.PutMenuRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping(path="{userId}")
    public List<MenuListResponse> getMenuList(@PathVariable("userId") Long userId){
        return menuService.getMenu(userId);
    }
    @PutMapping(path = "put")
    public void putMenu(@RequestBody Menu putMenuRequest){
        menuService.putMenu(putMenuRequest);
    }
}