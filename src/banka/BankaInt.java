package banka;
import java.util.ArrayList;

import ucty.SporiaciUcet;
import ucty.Ucet;
import zakaznici.Zakaznik;

/**
 * Rozhranie banky implementovane v triede Banka. Rozhranie obsahuje hlavicky funkcii pouzitych v triede
 * Banka. Rozhranie obsahuje jednu default metodu vypocitajUrok.
 */
public interface BankaInt {
	/**
	 * Funkcia ktora zurocuje zostatok na sporiacom ucte zakaznika, ktoreho dostane ako vstupny parameter.
	 * K zostatku na ucte pripisuje uorky, ktore vypocita ako 
	 * zostatokNaSporiacomUcteZakaznika* urokovaMieraBanky/100 (urokova miera je v %)  
	 * @param zakaznik	objekt typu Zakaznik, ktory reprezentuje zakaznika ktoreho zostatok na sporiacom
	 * ucte ma byt zuroceny
	 */
	default void vypocitajUrok(Zakaznik zakaznik) 
	{
		SporiaciUcet u= zakaznik.getSporiaciUcet();
		double uZostatok= u.getZostatok();
		double pridanaSuma= uZostatok* getUrokovaMiera()/100;
		double zurocenyZostatok= uZostatok + pridanaSuma;
		u.setZostatok(zurocenyZostatok);
		System.out.println("Urokova suma: " + pridanaSuma + ", zostatok po zuroceni: "+ u.getZostatok());
		
	}
	Zakaznik najdiZakaznika(int hladaneId);
	void novyZakaznik(String nMeno, Ucet nUcet, int nId, int nVek);
	void novyZvyhodneny(String nMeno, Ucet nUcet, int nId, int nVek, String nTyp);
	ArrayList<Zakaznik> getZakaznici();
	int getPocetZakaznikov();
	double getUrokovaMiera();
	double getPoplatok();
}
