package servlet;

import com.google.gson.Gson;
import dao.EventDAO;
import model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/events/*")
public class EventListServlet extends HttpServlet {

    // GET: Return all events
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
            return;
        }

        List<Event> events = new EventDAO().getAllEvents();

        String json = new Gson().toJson(events);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}
