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

package com.github.xingshuangs.iot.protocol.s7.service;


import com.github.xingshuangs.iot.protocol.s7.enums.*;
import com.github.xingshuangs.iot.protocol.s7.model.*;

/**
 * @author xingshuang
 */
public class DemoS7Custom {

    public static void main(String[] args) {
        S7PLC s7PLC = new S7PLC(EPlcType.S1200, "127.0.0.1");

        // bit数据读写
        byte[] expect = new byte[]{(byte) 0x00};
        s7PLC.writeRaw(EParamVariableType.BIT, 1, EArea.DATA_BLOCKS, 1, 0, 3,
                EDataVariableType.BIT, expect);
        byte[] actual = s7PLC.readRaw(EParamVariableType.BIT, 1, EArea.DATA_BLOCKS, 1, 0, 3);

        // byte数据读写
        expect = new byte[]{(byte) 0x02, (byte) 0x03};
        s7PLC.writeRaw(EParamVariableType.BYTE, 2, EArea.DATA_BLOCKS, 1, 1, 0,
                EDataVariableType.BYTE_WORD_DWORD, expect);
        byte[] actual1 = s7PLC.readRaw(EParamVariableType.BYTE, 2, EArea.DATA_BLOCKS, 1, 1, 0);

        // 对象形式发送
        RequestNckItem item = new RequestNckItem(ENckArea.C_CHANNEL, 1, 23, 1, ENckModule.S, 1);
        S7Data s7Data = NckRequestBuilder.creatNckRequest(item);
        S7Data ackData = s7PLC.readFromServerByPersistence(s7Data);

        // 裸报文发送
        byte[] sendByteArray = new byte[]{
                // tpkt
                (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x1D,
                // cotp DT Data
                (byte) 0x02, (byte) 0xF0, (byte) 0x80,
                // header
                (byte) 0x32, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x00,
                // parameter
                (byte) 0x04, (byte) 0x01,
                // request item
                (byte) 0x12, (byte) 0x08, (byte) 0x82, (byte) 0x41, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x7f, (byte) 0x01
        };
        byte[] recByteArray = s7PLC.readFromServerByPersistence(sendByteArray);

        s7PLC.close();
    }
}
