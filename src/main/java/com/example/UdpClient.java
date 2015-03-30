package com.example;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import static org.jboss.netty.channel.Channels.pipeline;

public class UdpClient {
  private ChannelFuture future;
  private ClientBootstrap bootstrap;

  public UdpClient() throws InterruptedException {

    bootstrap = new ClientBootstrap(new NioDatagramChannelFactory());

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipleline = pipeline();
        pipleline.addLast("encode", new StringEncoder());
        pipleline.addLast("decode", new StringDecoder());
        pipleline.addLast("handler", new SimpleHandler("UdpClient"));
        return pipleline;
      }
    });
    future = bootstrap.connect(new InetSocketAddress(8080));

  }

  public void sendMsg(String msg) {
    future.getChannel().write(msg);
    System.out.println("Sent msg: " + msg);
  }

  public void close() {
    future.getChannel().unbind();
    future.getChannel().close();
    bootstrap.shutdown();
  }
}