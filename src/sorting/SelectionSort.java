package sorting;

import java.io.*;
import java.util.*;

public class SelectionSort {
    public static void main(String[] args) {

        String inputPath = "input/average_case_bankdataset.csv";
//        String inputPath = "input/best_case_bankdataset.csv";
//        String inputPath = "input/worst_case_bankdataset.csv";

        String outputPath = "output/bubble_sorted.csv";

        if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        List<Record> records = readCSV(inputPath);
        Record[] arr = records.toArray(new Record[0]);

        long start = System.nanoTime();
        long counter = selectionSort(arr);
        long end = System.nanoTime();

        System.out.println("Selection Sort executed statements: " + counter);
        System.out.println("Execution time: " + ((end - start) / 1_000_000) + " ms");

        writeCSV(arr, outputPath);
    }

    public static long selectionSort(Record[] arr) {
        long counter = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                counter++; // comparison
                if (arr[j].getLocation().compareTo(arr[minIndex].getLocation()) < 0) {
                    minIndex = j;
                    counter++; // assignment
                }
            }
            if (minIndex != i) {
                Record temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                counter += 3; // swap assignments
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
