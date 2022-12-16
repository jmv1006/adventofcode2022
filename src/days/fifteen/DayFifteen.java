package src.days.fifteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DayFifteen {
    HashMap<String, String> sensorsAndBeacons = new HashMap<>();
    HashMap<String, Integer> manhattanDistances = new HashMap<>();

    int largestX = 0;
    int smallestX = 0;


    void readInput() {
        try {
            File myObj = new File("src/days/fifteen/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitData = data.split(":");

                String[] splitSensor = splitData[0].split("=");
                String splitSensorX = splitSensor[1].split(",")[0];
                String splitSensorY = splitSensor[2];
                String sensorCoordinateString = splitSensorX + ":" + splitSensorY;
                int[] sensorCoordinatesArray = coordinateStringToArray(sensorCoordinateString);


                String[] splitBeacon = splitData[1].split("=");
                String splitBeaconX = splitBeacon[1].split(",")[0];
                String splitBeaconY = splitBeacon[2];
                String beaconCoordinateString = splitBeaconX + ":" + splitBeaconY;
                int[] beaconCoordinateArray = coordinateStringToArray(beaconCoordinateString);

                manhattanDistances.put(sensorCoordinateString, calculateManhattanDistance(sensorCoordinatesArray, beaconCoordinateArray));
                sensorsAndBeacons.put(sensorCoordinateString, beaconCoordinateString);
                updateLimits(sensorCoordinatesArray);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    void updateLimits(int[] sensorCoord) {
        largestX = Math.max(largestX, sensorCoord[0] + manhattanDistances.get(sensorCoord[0] + ":" + sensorCoord[1]));
        smallestX = Math.min(smallestX, sensorCoord[0] - manhattanDistances.get(sensorCoord[0] + ":" + sensorCoord[1]));
    }
    int calculateManhattanDistance(int[] pointOne, int[] pointTwo) {
        return Math.abs(pointOne[0] - pointTwo[0]) + Math.abs(pointOne[1] - pointTwo[1]);
    }

    int[] coordinateStringToArray(String coordinate) {
        String[] coordinateArr = coordinate.split(":");
        return new int[]{Integer.parseInt(coordinateArr[0]), Integer.parseInt(coordinateArr[1])};
    }

    boolean pointIsWithinDistance(int[] pointOne, int[] pointTwo, int distance) {
        return calculateManhattanDistance(pointOne, pointTwo) <= distance;
    }
    void findPositions() {
        System.out.println("Min");
    }


    HashSet<String> evaluateRow(int y) {
        // a point that is not within manhattan distance of ANY sensor
        HashSet<String> withinDistance = new HashSet<>();
        HashSet<String> notWithin = new HashSet<>();

        for(int x = 0; x <= 4000000; x++) {
            //For each column between 0 and 4000000, go over each sensor and see if the point is within its manhattan range
            int[] point = new int[]{x, y};
            //if this is a beacon location
            if(sensorsAndBeacons.containsValue(point[0] + ":" + point[1])) continue;

            //for each sensor, check if this point is within its manhattan distance
            for(String sensor : sensorsAndBeacons.keySet()) {
                int[] sensorCoordinate = coordinateStringToArray(sensor);
                boolean within = pointIsWithinDistance(sensorCoordinate, point, manhattanDistances.get(sensor));
                String pointString = point[0] + ":" + point[1];

                //if this point is within manhattan distance of a sensor
                if(within && notWithin.contains(pointString)) {
                    withinDistance.add(pointString);
                    notWithin.remove(pointString);
                } else if(within) {
                    withinDistance.add(pointString);
                } else if(!withinDistance.contains(pointString)) {
                    notWithin.add(pointString);
                }
            }
        }

        //return notWithin;
        return withinDistance;
    }

    public static void main(String[] args) {
        DayFifteen day = new DayFifteen();
        day.readInput();
        day.findPositions();
    }
}
