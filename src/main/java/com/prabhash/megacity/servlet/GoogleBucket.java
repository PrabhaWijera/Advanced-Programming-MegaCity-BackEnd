package com.prabhash.megacity.servlet;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/googleUploadImage")
@MultipartConfig
public class GoogleBucket extends HttpServlet {
    private static final String BUCKET_NAME = "megacitybucket"; // Your GCS bucket name
    private static final Logger logger = Logger.getLogger(GoogleBucket.class.getName());  // Logger for debugging

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the uploaded file from the request
            Part filePart = request.getPart("file");
            if (filePart == null) {
                logger.severe("No file uploaded.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("No file uploaded");
                return;
            }

            String fileName = UUID.randomUUID().toString() + "-" + filePart.getSubmittedFileName(); // Ensure unique filename

            // Log the file details for debugging
            logger.info("Received file: " + fileName);
            logger.info("File content type: " + filePart.getContentType());

            // Load the service account credentials from the classpath
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("service-account-file.json");
            if (serviceAccount == null) {
                logger.severe("Service account credentials file not found in classpath.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Service account credentials file not found in classpath.");
                return;
            }

            // Initialize Google Cloud Storage with Service Account credentials
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();

            // Create a BlobId and BlobInfo for the image to upload
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(filePart.getContentType())  // Set the content type of the file
                    .build();

            // Upload the file to Google Cloud Storage
            storage.create(blobInfo, filePart.getInputStream());

            // Respond with the URL of the uploaded image
            String imageUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;
            logger.info("Image uploaded successfully: " + imageUrl);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(imageUrl);  // Send image URL back to frontend

        } catch (IOException e) {
            logger.severe("IOException occurred: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace to the server logs for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error uploading image: " + e.getMessage());  // Display the specific error message
        } catch (Exception e) {
            logger.severe("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace to the server logs for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Unexpected error: " + e.getMessage());  // Display the specific error message
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Load the service account credentials from the classpath
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("service-account-file.json");
            if (serviceAccount == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Service account credentials file not found in classpath.");
                return;
            }

            // Initialize Google Cloud Storage with Service Account credentials
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();

            // List all blobs in the specified bucket
            List<String> imageUrls = new ArrayList<>();
            Bucket bucket = storage.get(BUCKET_NAME);
            for (Blob blob : bucket.list().iterateAll()) {
                // Construct the public URL for each image
                String imageUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + blob.getName();
                imageUrls.add(imageUrl);
            }

            // Respond with the list of image URLs as a JSON array
            response.setContentType("application/json");
            response.getWriter().write(new com.google.gson.Gson().toJson(imageUrls));

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error fetching images: " + e.getMessage());
        }
    }

}