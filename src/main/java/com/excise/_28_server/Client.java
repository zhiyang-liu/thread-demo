package com.excise._28_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by liuzhiyang on 2020/11/20 下午2:12
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket client = new Socket();

        client.connect(new InetSocketAddress("localhost", 8000));

        PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println("Hello!");
        printWriter.flush();

        // bufferedReader.readLine()为阻塞方法，填满缓冲区或者遇到/n之类的才会返回
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("from server:" + bufferedReader.readLine());

        printWriter.println("Hello again!");
        printWriter.flush();

        System.out.println("from server:" + bufferedReader.readLine());

        printWriter.close();
        bufferedReader.close();
        client.close();
    }

}
