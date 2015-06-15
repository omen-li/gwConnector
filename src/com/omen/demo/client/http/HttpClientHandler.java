package com.omen.demo.client.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
 




import java.nio.charset.Charset;

import com.omen.demo.utils.MyLogger;
 
/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 14-6-25
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        MyLogger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        DefaultFullHttpResponse fullHttpResponse = (DefaultFullHttpResponse) msg;
 
        MyLogger.debug("Client recv STATUS: " + fullHttpResponse.getStatus());
        MyLogger.debug("Client recv VERSION: " + fullHttpResponse.getProtocolVersion());
 
        if (!fullHttpResponse.headers().isEmpty()) {
            for (String name : fullHttpResponse.headers().names()) {
                for (String value : fullHttpResponse.headers().getAll(name)) {
                    MyLogger.debug("Client recv HEADER: " + name + " = " + value);
                }
            }
        }
 
        if (HttpHeaders.isTransferEncodingChunked(fullHttpResponse)) {
            MyLogger.debug("Client recv CHUNKED CONTENT {");
        } else {
            MyLogger.debug("Client recv CONTENT {");
            ByteBuf content = fullHttpResponse.content();
            MyLogger.info(content.toString(CharsetUtil.UTF_8));
            MyLogger.debug("Client recv } END OF CONTENT");
        }
    }
 
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
 

}