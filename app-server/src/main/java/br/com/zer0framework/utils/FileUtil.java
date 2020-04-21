package br.com.zer0framework.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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


    public static String stringRandomizer(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
