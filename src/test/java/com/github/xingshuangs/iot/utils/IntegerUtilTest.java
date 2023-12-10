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

package com.github.xingshuangs.iot.utils;

import org.junit.Test;

import static org.junit.Assert.*;


public class IntegerUtilTest {

    @Test
    public void toByteArray() {
        byte[] actual = IntegerUtil.toByteArray(2111286);
        byte[] expect = new byte[]{(byte) 0x00, (byte) 0x20, (byte) 0x37, (byte) 0x36};
        assertArrayEquals(expect, actual);
    }

    @Test
    public void toByteArray1() {
        byte[] actual = IntegerUtil.toByteArray(2111286, true);
        byte[] expect = new byte[]{(byte) 0x36, (byte) 0x37, (byte) 0x20, (byte) 0x00};
        assertArrayEquals(expect, actual);
    }

    @Test
    public void toInt32() {
        byte[] data = new byte[]{(byte) 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        int actual = IntegerUtil.toInt32(data);
        assertEquals(2147483647, actual);
        data = new byte[]{(byte) 0x00, (byte) 0x3B, (byte) 0x3B, (byte) 0xA1};
        actual = IntegerUtil.toInt32(data, 0, false);
        assertEquals(3881889, actual);
        data = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        actual = IntegerUtil.toInt32(data, 0, false);
        assertEquals(-1, actual);
        data = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        actual = IntegerUtil.toInt32(data, 0, false);
        assertEquals(-2147483648, actual);
        data = new byte[]{(byte) 0x8F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        actual = IntegerUtil.toInt32(data, 0, false);
        assertEquals(-1879048193, actual);

        data = new byte[]{(byte) 0x1F, (byte) 0x3B, (byte) 0x3B, (byte) 0xA1};
        actual = IntegerUtil.toInt32(data, 0, true);
        assertEquals(-1589953761, actual);
    }

    @Test
    public void toUInt32() {
        byte[] data = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        long actual = IntegerUtil.toUInt32(data);
        assertEquals(4294967295L, actual);
        data = new byte[]{(byte) 0x1F, (byte) 0x3B, (byte) 0x3B, (byte) 0xA1};
        actual = IntegerUtil.toUInt32(data, 0, false);
        assertEquals(523975585L, actual);

        data = new byte[]{(byte) 0x1F, (byte) 0x3B, (byte) 0x3B, (byte) 0xA1};
        actual = IntegerUtil.toUInt32(data, 0, true);
        assertEquals(2705013535L, actual);
    }
}