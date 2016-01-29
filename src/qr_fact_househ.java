import java.io.File;

public class qr_fact_househ extends MatrixOperations{

    public static double[][] Pascal;
    public static double[][] Q;
    public static double[][] R;


    public static void main(String[] args) throws Exception {
        MatrixInput input = new MatrixInput();
        System.out.println("To enter matrix through terminal, press 1");
        System.out.println("To enter matrix through a file, press 2");
        Matrix matrix1;
        String inputTypeLine = input.nextLine();
        int inputType = Integer.parseInt(inputTypeLine);
        if (inputType == 1) {
            System.out.println("Please enter a matrix:");
            System.out.println("Enter empty line when you finish");
            matrix1 = input.readMatrix();
        } else {
            System.out.println("Enter file path:");
            String path = input.nextLine();
            File file = new File(path);
            MatrixInput fileInput = new MatrixInput(file);
            matrix1 = fileInput.readMatrixFromFile();
        }
        qr_fact_househ.setMatrix(matrix1);
        Matrix q = qr_fact_househ.getQ();
        Matrix r = qr_fact_househ.getR();
        System.out.println("Q = \t\n" + q.toString());
        System.out.println("R = \t\n" + r.toString());
        double error = MatrixOperations.maxNorm(
                MatrixOperations.subtract(MatrixOperations.multiply(q, r), matrix1));
        System.out.println("Error ||QR - A|| = " + error);
    }

    public static void setMatrix(Matrix matrix1)  {
        Pascal = matrix1.getMatrix();
        reflection(matrix1.getNumCols());
    }

    public static void reflection(int size) {
        Q = new double[size][size];
        R = new double[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == j){
                    Q[i][j] = 1;
                } else {
                    Q[i][j] = 0;
                }
            }
        }

        double[][] I = new double[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == j){
                    I[i][j] = 1;
                } else {
                    I[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            double[] X = new double[size];
            for (int j = 0; j < size; j++) {
                X[j] = Pascal[j][i];
                if (j < i) {
                    X[j] = 0;
                }
            }

            Vector vX = new Vector(X);

            double[] C = new double[size];
            for (int c = 0; c < size; c++) {
                if (c == i) {
                    C[i] = vectorMagnitude(vX);
                } else {
                    C[c] = 0;
                }
            }
            Vector vC = new Vector(C);

            if (i != size - 1) {
                double[][] U = new double[size][1];
                double[] U1 = vectorSubtract(vX, vC).getVector();

                for (int k = 0; k < size; k++) {
                    U[k][0] = U1[k];
                }

                double[][] UT = transpose(new Matrix(U)).getMatrix();
                double[][] UUT = multiply(new Matrix(U),
                        new Matrix(UT)).getMatrix();
                double div = 2 / (vectorMagnitude(new Vector(U1))
                        * vectorMagnitude(new Vector(U1)));
                double[][] TwoUUT = scalarMultiply(div,
                        new Matrix(UUT)).getMatrix();
                double[][] Ha = subtract(new Matrix(I),
                        new Matrix(TwoUUT)).getMatrix();


                for (int row = 0; row < size; row++) {
                    for (int column = 0; column < size; column++) {
                        if (row == column && row <= (i - 1)) {
                            Ha[row][column] = 1;
                        }
                    }
                }

                R = multiply(new Matrix(Ha), new Matrix(Pascal)).getMatrix();
                for (int m = 0; m < size; m++) {
                    for (int n = 0; n < size; n++) {
                        Pascal[m][n] = R[m][n];
                    }
                }
                Q = multiply(new Matrix(Q), new Matrix(Ha)).getMatrix();
            }
        }
    }

    public static Matrix getQ()  {
        return new Matrix(Q);
    }

    public static Matrix getR()  {
        return new Matrix(R);
    }
}

