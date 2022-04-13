package ucty;

import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 *Trieda SporiaciUcet reprezentuje sporiaci ucet ktory si zakaznici banky mozu zalozit. Obsahuje 
 *argumenty zostatok, co je aktualny zostatok na sporiacom ucte, cislo uctu, pocitadlo, v ktorom si ukladam
 *pocet zaznamov o prevodoch na ucet a este obsahuje ArrayList platby, v ktorom sa ukladaju zaznamy
 *o platbach alebo vkladoch na ucet (pre sporiaci ucet su to len vklady pretoze sa z neho neda platit)
 *
 */
public class SporiaciUcet {
	protected double zostatok;
	protected String cisloUctu;
	private int pocitadlo;
	protected ArrayList <Zaznam> platby;
	
	/**
	 * Konstruktor ktory vytvara objekt typu SporiaciUcet bez blizsej specifikacie argumentov.
	 */
	public SporiaciUcet() {
		cisloUctu= " ";
		zostatok= 0.0;
		pocitadlo= 0;
		initZaznamy();
	}
	
	/**
	 * Konstruktor ktory prekonava konstruktor SporiaciUcet(). Tento nastavi hodnoty argumentov.
	 * @param nCisloUctu	Cislo noveho sporiaceho uctu
	 * @param nZostatok		Zostatok na novom sporiacom ucte
	 */
	public SporiaciUcet(String nCisloUctu, double nZostatok) {
		cisloUctu= nCisloUctu;
		zostatok= nZostatok;
		pocitadlo=0;
		initZaznamy();
	}
	
	/**
	 * Funkcia ktora inicializuje ArrayList zaznamov o platbach. Najskor tento ArrayList vytvori
	 * a prida do neho prvy zaznam o pociatocnom vklade na ucet
	 */
	private void initZaznamy() {
		platby= new ArrayList<Zaznam>();
		platby.add(new Zaznam());
		platby.get(pocitadlo).pridajZaznam("Pociatocny vklad", "Zalozenie uctu", zostatok, zostatok);
		pocitadlo= pocitadlo+1;
	}
	
	/**
	 * Funkcia na vlozenie urcitej sumy na ucet. Vstupnym parametrom je suma ktora ma byt vlozena na ucet,
	 * ta sa pripocita k aktualnemu zostatku na ucte. Potom prida zaznam do zoznamu platieb
	 * a vypise oznam o uspesnom vlozeni sumy na ucet. Vkladanie sumy prebieha iba ak je vkladana suma 
	 * vacsia ako 0, v pripade ze nie je, program vypise upozornenie.
	 * @param kolko suma ktora sa ma vlozit na ucet
	 */
	public void vklad(double kolko)
	{
		if(kolko>= 0) {
			zostatok= zostatok + kolko;
			platby.add(new Zaznam());
			Zaznam pridany= platby.get(pocitadlo);
			pridany.pridajZaznam("Vklad", null, kolko, zostatok);
			pocitadlo= pocitadlo+1;
			System.out.println(kolko+" bolo uspesne vlozene na ucet. " + "Novy zostatok je "+zostatok);
		}
		else {
			System.out.println("Vloz kladnu sumu");
		}
		
	}
	
	/**
	 * Funkcia ktora prekonava predchadzajucu funkciu vklad(). Tato funkcia dostane za parameter aj poznamku
	 * ktoru zapise do zaznamu o pohyboch na ucte. Tato funkcia sa vyuziva vtedy, ak pouzivatel banky
	 * vykonava platbu na iny ucet banky. Vtedy on vykona platbu, cize zada poznamku a rovnaka poznamka
	 * sa zapise aj do zaznamov prijimatela, ktory ale tuto platbu vedie ako vklad.
	 * @param kolko	suma ktora sa ma previest na ucet	
	 * @param poznm	poznamka kora sa zapise do zaznamov o pohyboch na ucte
	 */
	public void vklad(double kolko, String poznm)
	{
		if(kolko>= 0) {
			zostatok= zostatok + kolko;
			platby.add(new Zaznam());
			Zaznam pridany= platby.get(pocitadlo);
			pridany.pridajZaznam("Vklad", poznm, kolko, zostatok);
			pocitadlo= pocitadlo+1;
			System.out.println(kolko+" bolo uspesne vlozene na ucet. " + "Novy zostatok je "+zostatok);
		}
		else {
			System.out.println("Vloz kladnu sumu");
		}
		
	}
	
	/**
	 * Funkcia ktora vypise celu historiu uctu. For cyklom prejde cely zoznam o pohyboch na ucte a 
	 * prostrednictvom funkcie vypisZaznam() sa vypisu jednotlive zaznamy v zozname.
	 */
	public void vypisPlatby() {
		for(Zaznam zaznam : platby) {
			if (zaznam.getZmena() != 0.0) {
				System.out.println("*************************");
				System.out.println("Cislo uctu: "+ getCisloUctu());
				zaznam.vypisZaznam();
				System.out.println("*************************");
			}
		}
	}
	
	/**
	 * Funkcia ktora rovnako ako predchadzajuca funkcia vypisPlatby() vypise celu historiu uctu. Tato 
	 * funkcia robi ale vypis do grafickeho rozhrania, zatial co predchadzajuca funkcia vypisuje zazamy
	 * do konzoly.
	 * @param textArea oblast textu v grafickom rozhrani na ktoru funkcia vypise zaznmy
	 */
	public void vypisPlatby(JTextArea textArea) {
		for(Zaznam zaznam : platby) {
			if(zaznam.getZmena() != 0.0) {
				textArea.append("*******************************\n");
				zaznam.vypisZaznam(textArea);
				textArea.append("********************************\n");
			}
		}
	}
	
	/**
	 * Funkcia na ziskanie cisla uctu cez enkapsulaciu
	 * @return Funkcia vracia retazec cislo uctu
	 */
	public String getCisloUctu() {
		return cisloUctu;
	}
	
	/**
	 * Funkcia na nastavenie cisla uctu cez enkapsulaciu
	 * @param cislo	cislo noveho uctu
	 */
	public void setCisloUctu(String cislo) {
		this.cisloUctu= cislo;
	}
	
	/**
	 * Funkcia na ziskanie zostatku zo sporiaceho uctu cez enkapsulaciu
	 * @return	Funkcia vracia zostatok sporiaceho uctu
	 */
	public double getZostatok() {
		return zostatok;
	}
	
	public void setZostatok(double suma) {
		this.zostatok= suma;
	}
	
	public int getPocitadlo()
	{
		return pocitadlo;
	}

}
