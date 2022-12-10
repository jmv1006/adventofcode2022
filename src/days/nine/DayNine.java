package src.days.nine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayNine {
    List<String[]> readInput() {
        List<String[]> commands = new ArrayList<>();
        try {
            File myObj = new File("src/days/nine/input.txt");
            Scanner myReader = new Scanner(myObj);

            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] command = data.split(" ");
                commands.add(command);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return commands;
    }

    int[] determineTailPosition(int[] currentTailPosition, int[] headPosition) {
        // Head and Tail MUST ALWAYS BE TOUCHING!!!
        int verticalDifference = headPosition[1] - currentTailPosition[1];
        int horizontalDifference = headPosition[0] - currentTailPosition[0];

        //overlap
        if(headPosition[0] == currentTailPosition[0] && headPosition[1] == currentTailPosition[1]) return currentTailPosition;

        // diagonal
        if((Math.abs(horizontalDifference) == 1 && Math.abs(verticalDifference) == 1)) return currentTailPosition;

        //up, down, left, right
        if((Math.abs(horizontalDifference) == 1 && Math.abs(verticalDifference) <= 1) || (Math.abs(horizontalDifference) <= 1 && Math.abs(verticalDifference) == 1)) return currentTailPosition;


        //if not in same column or row, diagonal move
        if(headPosition[0] != currentTailPosition[0] && headPosition[1] != currentTailPosition[1]) {

            //move up and right
            if(horizontalDifference > 0 && verticalDifference > 0) {
                currentTailPosition[0] += 1;
                currentTailPosition[1] += 1;
                return currentTailPosition;
            }

            //move left and up
            if(horizontalDifference < 0 && verticalDifference > 0) {
                currentTailPosition[0] -= 1;
                currentTailPosition[1] += 1;
                return currentTailPosition;
            }

            //move down and right
            if(horizontalDifference > 0 && verticalDifference < 0) {
                currentTailPosition[0] += 1;
                currentTailPosition[1] -= 1;
                return currentTailPosition;
            }

            //move down and left
            if(horizontalDifference < 0 && verticalDifference < 0) {
                currentTailPosition[0] -= 1;
                currentTailPosition[1] -= 1;
                return currentTailPosition;
            }

        }

        //left or down move
        if(verticalDifference < 0) {
            currentTailPosition[1] -= 1;
        } else if(horizontalDifference < 0) {
            currentTailPosition[0] -= 1;
        }

        //up or right
        if(verticalDifference > 1) {
            currentTailPosition[1] += 1;
        } else if (horizontalDifference > 1) {
            currentTailPosition[0] += 1;
        }

        return currentTailPosition;
    }
    void handleCommands(List<String[]> commands) {
        int[] headPosition = new int[] {0, 0};
        int[] tailPosition = new int[] {0, 0};
        HashSet<String> points = new HashSet<>();

        for(String[] command : commands) {
            String direction = command[0];
            int numberOfSteps = Integer.parseInt(command[1]);

            for(int i = 1; i <= numberOfSteps; i++) {
                switch (direction) {
                    case "U" -> headPosition[1] += 1;
                    case "D" -> headPosition[1] -= 1;
                    case "L" -> headPosition[0] -= 1;
                    case "R" -> headPosition[0] += 1;
                }

                tailPosition = determineTailPosition(tailPosition, headPosition);
                points.add(Integer.toString(tailPosition[1]) + Integer.toString(tailPosition[0]));
                System.out.println(Arrays.toString(headPosition) + " : " + Arrays.toString(tailPosition));
            }
        }
        System.out.println(points.size());
    }

    void handleCommandsPartTwo(List<String[]> commands) {
        int[] headPosition = new int[] {0, 0};
        List<int[]> knots = new ArrayList<>();
        HashSet<String> tailPositions = new HashSet<>();

        for(int i = 0; i < 9; i++) {
            knots.add(new int[] {0, 0});
        }

        //[1, 2, 3, 4, 5, 6, 7, 8, 9]
        // 0  1  2  3  4  5  6  7  8
        int[] tailPosition = knots.get(8);

        // for each know, I need to do determineTailPosition (currentKnot, currentKnot + 1)
        for(String[] command : commands) {
            String direction = command[0];
            int numberOfSteps = Integer.parseInt(command[1]);

            for(int i = 1; i <= numberOfSteps; i++) {
                switch (direction) {
                    case "U" -> headPosition[1] += 1;
                    case "D" -> headPosition[1] -= 1;
                    case "L" -> headPosition[0] -= 1;
                    case "R" -> headPosition[0] += 1;
                }

                //knot 1 updating based on head position
                int[] positionOne = knots.get(0);
                positionOne = determineTailPosition(knots.get(0), headPosition);

                //each knot in the trail updating based on its neighbor
                for(int j = 1; j < knots.size(); j++) {
                    int[] knotPosition = knots.get(j);
                    knotPosition = determineTailPosition(knots.get(j), knots.get(j - 1));
                }

                //after each step, update the tail position
                tailPositions.add(Integer.toString(tailPosition[1]) + Integer.toString(tailPosition[0]));
            }
        }
        for(int[] knot : knots) {
            System.out.println(Arrays.toString(knot));
        }
        System.out.println(tailPositions.size());
    }

    public static void main(String[] args) {
        DayNine day = new DayNine();
        List<String[]> commands = day.readInput();
        day.handleCommandsPartTwo(commands);
    }
}
