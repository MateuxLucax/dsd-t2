package com.udesc.t2_dsd_infra;

import com.udesc.t2_dsd.model.Car;
import com.udesc.t2_dsd.model.Position;
import com.udesc.t2_dsd.model.World;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database database;
    private Database() {
        this.cars = Collections.synchronizedMap(new HashMap());
    }
    
    private World world;
    private Map<Position, Car> cars;
    
    public static synchronized Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public synchronized Map<Position, Car> getCars() {
        return cars;
    }

    public void setCars(Map<Position, Car> cars) {
        this.cars = cars;
    }
}
