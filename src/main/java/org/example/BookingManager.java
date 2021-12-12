package org.example;


import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//
public class BookingManager {
    private final ArrayList<Booking> bookingList;
    private final PassengerStore passengerStore;
    private final VehicleManager vehicleManager;

    // Constructor
    public BookingManager(String filename, PassengerStore pS, VehicleManager vM) {
        this.bookingList = new ArrayList<>();
        //loadBookingdataFromFile(filename);
        loadBookingDataFromFile(filename);
        passengerStore = pS;
        vehicleManager = vM;
    }

    //TODO implement functionality as per specification

    public void addBooking(int pID, int vID, LocalDateTime dateTime, LocationGPS startLocation, LocationGPS endLocation, double cost) {

        DecimalFormat df = new DecimalFormat("0.00");
        cost = Double.parseDouble(df.format(cost));


        Booking booking = new Booking(pID, vID, dateTime, startLocation, endLocation, cost);


        boolean found = false;

        for (Booking b : bookingList) {
            if (booking.getPassengerId() == b.getPassengerId() && booking.getBookingDateTime().isEqual(b.getBookingDateTime())) {
                found = true;
                System.out.println("Booking already exists");
            }
        }
        if (!found) {
            bookingList.add(booking);
        }
    }

    public void displayForm(Booking b) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Booking ID: " + b.getBookingId());
        System.out.println("Passenger ID: " + b.getPassengerId());
        System.out.println("Vehicle ID : " + b.getVehicleId());
        System.out.println("Booking Date and Time: " + b.getBookingDateTime());
        System.out.println("Start Location: " + b.getStartLocation());
        System.out.println("End Location: " + b.getEndLocation());
        System.out.println("Total Cost: " + b.getCost());
        System.out.println("-----------------------------------------------------");

    }

    public void displayAllForm() {
        ArrayList<Booking> booking = bookingList;
        for (Booking b : booking) {
            displayForm(b);
        }
    }


    public void removeBooking(Booking b) {

        System.out.println("Booking " + b.getBookingId() + " removed");
        bookingList.remove(b);
    }


    public Booking findBookingById(int bookingId) {

        for (Booking b : bookingList) {
            if (b.getBookingId() == bookingId) {
                return b;
            }
        }
        return null;
    }

    public ArrayList findBookingByVehicleID(int vehicleId) {
        ArrayList list = new ArrayList();
        for (Booking b : bookingList) {
            if (b.getVehicleId() == vehicleId) {
                list.add(b);
            }
        }

        return list;
    }

    public ArrayList findBookingByPassengerID(int passengerID) {
        ArrayList list = new ArrayList();
        for (Booking b : bookingList) {
            if (b.getPassengerId() == passengerID) {
                list.add(b);
            }
        }

        return list;
    }

    public void showBookingByPassengerName(String name) {

        ArrayList<Booking> nameList = filterByNameBookings(name);
        nameList.sort(new BookingDateComparator());
        for (Booking b : nameList) {
            displayForm(b);
        }

    }


    public void displayAllBookings() {
        System.out.println("These are the current bookings\n");
        for (Booking b : bookingList) {
            System.out.println(b.toString());
        }
    }

    public void displayFutureBookings() {
        System.out.println("These are all the future bookings");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Booking> futureBookings = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.getBookingDateTime().isAfter(now)) {
                futureBookings.add(b);
            }
        }
        if (bookingList.isEmpty()) {
            System.out.println("There are currently now booking in the future");
        } else {
            futureBookings.sort(new BookingDateComparator());
            for (Booking b : futureBookings) {
                displayForm(b);
            }
        }


    }

    public ArrayList<Booking> filterByNameBookings(String name) {
        Passenger passenger = passengerStore.findPassengerByName(name);
        ArrayList<Booking> nameFilter = new ArrayList<>();
        if (passenger == null) {
            System.out.println("No passenger with that name found");
            return null;
        }

        for (Booking b : bookingList) {
            if (b.getPassengerId() == passenger.getId()) {
                nameFilter.add(b);
            }
        }
        if (nameFilter.isEmpty()) {
            System.out.println("no booking with that passenger found");
            return null;
        }
        return nameFilter;
    }


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
                double endLatitude = sc.nextDouble();
                double endLongitude = sc.nextDouble();
                double cost = sc.nextDouble();

                String date = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
                LocalDateTime dateTime = LocalDateTime.parse(date, dTF);

                LocationGPS startLocation = new LocationGPS(startLatitude, startLongitude);
                LocationGPS endLocation = new LocationGPS(endLatitude, endLongitude);


                bookingList.add(new Booking(id, passengerID, vehicleID, dateTime, startLocation, endLocation, cost));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }


    public boolean checkAvailability(int vehicleID, LocalDateTime bookingTime) {

        ArrayList<Booking> list = findBookingByVehicleID(vehicleID);

        if (!list.isEmpty()) {
            for (Booking b : list) {
                if (b.getBookingDateTime().isEqual(bookingTime)) {
                    return true;

                }
            }
        }

        return false;
    }

    public double deg2rad(double angle) {
        return angle * Math.PI / 100;
    }

    public double Distance(LocationGPS start, LocationGPS end) {

        double startLat = Math.toRadians(start.getLatitude());
        double startLon = Math.toRadians(start.getLongitude());
        double endLat = Math.toRadians(end.getLatitude());
        double endLon = Math.toRadians(end.getLongitude());

        double dlong = endLon - startLon;
        double dlat = endLat - startLat;

        double ans = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dlong / 2), 2);
        ans = 2 * Math.asin(Math.sqrt(ans));

        double R = 3956;

        ans = ans * R;

        return ans;

    }

    public double calculateCosts(String type, double distance) {
        double total;


        if (type.equalsIgnoreCase("Car")) {
            total = distance * 2.00;
            return total;
        } else if (type.equalsIgnoreCase("4x4")) {
            total = distance * 4.00;
            return total;
        } else if (type.equalsIgnoreCase("van")) {
            total = distance * 6.00;
            return total;
        } else if (type.equalsIgnoreCase("truck")) {
            total = distance * 10.00;
            return total;
        }
        return -1;
    }

    public double averageJourney() {


        int count = 0;
        double distance = 0;
        for (Booking b : bookingList) {

            Vehicle vehicle = vehicleManager.findVehicleById(b.getVehicleId());


            distance += Distance(vehicle.getDepotGPSLocation(), b.getStartLocation());
            distance += Distance(b.getStartLocation(), b.getEndLocation());
            distance += Distance(b.getEndLocation(), vehicle.getDepotGPSLocation());

            count += 1;

        }


        return (distance / count);
    }


    public boolean checkBookingTime(int bID, int pID) {

        Booking booking = findBookingById(bID);
        LocalDateTime date = booking.getBookingDateTime();

        for (Booking b : bookingList) {
            if (b.getPassengerId() == pID && b.getBookingId() != bID) {

                if (b.getBookingDateTime().equals(date)) {
                    return false;
                }

            }
        }
        return true;
    }

    public void loadBookingDataFromFile(String filename) {

        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);

            Object o;

            while (true) {
                try {
                    o = ois.readObject();

                    if (o instanceof Booking) {
                        bookingList.add((Booking) o);
                    } else {
                        System.err.println("Unexpected object in file");
                    }
                } catch (IOException ex) {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream f = new FileOutputStream("bookings.ser");
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (Booking b : bookingList) {
                o.writeObject(b);
            }
            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}