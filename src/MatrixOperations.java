public class MatrixOperations {

    /**
     *Add 2 matrices together
     *@param matrix1
     *@param  matrix2
     *@return Sum of two matrices
     */
    public static Matrix add(Matrix matrix1, Matrix matrix2) {
        if (!(matrix1.getNumCols() == matrix2.getNumCols())) {
            System.out.println("Cannot add a matrix with " + matrix1.getNumCols() + " columns and a matrix with "
                    + matrix2.getNumCols() + " columns.");
        } else if (!(matrix1.getNumRows() == matrix2.getNumRows())) {
            System.out.println("Cannot add a matrix with " + matrix1.getNumRows() + " rows and a matrix with "
                    + matrix2.getNumRows() + "rows");
        }
        double[][] sum = new double[matrix1.getNumRows()][matrix1.getNumCols()];
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix2.getNumCols(); j++) {
                sum[i][j] = matrix1.get(i, j) + matrix2.get(i, j);
            }
        }
        return new Matrix(sum);
    }

    /**
     *Subtract matrices
     *@param matrix1
     *@param  matrix2
     *@return the matrix result from subtraction
     */
    public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
        if (!(matrix1.getNumCols() == matrix2.getNumCols())) {
            System.out.println("Cannot subtract a matrix " + matrix1.getNumCols() + " with columns"
                    + " and a matrix with "
                    + matrix2.getNumCols() + " columns.");
        } else if (!(matrix1.getNumRows() == matrix2.getNumRows())) {
            System.out.println("Cannot subtract a matrix " + matrix1.getNumRows() + " rows and a matrix with "
                    + matrix2.getNumRows() + "rows.");
        }
        double[][] sub = new double[matrix1.getNumRows()][matrix1.getNumCols()];
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix2.getNumCols(); j++) {
                sub[i][j] = matrix1.get(i, j) - matrix2.get(i, j);
            }
        }
        return new Matrix(sub);
    }
