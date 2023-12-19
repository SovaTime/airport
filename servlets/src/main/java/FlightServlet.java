import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {

    private final FlightRepository repository = new FlightRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Flight flight = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            flight = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (flight == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(flight);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Flight flight = new Flight(0);
        long id = repository.create(flight);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println("{\"id\": " + id + "}");
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