package ucty;

import javax.swing.JTextArea;

/**
 * Trieda Zaznam, ktora reprezentuje zaznamy o platbach na ucte. Trieda obsahuje argumenty poznamka, co je
 * poznamka ktoru ma pouzivatel moznost zadat pri platbe z uctu. Pri vkladani na ucet sa poznamka nezadava.
 * zostavajucaSuma je udaj o zostatku ktory bol na ucte v case vytvorenia zaznamu, cize v case platenia
 * alebo vkladania na ucet. Zmena je udaj o sume ktora sa zmenila, bud bola na ucet pridana alebo z
 * uctu odpisana. Typ reprezentuje co sa s uctom robilo. Bud je to "vklad" alebo je to "platba".
 *
 */
public class Zaznam {
	private String poznamka;
	private double zostavajucaSuma;
	private double zmena;
	private String typ;
	
	/**
	 * Konstruktor na vytvorenie zaznam. Retazce nastavi najskor na prazdne retazce a vsetky sumy na 0
	 * (zmenia sa vo funkcii pridajZaznam())
	 */
	public Zaznam() {
		typ= " ";
		poznamka= " ";
		zostavajucaSuma= 0.0;
		zmena= 0.0;
	}
	
	/**
	 * Funkcia ktora zapisuje hodnoty vytvorenemu zaznamu
	 * @param nTyp	typ pohybu na ucte, bud platba alebo vklad
	 * @param nPoznamka	poznamka od pouzivatela
	 * @param nZmena	suma ktora sa na ucte zmeniala - pri vklade sa pripocitala alebo pri platbe sa z
	 * uctu odpocitala
	 * @param nZostatok	novy zostatok na ucte po vlozeni alebo platbe
	 */
	public void pridajZaznam(String nTyp, String nPoznamka, double nZmena, double nZostatok){
		typ= nTyp;
		poznamka= nPoznamka;
		zmena= nZmena;
		zostavajucaSuma= nZostatok;
	}
	
	/**
	 * Funkcia ktora vypise jeden zaznam. Pouziva sa vo funkcii vypisPlatby(), ktora vypise celu historiu
	 * uctu. Tato jej cast vypise len jeden zaznam do konzoly (pouzivala sa v konzolovej aplikacii).
	 */
	public void vypisZaznam() {
		System.out.println("Typ zaznamu: "+ typ);
		if(poznamka == null) {
			System.out.println("Poznamka: ----");
		} else {
			System.out.println("Poznamka: "+ poznamka);
		}
		System.out.printf("Suma v pohybe: %.2f\n", zmena);
		System.out.printf("Zostavajuca suma: %.2f\n", zostavajucaSuma);

	}
	
	/**
	 * Rovnaka funkcia ktora vypisuje jednotlive zaznamy uctu s tym rozdielom, ze tato funkcia dostane ako
	 * vstupny parameter oblast textu v grafickom rozhrani. Zaznamy teda nevypisuje do konzoly ale do 
	 * grafickeho rozhrania.
	 * @param textArea	Oblast textu v grafickom rozhrani na ktoru zaznamy vypise.
	 */
	public void vypisZaznam(JTextArea textArea) {
		textArea.append("Typ zaznamu: "+ typ+ "\n");
		if(poznamka == null) {
			textArea.append("Poznamka: ----\n");
		} else {
			textArea.append("Poznamka: "+ poznamka+"\n");
		}
		String s=String.valueOf(zmena);
		textArea.append("Suma v pohybe: "+ s+"\n");
		s=String.valueOf(zostavajucaSuma);
		textArea.append("Zostavajuca suma: "+s+"\n");

	}
	
	/**
	 * Funkcia ktora vrati sumu ktora sa na ucte zmenila - bud bola na ucet pripisana alebo bola z uctu 
	 * odpisana prostrednictvom enkapsulacie
	 * @return	Vracia sumu ktora sa na ucte zmenila
	 */
	public double getZmena() {
		return zmena;
	}
	
	/**
	 * Funkcia na ziskanie poznamky ktoru zadal pouzivatel prostrednictvom enkapsulacie
	 * @return	Funkcia vracia retazec poznamky.
	 */
	public String getPoznamka() {
		return poznamka;
	}
}
