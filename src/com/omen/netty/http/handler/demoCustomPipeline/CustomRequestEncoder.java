package com.omen.netty.http.handler.demoCustomPipeline;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import static io.netty.handler.codec.http.HttpConstants.*;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class CustomRequestEncoder extends HttpObjectEncoder<HttpRequest> {
    private static final char SLASH = '/';
    private static final byte[] CRLF = { CR, LF };

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
        System.out.println("do noting");
    }
}
