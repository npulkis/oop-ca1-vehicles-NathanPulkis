package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuStarter {
    // Components (objects) used in this App
    PassengerStore passengerStore;  // encapsulates access to list of Passengers
    VehicleManager vehicleManager;  // encapsulates access to list of Vehicles
    BookingManager bookingManager;  // deals with all bookings

    public static void main(String[] args) {
        MenuStarter app = new MenuStarter();
        app.start();
    }

    public void start() {
        // create PassengerStore and load all passenger records from text file
        passengerStore = new PassengerStore("passengers.OUT");

        // create VehicleManager, and load all vehicles from text file
        vehicleManager = new VehicleManager("vehicles.OUT");




        try {
            displayMainMenu();        // User Interface - Menu
        } catch (IOException e) {
            e.printStackTrace();
        }




        //   vehicleManager.displayAllVehicles();

//
//           String registration = "172LH234106";
//           Vehicle vehicle = vehicleManager.findVehicleByReg(registration);
//           if (vehicle == null)
//              System.out.println("No vehicle with registration " + registration + " was found.");
//           else
//               System.out.println("Found Vehicle: " + vehicle.toString());

        // Create BookingManager and load all bookings from file
        // bookingManager = new BookingManager("bookings.txt");

        //pMgr.saveToFile();

        System.out.println("Program ending, Goodbye");
    }

    private void displayMainMenu() throws IOException {

        final String MENU_ITEMS = "\n*** MAIN MENU OF OPTIONS ***\n"
                + "1. Passengers\n"
                + "2. Vehicles\n"
                + "3. Bookings\n"
                + "4. Exit\n"
                + "Enter Option [1,2,4]";

        final int PASSENGERS = 1;
        final int VEHICLES = 2;
        final int BOOKINGS = 3;
        final int EXIT = 4;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case PASSENGERS:
                        System.out.println("Passengers option chosen");
                        displayPassengerMenu();
                        break;
                    case VEHICLES:
                        System.out.println("Vehicles option chosen");
                        displayVehicleMenu();
                        break;
                    case BOOKINGS:
                        System.out.println("Bookings option chosen");
                        displayBookingMenu();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        close();
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

        System.out.println("\nExiting Main Menu, goodbye.");

    }

    // Sub-Menu for Passenger operations
    //
    private void displayPassengerMenu() {
        final String MENU_ITEMS = "\n*** PASSENGER MENU ***\n"
                + "1. Show all Passengers\n"
                + "2. Find Passenger by Name\n"
                + "3. Add Passenger\n"
                + "4. Delete Passenger\n"
                + "5. Exit\n"
                + "Enter Option [1,2,3,4.5]";

        final int SHOW_ALL = 1;
        final int FIND_BY_NAME = 2;
        final int ADD_PASSENGER = 3;
        final int DELETE_PASSENGER =4;
        final int EXIT = 5;


        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case SHOW_ALL:
                        System.out.println("Display ALL Passengers");
                        passengerStore.displayAllPassengers();
                        promptEnterKey();
                        break;
                    case FIND_BY_NAME:
                        System.out.println("Find Passenger by Name");
                        System.out.println("Enter passenger name: ");
                        String name = keyboard.nextLine();
                        Passenger p = passengerStore.findPassengerByName(name);
                        if (p == null)
                            System.out.println("No passenger matching the name \"" + name + "\"");
                        else
                            System.out.println("Found passenger: \n" + p.toString());

                        promptEnterKey();
                        break;

                    case ADD_PASSENGER:


                        boolean result;
                        do {
                           result=addPassenger();
                        }while (!result);

                        promptEnterKey();
                        break;

                    case DELETE_PASSENGER:
                        System.out.println("Delete Passenger chosen");

                        System.out.println("Enter Passenger id:");
                       int id = keyboard.nextInt();
                       keyboard.nextLine();

                       Passenger pass = passengerStore.findPassengerById(id);
                       if (pass ==null){
                           System.out.println("Passenger not found");
                           promptEnterKey();
                           break;
                       }
                       passengerStore.removePassenger(pass);

                       promptEnterKey();
                        break;


                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        promptEnterKey();
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

    }


    private void displayVehicleMenu() {
        final String MENU_ITEMS = "\n*** VEHICLE MENU ***\n"
                + "1. Show all Vehicle\n"
                + "2. Find Vehicle by Registration\n"
                + "3. Find Vehicle by Type\n"
                + "4. Exit\n"
                + "Enter Option [1,2,3,4]";

        final int SHOW_ALL = 1;
        final int FIND_BY_REG = 2;
        final int FIND_BY_TYPE = 3;
        final int EXIT = 4;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case SHOW_ALL:
                        System.out.println("\nDisplay ALL Vehicles");
                        ArrayList<Vehicle> vehicles = vehicleManager.findAllVehicles();
                        for (Vehicle v : vehicles) {
                            System.out.println(v.toString());
                        }
                        promptEnterKey();
                        break;
                    case FIND_BY_REG:
                        System.out.println("\nFind Vehicle by Registration");
                        System.out.print("Enter vehicle registration: ");
                        String reg = keyboard.nextLine();
                        Vehicle v = vehicleManager.findVehicleByReg(reg);
                        if (v == null)
                            System.out.println("No vehicle matching the registration \"" + reg + "\"");
                        else
                            System.out.println("Found vehicle: \n" + v.toString());
                        promptEnterKey();
                        break;

                    case FIND_BY_TYPE:

                        System.out.println("\nFind Vehicle by Type");

                        System.out.println("1. Van");
                        System.out.println("2. Truck");
                        System.out.print("\nEnter Type: ");
                        typeSelection();
                        break;

                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

    }



    private void displayBookingMenu() {
        final String MENU_ITEMS = "\n*** BOOKING MENU ***\n"
                + "1.Show all bookings\n"
                + "2.Make a booking\n"
                + "3.Exit\n"
                + "Enter Option [1,2,3]";

        final int SHOW_ALL = 1;
        final int MAKE_BOOKING = 2;
        final int EXIT = 3;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case SHOW_ALL:
                       break;
                    case MAKE_BOOKING:

                        boolean result;
                        do {
                            result = makeBooking();
                        } while (!result);

                        promptEnterKey();
                        break;

                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

    }

    public void typeSelection() {

        Scanner keyboard = new Scanner(System.in);
        String usersInput = keyboard.nextLine();
        int option = Integer.parseInt(usersInput);
        final int VAN = 1;
        final int TRUCK = 2;

        switch (option) {
            case VAN:
                ArrayList<Vehicle> typeList = vehicleManager.findVechicleByType("Van");
                for (Vehicle b : typeList) {
                    System.out.println(b.toString());
                }
                break;
            case TRUCK:
                ArrayList<Vehicle> typeList2 = vehicleManager.findVechicleByType("Truck");
                for (Vehicle b : typeList2) {
                    System.out.println(b.toString());
                }
                break;
            default:
                System.out.print("Invalid option - please enter number in range");
                break;
        }


    }

    public boolean addPassenger() {

        try {


            Scanner keyboard = new Scanner(System.in);


            System.out.print("Enter Passenger name: ");
            String pName = keyboard.nextLine();
            System.out.print("Enter Passenger email: ");
            String pEmail = keyboard.nextLine();

            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(pEmail);
            if (!matcher.matches()) {
                System.out.println("\nWrong email input: Email must only contain A-Z a-z 0-9 .,-,_ and @ ");

             return false;
            }

            System.out.print("Enter Passenger phone number: ");
            String pPhone = keyboard.nextLine();

            regex = "^[0-9]+(-[0-9]+)+$";
            pattern = Pattern.compile(regex);

            matcher = pattern.matcher(pPhone);
            if (!matcher.matches()) {
                System.out.println("\nWrong phone input: Phone must only contain  0-9 and - \n" +
                        "please try again");

               return false;
            }


            System.out.print("Enter Passenger latitude: ");
            double pLatitude = keyboard.nextDouble();
            System.out.print("Enter Passenger longitude: ");
            double pLongitude = keyboard.nextDouble();


            passengerStore.addPassenger(pName, pEmail, pPhone, pLatitude, pLongitude);
            keyboard.nextLine();
        } catch (InputMismatchException | NumberFormatException err) {
            System.out.println("Wrong input please try again   " + err.toString());
            return false;
        }
    return true;}

    public boolean makeBooking(){

        try {
            Scanner keyboard = new Scanner(System.in);


            vehicleManager.displayAllVechicleIds();
            passengerStore.displayAllPassengerIds();


            System.out.println("Input Passenger ID:");

            int pID = keyboard.nextInt();

            if (passengerStore.findPassengerById(pID) == null) {
                System.out.println("Can't find passenger with given ID");
                return false;
            }

            System.out.println("Input Vehicle ID:");
            int vID = keyboard.nextInt();

            if (vehicleManager.findVechicleById(vID) == null) {
                System.out.println("Can't find vehicle with given ID");
                return false;
            }


//            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            LocalDateTime now = LocalDateTime.now();
//            String dateString = now.format(dTF);
//            LocalDateTime dateTime = LocalDateTime.parse(dateString, dTF);

//        LocationGPS start = passengerStore.findPassengerById(pID).getLocation();
//        LocationGPS end = vehicleManager.findVehicleById(vID).getDepotGPSLocation();


            System.out.println("Enter Start latitude");
            double startLatitude = keyboard.nextDouble();
            System.out.println("enter start longitude");
            double startLongitude = keyboard.nextDouble();

            LocationGPS startLocation = new LocationGPS(startLatitude, startLongitude);

            System.out.println("Enter end latitude");
            double endLatitude = keyboard.nextDouble();
            System.out.println("Enter end longitude");
            double endLongitude = keyboard.nextDouble();
            keyboard.nextLine();

            LocationGPS endLocation = new LocationGPS(endLatitude, endLongitude);


            bookingManager.addBooking(pID, vID, startLocation, endLocation);

        }catch (InputMismatchException | NumberFormatException err) {
            System.out.println("Wrong input please try again   " + err.toString());
            return false;
        }
    return true;}

    public void promptEnterKey(){
        System.out.println("\nPress \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void close(){
        vehicleManager.save();
        passengerStore.save();
        System.out.println("Exiting Program.....");
    }
}