package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.ImageDAO;
import com.prabhash.megacity.dto.ImageDTO;
import com.prabhash.megacity.entity.Image;
import com.prabhash.megacity.service.ImageService;

import java.sql.Timestamp;
import java.util.List;

public class ImageServiceImpl implements ImageService {
    private ImageDAO imageDAO = new ImageDAO();

    @Override
    public void addImage(ImageDTO imageDTO) {
        // Convert DTO to Entity (Image)
        Image image = new Image();
        image.setName(imageDTO.getName());
        image.setPath(imageDTO.getPath());
        image.setCarId(imageDTO.getCarId()); // Using car_id as foreign key
        image.setCreatedAt(new Timestamp(System.currentTimeMillis()));  // Set current timestamp

        // Persist image in the database
        imageDAO.addImage(image);
    }

    @Override
    public List<ImageDTO> getImagesByCarId(int carId) {
        List<Image> images = imageDAO.getImagesByCarId(carId);  // Get images from the DAO
        return images.stream()
                .map(image -> new ImageDTO(image.getId(), image.getName(), image.getPath(), image.getCarId(), image.getCreatedAt()))
                .toList();  // Convert each Image entity to ImageDTO and return the list
    }


    @Override
    public void deleteImagesByCarId(int carId) {
        imageDAO.deleteImagesByCarId(carId);
    }
}
