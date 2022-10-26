package domain.controller;

import data.datasource.Database;
import domain.model.Car;
import domain.model.Position;
import domain.util.Constants;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarSpawner extends Thread {
    private final Random rng = new Random();
    private final int carCount;
    private final long interval;

    private final static Color[] carColors = {
        Color.red,
        Color.black,
        Color.blue,
        Color.orange.darker().darker(),
        Color.green.darker(),
        Color.cyan.darker(),
    };
    private int currentColor = 0;

    private int currentId = 0;
    
    public CarSpawner(int carCount, int interval) {
        this.carCount = carCount;
        this.interval = interval * 1000L;
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

    private int nextId() {
        return currentId++;
    }

    private synchronized void handleCars() {
        Database db = Database.getInstance();

        try {
            while (!isInterrupted()) {
                Thread.sleep(Constants.carSpawnIntervalMs);
                List<Position> entryPoints = db.getWorld().getEntryPoints();
                for (Position position : entryPoints) {
                    while (db.getCars().size() >= carCount) {    
                        Thread.sleep(Constants.carSpawnIntervalMs);
                    }

                    Map<Position, Car> cars = db.getCars();
                    Car currentCar = cars.get(position);
                    while (currentCar != null) {                        
                        currentCar = cars.get(position);
                    }
                    
                    //db.getWorld().getSemaphore(position).acquire();
                    int speed = rng.nextInt(Constants.minCarIntervalMs, 1 + Constants.maxCarIntervalMs);
                    Car car = new Car((Position) position.clone(), nextId(), speed, nextColor());
                    cars.put(position, car);
                    
                    car.start();
                    if (interval > 0)
                        Thread.sleep(interval);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted car spawner (ok)");
            currentId = 0;
        }
    }
}