/**
     * Adds two vectors
     * @param vector1 The first vector
     * @param vector2 The second vector
     * @return The sum of the two vectors
     */
    public static Vector vectorAdd(Vector vector1, Vector vector2) {
        if (!(vector1.getSize() == vector2.getSize())) {
            System.out.println("Cannot add a vector "
                    + "with length " + vector1.getSize()
                    + " and a vector with length " + vector2.getSize() + ".");
        }
        double[] subs = new double[vector1.getSize()];
        for (int i = 0; i < vector1.getSize(); i++) {
            subs[i] = vector1.get(i) + vector2.get(i);
        }
        return new Vector(subs);
    }
    /**
     *Multiply 2 matrices together
     *@param matrix1
     *@param  matrix2
     *@return product of two matrices
     */
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getNumCols() != matrix2.getNumRows()) {
            System.out.println("Cannot multiply a matrix with " + matrix1.getNumCols() + "columns and a matrix with "
                    + matrix2.getNumRows() + " rows.");
        }

        double[][] product = new double[matrix1.getNumRows()][matrix2.getNumCols()];
        for (int a = 0; a < matrix1.getNumRows(); a++) {
            for (int b = 0; b < matrix2.getNumCols(); b++) {
                product[a][b] = 0.0;
            }
        }
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix2.getNumCols(); j++) {
                for (int k = 0; k < matrix1.getNumCols(); k++) {
                    product[i][j] += matrix1.get(i, k) * matrix2.get(k, j);
                }
            }
        }
        return new Matrix(product);
    }

    /**
     * Multiplies a scalar and a matrix
     * @param scalar scalar
     * @param matrix1     matrix
     * @return product
     */
    public static Matrix scalarMultiply(double scalar, Matrix matrix1) {
        double[][] product = new double[matrix1.getNumRows()][matrix1.getNumCols()];
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix1.getNumCols(); j++) {
                product[i][j] = scalar * matrix1.get(i, j);
            }
        }
        return new Matrix(product);
    }


    public static Matrix squareInverse(Matrix m) {
        if (m.getNumCols() != m.getNumRows() || m.getNumCols() != 2) {
            System.out.println("Cannot compute the inverse of a non 2x2 matrix!");
        }
        double det = 1 / (m.get(0, 0) * m.get(1, 1) - m.get(0, 1) * m.get(1, 0));
        double [][] inv = new double[2][2];
        inv[0][0] = m.get(1, 1) * det;
        inv[1][1] = m.get(0, 0) * det;
        inv[0][1] = m.get(0, 1) * det * -1;
        inv[1][0] = m.get(1, 0) * det * -1;
        return new Matrix(inv);
    }
    public static Matrix cubedInverse(Matrix m){
        if (m.getNumCols() != m.getNumRows() || m.getNumCols() != 3) {
            System.out.println("Cannot compute the inverse of a non 3x3 matrix!");
        }
        double det =  m.get(0, 0) * (m.get(1, 1) * m.get(2, 2) - m.get(2, 1) * m.get(1, 2)) -
                m.get(0, 1) * (m.get(1, 0) * m.get(2, 2) - m.get(1, 2) * m.get(2, 0)) +
                m.get(0, 2) * (m.get(1, 0) * m.get(2, 1) - m.get(1, 1) * m.get(2, 0));
        double invertDet = 1.00/det;
        double [][] inv = new double[3][3];
        inv[0][0]=(m.get(1, 1) * m.get(2, 2) - m.get(2, 1) * m.get(1, 2)) * invertDet;
        inv[0][1]=(m.get(0, 2) * m.get(2, 1) - m.get(0, 1) * m.get(2, 2)) * invertDet;
        inv[0][2]=(m.get(0, 1) * m.get(1, 2) - m.get(0, 2) * m.get(1, 1)) * invertDet;

        inv[1][0]=(m.get(1, 2) * m.get(2, 0) - m.get(1, 0) * m.get(2, 2)) * invertDet;
        inv[1][1]=(m.get(0, 0) * m.get(2, 2) - m.get(0, 2) * m.get(2, 0)) * invertDet;
        inv[1][2]=(m.get(1, 0) * m.get(0, 2) - m.get(0, 0) * m.get(1, 2)) * invertDet;

        inv[2][0]=(m.get(1, 0) * m.get(2, 1) - m.get(2, 0) * m.get(1, 1)) * invertDet;
        inv[2][1]=(m.get(2, 0) * m.get(0, 1) - m.get(0, 0) * m.get(2, 1)) * invertDet;
        inv[2][2]=(m.get(0, 0) * m.get(1, 1) - m.get(1, 0) * m.get(0, 1)) * invertDet;

        return new Matrix(inv);



    }
    /**
     * magnitude of a vector
     * @param vector1 vector
     * @return magnitude of a vector
     */
    public static double vectorMagnitude(Vector vector1) {
        double magnitude = 0;
        for (int i = 0; i < vector1.getSize(); i++) {
            magnitude += Math.pow(vector1.get(i), 2);
        }
        magnitude = Math.sqrt(magnitude);
        return magnitude;
    }

    /**
     * Multiplies a matrix and a vector
     * @param matrix1
     * @param vector1
     * @return the resultant vector
     */
    public static Vector multMatrixVector(Matrix matrix1,Vector vector1) {
        if (matrix1.getSize()[1] != vector1.getSize()) {
            throw new IllegalArgumentException("Matrix and Vector must be of valid size");
        }

        double[] product = new double[matrix1.getSize()[0]];
        for (int i = 0; i < matrix1.getSize()[0]; i++) {
            for (int j = 0; j < matrix1.getSize()[1]; j++) {
                product[i] += matrix1.get(i,j) * vector1.get(j);
            }
        }
        return new Vector(product);

    }

    /**
     * Subtracts two vectors
     * @param vector1 The first vector
     * @param vector2 The second vector
     * @return The difference of the two vectors
     */
    public static Vector vectorSubtract(Vector vector1, Vector vector2) {
        if (!(vector1.getSize() == vector2.getSize())) {
            System.out.println("Cannot subtract a vector "
                    + "with length " + vector1.getSize()
                    + " and a vector with length " + vector2.getSize() + ".");
        }
        double[] subs = new double[vector1.getSize()];
        for (int i = 0; i < vector1.getSize(); i++) {
            subs[i] = vector1.get(i) - vector2.get(i);
        }
        return new Vector(subs);
    }

    /**
     * transpose of a matrix
     * @param matrix1 matrix
     * @return transpose of matrix
     */
    public static Matrix transpose(Matrix matrix1) {
        double[][] transpose = new double[matrix1.getNumCols()][matrix1.getNumRows()];
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix1.getNumCols(); j++) {
                transpose[j][i] = matrix1.get(i, j);
            }
        }
        return new Matrix(transpose);
    }

    public static double vectorMaxNorm(Vector v1) {
        double max = 0;
        for (int i = 0; i < v1.getSize(); i++) {
            if (Math.abs(v1.get(i)) > max) {
                max = v1.get(i);
            }
        }

        return Math.abs(max);
    }

    /**
     * Returns the max norm of a matrix
     * @param matrix1 matrix
     * @return max norm
     */
    public static double maxNorm(Matrix matrix1) {
        double max = 0;
        for (int i = 0; i < matrix1.getNumRows(); i++) {
            for (int j = 0; j < matrix1.getNumCols(); j++) {
                if (Math.abs(matrix1.get(i,j)) > max) {
                    max = matrix1.get(i,j);
                }
            }
        }
        return Math.abs(max);
    }
}
