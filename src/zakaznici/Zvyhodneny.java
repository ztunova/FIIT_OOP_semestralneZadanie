package zakaznici;
import ucty.Ucet;

/**
 * Trieda Zvyhodneny, ktora predstavuje triedu zvyhodneneho zakaznika, ktoremu banka neuctuje poplatok
 * pri platbe z uctu. Tato tireda dedi atributy a metody z triedy Zakaznik. Okrem toho obsahuje este
 * atribut typu String typ, ktory reprezentuje typ zvyhodneneho zakaznika. Ten sa odvija od veku zakaznika.
 * Ak je zakaznik mladsi ako 25 rokov jeho typ je student, ak je zakaznik starsi ako 60 rokov, jeho typ je
 * senior. 
 *
 */
public class Zvyhodneny extends Zakaznik {
	private String typ;
	
	/**
	 * Konstruktor ktory vytvara objekty typu Zvyhodneny.
	 * @param nMeno	meno zvyhodneneho zakaznika
	 * @param nUcet	parameter typu Ucet, reprezentuje bezny ucet priradeny zvyhodnenemu zakaznikovy
	 * @param nId	identifikacne cislo zvyhodneneho zakaznika
	 * @param nVek	vek zvyhodneneho zakaznika
	 * @param nTyp	typ zvyhodneneho zakaznika (senior alebo student)
	 */
	public Zvyhodneny(String nMeno, Ucet nUcet, int nId, int nVek, String nTyp)
	{
		this.meno= nMeno;
		this.ucet= nUcet;
		this.id= nId;
		this.vek= nVek;
		this.typ= nTyp;
	}
	
	/**
	 * Funkcia na zistenie typu zvyhodneneho zakaznika cez enkapsulaciu
	 * @return	Funkcia vracia typ zvyhodneneho zakaznika (student alebo senior)
	 */
	public String getTyp() {
		return typ;
	}
	
	/**
	 * Funkcia na nastavenie typu zvyhodneneho zakaznika cez enkapsulaciu
	 * @param nTyp	typ zvyhodneneho zakaznika
	 */
	public void setTyp(String nTyp) {
		this.typ= nTyp;
	}
	
	public void zobraz()
	{
		System.out.println("Meno zakaznika: "+ meno + " vek: "+ vek+ " status: "+ typ+" ID: "+id+ ",cislo uctu: "+ ucet.getCisloUctu() + ", zostatok na ucte: "+ ucet.getZostatok());
	}
}