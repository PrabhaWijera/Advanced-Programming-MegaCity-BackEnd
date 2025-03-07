package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.CarUserFilteringDAO;
import com.prabhash.megacity.dto.CarDTO;
import com.prabhash.megacity.entity.Car;
import com.prabhash.megacity.service.CarUserFilteringService;

import java.util.List;
import java.util.stream.Collectors;

public class CarUserFilteringServiceimpl implements CarUserFilteringService {

    private CarUserFilteringDAO carDAO = new CarUserFilteringDAO();

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars = carDAO.getAllCars();
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> filterCarsByStatus(String status) {
        List<Car> cars = carDAO.filterCarsByStatus(status);
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> filterCarsByModel(String model) {
        List<Car> cars = carDAO.filterCarsByModel(model);
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> filterCarsByStatusAndModel(String status, String model) {
        List<Car> cars = carDAO.filterCarsByStatusAndModel(status, model);
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Helper function to convert Car entity to CarDTO
    private CarDTO convertToDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getName(),
                car.getModel(),
                car.getPlate_number(),
                car.getYear(),
                car.getStatus()
        );
    }
}
