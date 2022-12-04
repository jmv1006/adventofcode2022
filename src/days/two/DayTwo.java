package src.days.two;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTwo {
    HashMap<String, Integer> shapeValues = new HashMap<>();
    HashMap<String, String> equivilents = new HashMap<>();
    HashMap<String, String> winningPairs = new HashMap<>();
    HashMap<String, String> losingPairs = new HashMap<>();
    ArrayList<String[]> readInput() {
        ArrayList<String[]> pairs = new ArrayList<>();

        try {
            File myObj = new File("src/days/two/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                pairs.add(data.split(" "));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return pairs;
    }

    int playRound(String elfChoice, String userChoice) {
        int roundTotal = 0;
        roundTotal += shapeValues.get(userChoice);

        if(equivilents.get(elfChoice).equals(userChoice)) {
            roundTotal += 3;
        } else if(userChoice.equals("X") && elfChoice.equals("C")) {
            roundTotal += 6;
        } else if(userChoice.equals("Y") && elfChoice.equals("A")) {
            roundTotal += 6;
        } else if(userChoice.equals("Z") && elfChoice.equals("B")) {
            roundTotal += 6;
        }

        return roundTotal;
    }

    int playRoundTwo(String elfChoice, String outcome) {
        int roundTotal = 0;

        if(outcome.equals("X")) {
            //Lose
            String losingLetter = losingPairs.get(elfChoice);
            roundTotal += shapeValues.get(losingLetter);
        } else if(outcome.equals("Y")) {
            //Draw
            String drawLetter = equivilents.get(elfChoice);
            roundTotal += shapeValues.get(drawLetter);
            roundTotal += 3;

        } else {
            //I Win
            String winningLetter = winningPairs.get(elfChoice);
            roundTotal += shapeValues.get(winningLetter);
            roundTotal += 6;
        }
        return roundTotal;
    }

    void startGame(ArrayList<String[]> pairs) {
        int totalScore = 0;
        for(String[] pair : pairs) {
            totalScore += playRoundTwo(pair[0], pair[1]);
        }
        System.out.println(totalScore);
    }

    public static void main(String[] args) {
        DayTwo day = new DayTwo();
        day.shapeValues.put("X", 1);
        day.shapeValues.put("Y", 2);
        day.shapeValues.put("Z", 3);

        day.equivilents.put("A", "X");
        day.equivilents.put("B", "Y");
        day.equivilents.put("C", "Z");

        day.winningPairs.put("A", "Y");
        day.winningPairs.put("B", "Z");
        day.winningPairs.put("C", "X");

        day.losingPairs.put("A", "Z");
        day.losingPairs.put("B", "X");
        day.losingPairs.put("C", "Y");

        day.startGame(day.readInput());

        // A = rock, B = paper, C = scissors
        // X = rock(1), Y = paper(2), Z = scissors(3)
        // 0 if lost, 3 if draw, 6 if win

        // Total score = item you selected + result
    }
}
