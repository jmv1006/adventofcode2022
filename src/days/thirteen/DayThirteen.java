package src.days.thirteen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayThirteen {
    ArrayList<String[]> readInput() {
        ArrayList<String[]> pairs = new ArrayList<>();
        try {
            File myObj = new File("src/days/thirteen/test.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String[] pair = new String[2];

                String left = myReader.nextLine();
                if(left.equals("")) continue;
                String right = myReader.nextLine();
                pair[0] = left;
                pair[1] = right;
                pairs.add(pair);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return pairs;
    }

    int getPacketValueSize(String packet) {
        int values = 0;
        Stack<String> subLists = new Stack<>();

        for(String character : packet.split("")) {
            if(character.equals("[")) {
                subLists.add(character);
                continue;
            }
            if(character.equals("]")) {
                subLists.pop();
                if(subLists.size() < 1) {
                    values += 1;
                }
                continue;
            }
            if(!character.equals(",") && !character.equals("") && subLists.size() == 0) {
                //character not in a sublist
                values += 1;
            }
        }

        return values;
    }

    int getPacketValueSizeTest(String packet) {
        int values = 0;
        Stack<String> subLists = new Stack<>();

        for(String character : packet.split("")) {
            if(character.equals("[")) {
                subLists.add(character);
                continue;
            }
            if(character.equals("]")) {
                subLists.pop();
                if(subLists.size() < 1) {
                    values += 1;
                }
                continue;
            }
            if(!character.equals(",") && !character.equals("") && subLists.size() == 0) {
                //character not in a sublist
                values += 1;
            }
        }

        return values;
    }

    boolean compareRecursive(String valueOne, String valueTwo) {
        if(valueOne.length() == 0 && valueTwo.length() == 0) return true;

        int valuesInOne = getPacketValueSizeTest(valueOne);
        //int valuesInTwo = getPacketValueSize(valueTwo);


        System.out.println(valuesInOne);
        //System.out.println(valueOne + " : " + valueTwo);
        return false;
    }

    void iterate(ArrayList<String[]> pairs) {
        String[] testPair = new String[] {"[6,[8, 9, 10]]", "[6,[9, 10, 11]]"};
        int inOrderCount = 0;

        compareRecursive(testPair[0].substring(1, testPair[0].length() - 1), testPair[1].substring(1, testPair[1].length() - 1));

        /*
        for(String[] pair : pairs) {
            boolean inOrder = compareRecursive(pair[0].substring(1, pair[0].length() - 1), pair[1].substring(1, pair[1].length() - 1));
            if(inOrder) inOrderCount += 1;
        }
         */
    }

    public static void main(String[] args) {
        DayThirteen day = new DayThirteen();
        ArrayList<String[]> pairs = day.readInput();
        day.iterate(pairs);
    }
}
