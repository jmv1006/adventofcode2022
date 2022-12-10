package src.days.seven;

import java.io.File;
import java.io.FileNotFoundException;
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

    void findSizes(String[] directions) {
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
                    //go to the given subdirectory
                    Directory subDir = currDir.findSubDir(directionArr[2]);
                    dirStack.add(subDir);
                    dirs.add(subDir);
                }
                continue;
            }

            //create a sub directory
            if(directionArr[0].equals("dir")) {
                currDir.addSubdir(new Directory(directionArr[1]));
                continue;
            }

            //is a file
            if(!directionArr[0].equals("$")) currDir.addFile(new src.days.seven.File(Integer.parseInt(directionArr[0]), directionArr[1]));
        }

        int homeDirSize = 0;
        for(Directory dir : dirs) {
            int size = getDirSize(dir);
            if(dir.name.equals("/")) homeDirSize = size;
        }

        int total_space = 70_000_000;
        int unused_space = total_space - homeDirSize;
        int required_space = 30_000_000 - unused_space;

        int smallest = homeDirSize;
        for(Directory dir: dirs) {
            int size = getDirSize(dir);
            if(size > required_space) {
                smallest = Math.min(smallest, size);
            }
        }
        System.out.println(smallest);
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
        day.findSizes(directions);
    }
}
