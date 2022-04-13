package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import banka.Banka;
import ucty.SporiaciUcet;
import ucty.Ucet;
import zakaznici.Zakaznik;

/**
 * Trieda Sporenie je triedou grafickeho rozhrania. Obsahuje metody a atributy potrebne na vytvorenie 
 * a zobrazenie obrazovky, na ktorej pouzivatel vidi stav svojho sporiaceho uctu. Ak pouzivatel sporiaci
 * ucet nema vytvoreny, ma moznost si tu sporiaci ucet zalozit. Ak uz sporenie ma, vidi tu informacie o nom,
 * ako cislo uctu, zostatok na ucte, urok ktorym sa zostatok uroci a historiu vkladov na ucet. Tiez tu
 * ma pouzivatel moznost previest zadanu sumu z bezneho uctu na sporiaci. Na sporiacom ucte sa zostatok
 * uroci ale nie je mozne z neho vykonavat platby.
 */
public class Sporenie implements ActionListener{
	private Banka banka;
	private Zakaznik prihlaseny;
	
	private static JFrame frame5;
	private static JLabel sumaLabel;
	private static JTextField sumaText;
	private static JLabel vytvoritLabel;
	private static JTextField vytvoritText;
	private static JButton potvrditBut;
	private static JButton naspatBut;
	private static JButton vytvoritBut;
	private static JLabel oznamCUctu;
	private static JLabel oznamZostatok;
	private static JLabel oznamUrok;
	private static JLabel oznamNieje1;
	private static JLabel oznamNieje2;
	private static JPanel panel;
	private static JTextArea historia;
	private static JLabel oznamHistoria;
	
	/**
	 * Konstruktor na vytvaranie objektu. Trieda Sporenie okrem atributov grafickeho rozhrania obsahuje
	 * este objekt typu Banka a objekt typu Zakaznik, ktore sa ulozia tu v konstruktore.
	 * @param nBank	aktualna banka s ktorou sa pracuje
	 * @param prihl	aktualne prihlaseny pouzivatel
	 */
	public Sporenie(Banka nBank, Zakaznik prihl) {
		this.banka= nBank;
		//z = banka.getZakaznici();
		this.prihlaseny= prihl;
	}
	
	/**
	 * Metoda na zobrazenie a vytvorenie obrazovky sporiaceho uctu.
	 * Ak zakaznik nema vytvoreny sporiaci ucet na obrazovke si o tom moze precitat oznam. Keby sa rozhodol
	 * sporiaci ucet si zalozit ma moznost zadat cislo noveho sporiaceho uctu do textoveho pola a 
	 * stlacenim tlacitka si vytvorit sporiaci ucet. Zostatok na nom je na zaciatku 0, ak teda chce 
	 * zurocovat zostatok musi na sporiaci ucet nieco vlozit z bezneho. Ak uz sporiaci ucet vytvoreny
	 * ma zobrazi sa obrazovka s inymi moznostami, ktore zabezpecuje metoda zobrazVytvorene(). 
	 */
	public void zobrazSporenie() {
		frame5= new JFrame("Sporiaci ucet");
		panel= new JPanel();
		
		frame5.setSize(550, 600);
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.add(panel);
		panel.setLayout(null);
		
		naspatBut= new JButton("Naspat");
		naspatBut.setBounds(400, 10, 100, 25);
		naspatBut.addActionListener(this);
		panel.add(naspatBut);
		
		if(prihlaseny.getVytvoreneSporenie() == false) {
			vytvoritBut= new JButton("Zalozit sporenie");
			vytvoritBut.setBounds(100, 300, 150, 25);
			vytvoritBut.addActionListener(this);
			panel.add(vytvoritBut);
			
			oznamNieje1= new JLabel("Nemate vytvoreny sporiaci ucet.");
			oznamNieje1.setBounds(10, 40, 300, 150);
			panel.add(oznamNieje1);
			
			oznamNieje2= new JLabel("Ak si ho zelate zalozit zvolte si cislo sporiaceho uctu a kliknite na 'Zalozit sporenie'");
			oznamNieje2.setBounds(10, 70, 500, 150);
			panel.add(oznamNieje2);
			
			vytvoritLabel= new JLabel("Cislo sporiaceho uctu: ");
			vytvoritLabel.setBounds(10, 200, 150, 25);
			panel.add(vytvoritLabel);
		
			vytvoritText= new JTextField(20);
			vytvoritText.setBounds(200, 200, 165, 25);
			panel.add(vytvoritText);
			
			frame5.setVisible(true);
		}
		
		if(prihlaseny.getVytvoreneSporenie() == true) {
			Sporenie w2= new Sporenie(banka, prihlaseny);
			frame5.setVisible(false);
			w2.zobrazVytvorene();
		}
	}
	
