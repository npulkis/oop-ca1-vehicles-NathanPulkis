package org.example;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager() {
        this.bookingList = new ArrayList<>();
    }

    public List<Booking> getAllBookings() {
        return this.bookingList;
    }


    //TODO implement functionality as per specification
    public void displayAllBooking(){
        for (Booking b: bookingList){
            System.out.println(b.toString());
        }
    }



    public void addBooking(int passengerId, int vehicleId,LocalDateTime dateTime, LocationGPS startLocation,LocationGPS endLocation){
        boolean found =false;
        Booking booking = new Booking(passengerId,vehicleId,dateTime,startLocation,endLocation);
      //  for (Booking b : bookingList){
//            if (b.equals(booking)){
//                System.out.println("Booking already exists");
//                found=true;
//                break;
//            }
            if (!found){
                bookingList.add(booking);
                System.out.println("\n Booking added");
            }
        //}
    }

}
