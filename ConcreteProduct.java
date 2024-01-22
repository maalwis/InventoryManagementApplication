public class ConcreteProduct extends AbstractProduct {

    String name;

    int quantity;

    double price;


    @Override
    double calculateTotalValue() {

        return quantity * price * (1 + InventoryManagementSystem.getTaxRate());

    }

    // other fields and methods...
}

