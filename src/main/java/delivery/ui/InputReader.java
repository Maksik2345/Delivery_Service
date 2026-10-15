package delivery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Значение не может быть пустым. Попробуйте ещё раз.");
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " [" + min + "-" + max + "]: ");
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Число должно быть от " + min + " до " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public BigDecimal readPrice(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String line = scanner.nextLine().trim().replace(',', '.');
            try {
                BigDecimal value = new BigDecimal(line);
                if (value.compareTo(BigDecimal.ZERO) >= 0) {
                    return value;
                }
                System.out.println("Цена не может быть отрицательной.");
            } catch (NumberFormatException e) {
                System.out.println("Введите число, например 199.90.");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (д/н): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("д") || line.equals("y")) {
                return true;
            }
            if (line.equals("н") || line.equals("n")) {
                return false;
            }
            System.out.println("Введите «д» или «н».");
        }
    }
}