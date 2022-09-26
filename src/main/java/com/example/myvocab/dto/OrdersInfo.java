package com.example.myvocab.dto;

import com.example.myvocab.model.enummodel.OrderStatus;

import java.time.LocalDate;

public interface OrdersInfo {
    Long getId();

    LocalDate getActiveDate();

    LocalDate getOrderDate();

    OrderStatus getStatus();


    String getEmail();

    String getFullName();

    String getPhone();


    Long getPkId();

    String getPkDescription();

    int getPkDuration();

    Long getPkPricePerMonth();

    String getPkTitle();

    String getPkType();


}
