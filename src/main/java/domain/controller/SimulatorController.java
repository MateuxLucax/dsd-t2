package domain.controller;

import domain.model.Car;
import domain.model.Position;
import data.datasource.Database;
import domain.model.Status;
import java.util.Map;
import presentation.view.UpdatableSimulatorView;

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
            spawner.interrupt();
        }
    };
    
    private void stopCars() {
        for (Map.Entry<Position, Car> entry : db.getCars().entrySet()) {
            Car car = entry.getValue();
            car.interrupt();
        }
        
        db.clearCars();
    }

}
