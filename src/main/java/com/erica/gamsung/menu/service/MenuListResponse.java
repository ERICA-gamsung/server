package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;

public record MenuListResponse(Long id, String name, Integer price) {

    public MenuListResponse(Menu menu) {
        this(menu.getId(),menu.getName(),menu.getPrice());
    }
}