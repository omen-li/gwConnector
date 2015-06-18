package com.dusun;

import com.omen.netty.server.Main;

public class Start {

	public static void main(String[] strs) throws Exception {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("new thread start to sleep 30");
					Thread.sleep(15000);
					System.out.println("new thread start to send response");
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t.start();

		Main.main(null);

	}
}
