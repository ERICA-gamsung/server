package com.erica.gamsung.menu.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
    private Long userId;
    private String name;
    private Integer price;
    @Override
    public boolean equals(Object param){
        if(this == param) return true;
        if(param == null || getClass()!=param.getClass()) return false;
        Menu menu = (Menu) param;
        return Objects.equals(id, menu.getId()) && Objects.equals(userId, menu.getUserId()) && Objects.equals(name,menu.getName())
                && Objects.equals(price, menu.getPrice());
    }
//    public boolean equals(Menu param){
//        System.out.println("qs;lfkjsd;fljs;dfjaskf");
//        return true;
//        if (this == param) return true;
//        if(param == null) return false;
//        return Objects.equals(id,param.getId()) && Objects.equals(userId,param.userId)
//                && Objects.equals(name,param.getName()) && Objects.equals(price,param.price);

//    }
}