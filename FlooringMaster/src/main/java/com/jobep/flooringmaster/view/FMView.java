/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.view;

import com.jobep.flooringmaster.dao.FMNoOrderForDateException;
import com.jobep.flooringmaster.model.Order;
import com.jobep.flooringmaster.model.Product;
import com.jobep.flooringmaster.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class FMView {
    private UserIO io;
    
    @Autowired
    public FMView(UserIO io){
        this.io = io;
    }
    
    public int displayMainMenu(){
        printBorder();
        io.print("<<Flooring Program>>\n1. Display Orders\n2. Add an Order\n3. Edit an Order\n4. Remove an Order\n5. Quit");
        printBorder();
        return io.readInt("What option would you like to pick?",1,5);
    }
    
    public boolean displayConfirmOrder(Order currOrder){
        displayOrder(currOrder);
        if(io.readInt("Does this look correct?\n1.Yes\n2.No",1,2) == 1)
            return true;
        return false;
    }
    
    public void displayOrder(Order currOrder){
        io.print(currOrder.getOrderNumber() + ": For " + currOrder.getCustomerName() + " using " + currOrder.getProductType());
        io.print("Costs:\n\tLabor: " + currOrder.getLaborCost() + "\n\tMaterials: " + currOrder.getMaterialCost() + "\n\tTax: " + currOrder.getTax() + "\n\tTotal: " + currOrder.getTotal());
    }
    
    public LocalDate displayGetDate(){ 
        String dateAsString = io.readString("Please enter a date in MMDDYYYY format (ie. 08161998 for August 16th, 1998)");
        while(!(dateAsString.matches("^[0-9]{8}$"))){
            dateAsString = io.readString("Invalid format: Please format date as MMDDYYYY");
        }
        LocalDate date = LocalDate.parse(dateAsString,DateTimeFormatter.ofPattern("MMddyyyy"));
        return date;
    }
    
    public int displayGetOrderNumber(){
        int orderNumber = io.readInt("Please enter the order's order number.");
        return orderNumber;
    }
    
    public void displayException(Exception e){
        io.print(e.getMessage());
    }
    
    public void displayOrdersList(List<Order> orders){
        displayBanner("LISTING ALL ORDERS FROM " + orders.get(0).getDate());
        for(Order currOrder : orders){
            displayOrder(currOrder);
        }
    }
    
    public Order displayAddOrder(List<Order> orders,List<Tax> taxes, List<Product> products){
        displayBanner("ADDING ORDER");
        Order newOrder;
        Tax orderTax = taxes.get(0);
        Product orderProduct;
        int max;
        
        
        LocalDate orderDate = displayGetDate();
        while(LocalDate.now().isAfter(orderDate)){
            io.print("Order date must be in the future.");
            orderDate = displayGetDate();
        }
        
        String customerName = io.readString("Please enter the customer's name. ");
        while(customerName.equals("") || !(customerName.matches("^[A-Za-z0-9,.\\s]*$"))){
            customerName = io.readString("Please enter the customer's name. (Names can only contain alphanumeric values, commas, and periods.");
        }
        String orderState = io.readString("Please enter the order's state.");
        
        List<String> stateNames = taxes.stream().map(t -> t.getStateAbbreviation()).collect(Collectors.toList());
        
        while(!stateNames.contains(orderState)){
            orderState = io.readString("We cannot sell in " + orderState + ", please enter a valid state, or type 0 to return to the main menu.");
            if(orderState.equals("0"))
                return null;
        }
        
        displayAllProducts(products);
        int answerToProductType = io.readInt("Which product would you like to select?",1,products.size());
        orderProduct = products.get(answerToProductType-1);
        String orderAreaString = io.readString("Please enter the area of the flooring.");
        
        
        BigDecimal orderArea = new BigDecimal(orderAreaString);
        while(orderArea.compareTo(new BigDecimal("100"))== -1){
            orderAreaString = io.readString("Please enter the area of the flooring. (Area must be a minimum of 100sq ft.)");
            orderArea = new BigDecimal(orderAreaString);
        }
        
        if(orders.isEmpty())
            max = 0;
        else{
            List<Integer> orderNumbers = orders.stream().map(p->p.getOrderNumber()).collect(Collectors.toList());
            max = orders.get(0).getOrderNumber();
            for(Integer currNum : orderNumbers){
                if(currNum > max)
                    max = currNum;
            }
        }
        for(Tax tax : taxes){
            if(tax.getStateAbbreviation().equals(orderState)){
                orderTax = tax;
                break;
            }
        }
        
        newOrder = new Order(orderProduct,orderTax);
        newOrder.setOrderNumber(max+1);
        newOrder.setDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setArea(orderArea);
        newOrder.calculateCosts();
        
        return newOrder;
    }
    public void printBorder(){
        io.print("><><><><><><><><><><><><><><><><><><><><");
    }
    
    public void displayAllProducts(List<Product> products){
        int i = 1;
        for(Product p : products){
            String toPrint = String.format("%d: %-10s Cost per square foot: %-5s Labor cost per square foot: %s",i,p.getProductType(),p.getCostPerSquareFoot(),p.getLaborCostPerSquareFoot());
            io.print(toPrint);
            i++;
        }
    }
    
    public Order displayEditMenu(Order toEdit,List<Product> products,List<Tax> taxes){
        displayBanner("EDITING MENU");
        Order editedOrder;
        Product newProduct ;
        Tax newTax = taxes.get(0);
        BigDecimal orderArea;
        Product originalProduct = (products.stream().filter(p -> p.getProductType().equals(toEdit.getProductType())).collect(Collectors.toList()).get(0));
        List<String> listOfProductNames = products.stream().map(p->p.getProductType()).collect(Collectors.toList());
        String newName = io.readString("Enter customer name("+ toEdit.getCustomerName()+")");
        while(!(newName.matches("^[A-Za-z0-9,.\\s]*$"))){
            io.print("Names can only contain alphanumeric values, commas, and periods.");
            newName = io.readString("Please enter customer name("+ toEdit.getCustomerName()+")");
        }
        if(newName.equals(""))
            newName = toEdit.getCustomerName();
        
        List<String> stateNames = taxes.stream().map(t -> t.getStateAbbreviation()).collect(Collectors.toList());
        String newState = io.readString("Please enter customer state (" + toEdit.getState() +")");
        
        while( !stateNames.contains(newState) && !(newState.equals(""))){
            newState  = io.readString("We cannot sell in " + newState + ", please enter a valid state.");
        }
        
        if(newState.equals(""))
            newState = toEdit.getState();
        
        String newProductType = io.readString("Please enter product type("+ toEdit.getProductType()+")");
        
        while(!(listOfProductNames.contains(newProductType))){
            if(newProductType.equals("")){
                newProduct = originalProduct;
                break;
            }
            io.print("Invalid option, please enter a product type by name.");
            displayAllProducts(products);     
            newProductType = io.readString("Please enter product type("+ toEdit.getProductType()+")");
        }
        
        final String finalProductType = newProductType;
        List<Product> fromFilter = products.stream().filter(p->p.getProductType().equals(finalProductType)).collect(Collectors.toList());
        if(fromFilter.isEmpty()){
            newProduct = originalProduct;
        }
        else{
            newProduct = fromFilter.get(0);
        }
        
        String orderAreaString = io.readString("Please enter the area of the flooring("+ toEdit.getArea()+").");
        
        if(orderAreaString.equals("")){
            orderArea = toEdit.getArea();
        }
        else{
            orderArea = new BigDecimal(orderAreaString);
            while(orderArea.compareTo(new BigDecimal("100"))== -1){
                orderAreaString = io.readString("Please enter the area of the flooring. (Area must be a minimum of 100sq ft.)");
                if(orderAreaString.equals(""))
                    orderArea = toEdit.getArea();
                orderArea = new BigDecimal(orderAreaString);
            }
        }
        
        for(Tax tax : taxes){
            if(tax.getStateAbbreviation().equals(newState)){
                newTax = tax;
                break;
            }
        }
        
        editedOrder = new Order(newProduct,newTax);
        editedOrder.setDate(toEdit.getDate());
        editedOrder.setOrderNumber(toEdit.getOrderNumber());
        editedOrder.setCustomerName(newName);
        editedOrder.setArea(orderArea);
        editedOrder.calculateCosts();
        return editedOrder;
    }
    
    public int displayRemoveOrder(Order toRemove){
        displayBanner("REMOVE MENU");
        displayOrder(toRemove);
        return io.readInt("Are you sure you would like to delete this order?\n1.Yes\n2.No");     
    }
    
    public void displayRemovedSuccessfully(){
        displayBanner("ORDER SUCCESSFULLY REMOVED");
    }

    public void displayEditedSuccesfully(){
        displayBanner("ORDER SUCCESSFULLY EDITED");
    }
    public void displayAddedSuccessfully(){
        displayBanner("ORDER ADDED SUCCESFULLY");
    }
    
    public void displayBanner(String message){
        printBorder();
        io.print(message);
        printBorder();
    }
}
