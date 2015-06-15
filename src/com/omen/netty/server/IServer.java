package com.omen.netty.server;


public interface IServer {
	
	public void start() throws Exception;
	
	public void stop() throws Exception;

	public void restart() throws Exception;
}
