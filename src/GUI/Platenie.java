package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import banka.Banka;
import ucty.Ucet;
import zakaznici.Zakaznik;
import zakaznici.Zvyhodneny;

/**
 * Trieda obrazovky z ktorej je mozne vykonat platbu. Metoda v triede sluzi na vytvorenie a zobrazenie
 * tejto obrazovky. Na obrazovke moze pouzivatel zadat sumu ktoru chce zaplatit, cislo cieloveho uctu
 * a poznamku k platbe. Nasledne po stlaceni tlacitka sa vykona platba. 
 */
public class Platenie implements ActionListener {
	private Banka banka;
	private Zakaznik prihlaseny;
	private ArrayList <Zakaznik> z;
	
	private static JFrame frame4;
	private static JLabel sumaLabel;
	private static JTextField sumaText;
	private static JLabel cielLabel;
	private static JTextField cielText;
	private static JButton potvrditBut;
	private static JLabel poznamkaLabel;
	private static JTextArea poznamkaText;
	private static JButton naspatBut;
	
	/**
	 * Konstruktor na vytvorenie objektu. Trieda okrem atributov grafickeho rozhrania obsahuje este
	 * aktualnu banku, jej zoznam zakaznikov a aktualne prihlaseneho pouzivatela. Tie sa ulozia 
	 * prave v konstruktore
	 * @param nBank aktualna banka
	 * @param prihl aktualne prihlaseny pouzivatel
	 */
	public Platenie(Banka nBank, Zakaznik prihl) {
		this.banka= nBank;
		this.prihlaseny= prihl;
		z = banka.getZakaznici();
	}
	
	/**
	 * Metoda ktora vytvori a zobrazi obrazovku z ktorej je mozne vykonat platbu na ucet. Obsahuje
	 * textove polia do ktorych pouzivatel zada sumu na zaplatenie, cislo cieloveho uctu a poznamku k platbe.
	 * Okrem toho obsajhuje este popisy poli a tlacitka.
	 */
	public void zobrazPlatenie() {
		frame4= new JFrame("Vlozit na bezny ucet");
		JPanel panel= new JPanel();
		
		frame4.setSize(550, 600);
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.add(panel);
		panel.setLayout(null);
		
		
		potvrditBut= new JButton("Potvrdit");
		potvrditBut.setBounds(100, 250, 150, 25);
		potvrditBut.addActionListener(this);
		panel.add(potvrditBut);
		
		naspatBut= new JButton("Naspat");
		naspatBut.setBounds(400, 10, 100, 25);
		naspatBut.addActionListener(this);
		panel.add(naspatBut);
		
		sumaLabel= new JLabel("Suma na zaplatenie: ");
		sumaLabel.setBounds(10, 20, 150, 25);
		panel.add(sumaLabel);
		
		sumaText= new JTextField(20);
		sumaText.setBounds(200, 20, 165, 25);
		panel.add(sumaText);
		
		cielLabel= new JLabel("Cielovy ucet: ");
		cielLabel.setBounds(10, 50, 150, 25);
		panel.add(cielLabel);
		
		cielText= new JTextField(20);
		cielText.setBounds(200, 50, 165, 25);
		panel.add(cielText);
		
		poznamkaLabel= new JLabel("Poznamka: ");
		poznamkaLabel.setBounds(10, 80, 150, 25);
		panel.add(poznamkaLabel);
		
		poznamkaText= new JTextArea(10, 10);
		poznamkaText.setBounds(200, 80, 165, 100);
		poznamkaText.setLineWrap(true);
		panel.add(poznamkaText);
		
		frame4.setVisible(true);
	}
	
	/**
	 * Metoda udalosti talcitok. Talcitko "Naspat" vrati pouzivatela na uvodnu obrazovku bez toho, aby
	 * musel vykonat platbu. Po stlaceni "Potvrdit" sa z textovych poli na obrazovke ulozia a ak treba
	 * preformatuju zadane udaje. Nasledne funkcia prejde cely zoznam zakaznikov banky a porovnva ich 
	 * cisla uctov s cislo uctu, ktory zakaznik zadal ako cielovy. Ak sa toto cislo uctu v banke najde,
	 * vykona sa platba z uctu prihlaseneho zakaznika, pricom tato platba tiez nie je spoplatnena
	 * a zaroven sa vykona vklad na cielovy ucet. Ak sa cielovy ucet v banke nenajde vykona sa len
	 * platba z uctu prihlaseneho zakaznika, ktora podla typu zakaznika moze byt spoplatnena. Ci 
	 * platbu vykonava zvyhodneny zakaznik, a teda nema byt spoplatnena, zistujem prostrednictvom
	 * operatora RTTI
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == naspatBut) {
			frame4.setVisible(false);
			Uvod w2= new Uvod(banka, prihlaseny);
			w2.zobrazUvod();
		}
		
		if(e.getSource() == potvrditBut) {
			Ucet prihlUcet= prihlaseny.getUcet();
			String poznm= poznamkaText.getText();
			String pomocny= sumaText.getText();
			double zaplatit= Double.parseDouble(pomocny);
			
			boolean hladanyU= false;
			String cHladanehoU= cielText.getText();
			Ucet hladany = null;
			for (int i=0; i<z.size(); i++) {
				Ucet aktUcet= z.get(i).getUcet();
				String aktCislo= aktUcet.getCisloUctu();
				if(aktCislo.equals(cHladanehoU)) {
					hladanyU= true;
					hladany= aktUcet;
				}
			}
			
			//zistenie typu zakaznika - ak je zvyhodneny vykona sa platba bez poplatku
			if(prihlaseny instanceof Zvyhodneny) {
				prihlUcet.platba(zaplatit, poznm);
				if(hladanyU== true) {
					hladany.vklad(zaplatit, poznm);
				}
			}
			else {
				double poplatok= banka.getPoplatok();
				if(hladanyU== true) {
					prihlUcet.platba(zaplatit, poznm);
					hladany.vklad(zaplatit, poznm);
				} else {
					prihlUcet.platba(zaplatit, poznm, poplatok);
				}
			}

			frame4.setVisible(false);
			Uvod w2= new Uvod(banka, prihlaseny);
			w2.zobrazUvod();
		}
	}

}
