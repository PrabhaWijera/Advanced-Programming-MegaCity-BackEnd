package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.BookingDTO;
import com.prabhash.megacity.entity.Booking;

import java.util.List;

public interface BookingService {
    boolean createBooking(BookingDTO bookingDTO);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    boolean updateBooking(int id, BookingDTO bookingDTO);
    boolean deleteBooking(int id);
}
