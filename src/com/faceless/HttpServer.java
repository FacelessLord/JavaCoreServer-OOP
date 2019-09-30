package com.faceless;

import com.faceless.containers.PropertyContainer;
import com.faceless.handlers.*;
import com.faceless.requests.RequestMapper;
import org.jsoup.nodes.Document;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
	private static final int               PORT              = 8080;
	final                RequestMapper     mapper            = new RequestMapper();
	public final         PropertyContainer propertyContainer = new PropertyContainer();
	public               Document          mainPageDocument  = Utilities.readDocument("mainpage.html");

	void runServer(String... args) throws Throwable
	{
		loadProperties();

		ServerSocket ss = new ServerSocket(PORT);//select socket to accept requests from
		//noinspection InfiniteLoopStatement
		while (true)
		{
			Socket socket = ss.accept();//lock until request accepted
			System.err.println("[INFO]Client accepted");
			new Thread(new SocketProcessor(this, socket)).start();//starting new thread to process request
		}
	}

	private void loadProperties()
	{
		int initialNumber = 25;
		propertyContainer.setProperty("counter", Integer.toString(initialNumber));
		propertyContainer.setProperty("Header", "Hello, you're on Faceless_Lord site(a.k.a. Ilya Strelets)");
		mapper.registerHandler("/", new MainHandler());
		mapper.registerHandler("/click", new ClickButtonHandler(1));
		mapper.registerHandler("/unclick", new ClickButtonHandler(-1));
		mapper.registerHandler("/reset", new ResetingHandler(initialNumber));
		mapper.registerHandler("/get", new GetValueHandler());
		mapper.registerHandler("/set", new SetValueHandler());
		mapper.registerHandler("/new", new NewValueHandler());
		mapper.registerHandler("/del", new DeleteValueHandler());
	}
}