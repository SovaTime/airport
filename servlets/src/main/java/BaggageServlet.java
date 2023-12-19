import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/baggage")
public class BaggageServlet extends HttpServlet {

    private final BaggageRepository repository = new BaggageRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Baggage baggage = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            baggage = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (baggage == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(baggage);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
           double weight = Double.parseDouble(request.getParameter("weight"));
           int count = Integer.parseInt(request.getParameter("count"));

           Baggage baggage = new Baggage(0, weight, count);
           long id = repository.create(baggage);

           response.setStatus(HttpServletResponse.SC_OK);
           response.addHeader("Content-Type", "application/json");
           response.getWriter().println("{\"id\": " + id + "}");
        } catch (NumberFormatException | NullPointerException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            double weight = Double.parseDouble(request.getParameter("weight"));
            int count = Integer.parseInt(request.getParameter("count"));

            Baggage baggage = new Baggage(id, weight, count);

            boolean isUpdated = repository.update(baggage);
            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException | NullPointerException exception) {
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