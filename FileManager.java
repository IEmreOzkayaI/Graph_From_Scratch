package Graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private List<String> dataBase = new ArrayList<String>();
    private int vertexAmount = 0;

    public void readFile(String file) {
        try {

            Scanner scan = new Scanner(new File(file));
            scan.useDelimiter("\\W+");

            while (scan.hasNext()) {
                String val = scan.nextLine();
                String[] lineElements = val.split("\\s+");

                for (int i = 0; i < lineElements.length - 1; i++) {
                    if (!dataBase.contains(lineElements[i])) {
                        vertexAmount++;
                    }
                }

                for (int i = 0; i < lineElements.length; i++) {
                    dataBase.add(lineElements[i]);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFileSize() {
        return dataBase.size();
    }

    public List<String> getFile() {
        return dataBase;
    }

    public Iterator<String> getFileIterator() {
        return dataBase.iterator();
    }

    public int getVertexAmount() {
        return vertexAmount;
    }
}
