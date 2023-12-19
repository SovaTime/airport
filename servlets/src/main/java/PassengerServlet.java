import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/passenger")
public class PassengerServlet extends HttpServlet {

    private final PassengerRepository repository = new PassengerRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Passenger passenger = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            passenger = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (passenger == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(passenger);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String passport = request.getParameter("passport");

        if (fullname == null || phone == null || passport == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Passenger passenger = new Passenger(0, fullname, phone, passport);
        long id = repository.create(passenger);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println("{\"id\": " + id + "}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String passport = request.getParameter("passport");

        if (fullname == null || phone == null || passport == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("id"));
            Passenger passenger = new Passenger(id, fullname, phone, passport);

            boolean isUpdated = repository.update(passenger);
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