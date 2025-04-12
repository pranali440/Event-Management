package servlet;

import dao.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
@WebServlet("/api/events/*")
public class DeleteEventServlet extends HttpServlet {
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); 
        System.out.println("Received DELETE request: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing event ID");
            return;
        }

        try {
            int eventId = Integer.parseInt(pathInfo.substring(1)); 
            System.out.println("Parsed event ID: " + eventId);
            
            boolean deleted = eventDAO.deleteEventById(eventId);
            System.out.println("EventDAO.deleteEventById returned: " + deleted);

            if (deleted) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT); 
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
                resp.getWriter().write("Event not found");
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            resp.getWriter().write("Invalid event ID format");
            e.printStackTrace();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            resp.getWriter().write("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}