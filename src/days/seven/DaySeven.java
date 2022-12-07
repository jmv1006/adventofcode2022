package src.days.seven;

import java.awt.image.RasterOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class DaySeven {
    String[] readInput() {
        String[] directions = new String[1031];
        int i = 0;
        try {
            File myObj = new File("src/days/seven/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                directions[i] = data;
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return directions;
    }

    void test(String[] directions) {
        ArrayList<Directory> dirs = new ArrayList<>();
        Stack<Directory> dirStack = new Stack<>();

        Directory currDir = new Directory("/");
        dirStack.add(currDir);
        dirs.add(currDir);

        for(String direction : directions) {
            String[] directionArr = direction.split(" ");
            if(direction.equals("$ cd /")) continue;

            currDir = dirStack.peek();

            //changing current directory
            if(directionArr[0].equals("$") && directionArr[1].equals("cd")) {
                if(directionArr[2].equals("..")) {
                    dirStack.pop();
                } else {
                    //go to a sub directory
                    Directory subDir = currDir.findSubDir(directionArr[2]);
                    dirStack.add(subDir);
                    dirs.add(subDir);
                }
                continue;
            }

            //is a sub dir
            if(directionArr[0].equals("dir")) {
                currDir.addSubdir(new Directory(directionArr[1]));
                continue;
            }

            //is a file
            if(!directionArr[0].equals("$")) currDir.addFile(new src.days.seven.File(Integer.parseInt(directionArr[0]), directionArr[1]));
        }

        int sum = 0;
        for(Directory dir : dirs) {
            int size = getDirSize(dir);
            if(size <= 100000) sum += size;
        }
        System.out.println(sum);
    }

    int getDirSize(Directory dir) {
        if(dir.subDirs.size() == 0 && dir.files.size() == 0) return 0;
        int dirSize = 0;

        for(src.days.seven.File file : dir.files) {
            dirSize += file.size;
        }

        for(Directory subDir : dir.subDirs) {
            dirSize += getDirSize(subDir);
        }

        return dirSize;
    }

    public static void main(String[] args) {
        DaySeven day = new DaySeven();
        String[] directions = day.readInput();
        day.test(directions);
    }
}
