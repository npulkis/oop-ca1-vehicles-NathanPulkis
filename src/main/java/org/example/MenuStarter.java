package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
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


        String passengerFile = "passengers.ser";
        String vehicleFile = "vehicles.ser";
        String bookingsFile = "bookings.ser";


        // create PassengerStore and load all passenger records from text file
        passengerStore = new PassengerStore(passengerFile);

        // create VehicleManager, and load all vehicles from text file
        vehicleManager = new VehicleManager("vehicles.OUT");

        bookingManager = new BookingManager("bookings.out", passengerStore, vehicleManager);


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
        final int DELETE_PASSENGER = 4;
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
                        passengerStore.displayAllForm();
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
                            System.out.println("Found passenger: \n" + p);

                        promptEnterKey();
                        break;

                    case ADD_PASSENGER:
                        boolean result;
                        do {
                            result = addPassenger();
                        } while (!result);

                        promptEnterKey();
                        break;

                    case DELETE_PASSENGER:
                        System.out.println("Delete Passenger chosen");

                        System.out.println("Enter Passenger id:");
                        int id = keyboard.nextInt();
                        keyboard.nextLine();

                        Passenger pass = passengerStore.findPassengerById(id);
                        if (pass == null) {
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
                + "4. Find Cars by number of seats\n"
                + "5. Exit\n"
                + "Enter Option [1,2,3,4,5]";

        final int SHOW_ALL = 1;
        final int FIND_BY_REG = 2;
        final int FIND_BY_TYPE = 3;
        final int SHOW_BY_SEATS = 4;
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
                            System.out.println("Found vehicle: \n" + v);
                        promptEnterKey();
                        break;

                    case FIND_BY_TYPE:

                        typeSelection();
                        break;

                    case SHOW_BY_SEATS:

                        ArrayList<Car> carList = vehicleManager.findVechicleByType("car");
                        carList.addAll(vehicleManager.findVechicleByType("4x4"));
                        System.out.print("Enter Number of seats: ");
                        int seats=keyboard.nextInt();
                        keyboard.nextLine();

                        ArrayList<Car> filteredArray= vehicleManager.filterSeats(carList,seats);

                        for (Car c : filteredArray) {
                            System.out.println(c.toString());
                        }
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


    private void displayBookingMenu() {
        {
            final String MENU_ITEMS = "\n*** BOOKING MENU ***\n"
                    + "1.Display Bookings\n"
                    + "2.Make a booking\n"
                    + "3.Delete a booking\n"
                    + "4.Edit Booking"
                    + "5.Exit\n"
                    + "Enter Option [1,2,3,4,5]";

            final int SHOW_ALL = 1;
            final int MAKE_BOOKING = 2;
            final int DELETE_BOOKING = 3;
            final int EDIT_BOOKING = 4;
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
                            bookingViews();
                            break;
                        case MAKE_BOOKING:

                            boolean result;
                            do {
                                result = makeBooking();
                            } while (!result);

                            promptEnterKey();
                            break;
                        case DELETE_BOOKING:
                            System.out.println("Delete Booking by ID");

                            System.out.println("Enter Booking id:");
                            int id = keyboard.nextInt();
                            keyboard.nextLine();

                            Booking booking = bookingManager.findBookingById(id);
                            if (booking == null) {
                                System.out.println("Booking not found");
                                promptEnterKey();
                                break;
                            }
                            bookingManager.removeBooking(booking);

                            promptEnterKey();
                            break;

                        case EDIT_BOOKING:
                            editBookingMenu();
                            keyboard.nextLine();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException | NullPointerException e) {
                    //System.out.print("Invalid option - please enter number in range");
                }
            } while (option != EXIT);

        }
    }

    private void bookingViews() {
        {
            final String MENU_ITEMS = "\n*** BOOKING Views ***\n"
                    + "1.Display all\n"
                    + "2.Display by Passenger ID\n"
                    + "3.Display by Passenger name\n"
                    + "4.Display by Booking ID\n"
                    + "5.Display all future bookings\n"
                    + "Enter Option [1,2,3,4,5]";

            final int SHOW_ALL = 1;
            final int SHOW_PID = 2;
            final int SHOW_PNAME = 3;
            final int SHOW_BID = 4;
            final int SHOW_FUTURE = 5;


            Scanner keyboard = new Scanner(System.in);
            int option = 0;

            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case SHOW_ALL:
                        bookingManager.displayAllForm();
                        //System.out.println(bookingManager.averageJourney());
                        promptEnterKey();
                        break;
                    case SHOW_BID:

                        System.out.print("Enter Booking ID: ");
                        int bookingID = keyboard.nextInt();
                        Booking booking = bookingManager.findBookingById(bookingID);

                        if (booking == null) {
                            System.out.println("Booking not found");
                        } else {
                            bookingManager.displayForm(booking);

                        }
                        promptEnterKey();
                        break;
                    case SHOW_PID:


                        System.out.print("Enter Passenger ID: ");
                        int passengerID = keyboard.nextInt();
                        keyboard.nextLine();
                        ArrayList<Booking> bookingList = bookingManager.findBookingByPassengerID(passengerID);

                        if (!bookingList.isEmpty()) {

                            for (Booking b : bookingList) {
                                bookingManager.displayForm(b);
                            }

                        } else {
                            System.out.println("Booking not found");

                        }
                        promptEnterKey();

                        break;
                    case SHOW_PNAME:
                        System.out.print("Enter Passenger name:");
                        String name = keyboard.nextLine();

                       bookingManager.showBookingByPassengerName(name);
                        promptEnterKey();
                        break;

                    case SHOW_FUTURE:
                        bookingManager.displayFutureBookings();
                        promptEnterKey();
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException | NullPointerException e) {
                System.out.print("Invalid option - please enter number in range");
            }


        }

    }

    public void typeSelection() {


        System.out.println("\nFind Vehicle by Type");

        System.out.println("1. Van");
        System.out.println("2. Truck");
        System.out.println("3. Car");
        System.out.println("4. 4X4");
        System.out.print("\nEnter Type: ");

        Scanner keyboard = new Scanner(System.in);
        String usersInput = keyboard.nextLine();
        int option = Integer.parseInt(usersInput);
        final int VAN = 1;
        final int TRUCK = 2;
        final int CAR = 3;
        final int FOURX = 4;

        switch (option) {
            case VAN:
                ArrayList<Van> typeListVan = vehicleManager.findVechicleByType("Van");
                for (Vehicle b : typeListVan) {
                    System.out.println(b.toString());
                }
                break;
            case TRUCK:
                ArrayList<Van> typeListTruck = vehicleManager.findVechicleByType("Truck");
                for (Vehicle b : typeListTruck) {
                    System.out.println(b.toString());
                }
                break;
            case CAR:
                ArrayList<Car> typeListCar = vehicleManager.findVechicleByType("Car");
                for (Car v : typeListCar) {
                    System.out.println(v.toString());
                }
                break;
            case FOURX:
                ArrayList<Car> typeListFour = vehicleManager.findVechicleByType("4X4");
                for (Car v : typeListFour) {
                    System.out.println(v.toString());
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
            System.out.println("Wrong input please try again   " + err);
            return false;
        }
        return true;
    }

    public boolean makeBooking() {

        try {
            Scanner keyboard = new Scanner(System.in);

            Passenger passenger = null;

            while (true) {
                System.out.print("Enter Passenger Name:");
                String pName = keyboard.nextLine();
                if (passengerStore.findPassengerByName(pName) != null) {
                    passenger = passengerStore.findPassengerByName(pName);
                    System.out.println("Passenger found");
                    break;
                }
                System.out.println("Cannot find passenger in our system. Try again.");
            }


            int pID = passenger.getId();

//            if (passengerStore.findPassengerById(pID) == null) {
//                System.out.println("Can't find passenger with given ID");
//                return false;
//            }

            typeSelection();

            System.out.println("Input Vehicle ID:");
            int vID = keyboard.nextInt();

            if (vehicleManager.findVechicleById(vID) == null) {
                System.out.println("Can't find vehicle with given ID");
                return false;
            }

            Vehicle vehicle = vehicleManager.findVechicleById(vID);

            String date = "";
            LocalDateTime now = LocalDateTime.now();
            int year = 0, month = 0, day = 0, hour = 0, minute = 0;


            while (true) {
                System.out.println("enter booking year: ");
                year = keyboard.nextInt();
                if (year >= now.getYear()) {
                    break;
                }
                System.out.println("Enter a year that that is not in the past");
            }
//
//            do {
//                System.out.println("enter booking year: ");
//                 year = keyboard.nextInt();
//                 keyboard.nextLine();
//            }while (year < now.getYear());

            date += Integer.toString(year);
            date += "-";


            if (year == now.getYear())
                while (true) {
                    System.out.println("enter booking month:");
                    month = keyboard.nextInt();
                    if (month >= now.getMonthValue() && month <= 12) {
                        break;
                    }
                    System.out.println("enter month thats not in the past");
                }
//
//                do {
//                    System.out.println("enter booking month:");
//                    month = keyboard.nextInt();
//                }while (month< now.getMonthValue() || month>12);
            if (year > now.getYear()) {

                while (true) {
                    System.out.println("enter booking month:");
                    month = keyboard.nextInt();
                    if (month >= 1 && month <= 12) {
                        break;
                    }
                    System.out.println("enter month that's between 1-12 inclusive");
                }
//                do {
//                    System.out.println("enter booking month:");
//                    month = keyboard.nextInt();
//                }while (month<1 || month >12);

            }


            if (month < 10) {
                date += "0";
                date += Integer.toString(month);
                date += "-";
            } else {
                date += Integer.toString(month);
                date += "-";
            }


            while (true) {
                System.out.println("enter booking day: ");
                day = keyboard.nextInt();
                if (day >= 1 && day <= 31) {
                    break;
                }
                System.out.println("enter date 1-31");
            }

//            do {
//                System.out.println("enter booking day: ");
//                day= keyboard.nextInt();
//            }while (day<1 || day>31);

            if (day < 10) {
                date += "0";
                date += Integer.toString(day);
                date += " ";

            } else {
                date += Integer.toString(day);
                date += " ";
            }


            while (true) {
                System.out.println("enter booking hour");
                hour = keyboard.nextInt();
                if (hour >= 1 && hour <= 24) {
                    break;
                }
                System.out.println("enter houer between 1-24");
            }


            date += Integer.toString(hour);
            date += ":";


            while (true) {
                System.out.println("enter booking minute");
                minute = keyboard.nextInt();
                if (minute >= 1 && minute <= 59) {
                    break;
                }
                System.out.println("enter minute 1-59");
            }


            date += Integer.toString(minute);

            System.out.println(date);


            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            //LocalDateTime now = LocalDateTime.now();
            LocalDateTime dateTime = LocalDateTime.parse(date, dTF);


            if (bookingManager.checkAvailability(vID, dateTime)) {
                System.out.println("Sorry the vehicle you have chosen is already book for the date and time specified.");

            } else {
                LocationGPS startLocation = passenger.getLocation();

                System.out.println("Enter end latitude");
                double endLatitude = keyboard.nextDouble();

                System.out.println("Enter end longitude");
                double endLongitude = keyboard.nextDouble();


                LocationGPS endLocation = new LocationGPS(endLatitude, endLongitude);

                double distance=0;
                distance +=bookingManager.Distance(vehicle.getDepotGPSLocation(),startLocation);

                distance +=bookingManager.Distance(startLocation,endLocation);
                distance +=bookingManager.Distance(endLocation,vehicle.getDepotGPSLocation());

               Vehicle v = vehicleManager.findVechicleById(vID);
               String type = v.getType();
                double cost = bookingManager.calculateCosts(type,distance);



                bookingManager.addBooking(pID, vID, dateTime, startLocation, endLocation,cost);
                keyboard.nextLine();
            }


        } catch (InputMismatchException | NumberFormatException err) {

            System.out.println("Wrong input please try again   " + err);

        }
        return true;
    }


    public void editBookingMenu(){

        {


            Scanner keyboard = new Scanner(System.in);
            Booking booking;
            int bID=0;
            while (true){

                System.out.println("Enter Booking ID");
                bID = keyboard.nextInt();
                keyboard.nextLine();
                booking = bookingManager.findBookingById(bID);
                if (booking!=null){
                    break;
                }
                System.out.println("Booking doesnt exist");
            }


            final String MENU_ITEMS = "\n*** BOOKING Views ***\n"
                    + "1.Edit Passenger ID\n"
                    + "2.Display by Passenger ID\n"
                    + "3.Display by Passenger name\n"
                    + "4.Display by Booking ID\n"
                    + "5.Display all future bookings\n"
                    + "Enter Option [1,2,3,4,5]";

            final int EDIT_PID = 1;
            final int EDIT_VID = 2;
            final int EDIT_DATE = 3;
            final int EDIT_START = 4;
            final int EDIT_END = 5;
            final int EXIT = 6;


            int option = 0;

            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case EDIT_PID:


                        System.out.println("enter passenfer id:");
                        int pid= keyboard.nextInt();

                        booking.setPassengerId(pid);

                        promptEnterKey();
                        break;
                    case EDIT_VID:
                        typeSelection();

                        int vID=0;
                        while (true) {
                            System.out.println("Input Vehicle ID:");
                            vID = keyboard.nextInt();
                            if (vehicleManager.findVechicleById(vID) != null){
                                 break;
                            }
                            System.out.println("Can't find vehicle with given ID");

                        }
                        booking.setVehicleId(vID);
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException | NullPointerException e) {
                System.out.print("Invalid option - please enter number in range");
            }


        }


    }

    public void editPassengerMenu(){

    }

    public void promptEnterKey() {
        System.out.println("\nPress \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


    public void close() {

        System.out.println("Saving.....");
        vehicleManager.save();
        passengerStore.save();
        bookingManager.save();
        System.out.println("Exiting Program.....");
    }
}