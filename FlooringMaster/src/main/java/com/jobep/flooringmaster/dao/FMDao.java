/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.dao;

import com.jobep.flooringmaster.model.Order;
import com.jobep.flooringmaster.model.Product;
import com.jobep.flooringmaster.model.Tax;
import java.util.List;

/**
 *
 * @author powel
 */
public interface FMDao {
    public void removeOrder(int orderNumber);
    public void addOrder(int orderNumber, Order order);
    public Order getOrder(int orderNumber);
    public Order unmarshallOrder(String line);
    public Product unmarshallProduct(String line);
    public Tax unmarshallTax(String line);
    public String marshall(Order order);
    public void writeFile() throws FMPersistenceException;
    public void readFiles() throws FMPersistenceException;
    public List<Order> getOrderList();
    public List<Product> getProductList();
    public List<Tax> getTaxList();
}
