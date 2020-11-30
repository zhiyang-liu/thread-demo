
package com.excise._30_aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AIOClient {

    public static void main(String[] args) throws Exception {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        client.connect(new InetSocketAddress("localhost", 8000), null, new CompletionHandler<Void, Object>() {

            @Override
            public void completed(Void result, Object attachment) {
                // 向服务端发送数据
                client.write(ByteBuffer.wrap("Hello!".getBytes()), null, new CompletionHandler<Integer, Object>() {

                    // 写入完成后，通知回调接口
                    @Override
                    public void completed(Integer result, Object attachment) {
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            // 读取数据依然是异步的，成功后会调用completed方法
                            client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

                                @Override
                                public void completed(Integer result, ByteBuffer buffer) {
                                    buffer.flip();
                                    System.out.println(new String(buffer.array()));
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {}

                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {}

                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {}

        });
        // 由于主线程马上结束，这里等待上述处理全部完成
        Thread.sleep(1000);
    }

}
