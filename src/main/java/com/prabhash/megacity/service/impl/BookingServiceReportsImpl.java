package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.BookingDAO;
import com.prabhash.megacity.entity.Booking;
import com.prabhash.megacity.service.BookingServiceReports;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;

public class BookingServiceReportsImpl implements BookingServiceReports {
    private BookingDAO bookingDAO = new BookingDAO();


    @Override
    public JRBeanCollectionDataSource generateIndividualBookingReport(int bookingId) {
        Booking booking = bookingDAO.getBookingById(bookingId); // Get booking by ID

        // If no booking is found with that ID, return null
        if (booking == null) {
            return null;
        }

        // Create a list of bookings (in real applications, it might be a list of objects from a query or other sources)
        List<Booking> bookingData = List.of(booking); // Wrap the single booking in a list

        // Return a JRBeanCollectionDataSource, which JasperReports can use to fill the report
        return new JRBeanCollectionDataSource(bookingData);
    }
}
