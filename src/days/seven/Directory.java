package src.days.seven;

import java.util.ArrayList;
import java.util.HashMap;

public class Directory {
    public String name;
    public ArrayList<File> files = new ArrayList<>();
    public ArrayList<Directory> subDirs = new ArrayList<>();

    public Directory(String initialName) {
        name = initialName;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addSubdir(Directory subDir) {
        subDirs.add(subDir);
    }

    public Directory findSubDir(String dirName) {
        for(Directory subdir : subDirs) {
            if (subdir.name.equals(dirName)) return subdir;
        }
        return null;
    }
}
