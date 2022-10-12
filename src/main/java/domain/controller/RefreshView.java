package domain.controller;

import data.datasource.Database;
import domain.model.Status;
import domain.util.Constants;
import presentation.view.UpdatableSimulatorView;

public class RefreshView extends Thread {
    private final UpdatableSimulatorView view;
    private final Database db;
    
    public RefreshView(UpdatableSimulatorView view) {
        this.view = view;
        this.db = Database.getInstance();
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Thread.sleep(Constants.refreshIntervalMs);
                view.updateView();
                if (db.getStatus() != Status.RUNNING)
                    db.clearCars();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
