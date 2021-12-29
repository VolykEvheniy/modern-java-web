package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.company.Main.RESULT_FILE_PATH;

public class SearchingNums implements Callable<Double> {
    private File dir;
    private ExecutorService executor;

    SearchingNums (File dir, ExecutorService executor){
        this.dir = dir;
        this.executor = executor;
    }

    @Override
    public Double call() {
        double num = 0;
        try {
            File[] files = dir.listFiles();
            List<Future<Double>> result = new ArrayList<>();

            for (File i: files) {
                if (i.isDirectory()){
                    SearchingNums search = new SearchingNums(i, executor);
                    result.add(executor.submit(search));
                } else {
                    if (i.getName().endsWith(".txt")){
                        num += finding(i);
                    }
                }
            }
            for (Future<Double> rez : result){
                num += rez.get();
            }

        } catch (ExecutionException | InterruptedException e){
            System.out.println(e);
        }
        return num;
    }

    public double finding(File file){
        double result = 0;

        try(Scanner sc = new Scanner((new FileInputStream(file)))){
            while (sc.hasNextLine()){
                double var = 0;
                String str = sc.nextLine();
                Matcher m = Pattern.compile("(\\d+(?:\\.\\d+)?)").matcher(str);
                //D:\KPI 2021\Сучасний Java Web\test
                while (m.find()) {
                    var += Double.parseDouble(m.group(1));
                }
                result +=var;
            }
        }catch (IOException e){
            System.out.println(e);
        }
        String fileName = file.getName();
        writeToFile(fileName, result);
        return result;
    }

    public static void writeToFile(String fileName, double numbers){
        File file = new File(RESULT_FILE_PATH);
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
            output.write(fileName + " " + numbers + "\n");
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
