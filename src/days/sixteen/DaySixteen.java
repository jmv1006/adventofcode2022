package src.days.sixteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DaySixteen {
    HashMap<String, ArrayList<String>> tunnelNetwork = new HashMap<>();
    HashMap<String, Integer> flowRates = new HashMap<>();

    HashSet<String> opened = new HashSet<>();

    void readInput() {
        try {
            File myObj = new File("src/days/sixteen/test.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitData = data.split(";");
                String valveInfo = splitData[0];
                String[] tunnels = splitData[1].replace(",", "").split(" ");

                String valve = valveInfo.split(" ")[1];
                String rate = valveInfo.split("=")[1];

                tunnelNetwork.put(valve, new ArrayList<>());

                for(int i = 5; i < tunnels.length; i++) {
                    tunnelNetwork.get(valve).add(tunnels[i]);
                }

                flowRates.put(valve, Integer.parseInt(rate));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    void dfs(String valve, int minute, int currentTotalPressure) {
        if(minute >= 30) return;

        //get potential total pressure for current valve
        int potentialTotal = calculatePotentialTotal(valve, minute);
        if(potentialTotal > currentTotalPressure) currentTotalPressure = potentialTotal;

        //System.out.println("Minute: " + minute + ", Current valve: " + valve + ", potential total pressure: " + potentialTotal);

        int maxPotential = 0;
        String maxNeighbor = "";
        for(String neighbor : tunnelNetwork.get(valve)) {
            if(calculatePotentialTotal(neighbor, minute + 2) > maxPotential) {
                maxPotential = calculatePotentialTotal(neighbor, minute + 2);
                maxNeighbor = neighbor;
            }

            //System.out.println(neighbor + ", Potential total: " + calculatePotentialTotal(neighbor, minute + 2) + " minutes elapsed " + (minute + 2));
        }

        System.out.println(maxNeighbor + " : " + maxPotential);
    }


    String findBestNeighbor(String origin) {
        int originPressure = flowRates.get(origin);
        String bestNeighbor = "";
        int maxPressureIncrease = 0;

        for(String neighbor : tunnelNetwork.get(origin)) {
            ArrayList<String> neighborsOfNeighbor = tunnelNetwork.get(neighbor);
            int neighborPressure = flowRates.get(neighbor);

            // loop through each neighbor of neighbor
            for(String neighborOfNeighbor : neighborsOfNeighbor) {
                if(neighborOfNeighbor.equals(origin)) continue;
                if(neighborPressure + originPressure + flowRates.get(neighborOfNeighbor) > maxPressureIncrease) {
                    maxPressureIncrease = neighborPressure + originPressure + flowRates.get(neighborOfNeighbor);
                    bestNeighbor = neighbor;
                }
            }
        }

        return bestNeighbor;
    }

    int calculatePotentialTotal(String valve, int minutesElapsed) {
        int flow = flowRates.get(valve);
        return flow * (30 - minutesElapsed);
    }

    public static void main(String[] args) {
        DaySixteen day = new DaySixteen();
        day.readInput();
        //day.dfs("DD", 2, 0);
        System.out.println(day.findBestNeighbor("FF"));
    }
    // One minute to open a valve, One minute to go from one valve to another

    // Minute one
    //Open AA

    //minute two
    // travel to valve BB

    //minute three
    // Open valve BB
}
