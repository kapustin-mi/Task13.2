package cs.vsu.ru.kapustin;

public class Utils {

    public static int[][] copyIntMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }

        return newMatrix;
    }
}
