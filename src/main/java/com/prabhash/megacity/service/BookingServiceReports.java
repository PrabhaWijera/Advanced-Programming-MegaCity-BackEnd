package com.prabhash.megacity.service;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public interface BookingServiceReports {
    JRBeanCollectionDataSource generateIndividualBookingReport(int bookingId);
}
