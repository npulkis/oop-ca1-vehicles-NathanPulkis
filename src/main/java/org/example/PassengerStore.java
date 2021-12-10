package org.example;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    public List<Passenger> getAllPassengers() {
        return this.passengerList;
    }

    public void displayAllPassengers() {
        for (Passenger p : this.passengerList) {
            System.out.println(p);
        }
    }

    public void displayAllPassengerIds(){
        System.out.println("\nPassenger Ids");
        for (Passenger p: this.passengerList){
            System.out.print(p.getId()+",");
        }
    }

    /**
     * Read Passenger records from a text file and create and add Passenger
     * objects to the PassengerStore.
     */
    private void loadPassengerDataFromFile(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String name = sc.next();
                String email = sc.next();
                String phone = sc.next();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();

                // construct a Passenger object and add it to the passenger list
                passengerList.add(new Passenger(id, name, email, phone, latitude, longitude));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    // TODO - see functional spec for details of code to add

    public void addPassenger(String name, String email, String phone, double latitude, double longitude) {

        boolean found = false;
        Passenger passenger = new Passenger(name, email, phone, latitude, longitude);
        for (Passenger p : passengerList) {
            if (p.equals(passenger)) {
                System.out.println("Passenger already exists");
                found = true;
                break;
            }
        }
        if (!found) {
            passengerList.add(passenger);
            System.out.println("\nPassenger Added");
        }
    }

    public Passenger findPassengerByName(String name) {

        for(Passenger p : passengerList){

            if( p.getName().toLowerCase().contains(name.toLowerCase())){
                return p;
            }
        }

  return null;  }

public Passenger findPassengerById(int id){
        for (Passenger p : passengerList){
            if (p.getId()==id){
                return p;
            }
        }
return null;}

    public void removePassenger(Passenger p){

        System.out.println("Passnger "+p.getId()+" removed");
        passengerList.remove(p);
    }

    public void save() {
        File file = new File("passengers.OUT");
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(file);

            System.out.println("saving");
            for (Passenger p : this.passengerList) {
                int id = p.getId();
                String name = p.getName();
                String email = p.getEmail();
                String phone = p.getPhone();
                double latitude = p.getLocation().getLatitude();  // Depot GPS location
                double longitude = p.getLocation().getLongitude();

                String info = id+","+name+","+email+","+phone+","+latitude+","+longitude;
//                if (v instanceof Van)
//                    info += ((Van) v).getLoadSpace();
//                else
//                    info += ((Car) v).getSeats();
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


} // end class
