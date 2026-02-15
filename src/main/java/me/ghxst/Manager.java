package me.ghxst;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Manager {
    private boolean isRunning = false;
    private long serverTime;
    private Process serverProcess;
    ProcessBuilder pb = new ProcessBuilder(
            "java",
            "@user_jvm_args.txt",
            "@libraries/net/neoforged/neoforge/21.1.219/win_args.txt"
    );
    private final String folder = "directory to your folder";
    private final String batFile = "run.bat";

    public void manager() {
        new Thread(() -> {
            while (true) {
                DayOfWeek dow = LocalDate.now().getDayOfWeek();
                LocalTime now = LocalTime.now();
                LocalTime startTime = null;
                LocalTime endTime = null;

                switch (dow) {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY:
                        startTime = LocalTime.of(15, 30);
                        endTime = LocalTime.of(21, 0);
                        break;
                    case FRIDAY:
                        startTime = LocalTime.of(16, 30);
                        endTime = LocalTime.of(23, 30);
                        break;
                    case SATURDAY:
                        startTime = LocalTime.of(10, 30);
                        endTime = LocalTime.of(6, 0);
                        break;
                    case SUNDAY:
                        startTime = LocalTime.of(10, 30);
                        endTime = LocalTime.of(22, 00);
                        break;
                }

                boolean shouldBeOn;
                if (dow == DayOfWeek.SATURDAY) {
                    shouldBeOn = (now.isAfter(LocalTime.of(10, 30)) && now.isBefore(LocalTime.of(23, 59)))
                            || (now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.of(6, 0)));
                } else {
                    shouldBeOn = !now.isBefore(startTime) && now.isBefore(endTime);
                }

                if (!isRunning && shouldBeOn) {
                    try {
                        ProcessBuilder pb = new ProcessBuilder(
                                "java",
                                "@user_jvm_args.txt",
                                "@libraries/net/neoforged/neoforge/21.1.219/win_args.txt"
                        );
                        pb.directory(new File(folder));
                        pb.inheritIO();
                        serverProcess = pb.start();
                        pb.directory(new File(folder));
                        serverProcess = pb.start();
                        isRunning = true;
                        serverTime = System.currentTimeMillis();
                        System.out.println("Server started automatically!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (isRunning && !shouldBeOn && serverProcess != null) {
                    try {
                        PrintWriter writer = new PrintWriter(serverProcess.getOutputStream(), true);
                        writer.println("stop");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isRunning = false;
                    System.out.println("Server stopped automatically!");
                }

                try {
                    Thread.sleep(60_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DayOfWeek dow = LocalDate.now().getDayOfWeek();
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            String status = isRunning ? "ONLINE" : "OFFLINE";
            String uptime = "00:00:00";

            if (isRunning) {
                long elapsed = System.currentTimeMillis() - serverTime;
                long hours = elapsed / (1000 * 60 * 60);
                long minutes = (elapsed / (1000 * 60)) % 60;
                long seconds = (elapsed / 1000) % 60;
                uptime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }

            LocalTime startTime = null;
            LocalTime endTime = null;
            switch (dow) {
                case MONDAY, TUESDAY, WEDNESDAY, THURSDAY:
                    startTime = LocalTime.of(15, 30);
                    endTime = LocalTime.of(21, 0);
                    break;
                case FRIDAY:
                    startTime = LocalTime.of(16, 30);
                    endTime = LocalTime.of(23, 30);
                    break;
                case SATURDAY:
                    startTime = LocalTime.of(10, 30);
                    endTime = LocalTime.of(6, 0);
                    break;
                case SUNDAY:
                    startTime = LocalTime.of(10, 30);
                    endTime = LocalTime.of(22, 0);
                    break;
            }
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println(" " );
            System.out.println("----------------------------");
            System.out.println("Current Day: " + today);
            System.out.println("Current Time: " + now.getHour() + ":" + now.getMinute());
            System.out.println("Scheduled Start: " + startTime);
            System.out.println("Scheduled End: " + endTime);
            System.out.println("Server Status: " + status);
            System.out.println("Uptime: " + uptime);
            System.out.println("----------------------------");
            System.out.println("1. Quit");

            if (scanner.hasNextInt() && scanner.nextInt() == 1) {
                System.exit(0);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}