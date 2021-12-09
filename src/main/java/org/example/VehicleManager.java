package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VehicleManager {
    private static final String FILE_NAME = "vehicles.out";
    private final ArrayList<Vehicle> vehicleList;  // for Car and Van objects

    public VehicleManager(String fileName) {
        this.vehicleList = new ArrayList<>();
        loadVehiclesFromFile(fileName);
    }

    public void displayAllVehicles() {
        for (Vehicle v : vehicleList)
            System.out.println(v.toString());
    }

    public void displayAllVechicleIds(){
        System.out.println("Vehicle ids");
        for (Vehicle v : vehicleList)
            System.out.print(v.getId()+",");
    }

    public void loadVehiclesFromFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String type = sc.next();  // vehicle type
                String make = sc.next();
                String model = sc.next();
                double milesPerKwH = sc.nextDouble();
                String registration = sc.next();
                double costPerMile = sc.nextDouble();
                int year = sc.nextInt();   // last service date
                int month = sc.nextInt();
                int day = sc.nextInt();
                int mileage = sc.nextInt();
                double latitude = sc.nextDouble();  // Depot GPS location
                double longitude = sc.nextDouble();



                if (type.equalsIgnoreCase("Van") ||
                        type.equalsIgnoreCase("Truck")) {

                    double loadSpace = sc.nextDouble();
                    // construct a Van object and add it to the passenger list
                    vehicleList.add(new Van(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            loadSpace));

                }
                if(type.equalsIgnoreCase("Car") ||
                    type.equalsIgnoreCase("4x4")){

                    int noOfSeats = sc.nextInt();
                    vehicleList.add(new Car(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            noOfSeats));
                }
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    //TODO add more functionality as per spec.

    public Vehicle findVehicleByReg(String regNum) {

        for (Vehicle v : vehicleList) {
            if (v.getRegistration().equalsIgnoreCase(regNum)) {
               return v;
            }
        }
        return null;
    }

    public Vehicle findVechicleById(int id){
        for (Vehicle v: vehicleList){
            if (v.getId()== id){
                return v;
            }
        }
    return null;}

    public ArrayList<Vehicle> findAllVehicles(){

        vehicleList.sort(new VehicleRegistrationComparator());

        return vehicleList;
    }

    public ArrayList<Vehicle> findVechicleByType(String type){
        ArrayList<Vehicle> typeList = new ArrayList<>();

        for (Vehicle v: vehicleList){
            if (v.getType().equalsIgnoreCase(type)){
                typeList.add(v);
            }
        }
        typeList.sort(new VehicleRegistrationComparator());
        return typeList;
    }

    public void save() {
        File file = new File("vehicles.OUT");
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(file);

            System.out.println("saving");
            for (Vehicle v : this.vehicleList) {
                int id = v.getId();
                String type = v.getType();  // vehicle type
                String make = v.getMake();
                String model = v.getModel();
                double milesPerKwH = v.getMilesPerKm();
                String registration = v.getRegistration();
                double costPerMile = v.getCostPerMile();
                int year = v.getLastServicedDate().getYear();   // last service date
                int month = v.getLastServicedDate().getMonthValue();
                int day = v.getLastServicedDate().getDayOfMonth();
                int mileage = v.getMileage();
                double latitude = v.getDepotGPSLocation().getLatitude();  // Depot GPS location
                double longitude = v.getDepotGPSLocation().getLongitude();

                String info = id+","+type+","+make+","+model+","+milesPerKwH+","+registration+","+costPerMile+","+year+","+month+","+day+","+mileage+","+latitude+","+longitude+",";
                if (v instanceof Van)
                    info += ((Van) v).getLoadSpace();
                else
                    info += ((Car) v).getSeats();
                fWriter.write(info+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
