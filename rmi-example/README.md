# Setting Up and Testing RMI in IntelliJ IDEA

## Step 1: Create a New Project

- Open IntelliJ IDEA
- Click on "New Project"
- Select "Java" on the left panel
- Choose your JDK (Java Development Kit) version (at least Java 8)
- Click "Next"
- Name your project (e.g., "RMICalculator")
- Click "Finish"

## Step 2: Create the Package Structure

- Right-click on the "src" folder in the Project panel
- Select "New" → "Package"
- Name it "com.example.rmi" (or any package name you prefer)

## Step 3: Create the Remote Interface

- Right-click on your package
- Select "New" → "Java Class"
- Name it "PriceCalculator"
- Replace the contents with PriceCalculator.java


## Step 4: Create the Remote Implementation

- Right-click on your package
- Select "New" → "Java Class"
- Name it "PriceCalculatorImpl"
- Replace the contents with PriceCalculatorImpl.java

## Step 5: Create the Server Class

- Right-click on your package
- Select "New" → "Java Class"
- Name it "PriceServer"
- Replace the contents with PriceServer.java

## Step 6: Create the Client Class

- Right-click on your package
- Select "New" → "Java Class"
- Name it "PriceClient"
- Replace the contents with PriceClient.java

## Step 7: Run the Application
### Running the Server:
- Right-click on the PriceServer class in the Project panel
- Select ```"Run PriceServer.main()"```
- You should see ```Price Calculator Server is running...``` in the Run console

### Running the Client:
- Right-click on the ```PriceClient``` class in the Project panel
- Select ```"Run PriceClient.main()"```
- A GUI window should appear

## Troubleshooting Common RMI Issues
### Port Already in Use
If you get an error saying port ```1099``` is already in use, you can:

- Check if another RMI registry is running
- Change the port number in both server and client code

### Connection Refused
If the client cannot connect to the server:

- Make sure the server is running first
- Check if any firewall is blocking the connection
- Verify that both client and server use the same port

### Class Not Found Exceptions
If you encounter ```ClassNotFoundException``` related to stubs:

- Since Java 5+, dynamic stub generation is used and no separate rmic compilation is needed
- Just ensure you're using a consistent class structure

## Important Notes for Beginners

- **Run Server First:** Always start the server before the client.
- **Package Structure:** Keep all your classes in the same package to avoid class loading issues.
- **IntelliJ Run Configuration:** You can create and save run configurations for both server and client for easier testing:
  - Click "Run" → "Edit Configurations"
  - Create separate configurations for server and client
- **Working with Multiple Terminals:** To run both server and client simultaneously in IntelliJ:
  - Start the server
  - Click the "+" icon in the Run window to add a new tab
  - Run the client in the new tab