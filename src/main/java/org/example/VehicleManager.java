package org.example;

import java.io.*;
import java.util.ArrayList;

public class VehicleManager {
    private final ArrayList<Vehicle> vehicleList;  // for Car and Van objects

    public VehicleManager(String fileName) {
        this.vehicleList = new ArrayList<>();
        loadVehicleDataFromFile(fileName);
    }

    public void displayAllVehicles() {
        for (Vehicle v : vehicleList)
            System.out.println(v.toString());
    }

    public void displayAllVehicleIds(){
        System.out.println("Vehicle ids");
        for (Vehicle v : vehicleList)
            System.out.print(v.getId()+",");
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

    public Vehicle findVehicleById(int id){
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

    public ArrayList findVehicleByType(String type){
        ArrayList<Van>  vanList = new ArrayList<>();
        ArrayList<Car> carList = new ArrayList<>();


        for (Vehicle v: vehicleList){
            if (v.getType().equalsIgnoreCase(type)){
                if (type.equalsIgnoreCase("car")||type.equalsIgnoreCase("4x4")){
                    carList.add((Car) v);
                }
                else {
                    vanList.add((Van) v);
                }
            }
        }
        if (type.equalsIgnoreCase("car")||type.equalsIgnoreCase("4x4")){
            carList.sort(new VehicleRegistrationComparator());
            return carList;
        }else{
            vanList.sort(new VehicleRegistrationComparator());
            return vanList;
        }

    }


    public ArrayList<Car> filterSeats(ArrayList<Car> carArrayList,int seats){
        ArrayList<Car> seatsList = new ArrayList<>();

        for (Car c:carArrayList){
            if (c.getSeats()==seats){
                seatsList.add((Car) c);
            }
        }
        seatsList.sort( new VehicleRegistrationComparator());
        return seatsList;
    }


    public void loadVehicleDataFromFile(String fileName){

        try{
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            Object o;

            while (true){
                try {
                    o = ois.readObject();

                    if (o instanceof Vehicle){
                        vehicleList.add((Vehicle) o);
                    }else {
                        System.err.println("Unexpected object in file");
                    }
                }catch (IOException ex){
                    break;
                }
            }

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream f = new FileOutputStream("vehicles.ser");
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (Vehicle v: vehicleList){
                o.writeObject(v);
            }
            o.close();
            f.close();

        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
