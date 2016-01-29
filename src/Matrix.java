import java.util.Deque;
import java.util.ArrayDeque;

// Matrix.java
// Note: Matrices entered in row major order
// e.g.: {{1,2,3},{4,5,6}} = [1,2,3]
//                           [4,5,6]
// Version 1.0

public class Matrix {

    private double[][] matrix;
    private int numRows;
    private int numCols;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        numRows = matrix.length;
        numCols = matrix[0].length;
    }

    /**
     * Returns the determinant of the matrix
     * @return the determinant
     */
    public double getDeterminant() {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Cannot find determinant of nonsquare matrix");
        }
        return determinantRecurse(matrix);
    }

    /**
     * Helper recursive method for getDeterminant
     * @param matrix being expanded
     * @return the determinant
     */
    public double determinantRecurse(double[][] matrix) {
        int determinant = 0;
        int sign = 1;

        if (numRows == 1) {     //Base case
            return matrix[0][0];
        }
        for (int c = 0; c < numRows; c++) {
            double[][] array = new double[numRows - 1][numCols - 1];    //Cofactor expansion
            for (int a = 1; a < numRows; a++) {
                for (int b = 0; b < numCols; b++) {
                    if (b < c) {
                        array[a - 1][b] = matrix[a][b];
                    } else if (b > c) {
                        array[a - 1][b - 1] = matrix[a][b];
                    }
                }
            }
            if (c % 2 != 0) {   //Flip signs
                sign = -1;
            }
            determinant += sign * matrix[0][c] * determinantRecurse(array);
        }
        return determinant;
    }

    public double getTwoDeterminant() {
        return 1 / (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]);
    }

    /**
     * Returns the transpose of the matrix
     * @return The transpose of the matrix
     */
    public Matrix getTranspose() {
        double[][] transpose = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return new Matrix(transpose);
    }

    /**
     * Returns the element at location (i, j)
     * @param i the row of the element
     * @param j the column of the element
     * @return the element at that location in the matrix
     */
    public double get(int i, int j) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) {
            throw new IndexOutOfBoundsException("i:" + i + " j:" + j);
        }
        return matrix[i][j];
    }

    /**
     * Multiplies the matrix by a scalar. Does not change the values of this
     * Matrix instance, instead returns a new Matrix.
     * @param c1 the scalar
     * @return the new matrix scaled
     */
    public Matrix scale(double c1) {
        double[][] scaled = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                scaled[i][j] = matrix[i][j] * c1;
            }
        }
        return new Matrix(scaled);
    }

    /**
     * Returns the dimensions of the array
     * @return int array of the dimensions [rows, cols]
     */
    public int[] getSize() {
        return new int[]{ numRows, numCols};
    }

    public static Matrix genElementarySwapMatrix(int row1, int row2, int size) {
        double[][] swapMatrix = new double[size][size];
        for (int i = 0; i < swapMatrix.length; i++) {
            for (int j = 0; j < swapMatrix[0].length; j++) {
                if ((i == row1 && j == row2) || (j == row1 && i == row2)) {
                    swapMatrix[i][j] = 1;
                } else {
                    swapMatrix[i][j] = 0;
                }
            }
        }
        return new Matrix(swapMatrix);
    }

    /**
     * Generates a square identity matrix of the given size
     * @param size of the array
     * @return the identity matrix
     */
    public static Matrix genIdentityMatrix(int size) {
        double[][] identity = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identity[i][j] = 1;
                } else {
                    identity[i][j] = 0;
                }
            }
        }
        return new Matrix(identity);
    }

    public String toString() {
        String out = "";
        for (double[] row : matrix) {
            out += "[ ";
            for (double elem : row) {
                out += " " + String.format("%9f", elem) + " ";
            }
            out +="]\n";
        }
        return out;
    }

    public String toStringFull() {
        String out = "";
        for (double[] row : matrix) {
            out += "[ ";
            for (double elem : row) {
                String num = "" + elem;
                out += " " + String.format("%21s", num) + " ";
            }
            out +="]\n";
        }
        return out;
    }

    public double getMaxNorm() {
        double max = Math.abs(matrix[0][0]);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (max < Math.abs(matrix[i][j])) {
                    max = Math.abs(matrix[i][j]);
                }
            }
        }
        return max;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }
}
