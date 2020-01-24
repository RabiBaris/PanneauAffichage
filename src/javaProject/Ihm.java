package javaProject;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import javax.json.*;

public class Ihm {

	private JFrame frame;
	JComboBox cmbDom;
	JComboBox cmbExt;
	JButton btnPlusD1;
	JButton btnPlusD2;
	JButton btnPlusD3;
	JButton btnPlusG1;
	JButton btnPlusG2;
	JButton btnPlusG3;
	JButton btnPause;
	JButton btnRepr;
	JButton btnStop;
	EquipeAbstraite eq1;
	EquipeAbstraite eq2;
	RencontresBDD bdd = new RencontresBDD();
	JComboBox cmbSport;
	int sc;
	int sc2;
	String cmd;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ihm window = new Ihm();
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
	public Ihm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {			

		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblDomicile = new JLabel("Domicile");
		lblDomicile.setBounds(100, 80, 60, 15);
		frame.getContentPane().add(lblDomicile);

		JLabel lblExterieur = new JLabel("Ext\u00E9rieur ");
		lblExterieur.setBounds(480, 80, 60, 15);
		frame.getContentPane().add(lblExterieur);

		cmbSport = new JComboBox();
		for (String s : bdd.getListeSport()) {
			cmbSport.addItem(s);
		}
		cmbSport.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cmbDom.removeAllItems();
				for (String s : bdd.getListeEquipe(cmbSport.getSelectedItem().toString())) {
					cmbDom.addItem(s);
				}

				cmbExt.removeAllItems();
				for (String s : bdd.getListeEquipe(cmbSport.getSelectedItem().toString())) {
					cmbExt.addItem(s);
				}

				switch (cmbSport.getSelectedItem().toString()) {
				case "basket":
					btnPlusD1.setText("+ 1");
					btnPlusD2.setText("+ 2");
					btnPlusD2.setVisible(true);
					btnPlusD3.setText("+ 3");
					btnPlusD3.setVisible(true);
					btnPlusG1.setText("+ 1");
					btnPlusG2.setText("+ 2");
					btnPlusG2.setVisible(true);
					btnPlusG3.setText("+ 3");
					btnPlusG3.setVisible(true);
					break;

				case "rugby":
					btnPlusD1.setText("+ 2");
					btnPlusD2.setText("+ 3");
					btnPlusD2.setVisible(true);
					btnPlusD3.setText("+ 5");
					btnPlusD3.setVisible(true);
					btnPlusG1.setText("+ 2");
					btnPlusG2.setText("+ 3");
					btnPlusG2.setVisible(true);
					btnPlusG3.setText("+ 5");
					btnPlusG3.setVisible(true);
					break;

				case "foot":
					btnPlusD1.setText("+ 1");
					btnPlusD2.setVisible(false);
					btnPlusD3.setVisible(false);
					btnPlusG1.setText("+ 1");
					btnPlusG2.setVisible(false);
					btnPlusG3.setVisible(false);
					break;

				default:
					// code block
				}

			}
		});

		cmbSport.setBounds(240, 190, 150, 22);
		frame.getContentPane().add(cmbSport);

		cmbDom = new JComboBox();
		for (String s : bdd.getListeEquipe(cmbSport.getSelectedItem().toString())) {
			cmbDom.addItem(s);
		}
		cmbDom.setBounds(50, 105, 150, 22);
		frame.getContentPane().add(cmbDom);

		cmbExt = new JComboBox();
		for (String s : bdd.getListeEquipe(cmbSport.getSelectedItem().toString())) {
			cmbExt.addItem(s);
		}
		cmbExt.setBounds(430, 105, 150, 22);
		frame.getContentPane().add(cmbExt);

		JLabel lblSport = new JLabel("Sport");
		lblSport.setBounds(290, 164, 60, 15);
		frame.getContentPane().add(lblSport);

		JButton btnNvlPar = new JButton("Nouvelle Partie");
		btnNvlPar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNvlPar.setVisible(false);
				btnPause.setVisible(true);
				btnStop.setVisible(true);
				
				
				
				
				switch(cmbSport.getSelectedItem().toString()) {
				case "basket":
					eq1 = new EquipeBasket(cmbDom.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					eq2 = new EquipeBasket(cmbExt.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					break;
				case "foot":
					eq1 = new EquipeFoot(cmbDom.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					eq2 = new EquipeFoot(cmbExt.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					break;
				case "rugby":
					eq1 = new EquipeRugby(cmbDom.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					eq2 = new EquipeRugby(cmbExt.getSelectedItem().toString(), cmbSport.getSelectedItem().toString());
					break;
				default:
				}
				
				cmd = "d";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnNvlPar.setBounds(240, 238, 150, 35);
		frame.getContentPane().add(btnNvlPar);
		
		btnStop = new JButton("Stop");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnStop.setVisible(false);
				btnNvlPar.setVisible(true);
				
				cmd = "s";
				String j1 = enregJson();
				envoyerDonnees(j1);
								
			}
		});
		btnStop.setBounds(240, 288, 150, 35);
		frame.getContentPane().add(btnStop);
		btnStop.setVisible(false);
		
		
		btnPause = new JButton("Pause");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPause.setVisible(false);
				btnRepr.setVisible(true);
			}
		});
		btnPause.setBounds(240, 238, 150, 35);
		frame.getContentPane().add(btnPause);
		btnPause.setVisible(false);
		
		btnRepr = new JButton("Reprendre");
		btnRepr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRepr.setVisible(false);
				btnPause.setVisible(true);
			}
		});
		btnRepr.setBounds(240, 238, 150, 35);
		frame.getContentPane().add(btnRepr);
		btnRepr.setVisible(false);

		btnPlusD1 = new JButton("+ 1");
		btnPlusD1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eq2.ajoutA();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusD1.setBounds(370, 350, 55, 55);
		frame.getContentPane().add(btnPlusD1);

		btnPlusD2 = new JButton("+ 2");
		btnPlusD2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eq2.ajoutB();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusD2.setBounds(440, 350, 55, 55);
		frame.getContentPane().add(btnPlusD2);
		btnPlusD2.setVisible(false);

		btnPlusD3 = new JButton("+ 3");
		btnPlusD3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eq2.ajoutC();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusD3.setBounds(510, 350, 55, 55);
		frame.getContentPane().add(btnPlusD3);
		btnPlusD3.setVisible(false);
		
		btnPlusG1 = new JButton("+ 1");
		btnPlusG1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eq1.ajoutA();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusG1.setBounds(200, 350, 55, 55);
		frame.getContentPane().add(btnPlusG1);
		
		btnPlusG2 = new JButton("+ 2");
		btnPlusG2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eq1.ajoutB();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusG2.setBounds(130, 350, 55, 55);
		frame.getContentPane().add(btnPlusG2);
		btnPlusG2.setVisible(false);
		
		btnPlusG3 = new JButton("+ 3");
		btnPlusG3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eq1.ajoutC();
				cmd = "scp";
				String j1 = enregJson();
				envoyerDonnees(j1);
			}
		});
		btnPlusG3.setBounds(60, 350, 55, 55);
		frame.getContentPane().add(btnPlusG3);
		btnPlusG3.setVisible(false);
		
	}
	
	public void envoyerDonnees(String mes) {
		Socket so = new Socket();
		SocketAddress me = new InetSocketAddress("127.0.0.1",5000);
		try {
			so.connect(me);
			OutputStream o = so.getOutputStream();
			InputStream i = so.getInputStream();
			byte[] rep = new byte[200];
			o.write(mes.getBytes());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
		
	public String enregJson() {
				
		sc = 0;
		sc2 = 0;
		
		sc = eq1.getScore();
		sc2 = eq2.getScore();
		
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
		objBuilder.add("commande", cmd);
		objBuilder.add("sp", cmbSport.getSelectedItem().toString());
		objBuilder.add("duree", bdd.getDuree(cmbSport.getSelectedItem().toString()));
		
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		JsonObjectBuilder obj2Builder = Json.createObjectBuilder();
		
		obj2Builder.add("nom", cmbDom.getSelectedItem().toString());
		obj2Builder.add("sc", sc);
		arrBuilder.add(obj2Builder);

		obj2Builder.add("nom", cmbExt.getSelectedItem().toString());
		obj2Builder.add("sc", sc2);								
		arrBuilder.add(obj2Builder);

		objBuilder.add("eqps", arrBuilder);
		
		JsonObject b = objBuilder.build();
		System.out.println(b);
		return b.toString();
	}
}
