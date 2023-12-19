import java.sql.*;

public class TerminalRepository implements CRUDRepository<Terminal> {

    @Override
    public long create(Terminal terminal) {
        String query = "INSERT INTO terminal(name) VALUES(?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, terminal.getName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating terminal failed, no rows affected");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("Failed to create terminal, id isn't found");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public Terminal read(long id) {
        String query = "SELECT * FROM terminal WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return new Terminal(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }

        return null;
    }

    @Override
    public boolean update(Terminal terminal) {
        String query = "UPDATE terminal SET name = ? WHERE id = " + terminal.getId();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, terminal.getName());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM terminal WHERE id = " + id;

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query) != 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}