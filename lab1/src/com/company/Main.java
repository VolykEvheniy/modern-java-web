package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
	public static final String RESULT_FILE_PATH = "D:\\KPI 2021\\Сучасний Java Web\\result.txt";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Введіть шлях до директорії:");
		String dir = sc.nextLine();

		File file;
		while (true) {
			file = new File(dir);
			if (!file.isDirectory()){
				System.out.println("Це не директорія, введіть шлях знову");
				dir = sc.nextLine();
			} else {
				System.out.println("Вітаю, ви ввели правильний шлях до директорії");
				break;
			}
		}
		ExecutorService executor = Executors.newFixedThreadPool(10);
		SearchingNums search = new SearchingNums(new File(dir), executor);
		Future<Double> task = executor.submit(search);

		try {
			System.out.println("Загальна сума: " + task.get());
			executor.shutdown();
			printFileWithResult();
			File result = new File(RESULT_FILE_PATH);
			result.delete();
		} catch (ExecutionException | InterruptedException e) {
			System.out.println(e);
		}
	}

	public static void printFileWithResult() {
		File result = new File(RESULT_FILE_PATH);
		try (Scanner sc = new Scanner((new FileInputStream(result)))) {
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
		} catch (IOException e){
			System.out.println(e);
		}
	}
}
