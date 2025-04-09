package com.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_type", nullable = false)
    private String orderType; // "PURCHASE" or "SALE"

    @Column(nullable = false)
    private String status; // "PENDING", "COMPLETED", "CANCELLED"

    @Column(name = "supplier_or_customer_name")
    private String contactName;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
}


//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long orderId;
//
//    @Column(nullable = false)
//    private String orderType;
//
//    @Column(nullable = false)
//    private String status;
//
//    private String supplierOrCustomerName;
//
//    @Column(nullable = false)
//    private LocalDateTime orderDate;
//
//    private String remarks;
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems;
//}