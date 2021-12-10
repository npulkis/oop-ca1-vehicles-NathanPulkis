package org.example;

import java.util.Comparator;

public class seatsComparator implements Comparator<Car> {

    public int compare(Car car1, Car car2) {
        return car2.getSeats() - car1.getSeats();
    }
}
