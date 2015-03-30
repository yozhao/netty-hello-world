package com.example;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class SimpleHandler extends SimpleChannelUpstreamHandler {
  private String name;
  public SimpleHandler(String name) {
    this.name = name;
  }
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("Receive message, message content:" + e.getMessage() + " in handler " + name);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    System.err.println("Exception caught:" + e.getCause() + " in handler " + name);
    e.getChannel().close();
  }
}