package com.omen.demo.client.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
 









import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.omen.demo.busiPojo.RetData;
import com.omen.demo.client.http.initializer.HttpClientInitializer;
import com.omen.demo.utils.MyLogger;
import com.omen.netty.server.Main;
 
/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 14-6-25
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class Client {
 
	private static final String url = "http://localhost:8000";
	
    public static HttpRequest getRequestMethod(Map<String, String> parameter, String url, String method) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
 
        String path = uri.getRawPath();
        String host = uri.getHost();
 
        HttpRequest request = null;
        if ("post".equalsIgnoreCase(method)) {
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, path);
 
            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
            // This encoder will help to encode Request for a FORM as POST.
            HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, false);
            // add Form attribute
            if (parameter != null) {
                Set<Map.Entry<String, String>> entrySet = parameter.entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    String key = e.getKey();
                    String value = e.getValue();
                    bodyRequestEncoder.addBodyAttribute(key, value);
                }
                try {
                    request = bodyRequestEncoder.finalizeRequest();
                } catch (HttpPostRequestEncoder.ErrorDataEncoderException e) {
                    // if an encoding error occurs
                    e.printStackTrace();
                }
            }
            
            
            request.headers().set(HttpHeaders.Names.CONTENT_TYPE,"application/json");
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            request.headers().set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar")));
        } else if ("get".equalsIgnoreCase(method)) {
            //uri.toString()没有查询参数的uri
            QueryStringEncoder encoder = new QueryStringEncoder(uri.toString());
            if (parameter != null) {
                Set<Map.Entry<String, String>> entrySet = parameter.entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    String key = e.getKey();
                    String value = e.getValue();
                    encoder.addParam(key, value);
                }
            }
            //encoder.toString()有查询参数的uri
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, encoder.toString());
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaders.Names.HOST, host);
            headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + ','
                    + HttpHeaders.Values.DEFLATE.toString());
            
//            headers.set(HttpHeaders.Names.CONTENT_TYPE,"text/plain");
 
            headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "fr");
            headers.set(HttpHeaders.Names.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
 
            headers.set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar"))
            );
        } else {
            System.err.println("this method is not support!");
        }
        return request;
    }
 
    public void run(String url, HttpRequest request) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
 
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "localhost" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }
 
        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP(S) is supported.");
        }
 
        boolean ssl = "https".equalsIgnoreCase(scheme);
 
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer(ssl));
 
            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();
            // send request
            ch.writeAndFlush(request).sync();
            ch.closeFuture().sync();
          
        } finally {
//        	  System.out.println(" try to keep the connection!");
            group.shutdownGracefully();
        }
    }
 
    
    public static void main(String args[]) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {
		RetData ret = new RetData();
		ret.setContent("testReq");

		String reqJson = JSON.toJSONString(ret);

		Map<String, String> getData = new HashMap<String, String>();
		getData.put("tags", "806:938356;");
		getData.put("sort", "_p");
		// getData.put(null, reqJson);

		HttpRequest get;
		try {
			get = getRequestMethod(getData, url, "post");
			MyLogger.info("client start to request :" + url);

			Client client = new Client();
			client.run(url, get);
			System.out
					.println("===============================================================client1 over ");

		} catch (Exception e) {
			e.printStackTrace();
		}

    	
    	
       /* for(int i=0;i<1;i++){
            Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
			    	RetData ret= new RetData();
			    	ret.setContent("testReq");
			    	
			    	String reqJson = JSON.toJSONString(ret);
			    	
					Map<String, String> getData = new HashMap<String, String>();
		        	getData.put("tags", "806:938356;");
		        	getData.put("sort", "_p");
//					getData.put(null, reqJson);
		        	
		        	
		        	HttpRequest get;
					try {
						get = getRequestMethod(getData, url, "post");
		        	MyLogger.info("client start to request :" + url);
		        	new Client().run(url, get);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
            t.start();
            
          }*/
        
        
        MyLogger.info("client over!");
    }
}