package com.mycompany.plantcaresystem.util;

import com.mycompany.plantcaresystem.models.Plant;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHelper {

    public static void exportPlants(List<Plant> plants, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            for (Plant p : plants) {
                fw.write(p.getInfo() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Export I/O Error: " + e.getMessage());
        }
    }
}
