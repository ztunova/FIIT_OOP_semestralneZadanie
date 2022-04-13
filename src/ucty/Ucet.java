package ucty;

import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 * Trieda Ucet, ktora reprezentuje bezny ucet ktory ma kazdy zakaznik banky. Tato trieda dedi parametre a 
 * metody z triedy SporiaciUcet. Okrem parametrov zdedenych z tejto tiredy obsahuje este argument prvaPlatba.
 * Ta je na zaciatok nastavena na true, co znamena, ze zakaznik este nevykonal prvu platbu z uctu. Prva 
 * platba je specialna preto, lebo za prvu platbu banka neuctuje ziadny poplatok ani beznemu nezvyhodnenemu
 * zakaznikovy.
 */
public class Ucet extends SporiaciUcet{
	private boolean prvaPlatba= true;
	private int pocitadlo;	
	
	/**
	 * konstruktor na vytvorenie uctu bez definovanych hodnot pre parametre triedy
	 */
	public Ucet() {
		cisloUctu= " ";
		zostatok= 0.0;
		pocitadlo= 0;
		initZaznamy();
	}
	
	/**
	 * Konstruktor prekonavajuci konstruktor Ucet(). Tento konstruktor tiez vytvara objekty typu ucet 
	 * ale ako vstupne argumenty dostane hodnoty, ktore ulozi do atributov triedy. Konstruktor tiez
	 * kontroluje zostatok na ucte, ci je vacsi ako 50, inak by vyhodil vlastnu vynimku.
	 * @param nCisloUctu cislo novo vytvoreneho bezneho uctu
	 * @param nZostatok  zostatok na vytvoranom ucte
	 */
	public Ucet(String nCisloUctu, double nZostatok) {
		try {
			skontroluj(nZostatok);
			cisloUctu= nCisloUctu;
			zostatok= nZostatok;
			pocitadlo=0;
			initZaznamy();
		}
		catch (NedostatocnyZostatokException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void initZaznamy() {
		platby= new ArrayList<Zaznam>();
		platby.add(new Zaznam());
		platby.get(pocitadlo).pridajZaznam("Pociatocny vklad", "Zalozenie uctu", zostatok, zostatok);
		pocitadlo= pocitadlo+1;
	}
	
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
	 * Kontrolna funkcia ktora skontroluje zostatokna ucte, ktory ak by bol mensi ako 50 vyhodi vlastnu
	 * vynimku 
	 * @param suma	Funkcia dostane ako vstupny parameter zostatok na ucte ktory ma skontrolovat
	 * @throws NedostatocnyZostatokException vlastna vynimka v pripade nedostatocneho zostatku na ucte
	 */
	public void skontroluj(double suma) throws NedostatocnyZostatokException
	{
		if(suma < 50) {
			throw new NedostatocnyZostatokException(suma);
		}
	}
	
	/**
	 * Funkcia na vykonanie platby z uctu. Najskor kontroluje platnost vstupu, ci suma ktora sa ma platit
	 * je vacsia ako 0. Nasledne kontroluje ci zakaznik uz spravil prvu platbu, teda sa odratava z
	 * uctu aj poplatok pre banku, alebo ci prave robi prvu platbu, teda poplatok pre banku sa este neuctuje.
	 * Nasledne funkcia skusa ci po odpocitany sumy na zaplatenie ostane na ucte zostatok spominanych 50. 
	 * Ak by bol zostatok v poriadku z uctu sa odpocita platena suma, inak funkcia skonci vlastnou vynimkou.
	 * @param kolko	suma ktora sa ma z uctu zaplatit
	 * @param poznm	poznamka k platbe od pouzivatela
	 * @param poplatok	poplatok ktory si uctuje banka za platbu
	 */
	public void platba(double kolko, String poznm, double poplatok) 
	{
		if (kolko >= 0) {
			if(prvaPlatba== true)
			{
				double pomocny= zostatok;
				pomocny= pomocny - kolko;
				
				try
				{
					skontroluj(pomocny);
					zostatok = zostatok - kolko;
					platby.add(new Zaznam());
					Zaznam pridany= platby.get(pocitadlo);
					pridany.pridajZaznam("Platba", poznm, kolko, zostatok);
					pocitadlo= pocitadlo +1;
					System.out.println("Platba uspesna, zvysny zostatok: "+ zostatok);
					prvaPlatba= false;
				}
				catch (NedostatocnyZostatokException ex)
				{
					ex.printStackTrace();
				}
				
			}
			else {
				double pomocny= zostatok;
				pomocny= pomocny - kolko - poplatok;
				
				try
				{
					skontroluj(pomocny);
					zostatok = zostatok - kolko - poplatok;
					platby.add(new Zaznam());
					Zaznam pridany= platby.get(pocitadlo);
					pridany.pridajZaznam("Platba", poznm, kolko, zostatok);
					pocitadlo= pocitadlo +1;
					System.out.println("Platba uspesna, zvysny zostatok: "+ zostatok);
				}
				catch (NedostatocnyZostatokException ex)
				{
					ex.printStackTrace();
				}
				
			}
		}
		else {
			System.out.println("Plat kladnu sumu");
		}
	}
	
	/**
	 * Funkcia ktora tiez sluzi na vykonanie platby z uctu. Tato funkcia ale vo vstupnych parametroch 
	 * nedostane poplatok, ktory si uctuje banka, teda v tejto funkcii poplatok za platbu neplati.
	 * Takisto sa tu nekontroluje ci je uz prva platba bola spravena, pretoze tu sa aj tak poplatok nerata.
	 * Tuto funkciu pouzivam pri platbach zvyhodnenych zakaznikov.
	 * @param kolko	suma na zaplatenie
	 * @param poznm	poznamka k platbe od pouzivatela
	 */
	public void platba(double kolko, String poznm) 
	{
		if (kolko >= 0) {
			double pomocny= zostatok;
			pomocny= pomocny - kolko;
			try
			{
				skontroluj(pomocny);
				zostatok = zostatok - kolko;
				platby.add(new Zaznam());
				Zaznam pridany= platby.get(pocitadlo);
				pridany.pridajZaznam("Platba", poznm, kolko, zostatok);
				pocitadlo= pocitadlo +1;
				System.out.println("Platba uspesna, zvysny zostatok: "+ zostatok);
			}
			catch (NedostatocnyZostatokException ex)
			{
				ex.printStackTrace();
			}
				
		}
			
		else {
			System.out.println("Plat kladnu sumu");
		}
	}
	
	public void vypisPlatby() {
		for(Zaznam zaznam : platby) {
			if(zaznam.getZmena() != 0.0) {
				System.out.println("*************************");
				System.out.println("Cislo uctu: "+ getCisloUctu());
				zaznam.vypisZaznam();
				System.out.println("*************************");
			}
		}
	}
	
	public void vypisPlatby(JTextArea textArea) {
		for(Zaznam zaznam : platby) {
			if(zaznam.getZmena() != 0.0) {
				textArea.append("*******************************\n");
				zaznam.vypisZaznam(textArea);
				textArea.append("********************************\n");
			}
		}
	}

}
