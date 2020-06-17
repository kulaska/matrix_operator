package processor;

import java.util.Scanner;

public class Menu {
    static private String[] options = {
            "Exit",
            "Add matrices", "Multiply matrix to a constant",
            "Multiply matrices",
            "Transpose matrix",
            "Find determinant",
            "Inverse matrix"
    };
    static private String[] transpositionOptions = {
            "Main diagonal",
            "Side diagonal",
            "Vertical line",
            "Horizontal line"
    };

    public Menu() {}

    static void showMenu(Scanner scanner) {
        for (int i = 1; i < options.length; i++) {
            System.out.println(i + ". " + options[i]);
        }

        System.out.println("0. " + options[0]);

        promptForMenuChoice(scanner);
    }

    static void showTransposeMenu(Scanner scanner) {
        for (int i = 0; i < transpositionOptions.length; i++) {
            System.out.println((i + 1) + ". " + transpositionOptions[i]);
        }

        promptForTransposingOption(scanner);
    }

    static void promptForMenuChoice(Scanner scanner) {
        System.out.println("Your choice:");

        int choice = Integer.parseInt(scanner.next());
        String choiceDecoded = options[choice];

        switch (choiceDecoded) {
            case "Add matrices":
                matrixAddition(scanner);
                showMenu(scanner);
                break;
            case "Multiply matrix to a constant":
                scalarMultiplication(scanner);
                showMenu(scanner);
                break;
            case "Multiply matrices":
                vectorMultiplication(scanner);
                showMenu(scanner);
                break;
            case "Transpose matrix":
                showTransposeMenu(scanner);
                showMenu(scanner);
                break;
            case "Find determinant":
                findDeterminantOfAMatrix(scanner);
                showMenu(scanner);
            case "Inverse matrix":
                findInverse(scanner);
                showMenu(scanner);
                break;
            case "Exit":
                System.exit(0);
            default:
                break;
        }
    }

    static void matrixAddition(Scanner scanner) {
        Matrix first = new Matrix();
        Matrix second = new Matrix();

        first.setMatrixFromInput(scanner);
        second.setMatrixFromInput(scanner);

        Matrix result = Matrix.addMatrices(first, second);

        result.outputMatrix();
    }

    static void scalarMultiplication(Scanner scanner) {
        Matrix matrix = new Matrix();
        matrix.setMatrixFromInput(scanner);

        double scalar = Double.parseDouble(scanner.next());

        Matrix result = Matrix.scalarMultiplication(scalar, matrix);

        result.outputMatrix();
    }

    static void vectorMultiplication(Scanner scanner) {
        Matrix first = new Matrix();
        Matrix second = new Matrix();

        first.setMatrixFromInput(scanner);
        second.setMatrixFromInput(scanner);

        Matrix result = Matrix.vectorMultiplication(first, second);

        result.outputMatrix();
    }

    static void promptForTransposingOption(Scanner scanner) {
        System.out.println("Your choice:");

        int choice = Integer.parseInt(scanner.next());
        String choiceDecoded = transpositionOptions[choice - 1];

        Matrix matrix = new Matrix();
        matrix.setMatrixFromInput(scanner);

        transposition(choiceDecoded, matrix);

        matrix.outputMatrix();
    }

    static void transposition(String choiceDecoded, Matrix matrix) {
        switch (choiceDecoded) {
            case "Main diagonal":
                Matrix.transposeByMain(matrix);
                break;
            case "Side diagonal":
                Matrix.transposeBySide(matrix);
                break;
            case "Vertical line":
                Matrix.transposeByVertical(matrix);
                break;
            case "Horizontal line":
                Matrix.transposeByHorizontal(matrix);
                break;
            default:
                break;
        }
    }

    static void findDeterminantOfAMatrix(Scanner scanner) {
        Matrix matrix = new Matrix();
        matrix.setMatrixFromInput(scanner);

        System.out.println(Matrix.findDeterminant(matrix));
    }

    static void findInverse(Scanner scanner) {
        Matrix matrix = new Matrix();

        matrix.setMatrixFromInput(scanner);
        matrix = Matrix.findInverseMatrix(matrix);

        matrix.outputMatrixWithPrecision();
    }
}
