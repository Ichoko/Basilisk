package com.basilisk.entity;
import javax.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Regions")
@Getter @Setter
public class Region {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @Column(name="City")
    private String city;

    @Column(name="Remark")
    private String remark;

    @ManyToMany
    @JoinTable(name="SalesmenRegions",
            joinColumns=@JoinColumn(name="RegionId"),//Tabel sales yg join pakai kolom ini(region ID)=> sesuai database
            inverseJoinColumns=@JoinColumn(name="SalesmanEmployeeNumber"))//lawannya dia akan join dengan tabel lain(sales)=>sesuai database
    private List<Salesman> salesmen;


    public Region(Long id, String city, String remark) {
        this.id = id;
        this.city = city;
        this.remark = remark;
    }
}
