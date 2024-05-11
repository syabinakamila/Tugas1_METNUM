     
import java.util.Arrays;

public class CroutMethodTest {

    public static void main(String[] args) {
        testSolve();
    }

    public static void testSolve() {
        System.out.println("Testing solve method:");

        // Test case 1
        double[][] A1 = {{2, 1}, {1, -3}};
        double[] B1 = {5, -7};
        double[] expectedSolution1 = {3, 2};
        testSolveWithTestCase(A1, B1, expectedSolution1);

        // Test case 2 (singular matrix)
        double[][] A2 = {{1, 2}, {2, 4}};
        double[] B2 = {3, 6};
        testSolveWithTestCase(A2, B2, null);

        // Add more test cases as needed
    }

    public static void testSolveWithTestCase(double[][] A, double[] B, double[] expectedSolution) {
        double[] solution = CroutMethod.solve(A, B);
        if (Arrays.equals(solution, expectedSolution)) {
            System.out.println("Test passed for matrix A: " + Arrays.deepToString(A) +
                    ", vector B: " + Arrays.toString(B) +
                    ", Expected solution: " + Arrays.toString(expectedSolution) +
                    ", Actual solution: " + Arrays.toString(solution));
        } else {
            System.out.println("Test failed for matrix A: " + Arrays.deepToString(A) +
                    ", vector B: " + Arrays.toString(B) +
                    ", Expected solution: " + Arrays.toString(expectedSolution) +
                    ", Actual solution: " + Arrays.toString(solution));
        }
    }
}
