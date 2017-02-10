package competidorMulticast;

import java.net.DatagramPacket;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {
	private PApplet app;
	private CommunicationManager cM;
	private String dirIP;
	private int puertoSend;
	private int x, y, vel, id, colorcito;
	private boolean llego;

	public Logica(PApplet app) {
		this.app = app;
		cM = new CommunicationManager();
		Thread nt = new Thread(cM);
		nt.start();
		cM.addObserver(this);

		dirIP = "228.5.6.7";

		x = 25;
		y = app.height / 2;
		vel = 0;
		id = 3;
		colorcito = (int) app.random(255);
		llego = false;
	}

	public void pintar() {
		app.background(255);
		app.fill(0, colorcito, 100);
		app.noStroke();
		app.ellipse(x, y, 50, 50);
		if (!llego && x >= app.width - 25) {
			llegada();
			llego = true;
			vel = 0;
		}
		x += vel;
	}

	public void llegada() {
		String msg = "El jugador " + id + "llego";
		cM.enviar(msg, dirIP, puertoSend);
	}

	@Override
	public void update(Observable o, Object arg) {
		DatagramPacket dPacket = (DatagramPacket) arg;
		String data = new String(dPacket.getData(), 0, dPacket.getLength());
		System.out.println("Llego el data de: " + dPacket.getAddress().toString());
		dirIP = dPacket.getAddress().toString();
		System.out.println("IP Server: " + dirIP);
		dirIP = dirIP.replaceAll("/", "");
		puertoSend = dPacket.getPort();

		if (data.contains("correr")) {
			vel = (int) app.random(1, 3);
		}

		if (data.contains("stop")) {
			vel = 0;
		}
	}

	public void enviarMensaje() {
		cM.enviar("correr", dirIP, 5000);
	}
}
