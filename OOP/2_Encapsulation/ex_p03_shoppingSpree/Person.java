package ex_p03_shoppingSpree;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private double money;
    private List<Product> products;

    public Person (String name, double money){
        this.setName(name);
        this.setMoney(money);
        this.products = new ArrayList<>();
    }

    private void setName (String name){
        if (!name.trim().isEmpty()){
            this.name = name;
        }else {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    private void setMoney(double money){
        if (money>=0){
            this.money = money;
        }else {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public String getName (){
        return this.name;
    }

    public void buyProduct (Product product){
        if (this.money>=product.getCost()){
            this.products.add(product);
            this.money-=product.getCost();
        }else {
            String message  = String.format("%s can't afford %s%n", this.name, product.getName());
            throw new IllegalArgumentException(message);
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}
