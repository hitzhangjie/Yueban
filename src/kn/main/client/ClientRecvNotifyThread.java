package kn.main.client;

/**
 * ClientRecvNotifyThread
 *
 * 接收服务器下发的通知，并对通知进行处理
 */
class ClientRecvNotifyThread extends Thread {

	/**
	 *
	 */
	public ClientRecvNotifyThread() {
		try {
			while(true) {
				Thread.currentThread().sleep(2000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}