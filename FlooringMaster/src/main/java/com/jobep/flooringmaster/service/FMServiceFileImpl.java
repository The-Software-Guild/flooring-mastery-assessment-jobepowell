/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.service;

import com.jobep.flooringmaster.dao.FMAuditDao;
import com.jobep.flooringmaster.dao.FMDao;
import com.jobep.flooringmaster.dao.FMNoOrderForDateException;
import com.jobep.flooringmaster.dao.FMPersistenceException;
import com.jobep.flooringmaster.model.Order;
import com.jobep.flooringmaster.model.Product;
import com.jobep.flooringmaster.model.Tax;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author powel
 */
@Component
public class FMServiceFileImpl implements FMService{
    
    private FMDao dao;
    private FMAuditDao audit;
    
    @Autowired
    public FMServiceFileImpl(FMDao dao,FMAuditDao audit){
        this.dao = dao;
        this.audit = audit;
    }
    /**
     * Adds an order to the in-memory map
     */
    @Override
    public void insertOrder(Order toInsert) {
        dao.addOrder(toInsert.getOrderNumber(), toInsert);
    }
    
    /**
     * 
     */
    @Override
    public Order getOrder(int orderNumber, LocalDate date)throws FMNoOrderForDateException{
        List<Order> possibleOrder = getOrdersByDate(date);
        List<Order> fromList = possibleOrder.stream().filter(p -> p.getOrderNumber() == orderNumber).collect(Collectors.toList());
        if(fromList.isEmpty())
            throw new FMNoOrderForDateException("ERROR: No order with that date/number combination");
        Order toReturn = fromList.get(0);
        return toReturn;
        
    }
    /**
     * Removes an entry from the map and replaces it with a new one
     */
    @Override
    public void editOrder(Order order) throws FMNoOrderForDateException{
        Order oldOrder = getOrder(order.getOrderNumber(),order.getDate());
        if(oldOrder == null)
            throw new FMNoOrderForDateException("ERROR: COULD NOT FIND ORDER FOR THAT DATE WHILE EDITING");
        removeOrder(oldOrder);
        insertOrder(order);
    }
    /**
     * removes an order from the map
     */
    @Override
    public void removeOrder(Order order) {
        dao.removeOrder(order.getOrderNumber());
    }
    
    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FMNoOrderForDateException{
        List<Order> orders = dao.getOrderList().stream().filter(p -> p.getDate().equals(date)).collect(Collectors.toList());
        if(orders.isEmpty()){
            throw new FMNoOrderForDateException("ERROR: COULD NOT FIND ORDERS FOR THAT DATE");
        }
        return orders;
    }
    
    @Override
    public void readFiles() throws FMPersistenceException{
        dao.readFiles();
    }
    
    @Override
    public void writeFile() throws FMPersistenceException{
        dao.writeFile();
    }

    @Override
    public List<Product> getAllProducts() {
       return dao.getProductList();
    }

    @Override
    public List<Tax> getAllTaxes() {
        return dao.getTaxList();
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.getOrderList();
    }

}
