package banka;

import java.util.ArrayList;
import ucty.SporiaciUcet;
import ucty.Ucet;
import zakaznici.Zakaznik;
import zakaznici.Zvyhodneny;

/**
 * Trieda Banka, ktora implementuje rozhranie BankaInt. Banka zakaznikom poskytuje urok v urcitej vyske.
 * Tiez beznym zakaznikom (ktory nie su zvyhodneny) uctuje urcity poplatok za platbu z uctu. Tieto hodnoty
 * je mozne nastavit prostrednictvom konstruktoru. Banka si uchovava zoznam svojich zakaznikov.
 * Funkciami banky su pridanie noveho zakaznika, bud zvyhodneneho alebo bezneho, vyhladanie konkretneho
 * zakaznika podla jeho ID a zurocovanie zostatku na sporiacom ucte zakaznika. Tato funkcia je 
 * implementovana v rozhrani BankaInt ako default method.
 *
 */

public class Banka implements BankaInt {
	/**
	 * Parameter urokovaMiera reprezentuje urok, ktory banka poskytuje na sporiacom ucte.
	 * Poplatok je poplatok, ktory si banka uctuje pri platbe. Tento poplatok sa neuctuje pre prvu platbu
	 * bezneho nezvyhodneneho zakaznika alebo sa neuctuje zvyhodnenym zakaznikom.
	 * Zakaznici je zoznam zakaznikov banky. Reprezentovany je ArrayListom v ktorom sa ukladaju objekty
	 * typu Zakaznik. PocetZakaznikov je momentalny pocet zakaznikov v banke (respektive aj dlzka ArrayListu).
	 */
	private double urokovaMiera;
	private double poplatok;
	private ArrayList <Zakaznik> zakaznici;
	private int pocetZakaznikov;
	
	/**
	 * Funkcia, ktora vyhladava jedneho konkretneho zakaznika v zozname zakaznikov. Vyhladava sa na zaklade
	 * zakaznikovho ID. Funkcia prostrednictvom cyklu prechadza celym zoznamom zakaznikov, pricom postupne
	 * zo zoznamu vybera jednotlivych zakaznikov. Potom porovna ID hladaneho zakaznika, ktore dostane ako 
	 * vstupny parameter s ID zakaznika vybrateho zo zoznamu. Ak sa ID zhoduju, funkcia vrati cely objekt
	 * najdeneho zakaznika. Ak funkcia prejde celym zoznamom bez toho, aby nasla zhodu, znamena to, ze 
	 * zakaznik s takymto ID sa v zozname nenachadza, teda funkcia vrati null.
	 * @param hladaneId identifikacne cislo hladaneho zakaznika 
	 * @return Funkcia vracia bud cely objekt najdeneho zakaznika alebo ak zakaznika s hladanym ID  
	 * nenajde, funkcia vrati null.
	 */
	public Zakaznik najdiZakaznika(int hladaneId) {
		for(int i=0; i< zakaznici.size(); i++) {
			Zakaznik akt= zakaznici.get(i);
			if(akt.getId() == hladaneId) {
				System.out.println("zakaznik" + akt.getMeno() +" najdeny, pozicia" + i);
				return akt;
			}
		}
		return null;
	}
	
	/**
	 * Funkcia sluzi na vytvorenie noveho zakaznika. Najskor skontroluje vsetkych doterajsich zakaznikov 
	 * podla ich ID. V pripade, ze nejaky zakaznik uz ma pridelene ID, ktore by sme chceli priradit 
	 * novemu zakaznikovy, funkcia vypise upozornenie. V opacnom pripade vytvori novy objekt zakaznika a 
	 * prida ho do zoznamu zakaznikov banky.
	 * @param nMeno	meno noveho zakaznika
	 * @param nUcet objekt typu Ucet ktory je prideleny kazdemu zakaznikovy banky (agregacia). Toto 
	 * konkretne je vytvoreny novy ucet ktory sa priradi novemu zakaznikovy
	 * @param nId identifikane cislo noveho zakaznika
	 * @param nVek vek noveho zakaznika
	 * 
	 */	
	public void novyZakaznik(String nMeno, Ucet nUcet, int nId, int nVek) {
		if(najdiZakaznika(nId) == null) {
			zakaznici.add(new Zakaznik(nMeno, nUcet, nId, nVek));
			pocetZakaznikov= pocetZakaznikov+1;
			//System.out.println("Zakaznik pridany");
		} else {
			System.out.println("Zakaznik tu uz je");
		}
	}
	
