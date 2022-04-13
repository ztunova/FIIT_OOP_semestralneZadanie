package GUI;

import java.awt.Font;
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
import ucty.Ucet;
import zakaznici.Zakaznik;
import zakaznici.Zvyhodneny;

/**
 * Trieda Registracia je sucastou grafickeho rozhrania. Vytvara sa v nej obrazovka, ktora sa zobrazi pri
 * spusteni programu a umoznuje vytvorit noveho zakaznika do banky. V pripade ze nejaky zakaznic uz su 
 * vytvoreny, da sa z tejto obrazovky prekliknut na prihlasenie pouzivatela. Atributami triedy su okrem
 * prvkov grafickeho rozhrania udaje potrebne na vytvorenie noveho zakaznika a zapisanie ho do zoznamu
 * zakaznikov banky.
 */
public class Registracia implements ActionListener {
	private double pociatocnaSuma;
	private int vek;
	private String cisloUctu;
	private String meno;
	private int id;
	private Banka banka;
	private ArrayList <Zakaznik> z;
	
	private static JFrame frame1;
	private static JLabel menoLabel;
	private static JTextField menoText;
	private static JLabel IDLabel;
	private static JTextField IDText;
	private static JLabel vekLabel;
	private static JTextField vekText;
	private static JLabel cUctuLabel;
	private static JTextField cUctuText;
	private static JLabel sumaLabel;
	private static JTextField sumaText;
	private static JButton registrovatBut;
	private static JButton prihlasitBut;
	private static JLabel success;
	private static JLabel prihl;
	private static JLabel title;
	
	/**
	 * V konstruktore sa do atributu tiredy ulozi objekt banky, ktory konstruktor dostane ako vstupny
	 * parameter. Z banky si konstruktor este ulozi zoznam zakaznikov banky
	 * @param nBank	objekt typu Banka, aktualna banka s ktorej zakaznikmi sa bude pracovat dalej
	 */
	public Registracia(Banka nBank) {
		this.banka= nBank;
		z = banka.getZakaznici();
	}
	
	/**
	 * Funkcia ktora vytvori obrazovku registracie, pouklada na nu vsetky tlacitka, popisy a textove polia
	 */
	public void zobrazRegistraciu() {
		frame1= new JFrame("Uvodna obrazovka");
		JPanel panel= new JPanel();
		
		frame1.setSize(500, 400);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(panel);
		panel.setLayout(null);
		
		title = new JLabel("Registracia zakaznika"); 
        title.setFont(new Font("Arial", Font.PLAIN, 20)); 
        title.setSize(300, 30); 
        title.setLocation(10, 10); 
        panel.add(title); 
		
		menoLabel= new JLabel("Meno:");
		menoLabel.setBounds(10, 50, 80, 25);
		panel.add(menoLabel);
		
		menoText= new JTextField(20);
		menoText.setBounds(130, 50, 165, 25);
		panel.add(menoText);
		
		IDLabel= new JLabel("ID:");
		IDLabel.setBounds(10, 80, 80, 25);
		panel.add(IDLabel);
		
		IDText = new JTextField();
		IDText.setBounds(130, 80, 165, 25);
		panel.add(IDText);
		
		vekLabel= new JLabel("Vek:");
		vekLabel.setBounds(10, 110, 80, 25);
		panel.add(vekLabel);
		
		vekText = new JTextField();
		vekText.setBounds(130, 110, 165, 25);
		panel.add(vekText);
		
		cUctuLabel= new JLabel("Cislo uctu:");
		cUctuLabel.setBounds(10, 140, 80, 25);
		panel.add(cUctuLabel);
		
		cUctuText = new JTextField();
		cUctuText.setBounds(130, 140, 165, 25);
		panel.add(cUctuText);
		
		sumaLabel= new JLabel("Pociatocna suma:");
		sumaLabel.setBounds(10, 170, 100, 25);
		panel.add(sumaLabel);
		
		sumaText = new JTextField();
		sumaText.setBounds(130, 170, 165, 25);
		panel.add(sumaText);
		
		
		registrovatBut= new JButton("Registrovat");
		registrovatBut.setBounds(10, 230, 100, 25);
		registrovatBut.addActionListener(this);
		panel.add(registrovatBut);
		
		prihl= new JLabel("Prihlasit pouzivatela");
		prihl.setBounds(10, 300, 300, 25);
		panel.add(prihl);
		
		success= new JLabel("");
		success.setBounds(10, 250, 350, 25);
		panel.add(success);
		
		prihlasitBut= new JButton("Prihlasit sa");
		prihlasitBut.setBounds(10, 330, 100, 25);
		prihlasitBut.addActionListener(this);
		panel.add(prihlasitBut);
		
		frame1.setVisible(true);
	}
	
	/**
	 * Funkcia riadiaca udalosti tlacitiek. Ak sa stlaci tlacitko "Registrovat" z textovych poli sa
	 * ulozia udaje, ktore sa posunu funkciam a konstruktorom na vytvorenie noveho zakaznika a jeho uctu
	 * a pridanie zakaznika do zoznamu zakaznikov banky.
	 * Ak sa stlaci tlacitko "Prihlasit" zobrazi sa nova obrazovka ktora umoznje uz existujucemu
	 * zakaznikovy prihlasit sa do systemu.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==registrovatBut) {
			//System.out.println("Tlacitko sa stlacilo");
			meno= menoText.getText();
			cisloUctu= cUctuText.getText();
			String pomocny= vekText.getText();
			vek= Integer.parseInt(pomocny);
			pomocny= sumaText.getText();
			pociatocnaSuma= Double.parseDouble(pomocny);
			pomocny= IDText.getText();
			id= Integer.parseInt(pomocny);
			//System.out.println(meno + ", "+ id + ", "+ cisloUctu+", "+ vek+", "+pociatocnaSuma);
		
			if(banka.najdiZakaznika(id) != null) {
				success.setText("Zadane ID je uz v systeme registrovane, prosim zvole ine");
				IDText.setText("");
			}
			else {
				success.setText("Registracia prebehla uspesne, mozete sa prihlasit");
				IDText.setText("");
				menoText.setText("");
				vekText.setText("");
				cUctuText.setText("");
				sumaText.setText("");
				
				Ucet novyUcet= new Ucet(cisloUctu, pociatocnaSuma);
		
				if(vek > 60 ) {
					banka.novyZvyhodneny(meno, novyUcet, id, vek, "Dochodca");
				}
				else if (vek < 25) {
					banka.novyZvyhodneny(meno, novyUcet, id, vek, "Student");
				}
				else {
					banka.novyZakaznik(meno, novyUcet, id, vek);
				}
		
				for (int i=0; i< z.size(); i++) {
					z.get(i).zobraz();
					if(z.get(i) instanceof Zvyhodneny) {
						System.out.println("Zakaznik s vyhodami");
					}
				}
		
			}
		}
		if(e.getSource()==prihlasitBut) {
			frame1.setVisible(false);
			Prihlasenie prihl= new Prihlasenie(banka);
			prihl.zobrazPrihlasenie();
			
		}
		
	}
	
	public String getMeno() {
		return meno;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCisloUctu() {
		return cisloUctu;
	}
	
	public double getSuma() {
		return pociatocnaSuma;
	}
	
	public int getVek() {
		return vek;
	}

}
