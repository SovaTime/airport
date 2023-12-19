import java.sql.*;

public class FlightRepository implements CRUDRepository<Flight> {

    @Override
    public long create(Flight flight) {
        String query = "INSERT INTO flight DEFAULT VALUES";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating flight failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create flight, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Flight read(long id) {
        String query = "SELECT * FROM flight WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return new Flight(resultSet.getLong("id"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Flight aircraft) {
        throw new IllegalStateException("Unimplemented method \"update\"");
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM flight WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
