package src.days.ten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTen {
    int xValue = 1;

    int pixelPos = 0;

    String[] crt = new String[6];
    int crtRow = 0;
    String currentCRTString = "";

    List<String> readInput() {
        List<String> commands = new ArrayList<>();

        try {
            File myObj = new File("src/days/ten/input.txt");
            Scanner myReader = new Scanner(myObj);

            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                commands.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return commands;
    }


    void handleCycle() {
        currentCRTString = currentCRTString + checkSprite(xValue);
        if(pixelPos == 39) {
            crt[crtRow] = currentCRTString;
            currentCRTString = "";
            crtRow += 1;
            pixelPos = -1;
        }
    }

    String checkSprite(int sprite) {
        if(sprite - 1 == pixelPos || sprite == pixelPos || sprite + 1 == pixelPos) {
            return "#";
        }
        else {
            return ".";
        }
    }
    void startCPU(List<String> commands) {
        for(String command : commands) {
            String[] commandArr = command.split(" ");

            if(commandArr.length > 1) {
                int value = Integer.parseInt(commandArr[1]);

                handleCycle();
                pixelPos += 1;

                handleCycle();
                pixelPos += 1;

                xValue += value;
            } else {
                // noop
                handleCycle();
                pixelPos += 1;
            }
        }
        for(String row: crt) {
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        DayTen day = new DayTen();
        List<String> commands = day.readInput();
        day.startCPU(commands);

    }
}
