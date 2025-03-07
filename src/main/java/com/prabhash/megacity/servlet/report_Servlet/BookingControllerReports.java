package com.prabhash.megacity.servlet.report_Servlet;

import com.prabhash.megacity.service.BookingServiceReports;
import com.prabhash.megacity.service.impl.BookingServiceReportsImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/booking/report/*")
public class BookingControllerReports extends HttpServlet {
    private BookingServiceReports bookingService = new BookingServiceReportsImpl();
    private Gson gson = new Gson();

    // Handle Generate and Download Individual Booking Report (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                int bookingId = Integer.parseInt(idParam);

                // Fetch the JRDataSource from the service layer
                JRBeanCollectionDataSource dataSource = bookingService.generateIndividualBookingReport(bookingId);

                if (dataSource != null) {
                    // Load JasperReport template
                    InputStream jasperTemplate = getServletContext().getResourceAsStream("/WEB-INF/reports/booking_report_template.jasper");

                    // Set up parameters for the report (like report title, company info, etc.)
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("reportTitle", "Booking Report");
                    parameters.put("bookingId", bookingId);

                    // Generate the report using the JasperReports engine
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperTemplate, parameters, dataSource);

                    // Set the response type for PDF
                    resp.setContentType("application/pdf");
                    resp.setHeader("Content-Disposition", "attachment; filename=booking_report_" + bookingId + ".pdf");

                    // Write the generated PDF report to the response output stream
                    JasperExportManager.exportReportToPdfStream(jasperPrint, resp.getOutputStream());
                } else {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(gson.toJson("Booking not found with ID " + bookingId));
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID");
            } catch (JRException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating report: " + e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Booking ID is required");
        }
    }
}
