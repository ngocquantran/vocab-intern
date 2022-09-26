package com.example.myvocab.dto;

import com.example.myvocab.model.enummodel.OrderStatus;

import java.time.LocalDate;

public interface OrderInfoTable {
    Long getId();

    LocalDate getActiveDate();

    LocalDate getOrderDate();

    OrderStatus getStatus();


    String getEmail();

    String getFullName();

    String getPkTitle();

}
