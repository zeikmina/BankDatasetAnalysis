package sorting;

import java.io.*;
import java.util.*;

public class InsertionSort {
    public static void main(String[] args) {
        String inputPath = "input/average_case_bankdataset.csv";
        String outputPath = "output/insertion_sorted.csv";

        if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        System.out.println("Reading input file: " + inputPath);
        List<Record> records = readCSV(inputPath);
        System.out.println("Finished reading " + records.size() + " records");

        Record[] arr = records.toArray(new Record[0]);

        System.out.println("⚡ Starting Insertion Sort...");
        long start = System.nanoTime();
        long counter = insertionSort(arr);
        long end = System.nanoTime();
        System.out.println("Sorting complete");

        System.out.println("Insertion Sort executed statements: " + counter);
        System.out.println("Execution time: " + ((end - start) / 1_000_000) + " ms");

        System.out.println("Writing sorted output to: " + outputPath);
        writeCSV(arr, outputPath);
        System.out.println("File saved: " + outputPath);
    }

    public static long insertionSort(Record[] arr) {
        long counter = 0;
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Record key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].getLocation().compareTo(key.getLocation()) > 0) {
                arr[j + 1] = arr[j];
                j--;
                counter += 2; // comparison + shift
            }
            arr[j + 1] = key;
            counter++;
            if (i % 100 == 0) {
                System.out.println("...Insertion Sort progress: " + i + "/" + n);
            }
        }
        return counter;
    }

    private static List<Record> readCSV(String filePath) {
        List<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                records.add(new Record(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
                count++;
                if (count % 100000 == 0) {
                    System.out.println("...loaded " + count + " records");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static void writeCSV(Record[] arr, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("date,domain,location,value,transaction_count\n");
            for (int i = 0; i < arr.length; i++) {
                bw.write(arr[i].toString());
                if (i < arr.length - 1) {
                    bw.newLine();
                }
                if (i % 100000 == 0 && i > 0) {
                    System.out.println("...written " + i + " records");
                }
            }
            System.out.println("Sorted dataset written to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
