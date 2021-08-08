/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.dao;

import com.jobep.flooringmaster.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author powel
 */
public class FMDaoFileImplTest {
    FMDaoFileImpl dao = new FMDaoFileImpl();
    
    public FMDaoFileImplTest() {
        dao.setFiles("src/testData/testProducts.txt", "src/testData/testTaxes.txt", "src/testOrders");
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        try{
            dao.readFiles();
        }catch(Exception e){
            System.out.println(e.getMessage());
            fail("Could not open files");
        }
    }
    
    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testRemoveOrder() {
        Order toRemove = new Order();
        toRemove.setOrderNumber(55);
        dao.addOrder(55, toRemove);
        assertTrue(dao.getOrderList().contains(toRemove),"Test object was not added to list");
        dao.removeOrder(55);
        assertFalse(dao.getOrderList().contains(toRemove),"Test object was not removed from list");
    }
    
    @Test
    public void testAddOrder(){
        Order toAdd = new Order();
        toAdd.setOrderNumber(56);
        dao.addOrder(56, toAdd);
        assertTrue(dao.getOrderList().contains(toAdd), "Test object was not added to list");
    }
    
    @Test 
    public void testGetOrder(){
        Order toGet = new Order();
        toGet.setOrderNumber(57);
        dao.addOrder(57,toGet);
        assertTrue(dao.getOrderList().contains(toGet), "Test object was not added to list");
        Order returnedOrder = dao.getOrder(57);
        assertEquals(toGet,returnedOrder,"Retrieved item does not match requested item");
        dao.removeOrder(57);
        returnedOrder = dao.getOrder(57);
        assertNotEquals(returnedOrder,toGet,"Retrieved item should not matched previously entered Item");
    }
    
    
}
