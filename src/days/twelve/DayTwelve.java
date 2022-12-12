package src.days.twelve;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTwelve {
    String[][] readInput() {
        // 77 x 41, 41 rows, 77 cols
        String[][] heightMap = new String[41][77];
        //String[][] heightMap = new String[5][8];

        int currentRow = 0;
        try {
            File myObj = new File("src/days/twelve/input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArr = data.split("");

                String[] row = new String[77];
                //String[] row = new String[8];

                int rowIterator = 0;
                for(String character : dataArr) {
                    row[rowIterator] = character;
                    rowIterator += 1;
                }

                heightMap[currentRow] = row;
                currentRow += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return heightMap;
    }

    void readInputTwo() {
        int currentRow = 0;
        try {
            File myObj = new File("src/days/twelve/input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data.length());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }
    int[] getStartingPosition(String[][] heightMap) {
        for(int row = 0; row < heightMap.length; row++) {
            for(int col = 0; col < heightMap[0].length; col++) {
                if(heightMap[row][col].equals("S")) return new int[] {row, col, 0};
            }
        }
        return new int[] {0, 0, 0};
    }

    void bfs(String[][] heightMap) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[41][77];

        int[] initialPosition = getStartingPosition(heightMap);
        visited[initialPosition[0]][initialPosition[1]] = true;
        queue.add(initialPosition);

        while(queue.size() > 0) {
            int[] currentPos = queue.remove();
            int row = currentPos[0];
            int col = currentPos[1];
            int distance = currentPos[2];

            if(heightMap[row][col].equals("E")) {
                System.out.println(row + " " + col + " " + distance);
                continue;
            }

            int charValue = heightMap[row][col].charAt(0);
            if(charValue == 83) charValue = 97;

            int[][] neighbors = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for(int[] neighborPair : neighbors) {
                int addedRow = row + neighborPair[0];
                int addedCol = col + neighborPair[1];

                // base cases
                if(addedRow < 0 || addedRow == heightMap.length || addedCol < 0 || addedCol == heightMap[0].length || visited[addedRow][addedCol]) continue;

                int destHeight = heightMap[addedRow][addedCol].charAt(0);
                if(destHeight == 69) destHeight = 122;
                int climb = destHeight - charValue;
                if(climb > 1) continue;

                if(!heightMap[addedRow][addedCol].equals("E")) visited[addedRow][addedCol] = true;
                queue.add(new int[] {addedRow, addedCol, distance + 1});
            }
        }
    }

    void partTwo(String[][] heightMap) {
        HashSet<Integer> distances = new HashSet<>();

        for(int row = 0; row < heightMap.length; row++) {
            for(int col = 0; col < heightMap[0].length; col++) {
                if(heightMap[row][col].equals("a")) {
                    int shortest = bfsTwo(heightMap, new int[] {row, col, 0});
                    distances.add(shortest);
                }
            }
        }
        System.out.println(distances);
    }

    int bfsTwo(String[][] heightMap, int[] startingPos) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[41][77];

        visited[startingPos[0]][startingPos[1]] = true;
        queue.add(startingPos);

        while(queue.size() > 0) {
            int[] currentPos = queue.remove();
            int row = currentPos[0];
            int col = currentPos[1];
            int distance = currentPos[2];

            if(heightMap[row][col].equals("E")) {
                System.out.println(row + " " + col + " " + distance);
                return distance;
            }

            int charValue = heightMap[row][col].charAt(0);
            if(charValue == 83) charValue = 97;

            int[][] neighbors = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for(int[] neighborPair : neighbors) {
                int addedRow = row + neighborPair[0];
                int addedCol = col + neighborPair[1];

                // base cases
                if(addedRow < 0 || addedRow == heightMap.length || addedCol < 0 || addedCol == heightMap[0].length || visited[addedRow][addedCol]) continue;

                int destHeight = heightMap[addedRow][addedCol].charAt(0);
                if(destHeight == 69) destHeight = 122;
                int climb = destHeight - charValue;
                if(climb > 1) continue;

                if(!heightMap[addedRow][addedCol].equals("E")) visited[addedRow][addedCol] = true;
                queue.add(new int[] {addedRow, addedCol, distance + 1});
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        DayTwelve day = new DayTwelve();
        String[][] heightMap = day.readInput();
        day.partTwo(heightMap);
    }
}
