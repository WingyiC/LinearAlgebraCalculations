import java.io.File;

public class lu_fact extends MatrixOperations{

    public static void main(String[] args) throws Exception{
        MatrixInput input = new MatrixInput();
        System.out.println("To enter matrix through terminal, press 1");
        System.out.println("To enter matrix through a file, press 2");
        Matrix matrix;
        String inputTypeLine = input.nextLine();
        int inputType = Integer.parseInt(inputTypeLine);
        if (inputType == 1) {
            System.out.println("Please enter a matrix:");
            System.out.println("Enter empty line when you finish.");
            matrix = input.readMatrix();
        } else {
            System.out.println("Enter file path.");
            String path = input.nextLine();
            File file = new File(path);
            MatrixInput fileInput = new MatrixInput(file);
            matrix = fileInput.readMatrixFromFile();
        }
        System.out.println("L = \t\n" + lu_fact.getLower(matrix, 0).toString());
        System.out.println("U = \t\n" + lu_fact.getUpper(matrix, 0).toString());
        double error = MatrixOperations.maxNorm(
                MatrixOperations.subtract(MatrixOperations.multiply(lu_fact.getLower(matrix, 0),
                        lu_fact.getUpper(matrix, 0)), matrix));
        System.out.println("Error ||LU - A|| = " + error);
        System.out.println();

    }

    public static Matrix getMult(Matrix matrix, int t) {
        if(t >= matrix.getNumRows()) {
            return matrix;
        }
        double[][] lower = new double[matrix.getNumCols()][matrix.getNumRows()];
        for(int i = 0; i < matrix.getNumCols(); i++) {
            for(int j = 0; j < matrix.getNumRows(); j++) {
                double pivot = matrix.get(t,t);
                if (pivot == 0) {
                    return getIdentity(matrix);
                } else if (i == j) {
                    lower[j][i] = 1;
                } else if (j > i && i == t) {
                    lower[j][i] = (-1*(matrix.get(j,i)/pivot));
                } else
                    lower[j][i] = 0;
            }
        }
        Matrix newMatrix = new Matrix(lower);
        return newMatrix;
    }

    public static Matrix getIdentity(Matrix matrix) {
        int size = matrix.getNumRows();
        double[][] ident = new double[size][size];
        for(int p = 0; p < size; p++) {
            for(int y = 0; y < size; y++) {
                if(p ==y) {
                    ident[p][y] = 1;
                }
                else {
                    ident[p][y] = 0;
                }
            }
        }
        Matrix identity = new Matrix(ident);
        return identity;
    }

    public static Matrix getUpper(Matrix matrix , int times) {
        Matrix next = multiply(getMult(matrix, times), matrix);
            while(times != matrix.getNumRows() - 2) {
                times++;
                next = multiply(getMult(next , times),next);
            }
            return next;
    }

    public static Matrix getLower(Matrix matrix , int times) {
            Matrix next = multiply(getMult(matrix, times), matrix);
        Matrix add = getMult(matrix, times);
        while(times != matrix.getNumRows() - 2) {
                times++;
                add = add(add , getMult(next , times));
                next = multiply(getMult(next , times),next);
            }
            double[][] finalL = new double[matrix.getNumCols()][matrix.getNumRows()];
            for(int i = 0; i < matrix.getNumCols(); i++) {
                for(int j = 0; j < matrix.getNumCols(); j++){
                    if(i == j) {
                        finalL[i][j] = add.get(i,j) / (matrix.getNumRows() - 1);
                    }
                    else {
                        if (add.get(i,j) != 0)
                            finalL[i][j] = -1*add.get(i,j);
                        else
                            finalL[i][j] = 0;
                    }
                }
            }
            Matrix lowerMatrix = new Matrix(finalL);
            return lowerMatrix;
    }
}
