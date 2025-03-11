package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.Car;

import java.util.List;

public interface CarUserFilteringDAO {

    public List<Car> getAllCars();
    public Car getCarById(int id);
    public List<Car> filterCarsByStatus(String status);
    public List<Car> filterCarsByModel(String model);
    public List<Car> filterCarsByStatusAndModel(String status, String model);

}
