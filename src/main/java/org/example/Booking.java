package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

class Booking
{
    private int bookingId;
    private int passengerId;
    private int vehicleId;
    private LocalDateTime bookingDateTime;
    private LocationGPS startLocation;
    private LocationGPS endLocation;

    private double cost;  //Calculated at booking time

    private IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");  // get access to the id Generator


    //TODO - see specification

    public Booking( int passengerId, int vehicleId, LocalDateTime bookingDateTime, LocationGPS startLocation, LocationGPS endLocation) {

        // some minimal validation
//        if ()
//            throw new IllegalArgumentException("null arguments encountered");

        this.bookingId = idGenerator.getNextId();
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = bookingDateTime;
        this.startLocation= startLocation;
        this.endLocation=endLocation;
    }



    // Version of constructor called when the passenger id is known,
    // as it was read from the "passengers.txt" file.
    //
//    public Booking( int bookingId,int passengerId, int vehicleId,
//                    LocalDateTime bookingDateTime, LocationGPS startLocation, LocationGPS endLocation) {
//
//        //some minimal validation
//        if ()
//            throw new IllegalArgumentException("null arguments encountered");
//
//        this.bookingId = bookingId;
//        this.passengerId = passengerId;
//        this.vehicleId = vehicleId;
//        this.bookingDateTime = bookingDateTime;
//        this.startLocation= startLocation;
//        this.endLocation=endLocation;
//    }

    public int getBookingId(){return bookingId;}
    public void setId(){};
    public int getPassengerId(){return passengerId;}
    public int getVehicleId(){return vehicleId;}
    public LocalDateTime getBookingDateTime(){return bookingDateTime;}
    public LocationGPS getStartLocation(){return startLocation;}
    public LocationGPS getEndLocation(){return endLocation;}
    public void setPassengerId(int passengerId){this.passengerId =passengerId;}
    public void setVehicleId(int vehicleId){this.vehicleId=vehicleId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId == booking.bookingId && passengerId == booking.passengerId && vehicleId == booking.vehicleId && Double.compare(booking.cost, cost) == 0 && bookingDateTime.equals(booking.bookingDateTime) && startLocation.equals(booking.startLocation) && endLocation.equals(booking.endLocation) && Objects.equals(idGenerator, booking.idGenerator);
    }


    @Override
    public String toString(){
        return this.getClass().getSimpleName()+ "{"
                + "bookingId=" + bookingId +", passengerId =" + passengerId
                + ", vehicleId=" + vehicleId + "booking Date=" +bookingDateTime
                + ", startLocation=" +startLocation + ", endLocation=" + endLocation +"}";
    }


}
