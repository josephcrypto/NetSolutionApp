package cn.coding.com.netsolutionapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_db")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String item;

    private int quantity;

    private String model;

    private String categories;

    private double COD;

    private String invoiceNo;

    private Date date;

    private double amount;

    private double profit;
}
