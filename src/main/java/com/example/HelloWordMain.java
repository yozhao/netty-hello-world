package com.example;

public class HelloWordMain {
  public static void main(String[] args) throws InterruptedException {
    TcpServer tcpServer = new TcpServer();
    TcpClient tcpClient = new TcpClient();
    for (int i = 0; i < 10; ++i) {
      tcpClient.sendMsg("Hello World in Tcp mode, seq:" + i);
      Thread.sleep(100);
    }

    UdpClient udpClient = new UdpClient();
    UdpServer udpServer = new UdpServer();
    for (int i = 0; i < 10; ++i) {
      udpClient.sendMsg("Hello World in Udp mode, seq:" + i);
      Thread.sleep(100);
    }

    tcpServer.close();
    tcpClient.close();
    udpServer.close();
    udpClient.close();
  }
}
