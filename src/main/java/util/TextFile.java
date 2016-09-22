package util;

import java.io.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class TextFile {

    public String getResPath(String res) {
        return getClass().getClassLoader()
                .getResource(res).getPath();
    }

    public String readFromTextFile(String filename) {
        String text = "";

        try {
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }
            reader.close();
        } catch (IOException e) {

        }

        return text;
    }

}
