/*
 * MIT License
 *
 * Copyright (c) 2021-2099 Oscura (xingshuang) <xingshuang_cool@163.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xingshuangs.iot.protocol.rtsp.service;


import com.github.xingshuangs.iot.protocol.rtp.model.frame.H264VideoFrame;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author xingshuang
 */
public class DemoRtspTcpNoAuthenticator {

    public static void main(String[] args) {
        // rtsp的地址
        URI uri = URI.create("rtsp://127.0.0.1:8554/11");
        // 构建RTSP客户端对象
        RtspClient client = new RtspClient(uri);
        // 设置RTSP交互过程信息打印，若不需要则可以不设置
        client.onCommCallback(System.out::println);
        // 设置接收的视频数据帧事件
        client.onFrameHandle(x -> {
            H264VideoFrame f = (H264VideoFrame) x;
            System.out.println(f.getFrameType() + ", " + f.getNaluType() + ", " + f.getTimestamp() + ", " + f.getFrameSegment().length);
        });
        client.onDestroyHandle(() -> System.out.println("close"));
        // 采用异步的形式关闭，由于是测试示例，写在启动前面
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            client.stop();
        });
        // 启动，返回异步执行对象
        CompletableFuture<Void> future = client.start();
        // 循环等待结束
        while (!future.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
