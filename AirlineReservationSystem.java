import java.util.*;

class Flight {
    int flightId;
    String flightName;
    String source;
    String destination;
    String date;
    double fare;
    int availableSeats;

    Flight(int flightId, String flightName, String source, String destination, String date, double fare, int availableSeats) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.fare = fare;
        this.availableSeats = availableSeats;
    }
}

class Passenger {
    String username;
    String password;

    Passenger(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Booking {
    int bookingId;
    String username;
    String flightName;
    String source;
    String destination;
    int tickets;
    double totalFare;

    Booking(int bookingId, String username, Flight flight, int tickets) {
        this.bookingId = bookingId;
        this.username = username;
        this.flightName = flight.flightName;
        this.source = flight.source;
        this.destination = flight.destination;
        this.tickets = tickets;
        this.totalFare = flight.fare * tickets;
    }
}

public class AirlineReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Passenger> users = new ArrayList<>();
    static ArrayList<Flight> flights = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    static String currentUser = "";
    static int bookingId = 1;

    public static void main(String[] args) {

        flights.add(new Flight(101, "IndiGo", "Chennai", "Bangalore", "10-11-2026", 2500, 40));

        flights.add(new Flight(102, "Air India", "Chennai", "Delhi", "10-11-2026", 5500, 50));

        flights.add(new Flight(103, "IndiGo", "Mumbai", "Chennai", "11-11-2026", 3500, 30));

        while (true) {
            System.out.println("\n--- AIRLINE RESERVATION SYSTEM ---");
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

        for (Passenger p : users) {
            if (p.username.equals(username)) {
                System.out.println("Username already exists!");
                return;
            }
        }

        users.add(new Passenger(username, password));
        System.out.println("Registration successful!");
    }

    static void userLogin() {
        System.out.print("Enter username: ");
        String username = sc.next();

        System.out.print("Enter password: ");
        String password = sc.next();

        for (Passenger p : users) {
            if (p.username.equals(username) && p.password.equals(password)) {
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
            System.out.println("1. View All Flights");
            System.out.println("2. Search Flights");
            System.out.println("3. Check Seat Availability");
            System.out.println("4. Book Tickets");
            System.out.println("5. View My Bookings");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewFlights();
                    break;
                case 2:
                    searchFlights();
                    break;
                case 3:
                    checkSeats();
                    break;
                case 4:
                    bookTicket();
                    break;
                case 5:
                    viewBookings();
                    break;
                case 6:
                    currentUser = "";
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewFlights() {
        System.out.println("\n--- AVAILABLE FLIGHTS ---");
        if (flights.isEmpty()) {
            System.out.println("No flights available!");
            return;
        }
        for (Flight f : flights) {
            displayFlight(f);
        }
    }

    static void displayFlight(Flight f) {
        System.out.println(
            "Flight ID: " + f.flightId +
            " | Airline: " + f.flightName +
            " | From: " + f.source +
            " | To: " + f.destination +
            " | Date: " + f.date +
            " | Fare: Rs." + f.fare +
            " | Available Seats: " + f.availableSeats
        );
    }

    static void searchFlights() {
        sc.nextLine();
        System.out.print("Enter source: ");
        String source = sc.nextLine();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine();

        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        boolean found = false;

        System.out.println("\n--- SEARCH RESULTS ---");

        for (Flight f : flights) {
            if (f.source.equalsIgnoreCase(source) && f.destination.equalsIgnoreCase(destination) && f.date.equals(date)) {
                displayFlight(f);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching flights found!");
        }
    }

    static void checkSeats() {
        System.out.print("Enter flight ID: ");
        int id = sc.nextInt();

        for (Flight f : flights) {
            if (f.flightId == id) {
                System.out.println("Flight: " + f.flightName);
                System.out.println("Available seats: " + f.availableSeats);
                return;
            }
        }

        System.out.println("Flight not found!");
    }

    static void bookTicket() {
        viewFlights();

        System.out.print("\nEnter flight ID: ");
        int id = sc.nextInt();

        Flight selected = null;

        for (Flight f : flights) {
            if (f.flightId == id) {
                selected = f;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Flight not found!");
            return;
        }

        System.out.print("Enter number of tickets: ");
        int tickets = sc.nextInt();

        if (tickets <= 0 || tickets > selected.availableSeats) {
            System.out.println("Invalid ticket quantity or insufficient seats!");
            return;
        }

        selected.availableSeats -= tickets;

        Booking b = new Booking( bookingId++, currentUser, selected, tickets );

        bookings.add(b);

        System.out.println("\nBooking successful!");
        System.out.println("Booking ID: " + b.bookingId);
        System.out.println("Flight: " + b.flightName);
        System.out.println("Number of tickets: " + b.tickets);
        System.out.println("Total fare: Rs." + b.totalFare);
    }

    static void viewBookings() {
        System.out.println("\n--- MY BOOKINGS ---");

        boolean found = false;

        for (Booking b : bookings) {
            if (b.username.equals(currentUser)) {
                System.out.println(
                    "Booking ID: " + b.bookingId +
                    " | Flight: " + b.flightName +
                    " | From: " + b.source +
                    " | To: " + b.destination +
                    " | Tickets: " + b.tickets +
                    " | Total: Rs." + b.totalFare
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found!");
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
        }
        else {
            System.out.println("Invalid admin credentials!");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Flights");
            System.out.println("2. Add Flight");
            System.out.println("3. Update Flight Fare");
            System.out.println("4. Delete Flight");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewFlights();
                    break;
                case 2:
                    addFlight();
                    break;
                case 3:
                    updateFare();
                    break;
                case 4:
                    deleteFlight();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addFlight() {
        System.out.print("Enter flight ID: ");
        int id = sc.nextInt();

        for (Flight f : flights) {
            if (f.flightId == id) {
                System.out.println("Flight ID already exists!");
                return;
            }
        }

        System.out.print("Enter airline name: ");
        String name = sc.nextLine();

        System.out.print("Enter source: ");
        String source = sc.nextLine();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine();

        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = sc.next();

        System.out.print("Enter ticket fare: ");
        double fare = sc.nextDouble();

        System.out.print("Enter available seats: ");
        int seats = sc.nextInt();

        if (fare <= 0 || seats < 0) {
            System.out.println("Invalid fare or seat count!");
            return;
        }

        flights.add(new Flight(id, name, source, destination, date, fare, seats));

        System.out.println("Flight added successfully!");
    }

    static void updateFare() {
        System.out.print("Enter flight ID: ");
        int id = sc.nextInt();

        for (Flight f : flights) {
            if (f.flightId == id) {
                System.out.print("Enter new fare: ");
                double fare = sc.nextDouble();
                if (fare <= 0) {
                    System.out.println("Invalid fare!");
                    return;
                }
                f.fare = fare;
                System.out.println("Flight fare updated!");
                return;
            }
        }

        System.out.println("Flight not found!");
    }

    static void deleteFlight() {
        System.out.print("Enter flight ID: ");
        int id = sc.nextInt();

        for (Flight f : flights) {
            if (f.flightId == id) {
                flights.remove(f);
                System.out.println("Flight deleted successfully!");
                return;
            }
        }

        System.out.println("Flight not found!");
    }
}