package src.days.fourteen;

import src.days.thirteen.Packet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayFourteen {
    ArrayList<String> readInput() {
        ArrayList<String> directions = new ArrayList<>();

        try {
            File myObj = new File("src/days/fourteen/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                directions.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return directions;
    }


    HashSet<String> drawRock(ArrayList<String> directions) {
        HashSet<String> rockLocations = new HashSet<>();

        for(String direction : directions) {
            String[] directionArr = direction.replace(" ", "").split("->");
            ArrayList<String> directionString = new ArrayList<>(Arrays.asList(directionArr));

            int l = 0;
            int r = 1;

            while (r < directionString.size()) {
                String[] leftCoordinate = directionString.get(l).split(",");
                String[] rightCoordinate = directionString.get(r).split(",");


                int xLeft = Integer.parseInt(leftCoordinate[0]);
                int yLeft = Integer.parseInt(leftCoordinate[1]);

                int xRight = Integer.parseInt(rightCoordinate[0]);
                int yRight = Integer.parseInt(rightCoordinate[1]);

                rockLocations.add(xRight + " : " + yRight);
                rockLocations.add(xLeft + " : " + yLeft);

                // vertical line
                if(xLeft == xRight) {
                    if(yLeft < yRight) {
                        while (yLeft < yRight - 1) {
                            yLeft += 1;
                            rockLocations.add(xLeft + " : " + yLeft);
                        }
                    }
                    if(yRight < yLeft) {
                        while (yRight < yLeft - 1) {
                            yRight += 1;
                            rockLocations.add(xRight + " : " + yRight);
                        }
                    }
                }

                //horizontal line
                if(yLeft == yRight) {
                    if(xLeft < xRight) {
                        while (xLeft < xRight - 1) {
                            xLeft += 1;
                            rockLocations.add(xLeft + " : " + yLeft);
                        }
                    }
                    else if(xRight < xLeft) {
                        while (xRight < xLeft - 1) {
                            xRight += 1;
                            rockLocations.add(xRight + " : " + yRight);
                        }
                    }
                }
                l += 1;
                r += 1;
            }
        }
        return rockLocations;
    }

    int getRockFloor(HashSet<String> rockLocations) {
        int rockFloor = 0;
        //find floor
        for(String coordinate : rockLocations) {
            int y = Integer.parseInt(coordinate.replace(" ", "").split(":")[1]);
            rockFloor = Math.max(rockFloor, y);
        }

        return rockFloor + 2;
    }

    void simulateSand(HashSet<String> rockLocations, int rockFloor) {
        HashSet<String> sandLocations = new HashSet<>();

        while (!sandLocations.contains("500 : 0")) {
            sandLocations = dropSand(rockLocations, rockFloor, sandLocations);
        }
        System.out.println(sandLocations.size());
    }

    HashSet<String> dropSand(HashSet<String> rockLocations, int rockFloor, HashSet<String> sandLocations) {
        String currentSandPoint = "500 : 0";

        while (!rockLocations.contains(currentSandPoint) && !sandLocations.contains(currentSandPoint)) {
            int[] currentPointArr = stringCoordinateToIntArray(currentSandPoint);
            currentPointArr[1] += 1;
            String updatedSandPoint = intCoordinateToString(currentPointArr);

            // if this is true, the updated block of sand is either a rock or another block of sand
            if(rockLocations.contains(updatedSandPoint) || sandLocations.contains(updatedSandPoint)) {
                //before letting the sand rest check if it can go diagonol. If so, break out of this and start the logic for diagonol movement
                if(sandCanGoDiagonal(stringCoordinateToIntArray(currentSandPoint), sandLocations, rockLocations, rockFloor)) break;
                //sand is at rest
                sandLocations.add(currentSandPoint);
                return sandLocations;
            }
            currentSandPoint = updatedSandPoint;
        }

        // currentSandPoint is now at the first vertical block
        int[] currentCoordinate = stringCoordinateToIntArray(currentSandPoint);


        //while the block of sand can go diagonal OR down....
        while (sandCanGoDiagonal(currentCoordinate, sandLocations, rockLocations, rockFloor) || sandCanGoDown(currentCoordinate, sandLocations, rockLocations, rockFloor)) {
            //check if sand can go down
            int[] downOne = currentCoordinate.clone();
            downOne[1] += 1;
            String updatedCoordinateDown = intCoordinateToString(downOne);
            //if this is true, it means the block of sand can go down a unit
            if(!sandLocations.contains(updatedCoordinateDown) && !rockLocations.contains(updatedCoordinateDown)) {
                currentCoordinate = stringCoordinateToIntArray(updatedCoordinateDown);
                continue;
            }

            int[] diagonalLeft = currentCoordinate.clone();
            diagonalLeft[0] -= 1;
            diagonalLeft[1] += 1;
            String updatedCoordinateLeft = intCoordinateToString(diagonalLeft);
            //if this is true, the block of sand can go diagonal and down to the left
            if(!sandLocations.contains(updatedCoordinateLeft) && !rockLocations.contains(updatedCoordinateLeft)) {
                currentCoordinate = stringCoordinateToIntArray(updatedCoordinateLeft);
                continue;
            };

            int[] diagonalRight = currentCoordinate.clone();
            diagonalRight[0] += 1;
            diagonalRight[1] += 1;
            String updatedCoordinateRight = intCoordinateToString(diagonalRight);
            //if this is true, the block of sand can go diagonal and down to the right...
            if(!sandLocations.contains(updatedCoordinateRight) && !rockLocations.contains(updatedCoordinateRight)) {
                currentCoordinate = stringCoordinateToIntArray(updatedCoordinateRight);
                continue;
            };
        }

        sandLocations.add(intCoordinateToString(currentCoordinate));
        return sandLocations;
    }

    boolean sandCanGoDiagonal(int[] coordinate, HashSet<String> sandLocations, HashSet<String> rockLocations, int rockFloor) {

        int[] diagonalLeft = coordinate.clone();
        //down one and to the left
        diagonalLeft[0] -= 1;
        diagonalLeft[1] += 1;
        String updatedLeft = intCoordinateToString(diagonalLeft);
        if(!sandLocations.contains(updatedLeft) && !rockLocations.contains(updatedLeft) && diagonalLeft[1] < rockFloor) return true;


        //down one and to the right
        int[] diagonalRight = coordinate.clone();
        diagonalRight[0] += 1;
        diagonalRight[1] += 1;
        String updatedRight = intCoordinateToString(diagonalRight);
        return !sandLocations.contains(updatedRight) && !rockLocations.contains(updatedRight) && diagonalRight[1] < rockFloor;
    }

    boolean sandCanGoDown(int[] coordinate, HashSet<String> sandLocations, HashSet<String> rockLocations, int rockFloor) {
        int[] coordinateCopy = coordinate.clone();
        coordinateCopy[1] += 1;
        String updatedCoordinate = intCoordinateToString(coordinateCopy);
        return !sandLocations.contains(updatedCoordinate) && !rockLocations.contains(updatedCoordinate) && coordinateCopy[1] < rockFloor;
    }

    int[] stringCoordinateToIntArray(String coordinate) {
        String[] coordinateArr = coordinate.replace(" ", "").split(":");
        return new int[]{Integer.parseInt(coordinateArr[0]), Integer.parseInt(coordinateArr[1])};
    }

    String intCoordinateToString(int[] coordinate) {
        return coordinate[0] + " : " + coordinate[1];
    }
    void startScan(ArrayList<String> directions) {
        //formatted xCoordinate : yCoordinate
        HashSet<String> rockLocations = drawRock(directions);

        //find floor
        int rockFloor = getRockFloor(rockLocations);
        simulateSand(rockLocations, rockFloor);
    }

    public static void main(String[] args) {
        DayFourteen day = new DayFourteen();
        ArrayList<String> directions = day.readInput();
        day.startScan(directions);
    }
}
