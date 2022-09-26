package com.example.myvocab.repo;

import com.example.myvocab.dto.OrderInfoTable;
import com.example.myvocab.dto.OrdersInfo;
import com.example.myvocab.model.Orders;
import com.example.myvocab.model.enummodel.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {


    @Query("select o.id,o.activeDate,o.orderDate,o.status,o.aPackage,o.user.email,o.user.fullName,o.user.phone  from Orders o where o.id = ?1")
    Optional<OrdersInfo> findOrderInfoById(Long orderId);

    @Query("select o.id as id,o.activeDate as activeDate, o.orderDate as orderDate, o.status as status,u.email as email,u.fullName as fullName,u.phone as phone," +
            " p.id as pkId, p.title as pkTitle, p.description as pkDescription, p.type as pkType, p.duration as pkDuration, p.pricePerMonth as pkPricePerMonth" +
            " from Orders o" +
            " left join o.aPackage p" +
            " left join o.user u" +
            " where o.user.id = ?1")
    List<OrdersInfo> findByUser_Id(String userId);


    @Query("select o from Orders o where o.user.id = ?1 and o.status = ?2")
    <T> List<T> findByUser_IdAndStatus(String id, OrderStatus status, Class<T> type);


    @Query("select o.id as id,o.activeDate as activeDate, o.orderDate as orderDate, o.status as status,u.email as email,u.fullName as fullName,u.phone as phone," +
            " p.id as pkId, p.title as pkTitle, p.description as pkDescription, p.type as pkType, p.duration as pkDuration, p.pricePerMonth as pkPricePerMonth" +
            " from Orders o" +
            " left join o.aPackage p" +
            " left join o.user u" +
            " where o.user.id = ?1 and o.status = ?2")
    List<OrdersInfo> findOrderInfoByUser_IdAndStatus(String id, OrderStatus status);


    @Query("select o.id as id,o.activeDate as activeDate, o.orderDate as orderDate, o.status as status,u.email as email,u.fullName as fullName, p.title as pkTitle" +
            " from Orders o" +
            " left join o.aPackage p" +
            " left join o.user u" +
            " where o.status = ?1 order by o.orderDate DESC")
    Page<OrderInfoTable> findByStatusOrderByOrderDateDesc(OrderStatus status, Pageable pageable);


    @Query("select o.id as id,o.activeDate as activeDate, o.orderDate as orderDate, o.status as status,u.email as email,u.fullName as fullName, p.title as pkTitle" +
            " from Orders o" +
            " left join o.aPackage p" +
            " left join o.user u" +
            " where o.status = ?1 and concat(o.user.fullName,' ',o.user.email,' ',o.aPackage.title) like %?2% group by o order by o.orderDate desc ")
    Page<OrderInfoTable> findByStatusAndKeyWord(OrderStatus status,String keyword, Pageable pageable);

    boolean existsByUser_IdAndStatus(String id, OrderStatus status);







}