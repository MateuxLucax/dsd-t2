package com.udesc.t2_dsd.controller;

import com.udesc.t2_dsd.model.Car;
import com.udesc.t2_dsd.model.Position;
import com.udesc.t2_dsd.infra.Database;
import com.udesc.t2_dsd.util.Constants;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarSpawner extends Thread {

    private final Random rng = new Random();
    private final int carCount;

    private final static Color[] carColors = {
        Color.red,
        Color.black,
        Color.blue,
        Color.orange.darker().darker(),
        Color.green.darker(),
        Color.pink.darker().darker(),
        Color.cyan.darker(),
    };
    private int currentColor = 0;
    
    public CarSpawner(int carCount) {
        this.carCount = carCount;
    }
    
    @Override
    public void run() {
        handleCars();
    }

    private Color nextColor() {
        var i = currentColor;
        currentColor = (currentColor + 1) % carColors.length;
        return carColors[i];
    }

    private synchronized void handleCars() {
        Database db = Database.getInstance();
        
        while (true) {            
            try {
                Thread.sleep(Constants.carSpawnIntervalMs);
                System.out.println("Executando runner: " + System.currentTimeMillis() + " - " + db.getCars().size());
                
                List<Position> entryPoints = db.getWorld().getEntryPoints();
                for (Position position : entryPoints) {
                    if (db.getCars().size() >= carCount)
                        break;

                    Map<Position, Car> cars = db.getCars();

                    while (cars.get(position) != null);
                    int speed = rng.nextInt(Constants.minCarIntervalMs, 1 + Constants.maxCarIntervalMs);
                    Car car = new Car((Position) position.clone(), speed, nextColor());
                    cars.put(position, car);
                    car.start();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
