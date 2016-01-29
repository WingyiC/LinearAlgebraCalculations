public class AugmentedMatrix {

    private double[][] matrix;
    private double[] vector;

    public Matrix getMatrix() {
        return new Matrix(matrix);
    }

    public Vector getVector() {
        return new Vector(vector);
    }

    public void setMatrix(Matrix m) {
        this.matrix = m.getMatrix();
    }

    public void setVector(Vector v) {
        this.vector = v.getVector();
    }
}
