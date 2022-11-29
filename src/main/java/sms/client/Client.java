package sms.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        Scanner scanner;
        String message;
        String sendMessage;

        InputStream inputStream;
        InputStreamReader inputStreamReader;

        OutputStream outputStream;
        OutputStreamWriter outputStreamWriter;

        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;

        scanner = new Scanner(System.in);
        try {
            socket = new Socket("47.98.101.188", 6000);

            System.out.println("---The client is already connected to the server---");

            //InputStream
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            //OutputStream
            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                //sendMessage
                sendMessage = scanner.next();
                if (sendMessage.equals("!end")) {
                    bufferedWriter.write(sendMessage);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    bufferedReader.close();
                } else {
                    System.out.println("Sent Message: " + sendMessage);
                    bufferedWriter.write(sendMessage);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                //getMessage
                message = bufferedReader.readLine();
                if (message.equals("!end")) {
                    System.out.println("Received Message: " + message);
                    bufferedWriter.close();
                    bufferedReader.close();
                } else {
                    System.out.println("Received Message: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
