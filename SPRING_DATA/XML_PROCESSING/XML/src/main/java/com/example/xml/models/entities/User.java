package com.example.xml.models.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column()
    private int age;

    @OneToMany
    private Set<User> friends;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private Set<Product> products;

//    @OneToMany(mappedBy = "buyer")
//    private Set<Product> soldProducts;


    public User(){};

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

//    public Set<Product> getSoldProducts() {
//        return soldProducts;
//    }
//
//    public void setSoldProducts(Set<Product> soldProducts) {
//        this.soldProducts = soldProducts;
//    }
}
