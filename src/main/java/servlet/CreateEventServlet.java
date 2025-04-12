package servlet;

import dao.EventDAO;
import model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/create-event")
public class CreateEventServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String dateStr = request.getParameter("date");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setLocation(location);
            event.setDate(date);

            new EventDAO().createEvent(event);
            response.sendRedirect("event_list.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: Invalid Date Format");
        }
    }
}