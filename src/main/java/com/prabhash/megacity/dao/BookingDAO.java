package com.prabhash.megacity.dao;

import com.prabhash.megacity.dto.BookingDTO;
import com.prabhash.megacity.entity.Booking;

import java.util.List;

public interface BookingDAO {


    boolean createBooking(Booking booking);
    public Booking getBookingById(int id);
    public boolean updateBooking(Booking booking);
    public boolean deleteBooking(int id);
    List<Booking> getAllBookings();
}
