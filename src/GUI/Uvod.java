package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import banka.Banka;
import ucty.Ucet;
import zakaznici.Zakaznik;
import zakaznici.Zvyhodneny;

/**
 * Trieda uvodnej obrazovky po prihlaseni sa do bankoveho systemu. Prihlaseny zakaznik banky tu vidi
 * udaje o ucte, teda meno majitela, cislo uctu, zostatok na ucte, ci je prihlaseny zakaznik zvyhodneny  
 * alebo nie a vidi tu historiu svojho uctu, cize vypisany zoznam platieb a vkladov na ucet.
 * Zakaznik sa moze z tejto obrazovky tlacitkami preklikavat madzi moznostami vykonat platbu, vlozit na 
 * ucet a skontrolovat svoj sporiaci ucet.
 */
public class Uvod implements ActionListener {
	private Banka banka;
	private ArrayList <Zakaznik> z;
	private Zakaznik prihlaseny;
	
	private static JFrame frame2;
	private static JButton odhlBut;
	private static JButton platbaBut;
	private static JButton vkladBut;
	private static JButton sporenieBut;
	private static JLabel oznamMeno;
	private static JLabel oznamCUctu;
	private static JLabel oznamZostatok;
	private static JLabel oznamStatus;
	private static JLabel oznamHistoria;
	private static JTextArea historia;
	
	/**
	 * konstruktor v ktorom sa ulozi banka, zoznam zakaznikov banky a aktualne prihlaseny zakaznik
	 * @param nBank	objekt typu Banka, aktualna banka
	 * @param prihl	aktualne prihlaseny pouzivatel
	 */
	public Uvod(Banka nBank, Zakaznik prihl) {
		this.banka= nBank;
		z = banka.getZakaznici();
		this.prihlaseny= prihl;
	}
	
	/**
	 * konstruktor ktory prekonava predchadzajuci konstruktor, v tomto sa prihlasenemu pouzivatelovy
	 * zapise novy zostatok na ucet.
	 * @param nBank	aktualna banka
	 * @param prihl	aktualne prihlaseny zakaznik banky
	 * @param novyZost	novy zostatok na ucte prihlaseneho zakaznika
	 */
	public Uvod(Banka nBank, Zakaznik prihl, double novyZost) {
		this.banka= nBank;
		z = banka.getZakaznici();
		this.prihlaseny= prihl;
		prihlaseny.getUcet().setZostatok(novyZost);
	}
	
	/**
	 * funkcia ktora vytvori a zobrazi uvodnu obrazovku po prihlaseni do bankoveho systemu.
	 * Pouzivatel tu vidi udaje o svojom ucte (meno majitela, cislo uctu, zostatok, historiu uctu) a moze
	 * sa preklikavat tlacitkami medzi moznostai vykonat platbu alebo vklad alebo skontrolovat svoj 
	 * sporiaci ucet
	 */
	public void zobrazUvod() {
		frame2= new JFrame("Obrazovka po prihlaseni");
		JPanel panel= new JPanel(new BorderLayout());
		
		frame2.setSize(500, 600);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		oznamHistoria= new JLabel("Historia uctu: ");
		oznamHistoria.setBounds(10, 180, 300, 25);
		panel.add(oznamHistoria);
		 historia = new JTextArea(100, 100);	 
		 Ucet aktUcet= prihlaseny.getUcet();
		 aktUcet.vypisPlatby(historia);
	     historia.setBounds(10, 200, 300, 300);
	     historia.setLineWrap(true);
	     historia.setEditable(false);
	     
	     scrollpane.setBounds(10, 200, 300, 300);
	     scrollpane.getViewport().add(historia);
	     panel.add(scrollpane);
		
		odhlBut= new JButton("Odhlasit");
		odhlBut.setBounds(300, 10, 150, 25);
		odhlBut.addActionListener(this);
		panel.add(odhlBut);
		
		platbaBut= new JButton("Vykonat platbu");
		platbaBut.setBounds(300, 40, 150, 25);
		platbaBut.addActionListener(this);
		panel.add(platbaBut);
		
		vkladBut= new JButton("Vlozit na ucet");
		vkladBut.setBounds(300, 70, 150, 25);
		vkladBut.addActionListener(this);
		panel.add(vkladBut);
		
		sporenieBut= new JButton("Sporiaci ucet");
		sporenieBut.setBounds(300, 100, 150, 25);
		sporenieBut.addActionListener(this);
		panel.add(sporenieBut);
		
		oznamMeno= new JLabel("Prihlaseny pouzivatel: "+prihlaseny.getMeno());
		oznamMeno.setBounds(10, 10, 300, 25);
		panel.add(oznamMeno);
		
		oznamCUctu= new JLabel("Cislo uctu: "+prihlaseny.getUcet().getCisloUctu());
		oznamCUctu.setBounds(10, 40, 300, 25);
		panel.add(oznamCUctu);
		
		oznamZostatok= new JLabel("Zostatok na ucte: "+prihlaseny.getUcet().getZostatok());
		//System.out.println("AAAZostatok na ucte: "+prihlaseny.getUcet().getZostatok());
		oznamZostatok.setBounds(10, 70, 300, 25);
		panel.add(oznamZostatok);
		
		if(prihlaseny instanceof Zvyhodneny) {
			oznamStatus= new JLabel("Zvyhodneny zakaznik ");
			oznamStatus.setBounds(10, 100, 300, 25);
			panel.add(oznamStatus);
		} else {
			oznamStatus= new JLabel("Bezny zakaznik ");
			oznamStatus.setBounds(10, 100, 300, 25);
			panel.add(oznamStatus);
		}
			
		frame2.setVisible(true);
	}
	
	/**
	 * Udalosti tlacitiek, podla toho ake sa stlacilo, sa zobrazi nova obrazovka pre prislusnu akciu
	 * "Odhlasit" odhlasi pouzivatela a zobrazi obrazovku registracie
	 * "Platba" zobrazi obrazovku z ktorej je mozne vzkonat platbu
	 * "Vlozit" zobrazi obrazovku z ktorej je mozne vykonat vklad na ucet
	 * "Sporenie" zobrazi informacie o sporiacom ucte prihlaseneho zakaznika
	 */
	public void actionPerformed(ActionEvent e) {
		//System.out.println("BBZostatok na ucte: "+prihlaseny.getUcet().getZostatok());
		if(e.getSource()==odhlBut) {
			frame2.setVisible(false);
			Registracia w2= new Registracia(banka);
			oznamMeno.setText("prihlasene");
			w2.zobrazRegistraciu();
		}
		
		if(e.getSource() == vkladBut) {
			frame2.setVisible(false);
			Vkladanie w2= new Vkladanie(banka, prihlaseny);
			w2.zobrazVkladanie();
		}
		
		if(e.getSource()== platbaBut) {
			frame2.setVisible(false);
			Platenie w2= new Platenie(banka, prihlaseny);
			w2.zobrazPlatenie();
		}
		
		if(e.getSource()== sporenieBut) {
			frame2.setVisible(false);
			Sporenie w2= new Sporenie(banka, prihlaseny);
			w2.zobrazSporenie();
		}
		
	}

}
