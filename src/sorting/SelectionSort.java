package sorting;

import java.io.*;
import java.util.*;

public class SelectionSort {
    public static void main(String[] args) {
        String inputPath = "input/average_case_bankdataset.csv";
        String outputPath = "output/selection_sorted.csv";

        if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        System.out.println("Reading input file: " + inputPath);
        List<Record> records = readCSV(inputPath);
        System.out.println("Finished reading " + records.size() + " records");

        Record[] arr = records.toArray(new Record[0]);

        System.out.println("⚡ Starting Selection Sort...");
        long start = System.nanoTime();
        long counter = selectionSort(arr);
        long end = System.nanoTime();
        System.out.println("Sorting complete");

        System.out.println("Selection Sort executed statements: " + counter);
        System.out.println("Execution time: " + ((end - start) / 1_000_000) + " ms");

        System.out.println("💾 Writing sorted output to: " + outputPath);
        writeCSV(arr, outputPath);
        System.out.println("File saved: " + outputPath);
    }

    public static long selectionSort(Record[] arr) {
        long counter = 0;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                counter++;
                if (arr[j].getLocation().compareTo(arr[minIndex].getLocation()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Record temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                counter += 3; // swap
            }
            if (i % 10000 == 0) {
                System.out.println("...Selection Sort progress: " + i + "/" + n);
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
