import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class InventoryManagementSystem {

    private static final int MIN_STOCK_THRESHOLD = 10;

    private static final double TAX_RATE = 0.1;

    private static Map<String, Product> inventory = new HashMap<>();

    private static Semaphore inventorySemaphore = new Semaphore(1);
    

    public static void loadProductsFromFile(String filename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String productId = parts[0];

                String name = parts[1];

                int quantity = Integer.parseInt(parts[2]);

                double price = Double.parseDouble(parts[3]);

                Product product = new Product(name, quantity, price);

                inventory.put(productId, product);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static double getTaxRate() {

        return TAX_RATE;

    }

    public static void addProductToInventory(String productId, Product product) {

        inventory.put(productId, product);

    }

    public static Product getProductFromInventory(String productId) {

        return inventory.get(productId);

    }

    public static void addInventory(Product product, int quantityToAdd) {

        product.quantity += quantityToAdd;

    }

    public static void removeInventory(Product product, int quantityToRemove) {

        if (product.quantity >= quantityToRemove) {

            product.quantity -= quantityToRemove;

        } 
        
        else {

            throw new IllegalArgumentException("Not enough stock available");

        }

    }

    public static void updateInventoryConcurrently(Product product, int quantityToAdd) {

        try {

            inventorySemaphore.acquire();

            addInventory(product, quantityToAdd);

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            inventorySemaphore.release();

        }

    }

    public static double calculateTotalValue(Product product) {

        return product.quantity * product.price * (1 + TAX_RATE);

    }

    public static boolean isLowOnStock(Product product) {

        return product.quantity < MIN_STOCK_THRESHOLD;

    }


    public static void handleException() {

        try {

            // code that may throw an exception

        } catch (Exception e) {

            // handle the exception gracefully

            e.printStackTrace();

        }

    }

}

