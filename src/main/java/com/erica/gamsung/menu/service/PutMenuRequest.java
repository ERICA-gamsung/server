package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;

public record PutMenuRequest(Long id, String name, Integer price) {
    public PutMenuRequest(Menu menu){
        this(menu.getId(),menu.getName(),menu.getPrice());
    }
}
