package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import banka.Banka;
import zakaznici.Zakaznik;

/**
 * Trieda grafickeho rozhrania v ktora reprezentuje obrazovku prihlasenia sa do uctu.
 * Okrem atributov grafickeho rozhrania obsahuje trieda este atribut banka, ktora reprezenetuje sucasnu
 * banku, jej zoznam zakaznikov z a objekt typu Zakaznik prihôaseny, cize aktualne prihlaseny zakaznik.
 */
public class Prihlasenie implements ActionListener {
	private Banka banka;
	private ArrayList <Zakaznik> z;
	private Zakaznik prihlaseny;
	
	private static JLabel pouzivatelLabel;
	private static JTextField pouzivatelText;
	private static JLabel hesloLabel;
	private static JPasswordField hesloText;
	private static JButton prihlasitBut;
	private static JLabel success;
	private static JFrame frame2;
	private static JLabel prihl;
	
	/**
	 * v konstruktore sa ulozi banka, ktoru konstruktor dostane ako vstupny parameter a ulozi sa zoznam 
	 * zakaznikov banky
	 * @param nBank	objekt typu Banka, cize aktualna banka so svojimi zakaznikmi, poplatkami a urokom
	 */
	public Prihlasenie(Banka nBank) {
		this.banka= nBank;
		z = banka.getZakaznici();
	}
	
	/**
	 * Funkcia ktora vytvori obrazovku prihlasnia. Zakaznik sa prihlasuje do systemu svojim menom a 
	 * ako heslo pouziva svoje ID.
	 */
	public void zobrazPrihlasenie() {
		frame2= new JFrame("Prihlasovanie");
		JPanel panel= new JPanel();
		
		frame2.setSize(350, 200);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(panel);
		panel.setLayout(null);
		
		pouzivatelLabel= new JLabel("Meno");
		pouzivatelLabel.setBounds(10, 20, 80, 25);
		panel.add(pouzivatelLabel);
		
		pouzivatelText= new JTextField(20);
		pouzivatelText.setBounds(100, 20, 165, 25);
		panel.add(pouzivatelText);
		
		hesloLabel= new JLabel("Heslo");
		hesloLabel.setBounds(10, 50, 80, 25);
		panel.add(hesloLabel);
		
		hesloText = new JPasswordField();
		hesloText.setBounds(100, 50, 165, 25);
		panel.add(hesloText);
		
		prihlasitBut= new JButton("Login");
		prihlasitBut.setBounds(10, 80, 80, 25);
		prihlasitBut.addActionListener(this);
		panel.add(prihlasitBut);
		
		success= new JLabel("");
		success.setBounds(10, 110, 300, 25);
		panel.add(success);
		
		prihl= new JLabel("Pre prihlasenie do systemu pouzite svoje meno a ID");
		prihl.setBounds(10, 140, 300, 25);
		panel.add(prihl);
		
		frame2.setVisible(true);
	}

	/**
	 * Metoda pre akciu tlacitka "Login". Podla mena a identifikacneho cisla pouzivatela, ktore zadal ako
	 * heslo funkcia vyhlada v zozname zakaznikov zakaznika, ktory sa prave prihlasil a toho si ulozi.
	 * V pripade ze zhodu nenajde vypise oznamenie o nespravnom mene alebo hesle. Ked sa pouzivatelovy 
	 * podari prihlasit zobrazi sa nova, uvodna obrazovka bankoveho systemu.
	 */
	public void actionPerformed(ActionEvent e) {
		//frame2.setVisible(false);
		String meno= pouzivatelText.getText();
		String pomocny= hesloText.getText();
		int id= Integer.parseInt(pomocny); 
		
		prihlaseny= banka.najdiZakaznika(id);
		
		if(prihlaseny != null) {
			if(meno.equals(prihlaseny.getMeno()) && id == prihlaseny.getId()) {
				frame2.setVisible(false);
				Uvod w2= new Uvod(banka, prihlaseny);
				String prih= prihlaseny.getMeno();
				System.out.println(prih +" prihlaseny");
				w2.zobrazUvod();
			}
			else {
				success.setText("nespravne meno alebo heslo");
				System.out.println(" nespravne meno alebo heslo");
			}
		}
		else {
			success.setText("nespravne meno alebo heslo");
			System.out.println(" nespravne meno alebo heslo");
		}
	}
}
