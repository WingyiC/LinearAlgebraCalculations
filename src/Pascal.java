public class Pascal extends MatrixOperations{

    public static void main(String[] args) {
        System.out.println("-----Using LU factorization to Solve ||Px - b||, solve for x-----");
        for (int i = 2; i <= 12; i++) {
            Vector x = solve_lu_b.solveSystem(pascal(i), pascalVector(i));
            System.out.println();
            System.out.println("::::::::::n = " + i + " Pascal Matrix");
            Matrix p = pascal(i);
            System.out.println(p.toString());
            System.out.println("Pascal Vector: ");
            Vector pv = pascalVector(i);
            System.out.println(pv.toString());
            System.out.println("Xsol:");
            System.out.println(x.toString());

            double error = MatrixOperations.maxNorm(
                    MatrixOperations.subtract(MatrixOperations.multiply(lu_fact.getLower(pascal(i),0), lu_fact.getUpper(pascal(i),0))
                            , pascal(i)));
            System.out.println("Error ||LU - P|| = " + error);

            double error2 = MatrixOperations.vectorMaxNorm(
                    MatrixOperations.vectorSubtract(MatrixOperations.multMatrixVector(pascal(i), x), pascalVector(i)));
            System.out.println("Error ||Px - b|| = " + error2);

        }
        System.out.println();
        System.out.println("-----Using QR factorization to Solve ||Px - b||, solve for x-----");
        for (int i = 2; i <= 12; i++) {
            Vector x = solve_qr_b.solveG(pascal(i), pascalVector(i));
            System.out.println();
            System.out.println("::::::::::n = " + i + " Pascal Matrix");
            Matrix p = pascal(i);
            System.out.println(p.toString());
            System.out.println("Pascal Vector: ");
            Vector pv = pascalVector(i);
            System.out.println(pv.toString());
            System.out.println("Xsol:");
            System.out.println(x.toString());

            double error = MatrixOperations.maxNorm(
                    MatrixOperations.subtract(MatrixOperations.multiply(qr_fact_givens.getQ(), qr_fact_givens.getR()), pascal(i)));
            System.out.println("Error ||QR - P|| = " + error);

            double error2 = MatrixOperations.vectorMaxNorm(
                    MatrixOperations.vectorSubtract(MatrixOperations.multMatrixVector(pascal(i), x), pascalVector(i)));
            System.out.println("Error ||Px - b|| = " + error2);
        }
    }

    public static Matrix pascal(int n) {
        double[][] pascal = new double[n][n];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                pascal[j - 1][i - 1] = pascalNum(j, i);
            }
        }
        Matrix p = new Matrix(pascal);
        return p;
    }

    public static int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return n * factorial(n-1);
    }

    public static int pascalNum(int j, int i) {
        return factorial((j - 1) + (i - 1)) / (factorial(j - 1) * factorial(i - 1));
    }

    public static Vector pascalVector(int n) {
        double[] pascalVector = new double[n];
        for (int i = 1; i <= n; i++) {
            pascalVector[i - 1] = 1.0/i;
        }
        Vector b = new Vector(pascalVector);
        return b;
    }


}