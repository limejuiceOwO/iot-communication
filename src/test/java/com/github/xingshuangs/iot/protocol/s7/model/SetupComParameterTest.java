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

package com.github.xingshuangs.iot.protocol.s7.model;

import com.github.xingshuangs.iot.protocol.s7.enums.EFunctionCode;
import org.junit.Test;

import static org.junit.Assert.*;


public class SetupComParameterTest {

    @Test
    public void byteArrayLength() {
        SetupComParameter setupComParameter = new SetupComParameter();
        assertEquals(8, setupComParameter.byteArrayLength());
    }

    @Test
    public void toByteArray() {
        SetupComParameter setupComParameter = new SetupComParameter();
        setupComParameter.setFunctionCode(EFunctionCode.READ_VARIABLE);
        setupComParameter.setReserved((byte) 0x00);
        setupComParameter.setMaxAmqCaller(0x0001);
        setupComParameter.setMaxAmqCallee(0x0001);
        setupComParameter.setPduLength(0x0004);
        byte[] actual = setupComParameter.toByteArray();
        byte[] expect = {(byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x04};
        assertArrayEquals(expect, actual);
    }

    @Test
    public void parameter() {
        Parameter parameter = new SetupComParameter();
        assertEquals(SetupComParameter.BYTE_LENGTH, parameter.byteArrayLength());
    }
}