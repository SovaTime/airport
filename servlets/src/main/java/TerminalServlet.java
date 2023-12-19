import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/terminal")
public class TerminalServlet extends HttpServlet {

    private final TerminalRepository repository = new TerminalRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Terminal terminal = null;

        try {
            long id = Long.parseLong(request.getParameter("id"));
            terminal = repository.read(id);
        } catch (NumberFormatException ignored) {
        }

        if (terminal == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String jsonString = Serialize.serial(terminal);
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        if (name == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Terminal aircraft = new Terminal(0, name);
        long id = repository.create(aircraft);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println("{\"id\": " + id + "}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");

        if (name == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("id"));
            Terminal terminal = new Terminal(id, name);

            boolean isUpdated = repository.update(terminal);
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