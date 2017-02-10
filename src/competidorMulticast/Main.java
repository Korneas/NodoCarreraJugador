package competidorMulticast;

import processing.core.PApplet;

public class Main extends PApplet{
	private Logica app;
	
	public static void main(String[] args){
		//System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("competidorMulticast.Main");
	}
	
	@Override
	public void settings(){
		size(600,200);
	}
	
	@Override
	public void setup(){
		app = new Logica(this);
	}
	
	@Override
	public void draw(){
		app.pintar();
	}
	
	@Override
	public void mouseClicked(){
		app.enviarMensaje();
	}
}
