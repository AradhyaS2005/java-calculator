import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;

class calculatorFrame extends Frame implements ActionListener, WindowListener {
    JTextField display;

    Button plusMinusChangeSign;
    Button zero;
    Button decimal;
    Button equals;
    Button one;
    Button two;
    Button three;
    Button addition;
    Button four;
    Button five;
    Button six;
    Button subraction;
    Button seven;
    Button eight;
    Button nine;
    Button multiplication;
    Button reciprocal;
    Button square;
    Button squareRoot;
    Button divide;
    Button percentage;
    Button clearEntry;
    Button clearAll;
    Button cross;

    double firstNumber = 0;
    String operator = "";
    boolean startNewNumber = true;

    public calculatorFrame() {
        super("Calculator.io");
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 40));
        add(display, BorderLayout.NORTH);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));

        // Initialize buttons
        plusMinusChangeSign = new Button("+/-");
        zero = new Button("0");
        decimal = new Button(".");
        equals = new Button("=");
        one = new Button("1");
        two = new Button("2");
        three = new Button("3");
        addition = new Button("+");
        four = new Button("4");
        five = new Button("5");
        six = new Button("6");
        subraction = new Button("-");
        seven = new Button("7");
        eight = new Button("8");
        nine = new Button("9");
        multiplication = new Button("*");
        reciprocal = new Button("1/x");
        square = new Button("x²");
        squareRoot = new Button("√x");
        divide = new Button("/");
        percentage = new Button("%");
        clearEntry = new Button("CE");
        clearAll = new Button("C");
        cross = new Button("⌫");

        // Add buttons to panel
        buttonPanel.add(percentage);
        buttonPanel.add(clearEntry);
        buttonPanel.add(clearAll);
        buttonPanel.add(cross);
        buttonPanel.add(reciprocal);
        buttonPanel.add(square);
        buttonPanel.add(squareRoot);
        buttonPanel.add(divide);
        buttonPanel.add(seven);
        buttonPanel.add(eight);
        buttonPanel.add(nine);
        buttonPanel.add(multiplication);
        buttonPanel.add(four);
        buttonPanel.add(five);
        buttonPanel.add(six);
        buttonPanel.add(subraction);
        buttonPanel.add(one);
        buttonPanel.add(two);
        buttonPanel.add(three);
        buttonPanel.add(addition);
        buttonPanel.add(plusMinusChangeSign);
        buttonPanel.add(zero);
        buttonPanel.add(decimal);
        buttonPanel.add(equals);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void appendDigit(String digit) {
        if (startNewNumber) {
            display.setText(digit);
            startNewNumber = false;
        } else {
            display.setText(display.getText().equals("0") ? digit : display.getText() + digit);
        }
    }

    private void setOperator(String operator) {
        firstNumber = Double.parseDouble(display.getText());
        this.operator = operator;
        startNewNumber = true;
    }

    private void calculate() {
        double secondNumber = Double.parseDouble(display.getText());
        double result = 0;

        try {
            switch (operator) {
                case "+" -> result = firstNumber + secondNumber;
                case "-" -> result = firstNumber - secondNumber;
                case "*" -> result = firstNumber * secondNumber;
                case "/" -> {
                    if (secondNumber == 0) {
                        display.setText("Cannot divide by zero");
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                }
                case "%" -> result = (firstNumber * secondNumber) / 100;
            }

            display.setText(removeTrailingZero(result));
            startNewNumber = true;

        } catch (Exception e) {
            display.setText("Error");
            startNewNumber = true;
        }
    }

    private void performUnaryOperation(String op) {
        double num = Double.parseDouble(display.getText());
        double result = 0;

        switch (op) {
            case "1/x" -> {
                if (num == 0) {
                    display.setText("Cannot divide by zero");
                    startNewNumber = true;
                    return;
                }
                result = 1 / num;
            }
            case "x²" -> result = num * num;
            case "√x" -> {
                if (num < 0) {
                    display.setText("Invalid Input");
                    startNewNumber = true;
                    return;
                }
                result = Math.sqrt(num);
            }
            case "+/-" -> result = -num;
        }

        display.setText(removeTrailingZero(result));
        startNewNumber = true;
    }

    private String removeTrailingZero(double num) {
        if (num == (long) num)
            return String.format("%d", (long) num);
        else
            return String.valueOf(num);
    }

    private void clearAll() {
        display.setText("0");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    private void clearEntry() {
        display.setText("0");
        startNewNumber = true;
    }

    private void backspace() {
        String current = display.getText();
        if (current.length() > 1) {
            display.setText(current.substring(0, current.length() - 1));
        } else {
            display.setText("0");
        }
    }

    public void actionListenerMethod() {
        Button[] allButtons = {
            zero, one, two, three, four, five, six, seven, eight, nine,
            addition, subraction, multiplication, divide, equals, decimal,
            percentage, reciprocal, square, squareRoot, plusMinusChangeSign,
            clearEntry, clearAll, cross
        };

        for (Button b : allButtons) b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == zero) appendDigit("0");
        else if (src == one) appendDigit("1");
        else if (src == two) appendDigit("2");
        else if (src == three) appendDigit("3");
        else if (src == four) appendDigit("4");
        else if (src == five) appendDigit("5");
        else if (src == six) appendDigit("6");
        else if (src == seven) appendDigit("7");
        else if (src == eight) appendDigit("8");
        else if (src == nine) appendDigit("9");
        else if (src == decimal) {
            if (!display.getText().contains(".")) display.setText(display.getText() + ".");
            startNewNumber = false;
        }
        else if (src == addition) setOperator("+");
        else if (src == subraction) setOperator("-");
        else if (src == multiplication) setOperator("*");
        else if (src == divide) setOperator("/");
        else if (src == percentage) setOperator("%");
        else if (src == reciprocal) performUnaryOperation("1/x");
        else if (src == square) performUnaryOperation("x²");
        else if (src == squareRoot) performUnaryOperation("√x");
        else if (src == plusMinusChangeSign) performUnaryOperation("+/-");
        else if (src == clearEntry) clearEntry();
        else if (src == clearAll) clearAll();
        else if (src == cross) backspace();
        else if (src == equals) calculate();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    public void windowListenerMethod() {
        addWindowListener(this);
    }
}

public class calculator {
    public static void main(String[] args) {
        calculatorFrame calculatorframe = new calculatorFrame();
        calculatorframe.setSize(350, 550);
        calculatorframe.setVisible(true);
        calculatorframe.actionListenerMethod();
        calculatorframe.windowListenerMethod();
    }
}
