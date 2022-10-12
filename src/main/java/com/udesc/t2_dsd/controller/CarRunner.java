package com.udesc.t2_dsd.controller;

import com.udesc.t2_dsd.model.Car;
import com.udesc.t2_dsd.model.Cell;
import com.udesc.t2_dsd.model.Position;
import com.udesc.t2_dsd.infra.Database;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRunner extends Thread {
    private int carCount;
    
    public CarRunner(int carCount) {
        this.carCount = carCount;
    }
    
    @Override
    public void run() {
        handleCars();
    }

    private synchronized void handleCars() {
        Database db = Database.getInstance();
        
        while (true) {            
            try {
                Thread.sleep(1000);
                System.out.println("Executando runner: " + System.currentTimeMillis() + " - " + db.getCars().size());
                
                List<Position> entryPoints = db.getWorld().getEntryPoints();
                for (Position position : entryPoints) {
                    if (db.getCars().size() >= carCount)
                        break;
                    
                    Car currentCar = db.getCars().get(position);
                    if (currentCar == null) {
                        Car car = new Car((Position) position.clone(), 1000);
                        db.getCars().put(position, car);
                        car.start();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
