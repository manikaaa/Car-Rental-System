import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carID;
    private String model;
    private String brand;
    private double basePricePerDay;

    private boolean isAvailable;

    public Car(String carID, String model, String brand, double basePricePerDay) {
        this.carID = carID;
        this.model = model;
        this.brand = brand;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getBasePriceDay() {
        return basePricePerDay;
    }

    public void setBasePrice(double basePricePerDay) {
        this.basePricePerDay = basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public void rent() {
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

}


class Customer{
    private String customerName;
    private String customerID;

    public Customer(String customerName, String customerID) {
        this.customerName = customerName;
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}


class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}

class CarRentalSystem {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent");
        }

    }

    public void returnCar(Car car) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("System err! Car not returned");

        }
        car.returnCar();
    }


    public void menu() {
        Scanner scanObj = new Scanner(System.in);

        while(true){
            System.out.println("-------Car Rental System-----");
            System.out.println("1.Rent a car");
            System.out.println("2.Return a car");
            System.out.println("3.Exit");
            System.out.print("Enter your choice: ");

            int choice = scanObj.nextInt();
            scanObj.nextLine();

            if(choice ==1){
                System.out.println("\nYou picked choice 1 - Rent a car");
                System.out.print("\nEnter your name: ");
                String customerName = scanObj.nextLine();

                System.out.println("\nHere are the available cars:");
                for(Car car: cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarID() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carID = scanObj.nextLine();

                System.out.print("Enter the number of days you want to rent: ");
                int rentalDays = scanObj.nextInt();
                scanObj.nextLine(); //consume newline

                Customer newCustomer = new Customer(customerName,"CUS" + (customers.size() + 1));
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car: cars){
                    if(car.getCarID().equals(carID) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar!= null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n-----Rental Information------");
                    System.out.println("Customer ID: " + newCustomer.getCustomerID());
                    System.out.println("Customer Name: " + newCustomer.getCustomerName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel() );
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.println("Total Price:$" + totalPrice);

                    System.out.print("\nConfirm Rental (Y/N): ");
                    String confirm = scanObj.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully");
                    }

                    else {
                        System.out.println("\n Rental Canceled");
                    }
                }

                else {
                    System.out.println("\nInvalid car selection or cat is not available");
                }
            } else if (choice == 2) {

                System.out.println("\nYou picked choice 2 - Return a car");
                System.out.print("\nEnter the car ID you want to return: ");
                String carID = scanObj.nextLine();

                Car carToReturn = null;
                for(Car car: cars){
                    if(car.getCarID().equals(carID) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn!= null){
                    Customer customer = null;
                    for(Rental rental: rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }

                    }

                    if(customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getCustomerName());
                    }
                    else {
                        System.out.println("Car was not rented or rental info is missing");
                    }


                }
                else {
                    System.out.println("Invalid carID. Please enter a valid ID");
                }
            } else if (choice == 3) {
                break;
            }
            else {
                System.out.println("Invalid choice");
            }

        }

        System.out.println("\nThank you for using the car rental system");

        }



    }


public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "CX5", "Mazda", 60);
        Car car2 = new Car("C002", "Camry", "Toyota", 70);
        Car car3 = new Car("C003", "Civic", "Honda", 40);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();


        }
    }
