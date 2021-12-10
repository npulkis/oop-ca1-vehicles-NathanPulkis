package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
//
public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager() {
        this.bookingList = new ArrayList<>();
    }

    //TODO implement functionality as per specification

    // add a booking, find booking
    /*public boolean addBooking(int bookingId, int passengerId, int vehicleId, double LatitStart, double LatitEnd,
                              double LongitStart, double LongitEnd, double cost, int year, int month,
                              int day, int hour, int minute, int second)
    {
        LocationGPS startLocation = new LocationGPS(LatitStart, LongitStart);
        LocationGPS endLocation = new LocationGPS(LatitEnd, LongitEnd);
        LocalDateTime bookingDateTime = LocalDateTime.of(year, month, day, hour, minute, second);


        Booking booking = new Booking(bookingId, passengerId, vehicleId, bookingDateTime, startLocation, endLocation, cost);

        boolean found = false;
        // loop through here and check that email and password of passengers dont match
        for(Booking b: bookingList)
        {
            if(booking.equals(b))
            {
                found = true;
                return found;
            }
        }
        bookingList.add(booking);
        return found;
    }*/



    public boolean addBooking(int pID, int vID,LocalDateTime dateTime, LocationGPS startLocation,LocationGPS endLocation)
    {







//

        double cost = 10;

        Booking booking = new Booking( pID, vID, dateTime, startLocation, endLocation, cost);


        boolean found = false;
        // loop through here and check that email and password of passengers dont match
        for(Booking b: bookingList)
        {
            if(booking.equals(b))
            {
                found = true;
                return found;
            }
        }
        bookingList.add(booking);
        return found;
    }





    public double calculateCosts(String type, double distance)
    {
        double total = 0;
        double rawTotal = 0;

        if (type.equalsIgnoreCase("Car"))
        {
            rawTotal = distance * 2.00;
            return rawTotal;
        }
        else if (type.equalsIgnoreCase("4x4"))
        {
            rawTotal = distance * 4.00;
            return rawTotal;
        }
        else if (type.equalsIgnoreCase("van"))
        {
            rawTotal = distance * 6.00;
            return rawTotal;
        }
        else if (type.equalsIgnoreCase("truck"))
        {
            rawTotal = distance * 10.00;
            return rawTotal;
        }
        return -1;
    }







    public Booking findBookingById(int bookingId)
    {
        for(Booking b: bookingList) {
            if (b.getBookingId() == bookingId) {
                return b;
            }
        }
        return null;
    }


    public void displayAllBookings()
    {
        System.out.println("These are the current bookings\n");
        for(Booking b: bookingList) {
            System.out.println(b.toString());
        }
    }

//    public int assignBookingId()
//    {
//        int id = 0;
//        int largest = 0;
//        int new_load = 0;
//        for (Booking b : bookingList) {
//            new_load = b.getBookingId();
//            if(new_load > largest)
//            {
//                largest = new_load;
//            }
//        }
//        id = largest + 1;
//        return id;
//    }



}