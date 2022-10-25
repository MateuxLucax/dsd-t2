package data.datasource;

import domain.model.Car;
import domain.model.Position;
import domain.model.enums.Status;
import domain.model.World;
import domain.model.parallel.Lockable;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database database;
    private Database() {
        this.cars = new HashMap<>();
        this.status = Status.STOPPED;
    }
    
    private World world;
    private Map<Position, Car> cars;
    private Status status;
    private Lockable lockable;

    public static synchronized Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
    
    public Car getCar(Position position) {
        return cars.get(position);
    }
    
    public Car removeCar(Position position) {
        return cars.remove(position);
    }
    
    public void setCar(Position position, Car car) {
        cars.put(position, car);
    }

    public Map<Position, Car> getCars() {
        return cars;
    };
    
    public void clearCars() {
        this.cars.clear();
    };

    public Lockable getLockable() {
        return lockable;
    }

    public void setLockable(Lockable lockable) {
        this.lockable = lockable;
    }
}
