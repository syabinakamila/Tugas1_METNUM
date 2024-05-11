import java.util.Arrays;

public class LUGaussMethod {

    public static double[] solve(double[][] A, double[] B) {
        double[][][] LU = decomposeLU(A);
        if (LU == null) {
            System.out.println("Matrix is singular, cannot solve the system.");
            return null;
        }
        double[] Y = forwardSubstitution(LU[0], B);
        double[] X = backwardSubstitution(LU[1], Y);
        return X;
    }

    public static double[][][] decomposeLU(double[][] A) {
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        // Initialize L matrix as identity matrix and U as zero matrix
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = 0; j < n; j++) {
                U[i][j] = 0;
            }
        }

        // Perform Gaussian elimination with partial pivoting
        for (int k = 0; k < n; k++) {
            // Find the pivot row
            int pivotRow = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[pivotRow][k])) {
                    pivotRow = i;
                }
            }
            if (A[pivotRow][k] == 0) {
                return null; // Matrix is singular
            }

            // Swap rows to bring pivot row to the top
            double[] temp = A[k];
            A[k] = A[pivotRow];
            A[pivotRow] = temp;

            temp = L[k];
            L[k] = L[pivotRow];
            L[pivotRow] = temp;

            // Perform elimination
            for (int i = k + 1; i < n; i++) {
                double factor = A[i][k] / A[k][k];
                L[i][k] = factor;
                for (int j = k; j < n; j++) {
                    A[i][j] -= factor * A[k][j];
                }
            }
        }

        // Populate U matrix from the upper triangle of A
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, U[i], i, n - i);
        }

        double[][][] LU = {L, U};
        return LU;
    }

    public static double[] forwardSubstitution(double[][] L, double[] B) {
        int n = L.length;
        double[] Y = new double[n];
        for (int i = 0; i < n; i++) {
            Y[i] = B[i];
            for (int j = 0; j < i; j++) {
                Y[i] -= L[i][j] * Y[j];
            }
        }
        return Y;
    }

    public static double[] backwardSubstitution(double[][] U, double[] Y) {
        int n = U.length;
        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            X[i] = Y[i];
            for (int j = i + 1; j < n; j++) {
                X[i] -= U[i][j] * X[j];
            }
            X[i] /= U[i][i];
        }
        return X;
    }

    public static void main(String[] args) {
        double[][] A = {{2, 1}, {1, -3}};
        double[] B = {5, -7};
        double[] X = solve(A, B);
        System.out.println("Solution: " + Arrays.toString(X));
    }
}
