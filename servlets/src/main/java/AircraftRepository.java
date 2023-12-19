import java.sql.*;

public class AircraftRepository implements CRUDRepository<Aircraft> {

    @Override
    public long create(Aircraft aircraft) {
        String query = "INSERT INTO aircraft(name, fullnamepilot) VALUES(?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, aircraft.getName());
            preparedStatement.setString(2, aircraft.getFullNamePilot());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating aircraft failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create aircraft, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Aircraft read(long id) {
        String query = "SELECT * FROM aircraft WHERE id = " + id;

        try (Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            
            if (resultSet.next()) {
                return new Aircraft(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("fullnamepilot")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Aircraft aircraft) {
        String query = "UPDATE aircraft SET name = ?, fullnamepilot = ? WHERE id = " + aircraft.getId();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, aircraft.getName());
            preparedStatement.setString(2, aircraft.getFullNamePilot());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM aircraft WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}