package domain.controller;

import data.datasource.Database;
import domain.model.Car;
import domain.model.Position;
import domain.model.enums.Status;
import presentation.view.UpdatableSimulatorView;

import java.util.Map;

public class SimulatorController {
    private final Database db;
    private CarSpawner spawner;
    private final RefreshView refresh;

    public SimulatorController(UpdatableSimulatorView view) {
        this.db = Database.getInstance();
        
        refresh = new RefreshView(view);
        refresh.start();
    }
    
    public void handleStart(int carCount, int interval) {
        db.setStatus(Status.RUNNING);
        handleStopSpawner();
        stopCars();
        
        
        db.getWorld().loadLocks();
        spawner = new CarSpawner(carCount, interval);
        spawner.start();
    };
    
    public void handleStop() {
        db.setStatus(Status.STOPPED);
        handleStopSpawner();
        stopCars();
    }
    
    public void handleStopAndWait() {
        handleStopSpawner();
    }
    
    private void handleStopSpawner() {
        if (spawner != null) {
            spawner.stop();
        }
    };
    
    private void stopCars() {
        for (Map.Entry<Position, Car> entry : db.getCars().entrySet()) {
            Car car = entry.getValue();
            car.stop();
        }
        db.clearCars();
    }
}
