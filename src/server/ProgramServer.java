package server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class ProgramServer {

	public static void main(String[] args) {
		// create server program
		try {

			System.out.println("The server starts now");
			ServerCls server = new ServerCls(23456);
			while (server.getCounter() < 10) {
				System.out.println("--------------------------------");
				System.out.println(server.getCounter() + " times connected to a socket");
				System.out.println("Server wait to receive something");
				server.receiveData();
				System.out.println("prepare new data");
				server.preparePacket();
				System.out.println("Return data");
				server.returnPacket();
				System.out.println("--------------------------------");
			}
			System.out.println("Close server");
			server.closeServer();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

class ServerCls {

	private DatagramSocket server; // the server to receive information
	private DatagramPacket packet; // the package will receive
	private InetAddress ipReturns;
	private int portReturns;

	private static int counter = 0;

	public ServerCls(int port) throws SocketException {
		server = new DatagramSocket(port);
	}

	public void closeServer() {
		server.close();
	}

	public void receiveData() throws IOException {
		DatagramPacket pckReceive = new DatagramPacket(new byte[1024], 1024);

		server.receive(pckReceive);
		ipReturns = pckReceive.getAddress();
		portReturns = pckReceive.getPort();

		String dataReceive = new String(pckReceive.getData());
		System.out.println("Recieve: " + dataReceive);

		counter++;
	}

	public void preparePacket() throws UnsupportedEncodingException {

		String aux = "Data package:\n" + "\tPort: " + portReturns + "\tIp address: " + ipReturns;

		packet = new DatagramPacket(aux.getBytes("UTF-8"), aux.getBytes().length);
	}

	public void returnPacket() throws IOException {
		packet.setAddress(ipReturns);
		packet.setPort(portReturns);
		server.send(packet);
	}

	public int getCounter() {
		return counter;
	}

}