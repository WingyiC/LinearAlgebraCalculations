import java.io.File;

public class solve_qr_b extends MatrixOperations{

    public static void main(String[] args) throws Exception{
        MatrixInput input = new MatrixInput();
        System.out.println("To enter through terminal, press 1");
        System.out.println("To enter through a file, press 2");
        Matrix a;
        Vector b;
        String inputTypeLine = input.nextLine();
        int inputType = Integer.parseInt(inputTypeLine);
        if (inputType == 1) {
            System.out.println("Enter the matrix P:");
            System.out.println("Enter empty line to when you finish");
            a = input.readMatrix();
            System.out.println("Enter the  Vector b:");
            System.out.println("Separate vector components by " + "using a space.");
            b = input.readVector();
        } else {
            System.out.println("Enter file path.");
            System.out.println("File must contain augmented matrix");
            String path = input.nextLine();
            File file = new File(path);
            MatrixInput fileInput = new MatrixInput(file);
            AugmentedMatrix aug = fileInput.readAugmentedMatrixFromFile();
            a = aug.getMatrix();
            b = aug.getVector();
        }
        Vector x = solve_qr_b.solveG(a, b);
        System.out.println("Vector x = \n" + x.toString());
        double error = MatrixOperations.maxNorm(
                MatrixOperations.subtract(MatrixOperations.multiply(qr_fact_givens.getQ(), qr_fact_givens.getR()), a));
        System.out.println("Error ||QR - P|| = " + error);

        double error2 = MatrixOperations.vectorMaxNorm(
                MatrixOperations.vectorSubtract(MatrixOperations.multMatrixVector(a, x), b));
        System.out.println("Error ||Px - b|| = " + error2);

    }

    public static Matrix solveH(Matrix A, Matrix b) {
        int size = A.getNumRows();
        double[][] x = new double[1][size];
        return new Matrix(x);
    }

    public static Vector solveG(Matrix A, Vector b) {
        int size = A.getNumRows();
        double[] x = new double[size];

        qr_fact_givens.setMatrix(A);
        Matrix R = qr_fact_givens.getR();
        Matrix Q = qr_fact_givens.getQ();
        Matrix Qt = transpose(Q);
        Vector B = multMatrixVector(Qt, b);

        double[][] arrayR = R.getMatrix();
        double[] arrayB = B.getVector();

        for (int i = size - 1; i >= 0; i--) {
            double t = 0.0;
            for (int j = i + 1; j < size; j++) {
                t = t + (arrayR[i][j] * x[j]);
            }
            x[i] = (arrayB[i] - t) / arrayR[i][i];
        }
        return new Vector(x);
    }
}
