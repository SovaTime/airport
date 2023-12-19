import java.sql.*;

public class TicketRepository implements CRUDRepository<Ticket> {

    @Override
    public long create(Ticket ticket) {
        String query = "INSERT INTO ticket(cost, data, cityout, cityin) VALUES(?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ticket.getCost());
            preparedStatement.setString(2, ticket.getData());
            preparedStatement.setString(3, ticket.getCityOut());
            preparedStatement.setString(4, ticket.getCityIn());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating ticket failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create ticket, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Ticket read(long id) {
        String query = "SELECT * FROM ticket WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getLong("id"),
                        resultSet.getInt("cost"),
                        resultSet.getString("data"),
                        resultSet.getString("cityin"),
                        resultSet.getString("cityout")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Ticket ticket) {
        String query = "UPDATE ticket SET cost = ?, data = ?, cityout = ?, cityin = ? WHERE id = " + ticket.getId();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ticket.getCost());
            preparedStatement.setString(2, ticket.getData());
            preparedStatement.setString(3, ticket.getCityOut());
            preparedStatement.setString(4, ticket.getCityIn());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM ticket WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}