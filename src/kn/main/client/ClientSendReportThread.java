package kn.main.client;

import kn.main.client.ClientRecvNotifyThread;

/**
 * ClientSendReportThread
 *
 * 接收用户提交的待上报的事件，并将其上报给Server
 *
 * @author zhangjie
 * @see ClientRecvNotifyThread
 * @date 2016-08-07 11:03:11 PM
 */
class ClientSendReportThread extends Thread {

	// private EventQueueToSend;

	/**
	 *
	 */
	public ClientSendReportThread() {
	}

	/**
	 *
	 */
	public void run() {

		try {

			while(true) {
				// recheck addEvent
				//
				// sendEvent();

				Thread.currentThread().sleep(2000);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addEvent() {
	}

	private void sendEvent() {
	}
}