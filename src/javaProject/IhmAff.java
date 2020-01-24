package javaProject;

import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import java.util.TimerTask;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Timer;
import java.util.TimerTask;


public class IhmAff implements Runnable {

	private JFrame frame;
	Thread t;
	JLabel lblEquDom;
	JLabel lblEquExt;
	JLabel lblScoDom;
	JLabel lblScoExt;
	JLabel lblDuree;
	int etat = 0;
	Double dureeMatch;
	RencontresBDD bdd = new RencontresBDD();
	String cmd;
	JsonArray ja;
	JsonObject jo2;
	JsonObject jo3;
	JsonObject personObject;
	Timer ti = new Timer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IhmAff window = new IhmAff();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IhmAff() {
		initialize();
		t = new Thread(this);
		t.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblEquDom = new JLabel("New label");
		lblEquDom.setBounds(80, 50, 60, 15);
		frame.getContentPane().add(lblEquDom);
		
		lblEquExt = new JLabel("New label");
		lblEquExt.setBounds(300, 50, 60, 15);
		frame.getContentPane().add(lblEquExt);
		
		lblScoDom = new JLabel("New label");
		lblScoDom.setBounds(80, 90, 60, 15);
		frame.getContentPane().add(lblScoDom);
		
		lblScoExt = new JLabel("New label");
		lblScoExt.setBounds(300, 90, 60, 15);
		frame.getContentPane().add(lblScoExt);
		
		JLabel lbltxtDuree = new JLabel("Dur\u00E9e restante :");
		lbltxtDuree.setBounds(50, 200, 100, 15);
		frame.getContentPane().add(lbltxtDuree);
		
		lblDuree = new JLabel("New label");
		lblDuree.setBounds(160, 200, 60, 15);
		frame.getContentPane().add(lblDuree);
		
	}
	
	public void demarrer(Timer tim, String dM) {
		
		this.dureeMatch = Double.valueOf(dM);
		etat = 1;
		ti.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(etat == 1) {
					lblDuree.setText(String.valueOf(dureeMatch));
					dureeMatch -= 1;
					lblDuree.invalidate();
					if(dureeMatch < 0) {
						etat = 0;
						tim.cancel();
						finMatch(jo2.getInt("sc"), jo3.getInt("sc"), jo2.getString("nom"), jo3.getString("nom"), personObject.getString("sp"));
					}
				}
			}
		}, 0, 1000);
	}
	
	public void arretMatch(Timer tim) {
		tim.cancel();
	}
		
	public void finMatch(int sc1, int sc2, String eq1, String eq2, String sport) {
		bdd.enregMatch(sc1, sc2, eq1, eq2, sport);
	}


	@Override
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(5000 , 10);
			while(true)
			{
				byte[] rep = new byte[200];
				System.out.println("Connexion en cours...");
				Socket service = ss.accept();
				System.out.println("Connexion effectuée");
				InputStream is = service.getInputStream();
				OutputStream os = service.getOutputStream();

				JsonReader jr = Json.createReader(is);

				personObject = jr.readObject();

				jr.close();
				
				ja = personObject.getJsonArray("eqps");
				jo2 = ja.getJsonObject(0);

				jo3 = ja.getJsonObject(1);
				cmd = personObject.getString("commande");

				switch(cmd) {

				case "d":			
					lblEquDom.setText(jo2.getString("nom"));
					lblEquExt.setText(jo3.getString("nom"));
					lblScoDom.setText(String.valueOf(jo2.getInt("sc")));
					lblScoExt.setText(String.valueOf(jo3.getInt("sc")));
					lblDuree.setText(String.valueOf(personObject.getInt("duree")));
					demarrer(ti, lblDuree.getText());
					break;
				case "scp":
					lblScoDom.setText(String.valueOf(jo2.getInt("sc")));
					lblScoExt.setText(String.valueOf(jo3.getInt("sc")));
					break;
				case "s":
					arretMatch(ti);
					finMatch(jo2.getInt("sc"), jo3.getInt("sc"), jo2.getString("nom"), jo3.getString("nom"), personObject.getString("sp"));
					break;
				}
				
				


			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
