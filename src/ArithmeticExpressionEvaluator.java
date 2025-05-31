import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class ArithmeticExpressionEvaluator {

    private static boolean isOperator(char character) {
        return switch (character) {
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }

    private static int getPrecedence(char operator) {
        return switch (operator) {
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            default -> 0;
        };
    }

    private static double calculate(char operator, double firstOperand, double secondOperand) {
        return switch (operator) {
            case '+' -> firstOperand + secondOperand;
            case '-' -> secondOperand - firstOperand;
            case '*' -> firstOperand * secondOperand;
            case '/' -> {
                if (firstOperand == 0)
                    throw new ArithmeticException("You can't divide by zero");
                yield secondOperand / firstOperand;
            }
            default -> 0;
        };
    }

    public static double evaluateExpression(String text) throws ArithmeticException, IllegalArgumentException, NoSuchElementException {
        char[] characters = text.replaceAll("\\s+", "").toCharArray();
        Deque<Character> operators = new ArrayDeque<>();
        Deque<Double> operands = new ArrayDeque<>();

        for (int i = 0; i < characters.length; i++) {
            // Handling a positive number
            if (Character.isDigit(characters[i])) {
                StringBuilder positiveNumber = new StringBuilder();
                // The number can be multi-digit and contain a point
                while (i < characters.length && (Character.isDigit(characters[i]) || characters[i] == '.'))
                    positiveNumber.append(characters[i++]);
                operands.addFirst(Double.parseDouble(positiveNumber.toString()));
                i--;
            }

            // Handling a negative number
            else if (characters[i] == '-' && (i == 0 || characters[i-1] == '(')) {
                StringBuilder negativeNumber = new StringBuilder();
                do {
                    negativeNumber.append(characters[i++]);
                }
                while (i < characters.length && (Character.isDigit(characters[i]) || characters[i] == '.'));
                operands.addFirst(Double.parseDouble(negativeNumber.toString()));
                i--;
            }

            // Handling operators
            else if (isOperator(characters[i])) {
                while (!operators.isEmpty() && getPrecedence(operators.getFirst()) >= getPrecedence(characters[i])) {
                    operands.addFirst(calculate(
                            operators.removeFirst(),
                            operands.removeFirst(),
                            operands.removeFirst()
                    ));
                }
                operators.addFirst(characters[i]);
            }

            // Handling a left parenthesis
            else if (characters[i] == '(') {
                operators.addFirst(characters[i]);
            }

            // Handling a right parenthesis
            else if (characters[i] == ')') {
                while (operators.getFirst() != '(') {
                    operands.addFirst(calculate(
                            operators.removeFirst(),
                            operands.removeFirst(),
                            operands.removeFirst()
                    ));
                }
                operators.removeFirst();
            }

            else {
                throw new IllegalArgumentException("Illegal character in the expression");
            }
        }

        while (!operators.isEmpty()) {
            operands.addFirst(calculate(
                    operators.removeFirst(),
                    operands.removeFirst(),
                    operands.removeFirst()
            ));
        }

        // Only one value is left on the stack for operands and this is a result
        return operands.removeFirst();
    }
}
