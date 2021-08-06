/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.model;

import java.math.BigDecimal;

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
}
