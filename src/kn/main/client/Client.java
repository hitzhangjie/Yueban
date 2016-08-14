/**
 * Yueban client
 *
 * @author zhangjie
 * @date 2016-08-07 09:40:40 PM
 */
package kn.main.client;

import sun.plugin2.main.server.HeartbeatThread;

import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.*;

class Client {
	public static Socket connSocket = null;

	public String server_ip = null;
	public String server_port = null;

	// store other clients' ip address
	public static ArrayList<String> neighboursList = null;

	public Client(String ip, String port) {

		this.server_ip = ip;
		this.server_port = port;

		this.neighboursList = new ArrayList<String>();
	}

	/**
	 * thread entry
	 */
	public void run() {

		// if ip/port is not valid, exit the program
		if (!isValidIpAddress(server_ip) || !isValidPort(server_port)) {
			usage();
			return;
		}

		// ready to work
		try {
			connSocket = new Socket(server_ip, Integer.parseInt(server_port));

			// send heartbeat to the server
			new HeartbeatTimerTask(connSocket).start();
			new ClientSendReportThread(connSocket).start();

			// add a thread to send event to server

			// add a thread to recv event from server and handle event

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * echo the usage of current program
	 */
	public static void usage() {
		System.out.println("Usage:");
		System.out.println("	java Client <ip> <port>");
	}

	/**
	 * check whether the parameter is a valid ip address
	 *
	 * @param ip ip input
	 * @return return true if ip is valid, otherwise false
	 */
	public static boolean isValidIpAddress(String ip) {

		if (ip == null || ip.length() == 0)
			return false;

		String[] dottedNums = ip.split("\\.");

		if (dottedNums.length == 4) {
			for (String num_s : dottedNums) {
				try {
					int num_i = Integer.parseInt(num_s);
					if (num_i >= 0 && num_i <= 255)
						continue;
					else
						return false;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		} else
			return false;

		return true;
	}

	/**
	 * check whether the parameter is a valid port on the host
	 *
	 * @param port port input
	 * @return return true if port is valid, otherwise false
	 */
	public static boolean isValidPort(String port) {

		if (port == null || port.length() == 0)
			return false;

		try {
			int port_i = Integer.parseInt(port);
			if (port_i >= 1 && port_i <= 65535)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		// invalid parameters
		if (args.length != 2) {
			usage();
			return;
		}

		Client client = new Client(args[0], args[1]);
		client.run();
	}

}






