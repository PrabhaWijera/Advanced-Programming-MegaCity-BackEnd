package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.impl.ImageDAOo;

import com.prabhash.megacity.dto.ImageDToo;
import com.prabhash.megacity.service.ImageServicee;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;

public class ImageServiceImpll implements ImageServicee {
    private ImageDAOo imageDAO = new ImageDAOo();

    @Override
    public void saveImage(String imageName, String imageType, InputStream imageInputStream) throws Exception {
        // Read input stream into byte array
        byte[] imageData = imageInputStream.readAllBytes();
        imageDAO.saveImage(imageName, imageType, imageData);
    }

    @Override
    public byte[] getImage(Long imageId) throws Exception {
        return imageDAO.getImage(imageId);
    }
    @Override
    public String getAllImagesJSON() {
        List<ImageDToo> images = imageDAO.getAllImages(); // Fetch images from DAO
        return new Gson().toJson(images); // Convert to JSON
    }
}
