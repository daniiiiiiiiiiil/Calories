
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Products {
    public static void viewAllFood(Connection connection) {
        String sql = "SELECT name, calories,protein,fat,carbohydrate FROM food;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String foodName = resultSet.getString("name");
                int calories = resultSet.getInt("calories");
                int protein = resultSet.getInt("protein");
                int fat = resultSet.getInt("fat");
                int carbohydrate = resultSet.getInt("carbohydrate");
                System.out.println("Продукт: " + foodName + ", Калории: " + calories + ", Белки: " + protein + ", Жиры: " + fat + ", Углеводы: " + carbohydrate);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка вывода списка продуктов: " + e.getMessage());
        }
    }
}
