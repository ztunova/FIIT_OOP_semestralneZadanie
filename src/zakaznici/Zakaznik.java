package zakaznici;
import ucty.SporiaciUcet;
import ucty.Ucet;

/**
 * Trieda Zakaznik, ktora predstavuje bezneho, nezvyhodneneho zakaznika. Zakaznik obsahuje atributy meno, 
 * cize meno zakaznika, jeho vek, identifikacne cislo. Kazdy zakaznik v banke ma vytvoreny bezny ucet (ucet),
 * cize paramtrom zakaznika je aj objekt typu Ucet a objekt typu SporiaciUcet (agregacia). 
 * Sporiaci ucet zakaznici najskor nemaju vytvoreny, vytvori sa im az na ziadost.
 *  Na zistenie, ci uz zakaznik ma vytvoreny sporiaci ucet alebo nie, sluzi atribut
 * vytvoreneSporenie, ktore je prednastavene na false, cize zakaznik nema vytvoreny sporiaci ucet. Ked si
 * zakaznik sporenie zalozi tento atribut nadobudne hodnotu true a prostrednictvom funkcie setSporenie()
 * sa zakaznikovy priradi aj sporiaci ucet.
 * Trieda obsahuje prevazne funkcie enkapsulacie. 
 */
public class Zakaznik {
	protected String meno;
	protected int vek;
	protected int id;
	protected Ucet ucet;
	protected boolean vytvoreneSporenie= false;
	protected SporiaciUcet sporiaciUcet;
	
	public Zakaznik() {
		meno= " ";
		id= 0;
	}
		
	/**
	 * Konstruktor ktorym sa vytvara objekt zakaznika s danym menom, vekom, ID a uctom
	 * @param nMeno	meno vytvoreneho zakaznika
	 * @param nUcet	objekt typu Ucet je bezny ucet ktory sa priradi zakaznikovy
	 * @param nId	ID vytvoreneho zakaznika
	 * @param nVek	vek vytvoreneho zakaznika
	 */
	public Zakaznik(String nMeno, Ucet nUcet, int nId, int nVek)
	{
		this.meno= nMeno;
		this.ucet= nUcet;
		this.id= nId;
		this.vek= nVek;
	}
		
	public void zobraz()
	{
		System.out.println("Meno zakaznika: "+ meno + " vek: "+ vek+" ID: "+id+ ",cislo uctu: "+ ucet.getCisloUctu() + ", zostatok na ucte: "+ ucet.getZostatok());
	}
		
	/**
	 * Funkcia na ziskanie mena zakaznika cez enkapsulaciu
	 * @return	meno zakaznika
	 */
	public String getMeno()
	{
		return meno;
	}
	
	/**
	 * Funkcia na nastavenie mena zakaznika cez enkapsulaciu
	 * @param nMeno	meno zakaznika
	 */
	public void setMeno(String nMeno) {
		this.meno= nMeno;
	}
		
	/**
	 * Funkcia na ziskanie uctu zakaznika cez enkapsulaciu.
	 * @return Funkcia vracia objekt typu ucet - ucet priradeny zakaznikovy
	 */
	public Ucet getUcet()
	{
		return ucet;
	}
	
	/**
	 * Funkcia na zistenie ci dany zakaznik ma alebo nema vytvorene sporenie (enkapsulaciou)
	 * @return	vracia logicku hodnotu false alebo true podla toho, ci zakaznik ma alebo nema vytvoreny
	 * sporiaci ucet (respektive vracia atribut vytvoreneSporenie tak ako ostatne funkcie enkapsulacie)
	 */
	public boolean getVytvoreneSporenie() {
		return vytvoreneSporenie;
	}
	
	/**
	 * Funkcia na nastavenie ci zakaznik ma alebo nema vytvorene sporenie cez enkapsulaciu.
	 * @param stav parameter typu boolean - false ak zakaznik nema mat vytvorene sporenie, true
	 * ak ma mat. Parameter moze nadobudnut aj hodnotu false ale v programe pouzivam len true, ked
	 * zakaznik zada ze chce vytvorit sporiaci ucet, pretoze false je prednastavene. Znamena, 
	 * ze na zacitku zakaznik sporenie nema vytvorene.
	 */
	public void setVytvoreneSporenie(boolean stav) {
		this.vytvoreneSporenie= stav;
	}
	
	/**
	 * Funkcia na nastavenie sporiaceho uctu zakaznikovy prostrednictvom enkapsulacie. Zakaznikovy
	 * priradi objekt typu SporiaciUcet (agregacia).
	 * @param nSporenie	Objekt typu SporiaciUcet, ktory sa priradi zakaznikovy
	 */
	public void setSporenie(SporiaciUcet nSporenie) {
		this.sporiaciUcet = nSporenie;
	}
	
	/**
	 * Funkcia na ziskanie sporiaceho uctu zakaznika cez enkapsulaciu
	 * @return	Funkcia vracia objekt typu SporiaciUcet priradeny zakaznikovy
	 */
	public SporiaciUcet getSporiaciUcet()
	{
		return sporiaciUcet;
	}
	
	/**
	 * funkcia na ziskanie identifikacneho cisla zakaznika cez enkapsulaciu
	 * @return Funkcia vracia identifikacne cislo zakaznika
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Funkcia na ziskanie veku zakaznika cez enkapsulaciu
	 * @return Vracia vek zakaznika
	 */
	public int getVek() {
		return vek;
	}

	
}
