/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.service;

import com.jobep.flooringmaster.dao.FMNoOrderForDateException;
import com.jobep.flooringmaster.dao.FMPersistenceException;
import com.jobep.flooringmaster.model.Order;
import com.jobep.flooringmaster.model.Product;
import com.jobep.flooringmaster.model.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author powel
 */
public interface FMService {
    public void insertOrder(Order toInsert) throws FMPersistenceException;
    public void editOrder(Order order) throws FMNoOrderForDateException,FMPersistenceException;
    public void removeOrder(Order order) throws FMPersistenceException;
    public Order getOrder(int orderNumber, LocalDate date) throws FMNoOrderForDateException;
    public List<Order> getOrdersByDate(LocalDate date) throws FMNoOrderForDateException;
    public void readFiles() throws FMPersistenceException;
    public void writeFile() throws FMPersistenceException;
    public List<Product> getAllProducts();
    public List<Tax> getAllTaxes();
    public List<Order> getAllOrders();
}
