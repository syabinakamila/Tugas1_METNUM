import java.util.Arrays;

public class CroutMethod {

    public static double[] solve(double[][] A, double[] B) {
        double[][][] LU = decomposeCrout(A);
        if (LU == null) {
            System.out.println("Matrix is singular, cannot solve the system.");
            return null;
        }
        double[] Y = forwardSubstitution(LU[0], B);
        double[] X = backwardSubstitution(LU[1], Y);
        return X;
    }

    public static double[][][] decomposeCrout(double[][] A) {
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        // Initialize L matrix as zero matrix and U as identity matrix
        for (int i = 0; i < n; i++) {
            U[i][i] = 1;
            for (int j = 0; j < n; j++) {
                L[i][j] = 0;
            }
        }

        // Perform Crout decomposition
        for (int j = 0; j < n; j++) {
            // Compute entries of U
            for (int i = j; i < n; i++) {
                double sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += L[i][k] * U[k][j];
                }
                U[i][j] = A[i][j] - sum;
            }

            // Compute entries of L
            for (int i = j; i < n; i++) {
                if (i == j) {
                    L[i][j] = 1; // Diagonal entry of L
                } else {
                    double sum = 0;
                    for (int k = 0; k < j; k++) {
                        sum += L[j][k] * U[k][i];
                    }
                    L[i][j] = (A[i][j] - sum) / U[j][j];
                }
            }
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
            Y[i] /= L[i][i]; // Divide by diagonal element of L
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
            X[i] /= U[i][i]; // Divide by diagonal element of U
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
