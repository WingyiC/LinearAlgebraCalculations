import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 * This should contain any of the methods we need to write
 */
public class LinearAlgebra {

    public static void main(String[] args) throws java.io.IOException{
    // TODO: Pascal

    // TODO: Iterative methods
        System.out.println("\nPart B: Iterative Methods");

        double[] x0 = new double[3];
        x0[0] = 1;
        x0[1] = 2;
        x0[2] = 4;
        //Vector X0 = new Vector(x0);

        generateRandomVector();


        //gs_iter(gs, b, 0.0005, 400,X0);
        //jacobi_iter(gs, b, 0.0005, 400,X0);
        //System.out.println(MatrixOperations.cubedInverse(gs).toString());

    // TODO: Power Method
        System.out.println("\nPart C: Power Method");
        power_method_input();
        System.out.println("Generating random matrices...");
        System.out.println("Done. Files generated in root directory.");
        generate_random_matrices();
    }

    private static void power_method_input() throws java.io.IOException {
        Scanner in = new Scanner(System.in);
        MatrixInput input = new MatrixInput();
        //Inputs
        System.out.print("Input matrix A filename: ");
        FileParser aFile = new FileParser(in.nextLine());
        Matrix a = aFile.getMatrix();
        System.out.print("Input error tolerance: ");
        Double tol;
        tol = in.nextDouble();
        in.nextLine();
        System.out.print("Input initial approximation vector filename: ");
        FileParser uoFile = new FileParser(in.nextLine());
        Vector uo = uoFile.getVector();
        System.out.print("Input maximum number of iterations: ");
        int maxIterations = in.nextInt();
        in.nextLine();
        power_method(a, tol, uo, maxIterations);
    }

    private static void power_method(Matrix a, Double tol, Vector uo, int maxIterations) {
        //Calculations
        Double eigenvalue = 0.0;
        Double oldValue = tol - 1;
        Vector eigenvector = uo;
        int numIterations = 0;

        while (Math.abs(eigenvector.get(0) - oldValue) > tol && numIterations < maxIterations) {
            oldValue = eigenvector.get(0);
            eigenvector = MatrixOperations.multMatrixVector(a, eigenvector);
            eigenvector = eigenvector.scale(1 / eigenvector.get(1));
            eigenvalue = eigenvector.get(0);
            numIterations++;
        }

        //Output
        if (numIterations < maxIterations) {
            System.out.print("Approximated eigenvalue: " + eigenvalue + "\n");
            System.out.print("Approximated eigenvector: \n" + eigenvector.toString() + "\n");
            System.out.println("Number of iterations: " + numIterations);
        } else {
            System.out.println("Method does not converge after " + maxIterations + " iterations");
        }
    }

    private static double[] power_method_eigenvalue(Matrix a, Double tol, Vector uo, int maxIterations) {
        //Calculations
        Double eigenvalue = 0.0;
        Double oldValue = tol - 1;
        Vector eigenvector = uo;
        int numIterations = 0;

        while (Math.abs(eigenvector.get(0) - oldValue) > tol && numIterations < maxIterations) {
            oldValue = eigenvector.get(0);
            eigenvector = MatrixOperations.multMatrixVector(a, eigenvector);
            eigenvector = eigenvector.scale(1 / eigenvector.get(1));
            eigenvalue = eigenvector.get(0);
            numIterations++;
        }

        //Output
        double trace = a.get(0, 0) + a.get(1, 1);
        double det = a.getTwoDeterminant();
        double [] output = {trace, det, numIterations};
        return output;
    }

    private static void generate_random_matrices() throws FileNotFoundException{
        Matrix m;
        Matrix inv;
        double[] start = {1, 1};
        Vector v = new Vector(start);
        int count = 0;
        double [][] matrix = new double[2][2];
        PrintWriter writer = new PrintWriter("eigenvalues.txt");
        PrintWriter invwriter = new PrintWriter("inverses.txt");
        while(count < 1000) {
            matrix[0][0] = Math.random() * 4 - 2;
            matrix[0][1] = Math.random() * 4 - 2;
            matrix[1][0] = Math.random() * 4 - 2;
            matrix[1][1] = Math.random() * 4 - 2;
            if ((matrix[0][0] * matrix[1][1]) != (matrix[1][0] * matrix[0][1])) {
                m = new Matrix(matrix);
                inv = MatrixOperations.squareInverse(m);
                count++;
                double [] out = power_method_eigenvalue(m, .00005, v, 100);
                double [] invout = power_method_eigenvalue(inv, .00005, v, 100);
                writer.print(out[0] + "\t" + out[1] + "\t" + out[2] + "\n");
                invwriter.print(invout[0] + "\t" + invout[1] + "\t" + out[2] + "\n");
            }
        }
        writer.close();
        invwriter.close();
    }

