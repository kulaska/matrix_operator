package processor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Matrix {
    private double[][] matrix ;
    private int rows;
    private int columns;

    public void setMatrixFromInput(Scanner scanner) {
        int rowsLength = Integer.parseInt(scanner.next());
        int columnsLength = Integer.parseInt(scanner.next());

        matrix = new double[rowsLength][columnsLength];

        for (int i = 0; i < rowsLength; i++) {
            for (int j = 0; j < columnsLength; j++) {
                matrix[i][j] = Double.parseDouble(scanner.next());
            }
        }

        rows = rowsLength;
        columns = columnsLength;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrixToSet) {
        int numberOfRows = matrixToSet.length;
        int numberOfElementsInColumn = matrixToSet[0].length;

        for (int i = 0; i < matrixToSet.length; i++) {
            if (matrixToSet[i].length != numberOfElementsInColumn) {
                throw new Error("Incorrect matrix(column lengths)");
            }
        }
        rows = numberOfRows;
        columns = numberOfElementsInColumn;
        matrix = matrixToSet;
    }

    public void outputMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    public void outputMatrixWithPrecision() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(roundDown(matrix[i][j]) + " ");
            }

            System.out.println();
        }
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfColumns() {
        return columns;
    }

    public static Matrix scalarMultiplication(double scalar, Matrix matrixToMultiply) {
        int rows = matrixToMultiply.getNumberOfRows();
        int columns = matrixToMultiply.getNumberOfColumns();

        Matrix result = new Matrix();

        double[][] originalMatrix = matrixToMultiply.getMatrix();
        double[][] matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = originalMatrix[i][j] * scalar;
            }
        }

        result.setMatrix(matrix);

        return result;
    }

    public static Matrix addMatrices(Matrix addendumFirst, Matrix addendumSecond) {
        if (addendumFirst.getNumberOfRows() != addendumSecond.getNumberOfRows() ||
                addendumFirst.getNumberOfColumns() != addendumSecond.getNumberOfColumns()) {
            System.out.println("ERROR");
            throw new Error();
        }

        double[][] first = addendumFirst.getMatrix();
        double[][] second = addendumSecond.getMatrix();

        int numberOfRows = addendumFirst.getNumberOfRows();
        int numberOfColumns = addendumFirst.getNumberOfColumns();

        Matrix sum = new Matrix();

        double[][] finalMatrix = new double[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                finalMatrix[i][j] = first[i][j] + second[i][j];
            }
        }

        sum.setMatrix(finalMatrix);

        return sum;
    }

    public static Matrix vectorMultiplication(Matrix first, Matrix second) {
        if (first.getNumberOfColumns() != second.getNumberOfRows()) {
            throw new Error("Vector multiplication for matrices with these dimensions is impossible");
        }

        Matrix result = new Matrix();

        double[][] resultMatrix, firstMatrix, secondMatrix;

        firstMatrix = first.getMatrix();
        secondMatrix = second.getMatrix();

        int resultMatrixRows = first.getNumberOfRows();
        int resultMatrixColumns = second.getNumberOfColumns();

        resultMatrix = new double[resultMatrixRows][resultMatrixColumns];

        for (int i = 0; i < resultMatrixRows; i++) {
            for (int j = 0; j < resultMatrixColumns; j++) {
                resultMatrix[i][j] = 0;
                for (int k = 0; k < first.getNumberOfColumns(); k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        result.setMatrix(resultMatrix);

        return result;
    }

    public static void transposeByMain(Matrix matrixToTranspose) {
        int rowNumber = matrixToTranspose.getNumberOfRows();
        int columnNumber = matrixToTranspose.getNumberOfColumns();

        double[][] newMatrix = new double[columnNumber][rowNumber];
        double[][] oldMatrix = matrixToTranspose.getMatrix();

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                newMatrix[i][j] = oldMatrix[j][i];
            }
        }

        matrixToTranspose.setMatrix(newMatrix);
    }

    public static void transposeBySide(Matrix matrixToTranspose) {
        int rowNumber = matrixToTranspose.getNumberOfRows();
        int columnNumber = matrixToTranspose.getNumberOfColumns();

        double[][] newMatrix = new double[columnNumber][rowNumber];
        double[][] oldMatrix = matrixToTranspose.getMatrix();

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber - i; j++) {
                newMatrix[i][j] = oldMatrix[rowNumber - j - 1][columnNumber - i - 1];
                newMatrix[columnNumber - j - 1][rowNumber - i - 1] = oldMatrix[i][j];
            }
        }

        matrixToTranspose.setMatrix(newMatrix);
    }

    public static void transposeByVertical(Matrix matrixToTranspose) {
        int rowNumber = matrixToTranspose.getNumberOfRows();
        int columnNumber = matrixToTranspose.getNumberOfColumns();

        double[][] newMatrix = new double[rowNumber][columnNumber];
        double[][] oldMatrix = matrixToTranspose.getMatrix();

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                newMatrix[i][j] = oldMatrix[i][columnNumber - j - 1];
            }
        }

        matrixToTranspose.setMatrix(newMatrix);
    }

    public static void transposeByHorizontal(Matrix matrixToTranspose) {
        int rowNumber = matrixToTranspose.getNumberOfRows();
        int columnNumber = matrixToTranspose.getNumberOfColumns();

        double[][] newMatrix = new double[rowNumber][columnNumber];
        double[][] oldMatrix = matrixToTranspose.getMatrix();

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                newMatrix[i][j] = oldMatrix[rowNumber - i - 1][j];
            }
        }

        matrixToTranspose.setMatrix(newMatrix);
    }

    private static boolean checkIfSquared(Matrix matrix) {
        int rowsNumber, columnsNumber;
        rowsNumber = matrix.getNumberOfRows();
        columnsNumber = matrix.getNumberOfColumns();

        return rowsNumber == columnsNumber;
    }

    private static double findDeterminantOfLargeMatrix(double[][] matrix) {
        if (matrix.length == 1) {
            return matrix[0][0];
        }

        if (matrix.length == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }

        double temporary[][];
        double result = 0;
        int sign = 1;

        for (int i = 0; i < matrix.length; i++) {
            temporary = new double[matrix.length - 1][matrix.length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * sign * findDeterminantOfLargeMatrix(temporary);
            sign = -sign;
        }
        return result;
    }

    private static double findCofactorElement(double[][] arr, int row, int column) {
        double temporary[][] = new double[arr.length - 1][arr.length - 1];

        for (int j = 0; j < arr.length; j++) {
            for (int k = 0; k < arr.length; k++) {
                if (j < row && k < column) {
                    temporary[j][k] = arr[j][k] * (Math.pow(-1, k + j));
                } else if (k > column && j < row) {
                    temporary[j][k - 1] = arr[j][k] * (Math.pow(-1, k + j));
                } else if (k > column && j > row) {
                    temporary[j-1][k - 1] = arr[j][k] * (Math.pow(-1, k + j));
                } else if (j > row && k < column) {
                    temporary[j - 1][k] = arr[j][k] * (Math.pow(-1, k + j));
                }
            }
        }

        return findDeterminantOfLargeMatrix(temporary);
    }

    private static String roundDown(double number) {
        DecimalFormat format = new DecimalFormat("#.##");

        format.setRoundingMode(RoundingMode.CEILING);

        return format.format(number);
    }

    private static Matrix findCofactorMatrix(Matrix matrix) {
        Matrix newMatrix = new Matrix();
        double[][] oldMatrix = matrix.getMatrix();
        double[][] newMatrixArray = new double[matrix.getNumberOfRows()][matrix.getNumberOfColumns()];

        for (int i = 0; i < oldMatrix.length; i++) {
            for (int j = 0; j < oldMatrix.length; j++) {
                newMatrixArray[i][j] = findCofactorElement(oldMatrix, i, j);
            }
        }

        newMatrix.setMatrix(newMatrixArray);

        transposeByMain(newMatrix);

        return newMatrix;
    }

    public static Matrix findInverseMatrix(Matrix matrix) {
        Matrix cofactor = findCofactorMatrix(matrix);

        double inverseDeterminant = 1.0 / findDeterminant(matrix);

        return scalarMultiplication(inverseDeterminant, cofactor);
    }

    public static double findDeterminant (Matrix matrix) {
        if (!checkIfSquared(matrix)) {
            throw new Error("Non-squared matrix passed to the method");
        }

        return findDeterminantOfLargeMatrix(matrix.getMatrix());
    }
}
