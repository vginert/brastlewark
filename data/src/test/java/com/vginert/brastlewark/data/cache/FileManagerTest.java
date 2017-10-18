package com.vginert.brastlewark.data.cache;

import com.vginert.brastlewark.data.ApplicationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileManagerTest extends ApplicationTestCase {

    private FileManager fileManager;

    @Before
    public void setUp() {
        fileManager = new FileManager();
    }

    @After
    public void tearDown() {
        if (cacheDir() != null) {
            fileManager.clearDirectory(cacheDir());
        }
    }

    @Test
    public void testWriteToFile() {
        File fileToWrite = createDummyFile();
        String fileContent = "content";

        fileManager.writeToFile(fileToWrite, fileContent);

        assertThat(fileToWrite.exists(), is(true));
    }

    @Test
    public void testFileContent() {
        File fileToWrite = createDummyFile();
        String fileContent = "content\n";

        fileManager.writeToFile(fileToWrite, fileContent);
        String expectedContent = fileManager.readFileContent(fileToWrite);

        assertThat(expectedContent, is(equalTo(fileContent)));
    }

    private File createDummyFile() {
        String dummyFilePath = cacheDir().getPath() + File.separator + "dummyFile";
        return new File(dummyFilePath);
    }

    @Test
    public void testExist() {
        File fileToWrite = createDummyFile();
        String fileContent = "content";

        fileManager.writeToFile(fileToWrite, fileContent);

        assertThat(fileManager.exists(fileToWrite), is(true));
    }

    @Test
    public void testWriteReadPreferences() {
        final String FILE_NAME = "FILE_NAME";
        final String KEY = "KEY";
        final long value = 1000;
        fileManager.writeToPreferences(context(), FILE_NAME, KEY, value);
        final long valueReaded = fileManager.getFromPreferences(context(), FILE_NAME, KEY);
        assertThat(valueReaded, equalTo(value));
    }
}