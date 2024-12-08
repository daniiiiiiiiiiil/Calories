import java.sql.*;
import java.util.Scanner;


public class Calories {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AddCalories addCalories = new AddCalories();
        Settings settings = new Settings();
        meal_time mealTime = new meal_time();
        Сalories_today calories_today = new Сalories_today();
        date_of_calories Date_of_calories = new date_of_calories();
        All_calories allCalories = new All_calories();
        Products products = new Products();

        String dbUrl = settings.getDbUrl();
        String dbUser = settings.getDbUser();
        String dbPassword = settings.getDbPassword();


        try (DatabaseConnection dbConnection = new DatabaseConnection(dbUrl, dbUser, dbPassword);
             Connection connection = dbConnection.connect()) {

            if (connection == null) {
                System.err.println("Не удалось установить соединение с базой данных.");
                return;
            }

            while (true) {
                System.out.println("Консольное приложение для подсчёта калорий");
                System.out.println("Меню выбора того что хочет пользователь:\n" +
                        "1. Внести калории\n" +
                        "2. Посмотреть калории за сегодня\n" +
                        "3. Посмотреть калории за определенный день\n" +
                        "4. Посмотреть калории за все время\n" +
                        "5. Еда\n" +
                        "6. Выход\n");

                int choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера

                switch (choice) {
                    case 1:
                        addCalories.addCalories(connection);
                        break;
                    case 2:
                        calories_today.viewCaloriesToday(connection);
                        break;
                    case 3:
                        System.out.print("Введите дату (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        Date_of_calories.viewCaloriesByDate(connection, date);
                        break;
                    case 4:
                        allCalories.viewAllCalories(connection);
                        break;
                    case 5:
                        products.viewAllFood(connection);
                        break;
                    case 6:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте ещё раз.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка работы с базой данных: " + e.getMessage());
        }
    }

}