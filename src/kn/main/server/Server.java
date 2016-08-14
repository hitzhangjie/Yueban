/**
 * Yueban Server
 *
 * @author zhangjie
 * @date 2016-08-07 08:05:58 PM
 */
package kn.main.server;

import java.io.IOException;
import java.lang.*;
import java.net.*;
import java.util.*;

/**
 * Server class will listen the client's heartbeats and sent it back, then
 * Server side knows which clients are online and clients know whether server
 * are online.
 *
 * Server class maintains the whole online clients' list via a
 * ArrayList<ip,port> data structure.
 *
 * Why do we record port info? Because on the same pc, the same client
 * programme may be started several times and not only one process is created,
 * although on Android platform or iOS platform, operating system may take
 * some measure to guarantee only one process of the same app can be created.
 *
 * Besides, Server side will recv and handle the clients' requests. We'll
 * describe the different clients' requests and how server handles them in the
 * README.md in details.
 *
 */
public class Server {

	public ServerSocket listen_socket = null;

	// store client address
	public static ArrayList<SocketAddress> clientsList = null;

	// store socket connected to client
	public static ArrayList<Socket> socketsList = null;

	// store dj and followers' info
	public static HashMap<Integer, LinkedList<Integer>> djFollowersMap = null;
	// store uin Socket test
	public static HashMap<Integer, Socket> uinSocketMap = null;

	// lock
	public static final String LOCK = "MUTEX";
	public static final String LOCK_DJ = "MUTEX_DJ";
	public ArrayList<ServerHandleAppEventThread> handleAppEventThreadLists = null;
	public ServerCleanClientThread cleanClientThread = null;

	// server listen port, hard coded to 20000
	public int PORT = 20000;

	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}

	public void run() {

		try {
			listen_socket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server cannot create ServerSocket ... exit");
			System.exit(1);
		}
		Socket conn_socket = null;
		// 主线程只负责建立连接，并维护客户端的连接信息
		try {
			while ((conn_socket = listen_socket.accept()) != null) {
				conn_socket.setSoTimeout(3000);
				System.out.println("A new Connection is established");

				if (clientsList == null) {
					clientsList = new ArrayList<SocketAddress>();
					socketsList = new ArrayList<Socket>();
					handleAppEventThreadLists = new ArrayList<ServerHandleAppEventThread>();
					cleanClientThread = new ServerCleanClientThread();
				}

				SocketAddress remoteAddress = conn_socket.getRemoteSocketAddress();

				synchronized (LOCK) {
					// store client' info, including socketaddress and connectsocket
					clientsList.add(remoteAddress);
					socketsList.add(conn_socket);
				}

				// create a new client process thread used to handle client's requests
				ServerHandleAppEventThread handleAppEventThread = new ServerHandleAppEventThread(conn_socket);
				synchronized (LOCK) {
					handleAppEventThreadLists.add(handleAppEventThread);
				}
				handleAppEventThread.start();

				// clean offline clients
				// connSocket.isClosed() can not be used as a rule to judge whether client has disconnected!
				// so i use the tranferred data's validity as the rule.
				// cleanClientThread is deprecated!
				// fixme
				/*
				if (!cleanClientThread.isAlive())
					cleanClientThread.start();
					*/

				System.out.println("Server is listening ... waiting to be connected");

				Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server cannot listen on this ServerSocket ... exit");
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Server receive this Interupt event ... stop");
			System.exit(0);
		}
	}
}




