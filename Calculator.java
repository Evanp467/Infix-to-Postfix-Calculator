/**
 * Evan Phillips
 * Calculator class 
 */

package assg77_phillipsev20;

import java.util.*;

class Calculator {
    private String infix;
    private String postfix;
    private boolean converted;
    
    /**
     * Constructs a new Calculator object with the given infix expression.
     *
     * @param exp the infix expression to be converted and evaluated.
     */
    public Calculator(String exp) {
        this.infix = exp;
        this.converted = false;
    }
    
    /**
     * Returns a string representation of the infix expression stored in this Calculator object.
     *
     * @return the infix expression as a string.
     */
    public String toString() {
        return infix;
    }
    
    /**
     * Converts the infix expression stored in this Calculator object to postfix notation.
     * If the conversion is successful, the postfix expression is stored in this object for future use.
     *
     * @return true if the conversion is successful, false otherwise.
     */
    public boolean convertPostfix() {
        StringBuilder postfixBuilder = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(infix, "+-*/()", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.matches("\\d+")) {
                postfixBuilder.append(token).append(" ");
            } else if (token.matches("[+\\-*/]")) {
                while (!operators.isEmpty() && precedence(token.charAt(0)) <= precedence(operators.peek())) {
                    postfixBuilder.append(operators.pop()).append(" ");
                }
                operators.push(token.charAt(0));
            } else if (token.equals("(")) {
                operators.push(token.charAt(0));
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfixBuilder.append(operators.pop()).append(" ");
                }
                if (operators.isEmpty() || operators.pop() != '(') {
                    return false;
                }
            }
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                return false;
            }
            postfixBuilder.append(operators.pop()).append(" ");
        }

        this.postfix = postfixBuilder.toString().trim();
        this.converted = true;
        return true;
    }
    
    /**

    Determines the precedence of the given operator.
    @param operator The operator whose precedence is to be determined.
    @return The precedence of the operator, where higher values indicate higher precedence.
    */
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    
    /**
     * Returns the postfix expression obtained after converting the infix expression stored in this Calculator object.
     * If the infix expression has not been converted to postfix notation yet, an IllegalStateException is thrown.
     *
     * @return the postfix expression as a string.
     * @throws IllegalStateException if the infix expression has not been converted to postfix notation yet.
     */
    public String getPostfix() throws IllegalStateException {
        if (!converted) {
            throw new IllegalStateException("Expression not yet converted.");
        }
        return postfix;
    }
    
    /**
     * Evaluates the postfix expression obtained after converting the infix expression stored in this Calculator object.
     * If the infix expression has not been converted to postfix notation yet, an IllegalStateException is thrown.
     *
     * @return the result of the arithmetic expression.
     * @throws IllegalStateException if the infix expression has not been converted to postfix notation yet.
     */
    public int evaluate() throws IllegalStateException {
        if (!converted) {
            throw new IllegalStateException("Expression not yet converted.");
        }

        Stack<Integer> operands = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(postfix, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.matches("\\d+")) {
                operands.push(Integer.parseInt(token));
            } else {
                int b = operands.pop();
                int a = operands.pop();
                switch (token.charAt(0)) {
                    case '+':
                        operands.push(a + b);
                        break;
                    case '-':
                        operands.push(a - b);
                        break;
                    case '*':
                        operands.push(a * b);
                        break;
                    case '/':
                        operands.push(a / b);
                        break;
                }
            }
        }
        return operands.pop();
    }
}
