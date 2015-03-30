package com.example;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import static org.jboss.netty.channel.Channels.pipeline;

public class UdpServer {
  public ConnectionlessBootstrap bootstrap;

  public UdpServer() {
    bootstrap = new ConnectionlessBootstrap(new NioDatagramChannelFactory());
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipleline = pipeline();
        pipleline.addLast("encode", new StringEncoder());
        pipleline.addLast("decode", new StringDecoder());
        pipleline.addLast("handler", new SimpleHandler("UdpServer"));
        return pipleline;
      }
    });
    bootstrap.bind(new InetSocketAddress(8080));
    System.out.println("Udp is started at: " + new InetSocketAddress(8080));
  }

  public void close() {
    bootstrap.shutdown();
  }
}