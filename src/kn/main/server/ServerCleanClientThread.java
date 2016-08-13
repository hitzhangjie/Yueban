package kn.main.server;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * ServerCleanClientThread
 *
 * this class is used to clean the offline client
 */
class ServerCleanClientThread extends Thread {

	// mark the client index in Server.socketsList, Server.clientsList
	public ArrayList<Integer> markedAsRemovedList = null;

	/**
	 *
	 */
	public ServerCleanClientThread() {
		markedAsRemovedList = new ArrayList<Integer>();
	}

	/**
	 * remove offline clients
	 * @param removeList which stores the index that the client waiting to be
	 *					 removed located at
	 */
	public void removeOfflineClients(ArrayList<Integer> removeList) {

		if(removeList==null || removeList.size()==0)
			return;

		for(Integer index: removeList) {

			System.out.println( "client removed: ip:"
					+((InetSocketAddress)Server.clientsList.get(index)).getAddress().toString());

			synchronized(Server.LOCK) {
				Server.clientsList.remove(index);
				Server.socketsList.remove(index);
			}
		}
	}

	/**
	 * thread entry
	 */
	public void run() {

		try {

			markedAsRemovedList.clear();

			synchronized(Server.LOCK) {

				for(SocketAddress addr : Server.clientsList) {

					int index = Server.clientsList.indexOf(addr);
					Socket socket = Server.socketsList.get(index);

					// fixme!!!
					// 不止是这个，服务器还应该根据心跳包之间的心跳延时来决定
					// 是否将客户端判断为已经offline！
					if(socket.isClosed()) {
						markedAsRemovedList.add(index);
						continue;
					}
				}
				removeOfflineClients(markedAsRemovedList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}