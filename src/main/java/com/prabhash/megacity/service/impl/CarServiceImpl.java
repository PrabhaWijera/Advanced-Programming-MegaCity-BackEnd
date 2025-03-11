package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.CarDAO;
import com.prabhash.megacity.dao.impl.CarDAOimpl;
import com.prabhash.megacity.dto.CarDTO;
import com.prabhash.megacity.entity.Car;
import com.prabhash.megacity.service.CarService;

import java.util.List;

public class CarServiceImpl implements CarService {
    private CarDAO carDAOimpl = new CarDAOimpl();
    @Override
    public void addCar(CarDTO carDTO) {
        // Convert DTO to Entity (Car)
        Car car = new Car();
        car.setName(carDTO.getName());
        car.setModel(carDTO.getModel());
        car.setPlate_number(carDTO.getPlate_number());
        car.setYear(carDTO.getYear());
        car.setStatus(carDTO.getStatus());

        // Persist car in the database
        carDAOimpl.addCar(car);
    }

    @Override
    public CarDTO getCarById(int id) {
        Car car = carDAOimpl.getCarById(id);
        if (car != null) {
            return new CarDTO(car.getId(), car.getName(), car.getModel(), car.getPlate_number(), car.getYear(), car.getStatus());
        }
        return null;
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars = carDAOimpl.getAllCars();
        return cars.stream()
                .map(car -> new CarDTO(car.getId(), car.getName(), car.getModel(), car.getPlate_number(), car.getYear(), car.getStatus()))
                .toList();
    }

    @Override
    public void updateCar(CarDTO carDTO) {
        // Convert DTO to Entity (Car)
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setName(carDTO.getName());
        car.setModel(carDTO.getModel());
        car.getPlate_number();
        car.setYear(carDTO.getYear());
        car.setStatus(carDTO.getStatus());

        // Update car in the database
        carDAOimpl.updateCar(car);
    }

    @Override
    public void deleteCar(int id) {
        carDAOimpl.deleteCar(id);
    }





}
