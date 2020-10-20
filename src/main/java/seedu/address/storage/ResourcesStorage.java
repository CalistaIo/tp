package seedu.address.storage;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ResourcesStorage {

    private String filepath;

    public ResourcesStorage(String filepath) throws IOException {
        this.filepath = filepath;
        createFile();
    }

    public void createFile() throws IOException {
        File file = new File(filepath.substring(0, filepath.lastIndexOf("/")));
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(filepath);
        file.createNewFile();
    }

    public ArrayList<String> load() throws IOException {
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        ArrayList<String> links = new ArrayList<>();
        while (scanner.hasNext()) {
            links.add(scanner.nextLine());
        }
        scanner.close();
        return links;
    }

    public void save(ObservableList<String> lst) throws IOException {
        File file = new File(filepath);
        new FileWriter(file, false).close();
        FileWriter filewriter = new FileWriter(file, true);
        for (String link : lst) {
            filewriter.write(link + System.lineSeparator());
        }
        filewriter.close();
    }
}
