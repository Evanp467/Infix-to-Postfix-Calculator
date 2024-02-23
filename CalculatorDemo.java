/**
 * Evan Phillips
 * CalculatorDemo class
 */

package assg77_phillipsev20;

import java.util.Scanner;

public class CalculatorDemo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("Enter an infix expression:");
            String expression = scan.nextLine();
            Calculator calculator = new Calculator(expression);

            if (calculator.convertPostfix()) {
                System.out.println("Postfix expression: " + calculator.getPostfix());
                System.out.println("Result: " + calculator.evaluate());
            } else {
                System.out.println("Invalid expression.");
            }

            System.out.println("Do you want to evaluate another expression? (y/n)");
            String input = scan.nextLine();
            continueLoop = input.equalsIgnoreCase("y");
        }
    }
}