import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {

    private final TicketRepository repository = new TicketRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Ticket ticket = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            ticket = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (ticket == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(ticket);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String data = request.getParameter("data");
        String cityIn = request.getParameter("cityin");
        String cityOut = request.getParameter("cityout");

        if (data == null || cityIn == null || cityOut == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int cost = Integer.parseInt(request.getParameter("cost"));
            Ticket ticket = new Ticket(0, cost, data, cityIn, cityOut);

            long id = repository.create(ticket);

            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Type", "application/json");
            response.getWriter().println("{\"id\": " + id + "}");
        } catch (NumberFormatException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String data = request.getParameter("data");
        String cityIn = request.getParameter("cityin");
        String cityOut = request.getParameter("cityout");

        if (data == null || cityIn == null || cityOut == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("id"));
            int cost = Integer.parseInt(request.getParameter("cost"));
            Ticket ticket = new Ticket(id, cost, data, cityIn, cityOut);

            boolean isUpdated = repository.update(ticket);
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
