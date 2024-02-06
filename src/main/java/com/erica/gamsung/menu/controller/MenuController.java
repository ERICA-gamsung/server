package com.erica.gamsung.menu.controller;

import com.erica.gamsung.menu.service.MenuListResponse;
import com.erica.gamsung.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping(path="{userId}")
    public List<MenuListResponse> menuList(@PathVariable("userId") Long userId){
        return menuService.getMenu(userId);
    }
}