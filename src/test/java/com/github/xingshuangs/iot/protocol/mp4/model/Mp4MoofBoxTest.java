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

package com.github.xingshuangs.iot.protocol.mp4.model;

import com.github.xingshuangs.iot.protocol.mp4.enums.EMp4Type;
import com.github.xingshuangs.iot.utils.HexUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;


public class Mp4MoofBoxTest {

    private List<Mp4SampleData> samples;

    private Mp4TrackInfo trackInfo;

    @Before
    public void init() {
        this.samples = new ArrayList<>();
        Mp4SampleData data = new Mp4SampleData();
        data.setData(new byte[5459]);
        data.setDuration(3600);
        data.setCts(0);
        Mp4SampleFlag flag = new Mp4SampleFlag();
        flag.setDependedOn(2);
        data.setFlags(flag);
        this.samples.add(data);

        data = new Mp4SampleData();
        data.setData(new byte[4994]);
        data.setDuration(3600);
        data.setCts(0);
        flag = new Mp4SampleFlag();
        flag.setDependedOn(1);
        flag.setIsNonSync(1);
        data.setFlags(flag);
        this.samples.add(data);

        this.trackInfo = new Mp4TrackInfo();
        this.trackInfo.setId(1);
        this.trackInfo.setType("video");
        this.trackInfo.setTimescale(90000);
        this.trackInfo.setDuration(90000);
        this.trackInfo.setWidth(1920);
        this.trackInfo.setHeight(1080);
        this.trackInfo.setSps(new byte[]{0x67, 0x64, 0x00, 0x2A, (byte) 0xAC, 0x2B, 0x50, 0x3C, 0x01, 0x13, (byte) 0xF2, (byte) 0xCD, (byte) 0xC0, 0x40, 0x40, 0x40, (byte) 0x80});
        this.trackInfo.setPps(new byte[]{0x68, (byte) 0xEE, 0x3C, (byte) 0xB0});
        this.trackInfo.setSampleData(this.samples);
    }

    @Test
    public void mp4Type() {
        System.out.println(HexUtil.toHexString(EMp4Type.TFHD.getByteArray()));
    }

    @Test
    public void mp4MoofBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, (byte) 0x82,
                0x6D, 0x6F, 0x6F, 0x66,

                0x00, 0x00, 0x00, 0x10,
                0x6D, 0x66, 0x68, 0x64,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x01,

                0x00, 0x00, 0x00, 0x6A,
                0x74, 0x72, 0x61, 0x66,

                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x68, 0x64,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x01,

                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x64, 0x74,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,

                0x00, 0x00, 0x00, 0x34,
                0x74, 0x72, 0x75, 0x6E,
                0x00, 0x00, 0x0F, 0x01,
                0x00, 0x00, 0x00, 0x02,
                0x00, 0x00, 0x00, (byte) 0x8A,
                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x15, 0x57,
                0x02, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x13, (byte) 0x86,
                0x01, 0x01, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,

                0x00, 0x00, 0x00, 0x0E,
                0x73, 0x64, 0x74, 0x70,
                0x00, 0x00, 0x00, 0x00,
                0x20, 0x10
        };
        Mp4MoofBox box = new Mp4MoofBox(1, 0, this.trackInfo);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4MfhdBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x10,
                0x6D, 0x66, 0x68, 0x64,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x01
        };
        Mp4MfhdBox box = new Mp4MfhdBox(1);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4TrafBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x6A,
                0x74, 0x72, 0x61, 0x66,

                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x68, 0x64,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x01,

                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x64, 0x74,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,

                0x00, 0x00, 0x00, 0x34,
                0x74, 0x72, 0x75, 0x6E,
                0x00, 0x00, 0x0F, 0x01,
                0x00, 0x00, 0x00, 0x02,
                0x00, 0x00, 0x00, (byte) 0x8A,
                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x15, 0x57,
                0x02, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x13, (byte) 0x86,
                0x01, 0x01, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,

                0x00, 0x00, 0x00, 0x0E,
                0x73, 0x64, 0x74, 0x70,
                0x00, 0x00, 0x00, 0x00,
                0x20, 0x10
        };
        Mp4TrafBox box = new Mp4TrafBox(0, this.trackInfo);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4TfhdBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x68, 0x64,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x01
        };
        Mp4TfhdBox box = new Mp4TfhdBox(1);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4TfdtBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x10,
                0x74, 0x66, 0x64, 0x74,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00
        };
        Mp4TfdtBox box = new Mp4TfdtBox(0);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4TrunBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x34,
                0x74, 0x72, 0x75, 0x6E,
                0x00, 0x00, 0x0F, 0x01,
                0x00, 0x00, 0x00, 0x02,
                0x00, 0x00, 0x00, (byte) 0x8A,

                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x15, 0x57,
                0x02, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,

                0x00, 0x00, 0x0E, 0x10,
                0x00, 0x00, 0x13, (byte) 0x86,
                0x01, 0x01, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
        };

        Mp4TrunBox box = new Mp4TrunBox(this.samples);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4SdtpBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x0E,
                0x73, 0x64, 0x74, 0x70,
                0x00, 0x00, 0x00, 0x00,
                0x20, 0x10
        };
        Mp4SdtpBox box = new Mp4SdtpBox(this.samples);
        assertArrayEquals(expect, box.toByteArray());
    }

    @Test
    public void mp4MdatBox() {
        byte[] expect = new byte[]{
                0x00, 0x00, 0x00, 0x0C,
                0x6D, 0x64, 0x61, 0x74,
                0x00, 0x00, 0x00, 0x00
        };
        Mp4MdatBox box = new Mp4MdatBox(new byte[4]);
        assertArrayEquals(expect, box.toByteArray());
    }
}