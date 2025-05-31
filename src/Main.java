import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static boolean isLoopStopped(Scanner scanner) {
        String input = "";
        while (!(input.equals("y") || input.equals("n"))) {
            System.out.println("\nContinue? (y/n): ");
            input = scanner.nextLine();
        }
        return input.equals("n");
    }

    private static String prepareText(Scanner scanner) throws IOException {
        String text;
        System.out.println("""
                How to accept text? Please choose an option:
                1. From file
                2. From console
                """);
        System.out.println("Your choice: ");
        String input = scanner.nextLine();
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.println("Wrong input. You need to enter 1 for file or 2 for console");
            System.out.println("Your choice: ");
            input = scanner.nextLine();
        }
        if (input.equals("1")) {
            System.out.println("Enter file name: ");
            text = Files.readString(Paths.get(scanner.nextLine()));
        }
        else {
            System.out.println("Enter text: ");
            text = scanner.nextLine();
        }
        return text;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean mainLoop = true;
        boolean innerLoop;
        String choice;

        System.out.println("Welcome to Gehtsoft Technical Assessment");
        while (mainLoop) {
            System.out.println("""
                Please choose an option:
                1. Caesar Cipher Encryption
                2. Caesar Cipher Decryption
                3. Arithmetic Expression Evaluation
                4. Exit
                """);
            System.out.println("Enter your choice: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    innerLoop = true;
                    while (innerLoop) {
                        try {
                            String textForEncryption = prepareText(scanner);

                            System.out.println("Enter shift: ");
                            String shift = scanner.nextLine();
                            while (!shift.matches("-?\\d+")) {
                                System.out.println("Must be integer. Enter shift: ");
                                shift = scanner.nextLine();
                            }
                            String result = CaesarCipher.encryptTextWithShift(textForEncryption, Integer.parseInt(shift));
                            System.out.printf("Output: %s", result);
                        }
                        catch (FileNotFoundException fileNotFoundException) {
                            System.out.println("Wrong path to file");
                        }
                        catch (IOException exception) {
                            System.out.println("Error while entering file name: " + exception.getMessage());
                        }
                        if (isLoopStopped(scanner))
                            innerLoop = false;
                    }
                    break;
                case "2":
                    innerLoop = true;
                    while (innerLoop) {
                        try {
                            String textForDecryption = prepareText(scanner);
                            System.out.println("Enter shift value or \"-\" if you don't want to specify the shift.");
                            System.out.println("Enter shift: ");
                            String shift = scanner.nextLine();
                            while (!(shift.matches("-?\\d+") || shift.equals("-"))) {
                                System.out.println("Must be integer or \"-\". Enter shift: ");
                                shift = scanner.nextLine();
                            }
                            if (shift.equals("-")) {
                                Map<Integer, String> allVariants = CaesarCipher.brutoForce(textForDecryption);
                                System.out.println("All possible variants");
                                for (Map.Entry<Integer, String> entry: allVariants.entrySet()) {
                                    System.out.printf("\nShift: %d, decrypted text: %s", entry.getKey(), entry.getValue());
                                }
                            }
                            else {
                                String result = CaesarCipher.decryptTextWithShift(textForDecryption, Integer.parseInt(shift));
                                System.out.printf("Output: %s", result);
                            }
                        }
                        catch (FileNotFoundException fileNotFoundException) {
                            System.out.println("Wrong path to file");
                        }
                        catch (IOException exception) {
                            System.out.println("Error while entering file name: " + exception.getMessage());
                        }
                        if (isLoopStopped(scanner))
                            innerLoop = false;
                    }
                    break;
                case "3":
                    innerLoop = true;
                    while (innerLoop) {
                        System.out.println("Enter arithmetic expression: ");
                        try {
                            double result = ArithmeticExpressionEvaluator.evaluateExpression(scanner.nextLine());

                            if (result - Math.floor(result) == 0.0) {
                                System.out.printf("Result: %d", Math.round(result));
                            } else {
                                System.out.printf("Result: %f", result);
                            }
                        }
                        catch (ArithmeticException | IllegalArgumentException exception) {
                            System.out.println("Error: " + exception.getMessage());
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            System.out.println("Input string was not in the correct format");
                        }
                        if (isLoopStopped(scanner))
                            innerLoop = false;
                    }
                    break;
                case "4":
                    mainLoop = false;
                    break;
                default:
                    System.out.println("It is not a correct option. Please try again.");
                    break;
            }
        }
    }
}
