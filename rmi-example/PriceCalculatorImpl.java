package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PriceCalculatorImpl extends UnicastRemoteObject implements PriceCalculator {

    public PriceCalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double calculateSellingPrice(double costPrice, double discountAmount) throws RemoteException {
        // Calculate selling price by subtracting discount from cost price
        double sellingPrice = costPrice - discountAmount;

        // Print calculation details on server side
        System.out.println("Server calculating price:");
        System.out.println("Cost Price: Rs. " + costPrice);
        System.out.println("Discount Amount: Rs. " + discountAmount);
        System.out.println("Selling Price: Rs. " + sellingPrice);

        return sellingPrice;
    }
}
