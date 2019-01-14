package client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class ProgramClient {

	public static void main(String[] args) {

		try {

			ClientCls client = new ClientCls(54321);
			for (int i = 0; i < 10; i++) {
				client.setMessage("testing udp protocol " + i);
				client.sendData(23456, "127.0.0.1");
				client.takeData();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}

class ClientCls {

	private DatagramSocket socket;
	private DatagramPacket packet;

	public ClientCls(int port) throws Exception {
		socket = new DatagramSocket(port);
		packet = new DatagramPacket(new byte[1024], 1024);
	}

	public void sendData(int port, String ip) throws IOException {
		packet.setPort(port);
		packet.setAddress(InetAddress.getByName(ip));

		socket.send(packet);
	}

	public void takeData() throws IOException {
		packet = new DatagramPacket(new byte[1024], 1024);
		socket.receive(packet);
		String aux = new String(packet.getData());
		System.out.println(aux);
	}

	public void setMessage(String mess) throws UnsupportedEncodingException {
		packet.setData(mess.getBytes("UTF-8"));
	}

	@Override
	public String toString() {
		String aux = new String(packet.getData());
		return aux;
	}

}
