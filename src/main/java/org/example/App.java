package org.example;


import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * Nathan Pulkis
 *
 * This Vehicle Bookings Management Systems manages the booking of Vehicles
 * by Passengers.
 *
 * This program reads from 3 text files:
 * "vehicles.txt", "passengers.txt", and "next-id-store.txt"
 * You should be able to see them in the project pane.
 * You will create "bookings.txt" at a later stage, to store booking records.
 *
 * "next-id-store.txt" contains one number ("201"), which will be the
 * next auto-generated id to be used to when new vehicles, passengers, or
 * bookings are created.  The value in the file will be updated when new objects
 * are created - but not when objects are recreated from records in
 * the files - as they already have IDs.  Dont change it - it will be updated by
 * the IdGenerator class.
 */

public class App
{
    public static void main(String[] args)
    {
        System.out.println("\nWelcome to the VEHICLE BOOKINGS MANAGEMENT SYSTEM - CA1 for OOP\n");




        // create PassengerStore and load it with passenger records from text file
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
//        System.out.println("List of all passengers:");
//        passengerStore.displayAllPassengers();

        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager();
//        System.out.println("List of all Vehicles:");
//        vehicleManager.displayAllVehicles();


        //Test findVehicleByRegNum
//       if (vehicleManager.findVehicleByRegNum("151D987105")==null){
//           System.out.println("Vehicle Not Found");
//       }else{
//           System.out.println(vehicleManager.findVehicleByRegNum("151D987105"));
//       }
//        System.out.println("Program exiting... Goodbye");

       //Test addPassenger

//        passengerStore.addPassenger("Test","test@test.com","123456789",0.34,1.2);
//        passengerStore.displayAllPassengers();
//        passengerStore.addPassenger("Test","test@test.com","123456789",0.34,1.2);
//        passengerStore.displayAllPassengers();

        Vehicle test = vehicleManager.findVechicleById(105);
        LocationGPS start = test.getDepotGPSLocation();

        Passenger test2= passengerStore.findPassengerById(101);
        LocationGPS end = test2.getLocation();


        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        //BookingManager bookingManager = new BookingManager();

        LocalDateTime now = LocalDateTime.now();
        String dateString= now.format(dTF);
        LocalDateTime now1 = LocalDateTime.parse(dateString,dTF);



        Scanner keyboard = new Scanner(System.in);


        vehicleManager.displayAllVechicleIds();
        passengerStore.displayAllPassengerIds();


        System.out.println("Input Passenger ID:");

        int pID = keyboard.nextInt();


        System.out.println("Input Vehicle ID:");
        int vID = keyboard.nextInt();



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


       // bookingManager.addBooking(pID, vID, startLocation, endLocation);











    }
}
