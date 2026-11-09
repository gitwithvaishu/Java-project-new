import java.util.*;

class Medicine {
    int id;
    String name;
    double price;
    int stock;

    Medicine(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Order {
    int orderId;
    String username;
    String medicineName;
    int quantity;
    double total;
    String status;

    Order(int orderId, String username, String medicineName, int quantity, double total) {
        this.orderId = orderId;
        this.username = username;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.total = total;
        this.status = "Placed";
    }
}

public class Pharmacy {
    static Scanner sc = new Scanner(System.in);

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Medicine> medicines = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    static int orderId = 1;
    static String currentUser = "";

    public static void main(String[] args) {

        medicines.add(new Medicine(1, "Amoxicillin", 20, 50));
        medicines.add(new Medicine(2, "Dolo 650", 30, 40));
        medicines.add(new Medicine(3, "Vitamin C", 50, 30));
        medicines.add(new Medicine(4, "Cetirizine", 15, 25));

        while (true) {
            System.out.println("\n--- PHARMACY MANAGEMENT SYSTEM ---");
            System.out.println("1. Register");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    register();
                    break;

                case 2:
                    userLogin();
                    break;

                case 3:
                    adminLogin();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String username = sc.next();

        System.out.print("Enter password: ");
        String password = sc.next();

        for (User u : users) {
            if (u.username.equals(username)) {
                System.out.println("Username already exists!");
                return;
            }
        }

        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    static void userLogin() {
        System.out.print("Username: ");
        String username = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        for (User u : users) {
            if (u.username.equals(username) &&
                u.password.equals(password)) {

                currentUser = username;
                userMenu();
                return;
            }
        }

        System.out.println("Invalid username or password!");
    }

    static void userMenu() {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. View Medicines");
            System.out.println("2. Order Medicine");
            System.out.println("3. View My Orders");
            System.out.println("4. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewMedicines();
                    break;

                case 2:
                    orderMedicine();
                    break;

                case 3:
                    viewOrders();
                    break;

                case 4:
                    currentUser = "";
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewMedicines() {
        System.out.println("\n--- AVAILABLE MEDICINES ---");

        for (Medicine m : medicines) {
            System.out.println(
                m.id + ". " + m.name +
                " | Price: Rs." + m.price +
                " | Stock: " + m.stock
            );
        }
    }

    static void orderMedicine() {
        viewMedicines();

        System.out.print("Enter medicine ID: ");
        int id = sc.nextInt();

        Medicine selected = null;

        for (Medicine m : medicines) {
            if (m.id == id) {
                selected = m;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Medicine not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity <= 0 || quantity > selected.stock) {
            System.out.println("Invalid quantity or insufficient stock!");
            return;
        }

        double total = selected.price * quantity;
        selected.stock -= quantity;

        orders.add(new Order(orderId++, currentUser,
                selected.name, quantity, total));

        System.out.println("Order placed successfully!");
        System.out.println("Total amount: Rs." + total);
    }

    static void viewOrders() {
        System.out.println("\n--- MY ORDERS ---");

        boolean found = false;

        for (Order o : orders) {
            if (o.username.equals(currentUser)) {
                System.out.println(
                    "Order ID: " + o.orderId +
                    " | Medicine: " + o.medicineName +
                    " | Quantity: " + o.quantity +
                    " | Total: Rs." + o.total +
                    " | Status: " + o.status
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No orders found!");
        }
    }

    static void adminLogin() {
        System.out.print("Admin username: ");
        String username = sc.next();

        System.out.print("Admin password: ");
        String password = sc.next();

        if (username.equals("admin") &&
            password.equals("admin123")) {
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Medicines");
            System.out.println("2. Add Medicine");
            System.out.println("3. Update Stock");
            System.out.println("4. View All Orders");
            System.out.println("5. Update Order Status");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewMedicines();
                    break;

                case 2:
                    addMedicine();
                    break;

                case 3:
                    updateStock();
                    break;

                case 4:
                    viewAllOrders();
                    break;

                case 5:
                    updateOrderStatus();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addMedicine() {
        System.out.print("Enter medicine ID: ");
        int id = sc.nextInt();

        for (Medicine m : medicines) {
            if (m.id == id) {
                System.out.println("Medicine ID already exists!");
                return;
            }
        }

        System.out.print("Enter medicine name: ");
        String name = sc.next();

        System.out.print("Enter price: ");
        double price = sc.nextDouble();

        System.out.print("Enter stock: ");
        int stock = sc.nextInt();

        if (price <= 0 || stock < 0) {
            System.out.println("Invalid price or stock!");
            return;
        }

        medicines.add(new Medicine(id, name, price, stock));
        System.out.println("Medicine added successfully!");
    }

    static void updateStock() {
        viewMedicines();

        System.out.print("Enter medicine ID: ");
        int id = sc.nextInt();

        for (Medicine m : medicines) {
            if (m.id == id) {
                System.out.print("Enter new stock: ");
                int stock = sc.nextInt();

                if (stock < 0) {
                    System.out.println("Invalid stock!");
                    return;
                }

                m.stock = stock;
                System.out.println("Stock updated successfully!");
                return;
            }
        }

        System.out.println("Medicine not found!");
    }

    static void viewAllOrders() {
        System.out.println("\n--- ALL ORDERS ---");

        if (orders.isEmpty()) {
            System.out.println("No orders available!");
            return;
        }

        for (Order o : orders) {
            System.out.println(
                "Order ID: " + o.orderId +
                " | User: " + o.username +
                " | Medicine: " + o.medicineName +
                " | Quantity: " + o.quantity +
                " | Status: " + o.status
            );
        }
    }

    static void updateOrderStatus() {
        viewAllOrders();

        System.out.print("Enter order ID: ");
        int id = sc.nextInt();

        for (Order o : orders) {
            if (o.orderId == id) {
                System.out.print("Enter status (Placed/Processing/Delivered): ");
                o.status = sc.next();

                System.out.println("Order status updated!");
                return;
            }
        }

        System.out.println("Order not found!");
    }
}