	/**
	 * Metoda, ktora vytvara a zobrazuje obrazovku sporiaceho uctu ak zakaznik uz sporiaci ucet ma vytvoreny.
	 * Zakaznik si tu moze skontrolovat cislo sporiaceho uctu, zostatok na sporiacom ucte, urok banky
	 * a historiu vkladov na sporiaci ucet. 
	 */
	private void zobrazVytvorene() {
		banka.vypocitajUrok(prihlaseny);
		frame5= new JFrame("Sporiaci ucet");
		panel= new JPanel(new BorderLayout());
		
		frame5.setSize(550, 600);
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.add(panel);
		panel.setLayout(null);
		
		naspatBut= new JButton("Naspat");
		naspatBut.setBounds(400, 10, 100, 25);
		naspatBut.addActionListener(this);
		panel.add(naspatBut);
		
		potvrditBut= new JButton("Potvrdit");
		potvrditBut.setBounds(100, 200, 150, 25);
		potvrditBut.addActionListener(this);
		panel.add(potvrditBut);
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		oznamHistoria = new JLabel("Historia uctu: ");
		oznamHistoria.setBounds(10, 280, 300, 25);
		panel.add(oznamHistoria);
		 historia = new JTextArea(100, 100);	 
		 SporiaciUcet aktSp= prihlaseny.getSporiaciUcet();
		 aktSp.vypisPlatby(historia);
	     historia.setBounds(10, 300, 300, 250);
	     historia.setLineWrap(true);
	     historia.setEditable(false);
	     
	     scrollpane.setBounds(10, 300, 300, 250);
	     scrollpane.getViewport().add(historia);
	     panel.add(scrollpane);
	
		oznamCUctu= new JLabel("Cislo sporiaceho uctu: "+prihlaseny.getSporiaciUcet().getCisloUctu());
		oznamCUctu.setBounds(10, 50, 400, 25);
		panel.add(oznamCUctu);
		
		String strDouble = String.format("%.2f", prihlaseny.getSporiaciUcet().getZostatok());
		oznamZostatok= new JLabel("Zostatok na ucte: "+strDouble);
	
		oznamZostatok.setBounds(10, 80, 400, 25);
		panel.add(oznamZostatok);
		
		oznamUrok= new JLabel("Urok: "+banka.getUrokovaMiera());
		oznamUrok.setBounds(10, 110, 400, 25);
		panel.add(oznamUrok);
	
		sumaLabel= new JLabel("Suma na prevod na sporiaci ucet: ");
		sumaLabel.setBounds(10, 140, 200, 25);
		panel.add(sumaLabel);
	
		sumaText= new JTextField(20);
		sumaText.setBounds(300, 140, 165, 25);
		panel.add(sumaText);
		
		frame5.setVisible(true);
	}

	/**
	 * Metoda pre udalosti tlacitiek. Tlacitko "Naspat" vrati pouzivatela na uvodnu obrazovku. Tlacitko
	 * "Zalozit sporenie" sa zobrazuje len v pripade, ze prihlaseny zakaznik nema este vytvorene sporenie.
	 * Stlacenie tohoto tlacitka mu vytvori sporiaci ucet s takym cislom ako zadal do textoveho pola.
	 * Tlacitko "Potvrdit" sa zobrazuje len v pripade, ze zakaznik ma vytvoreny sporiaci ucet. 
	 * Ak zakaznik ma vytvoreny sporiaci ucet moze do textoveho pola zadat sumu, ktoru chce previest
	 * z bezneho uctu na sporiaci a stlacenie tlacitka "Potvrdit" tuto sumu prevedie. (Na sporiacom ucte
	 * sa zostatok zurocuje ale nie je mozne z neho vykonavat platby). Talcitko "Potvrdit" nepresmeruje
	 * pouzivatela na uvodnu obrazovku ale aktualizuje obrazovku sporiaceho uctu.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == naspatBut) {
			frame5.setVisible(false);
			Uvod w2= new Uvod(banka, prihlaseny);
			w2.zobrazUvod();
		}
		if(e.getSource() == vytvoritBut) {
			String cisloSporenia= vytvoritText.getText();
			SporiaciUcet nSporenie= new SporiaciUcet(cisloSporenia, 0);
			prihlaseny.setSporenie(nSporenie);
			prihlaseny.setVytvoreneSporenie(true);
			Sporenie w2= new Sporenie(banka, prihlaseny);
			frame5.setVisible(false);
			w2.zobrazVytvorene();
		}
		
		if(e.getSource()== potvrditBut) {
			String pomocny= sumaText.getText();
			double vlozit= Double.parseDouble(pomocny);
			SporiaciUcet aktSporenie= prihlaseny.getSporiaciUcet();
			Ucet aktUcet= prihlaseny.getUcet();
			aktSporenie.vklad(vlozit);
			aktUcet.platba(vlozit, "Prevod na sporiaci ucet", 0);
			String strDouble = String.format("%.2f", prihlaseny.getSporiaciUcet().getZostatok());
			oznamZostatok.setText("Zostatok na ucte: "+strDouble);
			SporiaciUcet aktSp= prihlaseny.getSporiaciUcet();
			aktSp.vypisPlatby(historia);
		}
	}

}
