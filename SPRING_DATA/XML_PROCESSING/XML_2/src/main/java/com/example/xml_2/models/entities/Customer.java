package com.example.xml_2.models.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
    @Column
    private String name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Sale> sales;

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

}
