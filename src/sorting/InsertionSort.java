package sorting;

import java.io.*;
import java.util.*;

public class InsertionSort {
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
        long counter = insertionSort(arr);
        long end = System.nanoTime();

        System.out.println("Insertion Sort executed statements: " + counter);
        System.out.println("Execution time: " + ((end - start) / 1_000_000) + " ms");

        writeCSV(arr, outputPath);
    }

    public static long insertionSort(Record[] arr) {
        long counter = 0;
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            Record key = arr[i];
            int j = i - 1;
            counter++; // assignment of key

            while (j >= 0 && arr[j].getLocation().compareTo(key.getLocation()) > 0) {
                counter++; // comparison
                arr[j + 1] = arr[j];
                counter++; // assignment
                j--;
            }
            arr[j + 1] = key;
            counter++; // final placement
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
