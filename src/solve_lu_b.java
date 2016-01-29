import java.io.File;

public class solve_lu_b extends lu_fact{
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
            System.out.println("Enter Vector b:");
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
        Vector x = solve_lu_b.solveSystem(a, b);
        System.out.println("Vector x = \n" + x.toString());
        double error = MatrixOperations.maxNorm(
                MatrixOperations.subtract(MatrixOperations.multiply(lu_fact.getLower(a,0), lu_fact.getUpper(a,0))
                        , a));
        System.out.println("Error ||LU - P|| = " + error);

        double error2 = MatrixOperations.vectorMaxNorm(
                MatrixOperations.vectorSubtract(MatrixOperations.multMatrixVector(a, x), b));
        System.out.println("Error ||Px - b|| = " + error2);

    }

    public static Vector solveLYB(Matrix l , Vector b) {
        double[] y = new double[b.getSize()];
        for(int z = 0; z < b.getSize(); z++) {
            y[z] = b.get(z);
        }
        for(int a = 0; a < l.getNumCols(); a++) {
            for(int c = 0; c < l.getNumRows(); c++) {
                double pivot = l.get(a,a);
                if(a < c) {
                    double factor = -1*(l.get(c,a)/pivot);
                    y[c] += factor*y[a];
                }
            }
        }
        Vector VecY = new Vector(y);
        return VecY;
    }

    public static Vector solveUXY(Matrix u, Vector y) {
        double[] x = new double[y.getSize()];
        for(int z = 0; z < y.getSize(); z++) {
            x[z] = y.get(z);
        }
        for (int i = u.getNumCols() - 1; i >= 0; i--) {
            double t = 0.0;
            for (int j = i + 1; j < u.getNumRows(); j++) {
                t = t + (u.get(i,j) * x[j]);
            }
            x[i] = (y.get(i) - t) / u.get(i,i);
        }
        Vector VecX = new Vector(x);
        return VecX;
    }

    public static Vector solveSystem(Matrix a, Vector b) {
        Matrix l = getLower(a , 0);
        Matrix u = getUpper(a , 0);
        Vector y = solveLYB(l,b);
        return solveUXY(u,y);
    }

}
