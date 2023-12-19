import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/aircraft")
public class AircraftServlet extends HttpServlet {

    private final AircraftRepository repository = new AircraftRepository();

    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Aircraft aircraft = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            aircraft = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (aircraft == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(aircraft);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String fullnamepilot = request.getParameter("fullnamepilot");

        if (name == null || fullnamepilot == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Aircraft aircraft = new Aircraft(0, name, fullnamepilot);
        long id = repository.create(aircraft);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println("{\"id\": " + id + "}");
    }

    @Override 
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String fullnamepilot = request.getParameter("fullnamepilot");

        if (name == null || fullnamepilot == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("id"));
            Aircraft aircraft = new Aircraft(id, name, fullnamepilot);

            boolean isUpdated = repository.update(aircraft);
            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override 
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = repository.delete(id);

            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}