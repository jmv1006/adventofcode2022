package src.days.eight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayEight {
    int[][] readInput() {
        int[][] grid = new int[99][99];
        int currRow = 0;

        try {
            File myObj = new File("src/days/eight/input.txt");
            Scanner myReader = new Scanner(myObj);

            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArr = data.split("");

                int[] row = new int[99];
                for(int col = 0; col < 99; col++) {
                    row[col] = Integer.parseInt(dataArr[col]);
                }

                grid[currRow] = row;
                currRow += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return grid;
    }


    int getScoreUp(int[][] grid, int r, int c, int maxHeight) {
        if(r < 0 || r > 98 || c < 0 || c > 98)  return 0;
        if(grid[r][c] >= maxHeight) return 1;
        return 1 + getScoreUp(grid, r - 1, c, maxHeight);
    }

    int getScoreDown(int[][] grid, int r, int c, int maxHeight) {
        if(r < 0 || r > 98 || c < 0 || c > 98)  return 0;
        if(grid[r][c] >= maxHeight) return 1;
        return 1 + getScoreDown(grid, r + 1, c, maxHeight);
    }

    int getScoreLeft(int[][] grid, int r, int c, int maxHeight) {
        if(r < 0 || r > 98 || c < 0 || c > 98)  return 0;
        if(grid[r][c] >= maxHeight) return 1;
        return 1 + getScoreLeft(grid, r, c - 1, maxHeight);
    }

    int getScoreRight(int[][] grid, int r, int c, int maxHeight) {
        if(r < 0 || r > 98 || c < 0 || c > 98)  return 0;
        if(grid[r][c] >= maxHeight) return 1;
        return 1 + getScoreRight(grid, r, c + 1, maxHeight);
    }

    int getScenicScore(int[][] grid, int r, int c) {
        //go up
        int up = getScoreUp(grid, r - 1, c, grid[r][c]);
        int down = getScoreDown(grid, r + 1, c, grid[r][c]);;
        int left = getScoreLeft(grid, r, c - 1, grid[r][c]);;
        int right = getScoreRight(grid, r, c + 1, grid[r][c]);;

        System.out.println(r + " " + c + " : " +  "Up:" + up + " Down:" + down + " Left:" + left + " Right:" + right);
        return up * down * left * right;
    }

    void iterateGrid(int[][] grid) {
        int total = 0;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < 99; col++) {
                total = Math.max(total, getScenicScore(grid, row, col));
            }
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        DayEight day = new DayEight();
        int[][] grid = day.readInput();
        day.iterateGrid(grid);
    }
}
