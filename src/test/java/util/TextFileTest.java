package util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class TextFileTest {

    TextFile textFile = new TextFile();

    @Test
    public void should_return_empty_if_file_not_exists()
            throws Exception {
        assertThat(textFile.readFromTextFile(""), is(""));
    }

    @Test
    public void should_return_all_contents_for_text_file()
            throws Exception {
        String file = textFile.getResPath("test.txt");
        assertThat(textFile.readFromTextFile(file),
                is("It's just for test!\n"));
    }
}