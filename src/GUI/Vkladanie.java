package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import banka.Banka;
import ucty.Ucet;
import zakaznici.Zakaznik;

/**
 * Trieda sluzi na vytvorenie a zobrazenie obrazovky z ktorej je mozne vykonat vlozenie zadanej sumy
 * na ucet. Zakaznik zada sumu a po stlaceni tlacitka sa suma vlozi na ucet a vrati zakaznika na
 * uvodnu obrazovku bankoveho systemu.
 */
public class Vkladanie implements ActionListener {
	private Banka banka;
	private Zakaznik prihlaseny;
	
	private static JFrame frame3;
	private static JLabel sumaLabel;
	private static JTextField sumaText;
	private static JButton potvrditBut;
	private static JButton naspatBut;

	/**
	 * Trieda okrem atributov grafickeho rozhrania obsahuje aktualnu banku, a aktualne prhlaseneho
	 * zakaznika. Tieto atributy sa ulozia tu v konstruktore.
	 * @param nBank	aktualna banka
	 * @param prihl	aktualne prihlaseny pouzivatel
	 */
	public Vkladanie(Banka nBank, Zakaznik prihl) {
		this.banka= nBank;
		this.prihlaseny= prihl;
	}
	
	/**
	 * Funkcia na vytvorenie a zobrazenie obrazovky. Zakaznik tu moze zadat sumu ktoru chce vlozit na svoj
	 * ucet a ktora sa po potvrdeni sumy na ucet prida.
	 */
	public void zobrazVkladanie() {
		frame3= new JFrame("Vlozit na bezny ucet");
		JPanel panel= new JPanel();
		
		frame3.setSize(500, 600);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.add(panel);
		panel.setLayout(null);
		
		
		potvrditBut= new JButton("Potvrdit");
		potvrditBut.setBounds(100, 100, 150, 25);
		potvrditBut.addActionListener(this);
		panel.add(potvrditBut);
		
		naspatBut= new JButton("Naspat");
		naspatBut.setBounds(350, 10, 100, 25);
		naspatBut.addActionListener(this);
		panel.add(naspatBut);
		
		sumaLabel= new JLabel("Suma na vlozenie: ");
		sumaLabel.setBounds(10, 50, 150, 25);
		panel.add(sumaLabel);
		
		sumaText= new JTextField(20);
		sumaText.setBounds(200, 50, 165, 25);
		panel.add(sumaText);
		
		frame3.setVisible(true);
	}

	/**
	 * Metoda udalosti tlacitiek. Tlacitko "Potvrdit" vlozi zadanu sumu na zakaznikov ucet a vrati 
	 * pouzivatela na uvodnu obrazovku bankoveho systemu. Tlacitko "Naspat" vrati pouzivatela
	 * na uvodnu obrazovku bez toho, aby vkladal nejaku sumu na ucet.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == naspatBut) {
			frame3.setVisible(false);
			Uvod w2= new Uvod(banka, prihlaseny);
			w2.zobrazUvod();
		}
		if(e.getSource()== potvrditBut) {
			Ucet aktUcet= prihlaseny.getUcet();
			String pomocny= sumaText.getText();
			double vlozit= Double.parseDouble(pomocny);
			aktUcet.vklad(vlozit);
		
			frame3.setVisible(false);
			Uvod w2= new Uvod(banka, prihlaseny);
			w2.zobrazUvod();
		}
	}

}
