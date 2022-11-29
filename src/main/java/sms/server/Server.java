package sms.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        ThreadPoolExecutor executor;
        try {
            serverSocket = new ServerSocket(9999);
            executor = new ThreadPoolExecutor(5, 6, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2));
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable r = () -> {
                    Scanner scanner;
                    String message;
                    String sendMessage;

                    InputStream inputStream;
                    InputStreamReader inputStreamReader;

                    OutputStreamWriter outputStreamWriter;
                    OutputStream outputStream;

                    BufferedReader bufferedReader;
                    BufferedWriter bufferedWriter;

                    System.out.println("---A new client has established a connection---");

                    scanner = new Scanner(System.in);
                    try {
                        //InputStream
                        inputStream = socket.getInputStream();
                        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                        bufferedReader = new BufferedReader(inputStreamReader);

                        //OutputStream
                        outputStream = socket.getOutputStream();
                        outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                        bufferedWriter = new BufferedWriter(outputStreamWriter);

                        while (true) {
                            //getMessage
                            message = bufferedReader.readLine();
                            if (message.equals("!end")) {
                                System.out.println("Received Message: " + message);
                                executor.shutdown();
                            }else {
                                System.out.println("Received Message: " + message);
                            }
                            //sendMessage
                            sendMessage = scanner.next();
                            if (sendMessage.equals("!end")) {
                                bufferedWriter.write(sendMessage);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                bufferedReader.close();
                            } else {
                                bufferedWriter.write(sendMessage);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                                System.out.println("Sent Message: " + sendMessage);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
                executor.execute(r);
                executor.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
