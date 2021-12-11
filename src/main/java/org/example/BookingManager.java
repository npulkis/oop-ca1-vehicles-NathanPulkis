package org.example;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//
public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private final PassengerStore passengerStore;
    private final VehicleManager vehicleManager;

    // Constructor
    public BookingManager(String filename,PassengerStore pS,VehicleManager vM) {
        this.bookingList = new ArrayList<>();
        loadBookingdataFromFile(filename);
        passengerStore = pS;
        vehicleManager = vM;
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



    public void addBooking(int pID, int vID,LocalDateTime dateTime, LocationGPS startLocation,LocationGPS endLocation)
    {


        double cost = 10;

        Booking booking = new Booking( pID, vID, dateTime, startLocation, endLocation, cost);


        boolean found = false;

        for(Booking b: bookingList)
        {
            if(booking.getPassengerId()==b.getPassengerId() && booking.getBookingDateTime().isEqual(b.getBookingDateTime()))
            {
                found = true;
                System.out.println("Booking already exists");
            }
        }
        if(!found) {
            bookingList.add(booking);
        }
    }

    public void displayForm(Booking b){

            System.out.println("-----------------------------------------------------");
            System.out.println("Booking ID: "+b.getBookingId());
            System.out.println("Passenger ID: "+b.getPassengerId());
            System.out.println("Vehicle ID : "+b.getVehicleId());
            System.out.println("Booking Date and Time: "+b.getBookingDateTime());
            System.out.println("Start Location: "+b.getStartLocation());
            System.out.println("End Location: "+b.getEndLocation());
            System.out.println("Total Cost: "+b.getCost());
            System.out.println("-----------------------------------------------------");

    }

    public void displayAllForm(){
       ArrayList<Booking> booking = bookingList;
       for (Booking b:booking){
           displayForm(b);
       }
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


    public void removeBooking(Booking b){

        System.out.println("Booking "+b.getBookingId()+" removed");
        bookingList.remove(b);
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

    public ArrayList findBookingByVehicleID(int vehicleId)
    {
        ArrayList list = new ArrayList();
        for(Booking b: bookingList) {
            if (b.getVehicleId()==vehicleId) {
                list.add(b);
            }
        }

        return list;
    }

    public ArrayList findBookingByPassengerID(int passengerID)
    {
        ArrayList list = new ArrayList();
        for(Booking b: bookingList) {
            if (b.getPassengerId()==passengerID) {
                list.add(b);
            }
        }

        return list;
    }

    public void showBookingByPassengerName(String name){

            ArrayList<Booking> nameList=filterByNameBookings(name);
            nameList.sort(new BookingDateComparator());
            for (Booking b:nameList){
                displayForm(b);
            }

        }


    public void displayAllBookings()
    {
        System.out.println("These are the current bookings\n");
        for(Booking b: bookingList) {
            System.out.println(b.toString());
        }
    }

    public void displayFutureBookings(){
        System.out.println("These are all the future bookings");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Booking> futureBookings = new ArrayList<>();
        for (Booking b: bookingList){
            if (b.getBookingDateTime().isAfter(now)){
                futureBookings.add(b);
            }
        }
        if (bookingList.isEmpty()){
            System.out.println("There are currently now booking in the future");
        }else {
            futureBookings.sort(new BookingDateComparator());
            for (Booking b : futureBookings){
             displayForm(b);
            }
        }


    }

    public ArrayList<Booking> filterByNameBookings(String name){
        Passenger passenger = passengerStore.findPassengerByName(name);
        ArrayList<Booking> nameFilter= new ArrayList<>();
        if (passenger==null){
            System.out.println("No passenger with that name found");
            return null;
        }

        for (Booking b :bookingList){
            if (b.getPassengerId()== passenger.getId()){
                nameFilter.add(b);
            }
        }
        if (nameFilter.isEmpty()){
            System.out.println("no booking with that passenger found");
            return null;
        }
   return nameFilter; }


    private void loadBookingdataFromFile(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                int passengerID = sc.nextInt();
                int vehicleID = sc.nextInt();
                int year = sc.nextInt();
                int month = sc.nextInt();
                int day = sc.nextInt();
                int hour = sc.nextInt();
                int minute = sc.nextInt();
                double startLatitude = sc.nextDouble();
                double startLongitude = sc.nextDouble();
                double endLatitude= sc.nextDouble();
                double endLongitude = sc.nextDouble();
                double cost = sc.nextDouble();

               String date = year +"-"+ month +"-"+ day +" "+ hour +":"+ minute;
                DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
                LocalDateTime dateTime = LocalDateTime.parse(date, dTF);

                LocationGPS startLocation = new LocationGPS(startLatitude,startLongitude);
                LocationGPS endLocation = new LocationGPS(endLatitude,endLongitude);


               bookingList.add(new Booking(id, passengerID,vehicleID,dateTime,startLocation,endLocation,cost));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }



    public void save() {
        File file = new File("bookings.OUT");
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(file);

            for (Booking b : bookingList) {
                int bookingID = b.getBookingId();
                int passengerID = b.getPassengerId();
                int vehicleID = b.getVehicleId();
                int year = b.getBookingDateTime().getYear();
                int month = b.getBookingDateTime().getMonthValue();
                int day = b.getBookingDateTime().getDayOfMonth();
                int hour = b.getBookingDateTime().getHour();
                int minute = b.getBookingDateTime().getMinute();
                double startLatitude = b.getStartLocation().getLatitude();
                double startLongitude = b.getStartLocation().getLatitude();
                double endLatitude=b.getEndLocation().getLatitude();
                double endLongitde=b.getEndLocation().getLongitude();
                double cost= b.getCost();


                String info = bookingID+","+passengerID+","+vehicleID+","+year+","+month+","+day+","+hour+","+minute+
                        ","+startLatitude+","+startLongitude+","+endLatitude+","+endLongitde+","+cost;
                fWriter.write(info+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                assert fWriter != null;
                fWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkAvailability(int vehicleID, LocalDateTime bookingTime){

        ArrayList<Booking> list = findBookingByVehicleID(vehicleID);

        if(!list.isEmpty()){
            for (Booking b : list){
                if (b.getBookingDateTime().isEqual(bookingTime)){
                    return true;

                }
            }
        }

    return false;}




}