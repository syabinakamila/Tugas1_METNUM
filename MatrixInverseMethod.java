
import java.util.Arrays;

public class MatrixInverseMethod {

    public static double[] solve(double[][] A, double[] B) {
        double[][] inverseA = inverse(A);
        if (inverseA == null) {
            System.out.println("Matrix is singular, cannot solve the system.");
            return null;
        }
        return multiply(inverseA, B);
    }

    public static double[][] inverse(double[][] A) {
        int n = A.length;
        double[][] augmentedMatrix = new double[n][2*n];

        // Copying A into the left half of augmentedMatrix and identity matrix into right half
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][i + n] = 1;
        }

        // Perform Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Pivot for the current row
            double pivot = augmentedMatrix[i][i];
            if (pivot == 0) {
                return null; // Matrix is singular
            }

            // Divide the row by the pivot
            for (int j = 0; j < 2*n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminate all other entries in the current column
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2*n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Extracting the right half (inverse of A) from augmentedMatrix
        double[][] inverseA = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseA[i], 0, n);
        }

        return inverseA;
    }

    public static double[] multiply(double[][] A, double[] B) {
        int n = A.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += A[i][j] * B[j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] A = {{2, 1}, {1, -3}};
        double[] B = {5, -7};
        double[] X = solve(A, B);
        System.out.println("Solution: " + Arrays.toString(X));
    }
}
