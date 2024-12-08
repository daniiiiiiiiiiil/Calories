import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class AddCalories{
    static void addCalories(Connection connection) {
        meal_time mealTime = new meal_time();
        Scanner scanner = new Scanner(System.in);
            System.out.println("Если в списке нету продукта,то вы добавите его в список");
            System.out.print("Введите название продукта: \n");
            String foodName = scanner.nextLine();
            System.out.print("Введите количество калорий: ");
            int calories = scanner.nextInt();
            System.out.print("Введите количество белков: ");
            int protein = scanner.nextInt();
            System.out.print("Введите количество жиров: ");
            int fat = scanner.nextInt();
            System.out.print("Введите количество углеводов: ");
            int carbohydrate = scanner.nextInt();
            System.out.println("В какое время суток ели: ");
            int meatTime = meal_time.getMealTimeChoice();

            scanner.nextLine();

            try {
                String insertFoodSql = "INSERT INTO food (name, calories,protein,fat,carbohydrate) SELECT ?, ?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM food WHERE name = ?);";
                try (PreparedStatement foodStatement = connection.prepareStatement(insertFoodSql)) {
                    foodStatement.setString(1, foodName);
                    foodStatement.setInt(2, calories);
                    foodStatement.setInt(3, protein);
                    foodStatement.setInt(4, fat);
                    foodStatement.setInt(5, carbohydrate);
                    foodStatement.setString(6, foodName);
                    foodStatement.executeUpdate();
                }

                String getFoodIdSql = "SELECT id FROM food WHERE name = ?";
                try (PreparedStatement getFoodIdStatement = connection.prepareStatement(getFoodIdSql)) {
                    getFoodIdStatement.setString(1, foodName);
                    try (ResultSet resultSet = getFoodIdStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int foodId = resultSet.getInt("id");
                            String insertCaloriesSql = "INSERT INTO calorie_log (user_id, food_id, calories_consumed,meat_time) VALUES (?, ?, ?, ?);";
                            try (PreparedStatement caloriesStatement = connection.prepareStatement(insertCaloriesSql)) {
                                caloriesStatement.setInt(1, 1);
                                caloriesStatement.setInt(2, foodId);
                                caloriesStatement.setInt(3, calories);
                                caloriesStatement.setInt(4, meatTime);
                                caloriesStatement.executeUpdate();
                                System.out.println("Калории добавлены успешно!");
                            }
                        } else {
                            System.out.println("Продукт не найден.");
                        }

                    }
                }

            } catch (SQLException e) {
                System.err.println("Ошибка добавления калорий: " + e.getMessage());
            }
        }
}