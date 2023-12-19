import java.sql.*;

public class PassengerRepository implements CRUDRepository<Passenger> {

    @Override
    public long create(Passenger passenger) {
        String query = "INSERT INTO passenger(fullname, phone, passport) VALUES(?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, passenger.getFullName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setString(3, passenger.getPassport());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating passenger failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create passenger, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Passenger read(long id) {
        String query = "SELECT * FROM passenger WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return new Passenger(
                        resultSet.getLong("id"),
                        resultSet.getString("fullname"),
                        resultSet.getString("phone"),
                        resultSet.getString("passport")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Passenger passenger) {
        String query = "UPDATE passenger SET fullname = ?, phone = ?, passport = ? WHERE id = " + passenger.getId();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passenger.getFullName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setString(3, passenger.getPassport());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM passenger WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
