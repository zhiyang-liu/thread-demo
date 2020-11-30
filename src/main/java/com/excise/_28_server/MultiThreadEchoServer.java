package com.excise._28_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * echo serve端
 * 接收客户端连接请求后，新开一个线程来执行，获取客户端输入并返回
 * Created by liuzhiyang on 2020/11/20 下午1:55
 */
public class MultiThreadEchoServer {

    private static ExecutorService tp = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {

        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader bufferedReader = null;
            PrintWriter printWriter = null;

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                printWriter = new PrintWriter(clientSocket.getOutputStream());

                String inputLine = null;
                while (true) { // 没有while true客户端不再输入后便会关闭连接了
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        System.out.println("client:" + inputLine);
                        printWriter.println(inputLine);
                        printWriter.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    clientSocket.close();
                    System.out.println("close" + clientSocket.getRemoteSocketAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ServerSocket echoServer = new ServerSocket(8000);
        Socket clientSocket = null;
        while (true) {
            clientSocket = echoServer.accept();
            System.out.println(clientSocket.getRemoteSocketAddress() + " connect !");
            tp.execute(new HandleMsg(clientSocket));
            System.out.println("start a thread to do");
        }
    }

}
