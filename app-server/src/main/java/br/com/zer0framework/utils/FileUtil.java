package br.com.zer0framework.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> getAllFileNamesInDirectory( String path ) {
        File root = new File(path);
        File[] list = root.listFiles();

        List<String> names = new ArrayList<>();

        assert list != null;
        for ( File f : list ) {
               names.add(f.getName());
        }
        return names;
    }
}
