
package com.excise._30_aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 单线程异步处理多个客户端
 */
public class AIOEchoServer {

    public final static int PORT = 8000;

    private AsynchronousServerSocketChannel server;

    public AIOEchoServer() throws IOException {
        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start() {
        System.out.println("Server listen on " + PORT);
        // 注册事件和事件完成后的处理器，accept会立即返回，并不会等待客户端的到来
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 异步方法成功时候调用
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    // 执行该方法意味着有客户连接成功，使用read读取客户数据，read方法也是异步的，返回的结果是future
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    // 将从客户端读取的数据返回给客户端
                    writeResult = result.write(buffer);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.accept(null, this);
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }

            // 失败时候调用
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed: " + exc);
            }
        });
    }

    public static void main(String args[]) throws Exception {
        new AIOEchoServer().start();
        // 主线程可以继续自己的行为，是必须的，否则start是异步的，不等客户端到来，程序已经运行完成，主线程退出
        while (true) {
            Thread.sleep(1000);
        }
    }

}
