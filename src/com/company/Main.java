package com.company;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int maxCountA;
    private static int maxCountB;
    private static int maxCountC;
    private static final Thread fillQueue = new Thread(Main::fillQueues);
    private static final Thread analyzerA = new Thread(Main::analyzeLetterA);
    private static final Thread analyzerB = new Thread(Main::analyzeLetterB);
    private static final Thread analyzerC = new Thread(Main::analyzeLetterC);

    private static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        fillQueue.start();
        analyzerA.start();
        analyzerB.start();
        analyzerC.start();

        System.out.println("Analyzing in process...");

        analyzerA.join();
        analyzerB.join();
        analyzerC.join();

        System.out.println("max A: " + maxCountA);
        System.out.println("max B: " + maxCountB);
        System.out.println("max C: " + maxCountC);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void fillQueues() {
        try {
            for (int i = 0; i < 10_000; i++) {
                queueA.put(generateText("abc", 100_000));
                queueB.put(generateText("abc", 100_000));
                queueC.put(generateText("abc", 100_000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeLetterA() {
        try {
            while (true) {
                String text = queueA.poll(1000, TimeUnit.MILLISECONDS);
                if (text == null) {
                    break;
                }
                int currentCountA = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'a') {
                        currentCountA++;
                    }
                }
                if (maxCountA < currentCountA) {
                    maxCountA = currentCountA;
                }
            }
            System.out.println("Analyzing queueA is finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void analyzeLetterB() {
        try {
            while (true) {
                String text = queueB.poll(1000, TimeUnit.MILLISECONDS);
                if (text == null) {
                    break;
                }
                int currentCountB = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'b') {
                        currentCountB++;
                    }
                }
                if (maxCountB < currentCountB) {
                    maxCountB = currentCountB;
                }
            }
            System.out.println("Analyzing queueB is finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeLetterC() {
        try {
            while (true) {
                String text = queueC.poll(1000, TimeUnit.MILLISECONDS);
                if (text == null) {
                    break;
                }
                int currentCountC = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'a') {
                        currentCountC++;
                    }
                }
                if (maxCountC < currentCountC) {
                    maxCountC = currentCountC;
                }
            }
            System.out.println("Analyzing queueC is finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

