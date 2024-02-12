package com.erica.gamsung.menu.service;

import com.erica.gamsung.menu.domain.Menu;

public record PutMenuRequest(Long id, Long userId, String name, Integer price) {
}
