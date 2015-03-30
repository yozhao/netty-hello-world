package com.example;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import static org.jboss.netty.channel.Channels.pipeline;

public class TcpClient {
  private ChannelFuture future;
  private ClientBootstrap bootstrap;

  public TcpClient() throws InterruptedException {
    bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory());

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipleline = pipeline();
        pipleline.addLast("encode", new StringEncoder());
        pipleline.addLast("decode", new StringDecoder());
        pipleline.addLast("handler", new SimpleHandler("TcpClient"));
        return pipleline;
      }
    });
    future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync();
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