    public static void generateRandomVector()throws FileNotFoundException{
        double[] arr = new double[3];


        double[][] arr2 = new double[3][3];
        arr2[0][0] = 1.000;
        arr2[0][1] = 0.500;
        arr2[0][2] = 0.33333333333333333333;
        arr2[1][0] = 0.500;
        arr2[1][1] = 1.000;
        arr2[1][2] = 0.250;
        arr2[2][0] = 0.33333333333333333333;
        arr2[2][1] = 0.250;
        arr2[2][2] = 1.000;
        double[] b = new double[3];
        b[0] = 0.100;
        b[1] = 0.100;
        b[2] = 0.100;
        Matrix gs = new Matrix(arr2);
        PrintWriter itWriter = new PrintWriter("iterative.txt");
        for(int i=0; i<100; i++) {
            arr[0] = Math.random() - Math.random();
            arr[1] = Math.random() - Math.random();
            arr[2] = Math.random() - Math.random();

            System.out.println("INITIAL VECTOR");
            Vector X0 = new Vector(arr);
//            System.out.println(X0.toString());
//            System.out.println();
            System.out.println("GAUSS SIEDEL");
            gs_iter(gs, b, .00005, 100, X0);
//            System.out.println();
//            System.out.println();
            System.out.println("JACOBI");
            jacobi_iter(gs, b, .00005, 100, X0);
            itWriter.print(arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\n");

        }
        itWriter.close();

    }

    private static void gs_iter(Matrix matrix, double[] b, double errAl, int maxIt , Vector X0) {
        int it = 0; //iteration
        double err; // max error
        int i;      // indexing variable
        int j;      // indexing variable
        double t;    // value holder
        double s;    // value holder
        double[][] u = new double[3][3];
        double[][] l = new double[3][3];
        double[][] d = new double[3][3];
        double[] a = new double[matrix.getNumRows()];
        a[0] = 0;
        a[1] = 0;
        a[2] = 0;
        Vector X = new Vector(a);

        Vector B = new Vector(b);

        for (it = 1; it <= maxIt; it++) {
            err = 0;
            for (i = 0; i < matrix.getNumRows(); i++) {
                s = 0;

                for (j = 0; j < matrix.getNumCols(); j++) {

                    //if(j!= i)
                    //  s +=  matrix.get(i,j)*a[j];
                    if (i == j) {
                        d[i][j] = matrix.get(i, j);
                        u[i][j] = 0;
                        l[i][j] = 0;
                    }
                    if (j > i) {
                        u[i][j] = matrix.get(i, j);
                        l[i][j] = 0;
                        d[i][j] = 0;
                    }
                    if (i > j) {
                        l[i][j] = matrix.get(i, j);
                        u[i][j] = 0;
                        d[i][j] = 0;
                    }
                }

            }
            Matrix D = new Matrix(d);
            Matrix L = new Matrix(l);
            Matrix U = new Matrix(u);
            System.out.println("Vector");


            Matrix DL = MatrixOperations.cubedInverse(MatrixOperations.subtract(D, L)); // (D-L)^-1

            Matrix U2 = MatrixOperations.scalarMultiply(-1, U);
            //X = MatrixOperations.scalarMultiply(-1,U);
            X = MatrixOperations.multMatrixVector(DL, MatrixOperations.vectorAdd(MatrixOperations.multMatrixVector(U2, X0), B));

            //X = MatrixOperations.vectorAdd(DL,C);
            System.out.println(X.toString());

            System.out.println("ITERATION " + it);

            err = Math.abs((X.get(0)-X0.get(0))/X0.get(0)) + Math.abs((X.get(1)-X0.get(1))/X0.get(1))+Math.abs((X.get(2)-X0.get(2))/X0.get(2));
            X0=X;

            if(err<errAl){
                System.out.println("current error: " + err+"   wanted error: " +  errAl);
                System.out.println("DONE");
                return;
            }
                System.out.println("current error: " + err+"   wanted error: " +  errAl);
        }

        System.out.println();
        System.out.println();

    }


    private static void jacobi_iter(Matrix matrix, double[] b, double errAl, int maxIt, Vector X0) {
        int it = 0; //iteration
        double err; // max error
        int i;      // indexing variable
        int j;      // indexing variable
        double t;    // value holder
        double s;    // value holder
        double[][] u = new double[3][3];
        double[][] l = new double[3][3];
        double[][] d = new double[3][3];
        double[] a = new double[matrix.getNumRows()];
        a[0] = 0;
        a[1] = 0;
        a[2] = 0;
        Vector X = new Vector(a);

        Vector B = new Vector(b);

        for (it = 1; it <= maxIt; it++) {
            err = 0;
            for (i = 0; i < matrix.getNumRows(); i++) {
                s = 0;

                for (j = 0; j < matrix.getNumCols(); j++) {

                    //if(j!= i)
                    //  s +=  matrix.get(i,j)*a[j];
                    if (i == j) {
                        d[i][j] = matrix.get(i, j);
                        u[i][j] = 0;
                        l[i][j] = 0;
                    }
                    if (j > i) {
                        u[i][j] = matrix.get(i, j);
                        l[i][j] = 0;
                        d[i][j] = 0;
                    }
                    if (i > j) {
                        l[i][j] = matrix.get(i, j);
                        u[i][j] = 0;
                        d[i][j] = 0;
                    }
                }

            }
            Matrix D = new Matrix(d);
            Matrix L = new Matrix(l);
            Matrix U = new Matrix(u);

            System.out.println("Vector");


            Matrix D2 = MatrixOperations.cubedInverse(D);
            Matrix LU = MatrixOperations.subtract(L,U);
            Vector DB = MatrixOperations.multMatrixVector(D2,B);
            X = MatrixOperations.vectorAdd(MatrixOperations.multMatrixVector(MatrixOperations.multiply(D2,LU),X0),DB);

            System.out.println(X.toString());

            System.out.println("ITERATION " + it);

            err = Math.abs((X.get(0)-X0.get(0))/X0.get(0)) + Math.abs((X.get(1)-X0.get(1))/X0.get(1))+Math.abs((X.get(2)-X0.get(2))/X0.get(2));

            X0 = X;
            if(err<errAl){
                System.out.println("current error: " + err+"   wanted error: " +  errAl);
                System.out.println("DONE");
                return;
            }
            System.out.println("current error: " + err+"   wanted error: " +  errAl);
        }

        System.out.println();
        System.out.println();
    }
}
