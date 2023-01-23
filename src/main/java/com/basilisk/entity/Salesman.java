package com.basilisk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name="Salesmen")
public class Salesman {
    @Id
    @Column(name="EmployeeNumber")
    private String employeeNumber;

    @Column(name="FirstName")
    private String firstName;

    @Column(name="LastName")
    private String lastName;

    @Column(name="Level")
    private String level;

    @Column(name="BirthDate")
    private LocalDate birthdate;

    @Column(name="HiredDate")
    private LocalDate hireDate;

    @Column(name="Address")
    private String address;

    @Column(name="City")
    private String city;

    @Column(name="Phone")
    private String phone;

    @Column(name="SuperiorEmployeeNumber")
    private String superiorEmployeeNumber;

    @ManyToOne
    @JoinColumn(name="SuperiorEmployeeNumber", insertable=false, updatable=false)
    private Salesman superior;

    @ManyToMany
    @JoinTable(name="SalesmenRegions",
            joinColumns=@JoinColumn(name="SalesmanEmployeeNumber"),//tabel yg akan join dengan kolom ini
            inverseJoinColumns=@JoinColumn(name="RegionId"))//tabel yg akan join ke yg akan join
    private List<Region> regions;

    public Salesman(String employeeNumber,
                    String firstName,
                    String lastName,
                    String level,
                    LocalDate birthdate,
                    LocalDate hireDate,
                    String address,
                    String city,
                    String phone,
                    String superiorEmployeeNumber) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.birthdate = birthdate;
        this.hireDate = hireDate;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.superiorEmployeeNumber = superiorEmployeeNumber;
    }
}
