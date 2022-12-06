package restaurant.core;

import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Food;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.sql.SQLRecoverableException;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double totalMoney;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
        this.totalMoney = 0;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood food = null;
        if (type.equals("Salad")){
            food = new Salad(name, price);
        }else if (type.equals("VeganBiscuits")){
            food = new VeganBiscuits(name, price);
        }

        if (healthFoodRepository.foodByName(name) != null){
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));
        }

        healthFoodRepository.add(food);
        return String.format(FOOD_ADDED, name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverages = null;
        if (type.equals("Smoothie")){
            beverages = new Smoothie(name, counter, brand);
        }else if (type.equals("Fresh")){
            beverages = new Fresh(name, counter, brand);
        }

        if (beverageRepository.beverageByName(name, brand )!= null){
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));
        }

        beverageRepository.add(beverages);
        return String.format(BEVERAGE_ADDED, type, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table = null;
        if (type.equals("Indoors")){
            table = new Indoors(tableNumber, capacity);
        }else if (type.equals("InGarden")){
            table = new InGarden(tableNumber, capacity);
        }
        if (tableRepository.byNumber(tableNumber) != null){
            throw new IllegalArgumentException(String.format(TABLE_IS_ALREADY_ADDED, tableNumber));
        }

        tableRepository.add(table);
        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {
       Table tableForReserve = this.tableRepository.getAllEntities().stream()
                .filter(table -> !table.isReservedTable() && table.getSize()>=numberOfPeople)
                .findFirst()
               .orElse(null);

       if (tableForReserve == null){
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }else {
           tableForReserve.reserve(numberOfPeople);
           return String.format(TABLE_RESERVED, tableForReserve.getTableNumber(), numberOfPeople);
       }

    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table table = tableRepository.getAllEntities().stream()
                .filter(table1 -> table1.getTableNumber() == tableNumber )
                .findFirst()
                .orElse(null);

        if (table == null){
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        HealthyFood food = healthFoodRepository.foodByName(healthyFoodName);
        if (food == null){
            return String.format(NONE_EXISTENT_FOOD, healthyFoodName);
        }

        table.orderHealthy(food);
        return String.format(FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = tableRepository.getAllEntities().stream()
                .filter(table1 -> table1.getTableNumber() == tableNumber )
                .findFirst()
                .orElse(null);

        if (table == null){
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        Beverages beverages = beverageRepository.beverageByName(name, brand);
        if (beverages == null){
            return String.format(NON_EXISTENT_DRINK, name, brand);
        }

        table.orderBeverages(beverages);
        return String.format(BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table table = tableRepository.getAllEntities().stream()
                .filter(table1 -> table1.getTableNumber() == tableNumber)
                .findFirst()
                .orElse(null);

        double bill = 0.0;
        if (table != null){
            bill = table.bill();
            totalMoney+=bill;
            table.clear();
        }
        return String.format(BILL,tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        return String.format(TOTAL_MONEY,totalMoney);
    }
}
