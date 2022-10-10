package com.udesc.t2_dsd.controller;

import com.udesc.t2_dsd.model.Car;
import com.udesc.t2_dsd.model.Position;
import com.udesc.t2_dsd.view.SimulatorView;
import com.udesc.t2_dsd.infra.Database;
import java.util.Map;

public class SimulatorController {
    private SimulatorView view;
    private Database db;
    private CarRunner runner;

    public SimulatorController(SimulatorView view) {
        this.view = view;
        this.db = Database.getInstance();
    }
    
    public void handleStart() {
        if (runner != null)
            this.runner.stop();
        
        runner = new CarRunner(view.getCarCount());
        runner.start();
    };
    
    public void handleStop() {
        runner.stop();
        for (Map.Entry<Position, Car> entry : db.getCars().entrySet()) {
            Car car = entry.getValue();
            try {
                car.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.getCars().clear();
    };
}
