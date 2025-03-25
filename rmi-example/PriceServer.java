package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PriceServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            PriceCalculator calculator = new PriceCalculatorImpl();

            // Create and start the registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the remote object to the registry with a name
            registry.rebind("PriceCalculator", calculator);

            System.out.println("Price Calculator Server is running...");
        } catch (Exception e) {
            System.err.println("Server Error: " + e.toString());
            e.printStackTrace();
        }
    }
}
