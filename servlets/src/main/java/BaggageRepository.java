import java.sql.*;

public class BaggageRepository implements CRUDRepository<Baggage> {

    @Override
    public long create(Baggage baggage) {
        String query = "INSERT INTO baggage(weight, count) VALUES(?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, baggage.getWeight());
            preparedStatement.setInt(2, baggage.getCount());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating baggage failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create baggage, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Baggage read(long id) {
        String query = "SELECT * FROM baggage WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return new Baggage(
                        resultSet.getLong("id"),
                        resultSet.getDouble("weight"),
                        resultSet.getInt("count")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Baggage baggage) {
        String query = "UPDATE baggage SET weight = ?, count = ? WHERE id = " + baggage.getId();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, baggage.getWeight());
            preparedStatement.setInt(2, baggage.getCount());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM baggage WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
