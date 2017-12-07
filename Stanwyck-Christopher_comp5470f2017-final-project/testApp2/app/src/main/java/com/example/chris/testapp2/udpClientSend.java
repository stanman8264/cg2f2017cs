package com.example.chris.testapp2;

import android.hardware.SensorEvent;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by Chris on 10/29/2017.
 */

public class udpClientSend extends Thread{
        String dstAddress;
        int dstPort=51000;
        DatagramSocket socket;
        InetAddress address;
        SensorEvent data;

        public udpClientSend(String address, SensorEvent recvData) {
            dstAddress = address;
            data = recvData;
        }

        @Override
        public void run() {
            try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(dstAddress);

                    byte[] buf = new byte[20];
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    buffer.putLong(data.timestamp);
                    buffer.putFloat(data.values[0]);
                    buffer.putFloat(data.values[1]);
                    buffer.putFloat(data.values[2]);

                    buffer.rewind();
                    buffer.get(buf);

                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, dstPort);
                    socket.send(packet);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }
            }
}
