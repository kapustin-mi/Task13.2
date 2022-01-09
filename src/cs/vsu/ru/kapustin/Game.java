package cs.vsu.ru.kapustin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    private final Random rnd = new Random();
    private final int[][] tileValues = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    public int[][] getTileValues() {
        return tileValues;
    }

    public void moveTilesLeft(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int i = 0; i < tileValues.length; i++) {
            List<Integer> movableTiles = new ArrayList<>();

            for (int j = 0; j < tileValues.length; j++) {
                if (tileValues[i][j] != 0) {
                    movableTiles.add(tileValues[i][j]);
                    tileValues[i][j] = 0;
                }
            }

            for (int k = 0; k < movableTiles.size(); k++) {
                tileValues[i][k] = movableTiles.get(k);
            }
        }
    }

    public void mergeAfterLeftShift(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int i = 0; i < tileValues.length; i++) {
            for (int j = 0; j < tileValues.length - 1; j++) {
                if (tileValues[i][j] == tileValues[i][j + 1] && (tileValues[i][j] != 0 && tileValues[i][j + 1] != 0)) {
                    tileValues[i][j] = tileValues[i][j] * 2;
                    tileValues[i][j + 1] = 0;
                }
            }
        }
    }

    public void moveTilesRight(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int i = 0; i < tileValues.length; i++) {
            List<Integer> movableTiles = new ArrayList<>();

            for (int j = 0; j < tileValues.length; j++) {
                if (tileValues[i][j] != 0) {
                    movableTiles.add(tileValues[i][j]);
                    tileValues[i][j] = 0;
                }
            }

            int column = tileValues.length - 1;
            for (int k = movableTiles.size() - 1; k >= 0; k--) {
                tileValues[i][column] = movableTiles.get(k);
                column--;
            }
        }
    }

    public void mergeAfterRightShift(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int i = 0; i < tileValues.length; i++) {
            for (int j = tileValues.length - 1; j > 0; j--) {
                if (tileValues[i][j] == tileValues[i][j - 1] && (tileValues[i][j] != 0 && tileValues[i][j - 1] != 0)) {
                    tileValues[i][j] = tileValues[i][j] * 2;
                    tileValues[i][j - 1] = 0;
                }
            }
        }
    }

    public void moveTilesUp(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int j = 0; j < tileValues.length; j++) {
            List<Integer> movableTiles = new ArrayList<>();

            for (int i = 0; i < tileValues.length; i++) {
                if (tileValues[i][j] != 0) {
                    movableTiles.add(tileValues[i][j]);
                    tileValues[i][j] = 0;
                }
            }

            for (int k = 0; k < movableTiles.size(); k++) {
                tileValues[k][j] = movableTiles.get(k);
            }
        }
    }

    public void mergeAfterUpShift(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }

        for (int j = 0; j < tileValues.length; j++) {
            for (int i = 0; i < tileValues.length - 1; i++) {
                if (tileValues[i][j] == tileValues[i + 1][j] && (tileValues[i][j] != 0 && tileValues[i + 1][j] != 0)) {
                    tileValues[i][j] = tileValues[i][j] * 2;
                    tileValues[i + 1][j] = 0;
                }
            }
        }
    }

    public void moveTilesDown(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }
        for (int j = 0; j < tileValues.length; j++) {
            List<Integer> movableTiles = new ArrayList<>();
            for (int i = 0; i < tileValues.length; i++) {
                if (tileValues[i][j] != 0) {
                    movableTiles.add(tileValues[i][j]);
                    tileValues[i][j] = 0;
                }
            }
            int row = tileValues.length - 1;
            for (int k = movableTiles.size() - 1; k >= 0; k--) {
                tileValues[row][j] = movableTiles.get(k);
                row--;
            }
        }
    }

    public void mergeAfterDownShift(int[][] tileValues) {
        if (tileValues == null) {
            tileValues = this.tileValues;
        }
        for (int j = 0; j < tileValues.length; j++) {
            for (int i = tileValues.length - 1; i > 0; i--) {
                if (tileValues[i][j] == tileValues[i - 1][j] && (tileValues[i][j] != 0 && tileValues[i - 1][j] != 0)) {
                    tileValues[i][j] = tileValues[i][j] * 2;
                    tileValues[i - 1][j] = 0;
                }
            }
        }
    }

    public void generateStartingArray() {
        int firstTile = rnd.nextInt(15);
        generateNewTile(firstTile/4, firstTile%4);
        int secondTile = firstTile;
        while (secondTile == firstTile) {
            secondTile = rnd.nextInt(15);
        }
        generateNewTile(secondTile/4, secondTile%4);
    }

    public void addTile() {
        int freeTiles = 0;
        List<Integer> freeTilesIndexes = new ArrayList<>();
        for (int i = 0; i < tileValues.length; i++) {
            for (int j = 0; j < tileValues.length; j++) {
                if (tileValues[i][j] == 0) {
                    freeTiles++;
                    freeTilesIndexes.add(i);
                    freeTilesIndexes.add(j);
                }
            }
        }
        if (freeTiles > 1) {
            int newTile = rnd.nextInt(1, freeTiles);
            generateNewTile(freeTilesIndexes.get((newTile - 1) * 2), freeTilesIndexes.get((newTile - 1) * 2 + 1));
        } else {
            generateNewTile(freeTilesIndexes.get(0), freeTilesIndexes.get(1));
        }
    }

    private void generateNewTile(int rowOfTile, int columnOfTile) {
        int ran = (int) (Math.random() * 10);
        if (ran >= 9) {
            tileValues[rowOfTile][columnOfTile] = 4;
        } else {
            tileValues[rowOfTile][columnOfTile] = 2;
        }
    }

    public boolean isWin(int valueForWin) {
        for (int[] tileValue : tileValues) {
            for (int j = 0; j < tileValues.length; j++) {
                if (tileValue[j] == valueForWin) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isLose() {
        int numberOfImpossibleMoves = 0;
        int[][] actualValuesOfTiles = Utils.copyIntMatrix(tileValues);

        moveTilesDown(actualValuesOfTiles);
        mergeAfterDownShift(actualValuesOfTiles);
        moveTilesDown(actualValuesOfTiles);

        if (Arrays.deepEquals(actualValuesOfTiles, tileValues)) {
            numberOfImpossibleMoves++;
        }

        actualValuesOfTiles = Utils.copyIntMatrix(tileValues);
        moveTilesUp(actualValuesOfTiles);
        mergeAfterUpShift(actualValuesOfTiles);
        moveTilesUp(actualValuesOfTiles);

        if (Arrays.deepEquals(actualValuesOfTiles, tileValues)) {
            numberOfImpossibleMoves++;
        }


        actualValuesOfTiles = Utils.copyIntMatrix(tileValues);
        moveTilesRight(actualValuesOfTiles);
        mergeAfterRightShift(actualValuesOfTiles);
        moveTilesRight(actualValuesOfTiles);

        if (Arrays.deepEquals(actualValuesOfTiles, tileValues)) {
            numberOfImpossibleMoves++;
        }


        actualValuesOfTiles = Utils.copyIntMatrix(tileValues);
        moveTilesLeft(actualValuesOfTiles);
        mergeAfterLeftShift(actualValuesOfTiles);
        moveTilesLeft(actualValuesOfTiles);

        if (Arrays.deepEquals(actualValuesOfTiles, tileValues)) {
            numberOfImpossibleMoves++;
        }


        return numberOfImpossibleMoves == 4;
    }
}
