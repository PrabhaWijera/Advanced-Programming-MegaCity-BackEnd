package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.Car;

import java.util.List;

public interface CarDAO {

    public void addCar(Car car);
    public Car getCarById(int id);
    public List<Car> getAllCars();
    public void updateCar(Car car);
    public void deleteCar(int id);
    public void updateCarStatus(int carId, String status);

}
