package sorting;

import java.io.*;
import java.util.*;

public class BubbleSort {
    public static void main(String[] args) {
        // Default paths
        String inputPath = "input/input.csv";
        String outputPath = "output/bubble_sorted.csv";

        // If user provides arguments, override
        if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        List<Record> records = readCSV(inputPath);
        Record[] arr = records.toArray(new Record[0]);

        long start = System.nanoTime();
        long counter = bubbleSort(arr);
        long end = System.nanoTime();

        System.out.println("Bubble Sort executed statements: " + counter);
        System.out.println("Execution time: " + ((end - start) / 1_000_000) + " ms");

        writeCSV(arr, outputPath);
    }

    public static long bubbleSort(Record[] arr) {
        long counter = 0;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                counter++; // comparison
                if (arr[j].getLocation().compareTo(arr[j + 1].getLocation()) > 0) {
                    Record temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    counter += 3; // swap assignments
                }
            }
        }
        return counter;
    }

    private static List<Record> readCSV(String filePath) {
        List<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                records.add(new Record(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static void writeCSV(Record[] arr, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("date,domain,location,value,transaction_count\n");
            for (Record r : arr) {
                bw.write(r.toString() + "\n");
            }
            System.out.println("Sorted dataset written to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
