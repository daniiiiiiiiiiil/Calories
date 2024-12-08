import java.util.Scanner;

public class meal_time {
    public static int getMealTimeChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("В какое время дня был прием пищи:");
            System.out.println("1. Завтрак");
            System.out.println("2. Обед");
            System.out.println("3. Ужин");
            System.out.println("4. Другое");
            System.out.print("Выбор (1-4): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } else {
                System.out.println("Неверный ввод. Попробуйте снова.");
                scanner.nextLine();
            }

        } while (true);

    }
    public static String getMealTimeFromUser() {
        int choice = getMealTimeChoice();
        String mealTime;
        switch (choice) {
            case 1:
                mealTime = "Завтрак";
                break;
            case 2:
                mealTime = "Обед";
                break;
            case 3:
                mealTime = "Ужин";
                break;
            case 4:
                mealTime = "Другое";
                break;
            default:
                mealTime = null;
                break;
        }
        return mealTime;
    }
}