	/**
	 * Funkcia ktora ma rovnaky ucel ako funkcia void novyZakaznik(), len s tym rozdielom, ze tato funkcia
	 * vytvara objekty typu Zvyhodneny, cize vytvara zvyhodneneho zakaznika (nie je mu uctovany poplatok
	 * za platbu). Tiez najskor skontroluje ci sa v banke nahodou uz nevyskytuje zakaznik, ktory by mal 
	 * rovnake ID. Ak tam taky nie je, vytvori noveho zvyhodneneho zakaznika a prida ho do zoznamu zakaznikov
	 * banky. Ak tam taky je, vypise upozornenie.
	 * @param nMeno meno noveho zakaznika
	 * @param nUcet	objekt typu Ucet, ktory je priradeny kazdemu zakaznikovy v banke
	 * @param nId	identifikacne cislo noveho zakaznika
	 * @param nVek	vek noveho zakaznika. Od neho sa urcuje typ zvyhodneneho zakaznika
	 * @param nTyp	typ zvyhodneneho zakaznika: ak je zakaznik mladsi ako 25 rokov je to student, ak
	 * je starsi ako 60 rokov je to senior
	 */
	public void novyZvyhodneny(String nMeno, Ucet nUcet, int nId, int nVek, String nTyp) {
		if(najdiZakaznika(nId) == null) {
			zakaznici.add(new Zvyhodneny(nMeno, nUcet, nId, nVek, nTyp));
			pocetZakaznikov= pocetZakaznikov+1;
			//System.out.println("Zakaznik pridany");
		} else {
			System.out.println("Zakaznik tu uz je");
		}
	}
	
	/**
	 * Konstruktor banky, vytvori objekt typu Banka s nulovou urokovou mierou a poplatkom za platbu.
	 * Ked sa banka vytvara nema este ani ziadnych zakaznikov, takze pocet zakaznikov v banke bude tiez 0
	 * a inicializuje sa ArrayList, v ktorom sa ukladaju zakaznici banky, cize akoby zoznam zakaznikov.
	 */
	
	public Banka() {
		this.pocetZakaznikov= 0;
		this.zakaznici = new ArrayList<Zakaznik>();
		urokovaMiera= 0;
		poplatok= 0;
	}
	
	/**
	 * Konstruktor ktory prekonava konstruktor public Banka(). Volanim tohto konstruktora je mozne nastavit
	 * banke lubovolnu urokovu mieru a poplatok. Rovnako ako predchadzajuci konstruktor inicializuje zoznam
	 * zakaznikov a ich pocet inicializuje na 0.
	 * @param urokM  urokova miera, ktoru chceme aby banka mala
	 * @param popl	poplatok ktory si bude banka uctovat beznym zakaznik (nie su to studenti 
	 * alebo dochodcovia) za kazdu platbu okrem prvej.
	 */
	public Banka(double urokM, double popl) {
		this.pocetZakaznikov= 0;
		this.zakaznici = new ArrayList<Zakaznik>();
		this.urokovaMiera= urokM;
		this.poplatok= popl;
	}
	
	/**
	 * Funkcia pre ziskanie zoznamu zakaznikov banky cez enkapsulaciu
	 * @return zoznam zakaznikov banky
	 */
	public ArrayList<Zakaznik> getZakaznici() {
		return zakaznici;
	}
	
	/**
	 * Funkcia na ziskanie poctu zakaznikov banky prostrednictvom enkapsulacie.
	 * @return pocet zakaznikov banky
	 */
	public int getPocetZakaznikov()
	{
		return pocetZakaznikov;
	}	
	
	/**
	 * Funkcia na ziskanie urokovej miery, ktoru poskytuje banka prostrednictvom enkapsulacie.
	 * @return hodnota urokovej miery v banke
	 */
	public double getUrokovaMiera()
	{
		return urokovaMiera;
	}
	
	/**
	 * funkcia na ziskanie poplatku prostrdnictvom enkapsulacie, ktory si banka uctuje pri platbe
	 * z uctu pri beznych zakaznikoch.
	 * @return poplatok banky
	 */
	public double getPoplatok() 
	{
		return poplatok;
	}
}