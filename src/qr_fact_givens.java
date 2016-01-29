import java.io.File;

public class qr_fact_givens extends MatrixOperations {

    public static double[][] Gn;
    public static double[][] An;
    public static double[][] Q;

    public static void main(String[] args) throws Exception {
        MatrixInput input = new MatrixInput();
        System.out.println("To enter matrix through terminal, press 1");
        System.out.println("To enter matrix through a file, press 2");
        Matrix m1;
        String inputTypeLine = input.nextLine();
        int inputType = Integer.parseInt(inputTypeLine);
        if (inputType == 1) {
            System.out.println("Please enter a matrix:");
            System.out.println("Enter empty line when you finish.");
            m1 = input.readMatrix();
        } else {
            System.out.println("Enter file path.");
            String path = input.nextLine();
            File file = new File(path);
            MatrixInput fileInput = new MatrixInput(file);
            m1 = fileInput.readMatrixFromFile();
        }
        qr_fact_givens.setMatrix(m1);
        Matrix q = qr_fact_givens.getQ();
        Matrix r = qr_fact_givens.getR();
        System.out.println("Q = \t\n" + q.toString());
        System.out.println("R = \t\n" + r.toString());
        double error = MatrixOperations.maxNorm(
                MatrixOperations.subtract(MatrixOperations.multiply(q, r)
                        , m1));
        System.out.println("Error ||QR - A|| = " + error);

    }

    public static void setMatrix(Matrix m1)  {
        An = m1.getMatrix();
        rotation();
    }

    public static Matrix getR()  {

        return new Matrix(An);
    }

    public static void rotation()  {
        Gn = new double[An.length][An[0].length];
        Q = new double[An.length][An[0].length];

        int size = An.length;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == j){
                    Gn[i][j] = 1;
                    Q[i][j] = 1;
                } else{
                    Gn[i][j] = 0;
                    Q[i][j] = 0;
                }
            }
        }
        double a;
        double b;
        double cosX;
        double sinX;

        for (int i = 0; i < size; i++) {
            for (int j = (size - 1); j > i; j--) {
                a = An[j - 1][i];
                b = An[j][i];
                cosX = a / (Math.sqrt(a*a+b*b));
                sinX = - b / (Math.sqrt(a*a+b*b));

                Gn[j][j] = cosX;
                Gn[j][j - 1] = sinX;
                Gn[j - 1][j] = -sinX;
                Gn[j - 1][j - 1] = cosX;

                Matrix an = new Matrix(An);
                Matrix gn = new Matrix(Gn);
                an = multiply(gn, an);
                An = an.getMatrix();

                Matrix q = new Matrix(Q);
                q = multiply(gn, q);
                Q = q.getMatrix();

                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (k == l) {
                            Gn[k][l] = 1;
                        } else {
                            Gn[k][l] = 0;
                        }
                    }
                }
            }
        }
    }

    public static Matrix getQ()  {

        return MatrixOperations.transpose(new Matrix(Q));
    }

}
