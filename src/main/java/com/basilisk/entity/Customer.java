package com.basilisk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Customers")
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @Column(name="CompanyName")
    private String companyName;

    @Column(name="ContactPerson")
    private String contactPerson;

    @Column(name="Address")
    private String address;

    @Column(name="City")
    private String city;

    @Column(name="Phone")
    private String phone;

    @Column(name="Email")
    private String email;

    @Column(name="DeleteDate")
    private LocalDateTime deleteDate;

    public Customer(Long id, String companyName, String contactPerson, String address, String city, String phone, String email) {
        this.id = id;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }
}
