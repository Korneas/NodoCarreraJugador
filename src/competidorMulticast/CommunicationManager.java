package competidorMulticast;

import java.net.*;
import java.util.Observable;

public class CommunicationManager extends Observable implements Runnable {

	private MulticastSocket mSocket;
	private final int PORT = 5000;
	private boolean life;

	public CommunicationManager() {
		life = true;
		try {
			mSocket = new MulticastSocket(PORT);
			InetAddress grupo = InetAddress.getByName("228.5.6.7");
			mSocket.joinGroup(grupo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviar(String msg, String direccionIP, int pt) {
		byte[] buffer = msg.getBytes();

		try {
			InetAddress hosting = InetAddress.getByName(direccionIP);
			DatagramPacket dPacket = new DatagramPacket(buffer, buffer.length, hosting, pt);
			System.out.println("Se envio data a: " + direccionIP);
			mSocket.send(dPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DatagramPacket recibir() {
		byte[] buffer = new byte[1024];
		DatagramPacket dPacket = new DatagramPacket(buffer, buffer.length);

		try {
			mSocket.receive(dPacket);
			return dPacket;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {
		while (life) {
			System.out.println("Edward es el putas");
			if (mSocket != null) {
				DatagramPacket dPacket = recibir();
				if (dPacket != null) {
					setChanged();
					notifyObservers(dPacket);
					clearChanged();
				}
			}
		}
	}
}
