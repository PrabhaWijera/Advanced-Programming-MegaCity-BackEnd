package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO addCar(CarDTO carDTO);
    CarDTO getCarById(int id);
    List<CarDTO> getAllCars();
    void updateCar(CarDTO carDTO);
    void deleteCar(int id);
}
