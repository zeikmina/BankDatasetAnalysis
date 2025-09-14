package sorting;

import java.io.*;
import java.util.*;

public class CSVGenerator {

    public static void main(String[] args) {
        int size = 1000000; // adjust as needed
        generateCSV(size, "input/best_case_bankdataset.csv", "best");
        generateCSV(size, "input/worst_case_bankdataset.csv", "worst");
        generateCSV(size, "input/average_case_bankdataset.csv", "average");
    }

    public static void generateCSV(int size, String filePath, String mode) {
        List<Record> records = new ArrayList<>();

        // Generate synthetic data
        for (int i = 0; i < size; i++) {
            String date = "2025-01-" + String.format("%02d", (i % 30) + 1);
            String domain = "domain" + (i % 100) + ".com";
            String location = "Location" + i; // unique per row
            int value = (int) (Math.random() * 1000);
            int transactionCount = (int) (Math.random() * 100);

            records.add(new Record(date, domain, location, value, transactionCount));
        }

        // Sorting based on mode
        if (mode.equalsIgnoreCase("best")) {
            records.sort(Comparator.comparing(Record::getLocation));
        } else if (mode.equalsIgnoreCase("worst")) {
            records.sort(Comparator.comparing(Record::getLocation).reversed());
        } else if (mode.equalsIgnoreCase("average")) {
            Collections.shuffle(records);
        }

        // Write to CSV (no trailing blank lines)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("date,domain,location,value,transaction_count\n");
            for (int i = 0; i < records.size(); i++) {
                bw.write(records.get(i).toString());
                if (i < records.size() - 1) { // no extra newline at the end
                    bw.newLine();
                }
            }
            System.out.println(mode + " case CSV generated at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
