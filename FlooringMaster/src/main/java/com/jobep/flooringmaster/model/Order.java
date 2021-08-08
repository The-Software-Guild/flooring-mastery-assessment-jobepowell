/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author powel
 */
public class Order {
    //File Format EX: 1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost; //(Area*CostPerSquareFoot)
    private BigDecimal laborCost; //(Area*LaborCostPerSquareFoot)
    private BigDecimal tax; //(MaterialCost+LaborCost) * (TaxRate/100)
    private BigDecimal total; //(MaterialCost + LaborCost + Tax)
    private LocalDate date;
    
    public Order(){
        
    }
    public Order(Product product, Tax tax){
        this.productType = product.getProductType();
        this.costPerSquareFoot = product.getCostPerSquareFoot();
        this.laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        this.state = tax.getStateAbbreviation();
        this.taxRate = tax.getTaxRate();
    }
    
    public void calculateCosts(){
        this.materialCost = this.area.multiply(this.costPerSquareFoot).setScale(2,RoundingMode.HALF_UP);
        this.laborCost = this.area.multiply(this.laborCostPerSquareFoot).setScale(2,RoundingMode.HALF_UP);
        this.tax = (this.materialCost.add(this.laborCost)).multiply(this.taxRate.divide(new BigDecimal("100"))).setScale(2,RoundingMode.HALF_UP);
        this.total = this.materialCost.add(this.laborCost.add(this.tax)).setScale(2,RoundingMode.HALF_UP);
    }
    
    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date=date;
    }
    
    public String toFileName(){
        return "Orders_" + this.date.format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt";
    }
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.orderNumber;
        hash = 79 * hash + Objects.hashCode(this.customerName);
        hash = 79 * hash + Objects.hashCode(this.state);
        hash = 79 * hash + Objects.hashCode(this.taxRate);
        hash = 79 * hash + Objects.hashCode(this.productType);
        hash = 79 * hash + Objects.hashCode(this.area);
        hash = 79 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 79 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 79 * hash + Objects.hashCode(this.materialCost);
        hash = 79 * hash + Objects.hashCode(this.laborCost);
        hash = 79 * hash + Objects.hashCode(this.tax);
        hash = 79 * hash + Objects.hashCode(this.total);
        hash = 79 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
}
