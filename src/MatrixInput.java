import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class MatrixInput {
    private Scanner scanner;

    public MatrixInput() {
        this.scanner = new Scanner(System.in);
    }

    public MatrixInput (File f) throws FileNotFoundException {
        this.scanner = new Scanner(f);
    }

    public double nextDouble() {
        return scanner.nextDouble();
    }

    public int nextInt() {
        return scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }


    public Vector readVector() {
        String stringVector = scanner.nextLine();
        double[] vector = parseRow(stringVector);
        return new Vector(vector);
    }

    public Matrix readMatrix() {
        List<double[]> rowList = new ArrayList<double[]>();
        int width = -1;
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            double[] row = parseRow(line);
            if (width != row.length && width != -1) {
                throw new InputMismatchException("Row with " + row.length
                        + " elements cannot be applied to matrix with width "
                        + "of " + width + ".");
            }
            width = row.length;
            rowList.add(row);
        }
        return new Matrix(rowList.toArray(new double[rowList.size()][]));
    }

    public Matrix readMatrixFromFile() {
        List<double[]> rowList = new ArrayList<double[]>();
        int width = -1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            double[] row = parseRow(line);
            if (width != row.length && width != -1) {
                throw new InputMismatchException("Row with " + row.length
                        + " elements cannot be applied to matrix with width "
                        + "of " + width + ".");
            }
            width = row.length;
            rowList.add(row);
        }
        return new Matrix(rowList.toArray(new double[rowList.size()][]));
    }

    private double[] parseRow(String row) {
        String[] asArray = row.split(" ");
        double[] result = new double[asArray.length];
        for (int i = 0; i < asArray.length; i++) {
            try {
                result[i] = Double.parseDouble(asArray[i]);
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Could not parse \""
                        + asArray[i] + "\" as a double.");
            }
        }
        return result;
    }

    public AugmentedMatrix readAugmentedMatrixFromFile() {
        List<double[]> rowList = new ArrayList<double[]>();
        List<Double> vectorList = new ArrayList<Double>();
        AugmentedMatrix augmat = new AugmentedMatrix();
        int vectorIndex = 0;
        int width = -1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break; // We're done
            }
            double[][] row = parseAugmentedRow(line);
            if (width != row[0].length && width != -1) {
                throw new InputMismatchException("Row with " + row.length
                        + " elements cannot be applied to matrix with width "
                        + "of " + width + ".");
            }
            width = row[0].length;
            rowList.add(row[0]);
            vectorList.add(row[1][0]);
        }
        double[] vectorArray = new double[vectorList.size()];
        for (Double d : vectorList) {
            vectorArray[vectorIndex] = d;
            vectorIndex++;
        }
        augmat.setMatrix(new Matrix(rowList.toArray(new double[rowList.size()][])));
        augmat.setVector(new Vector(vectorArray));
        return augmat;
    }


    private double[][] parseAugmentedRow(String row) {
        String[] asArray = row.split(" ");
        double[][] result = new double[2][asArray.length - 1];
        for (int i = 0; i < asArray.length; i++) {
            try {
                if (i < asArray.length - 1) {
                    result[0][i] = Double.parseDouble(asArray[i]);
                } else {
                    result[1][0] = Double.parseDouble(asArray[i]);
                }
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Could not parse \""
                        + asArray[i] + "\" as a double.");
            }
        }
        return result;
    }
}

