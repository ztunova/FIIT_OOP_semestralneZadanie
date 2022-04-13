package App;

import GUI.Registracia;
import banka.Banka;

/**
 * Trieda AppMain obsahuje len funkciu main, kde sa vytvori objekt typu Banka s urcitou urokovou mierou
 * a poplatkom za platbu. K tomu sa este vytvori a zobrazi uvodne okno pre registraciu noveho zakaznika.
 *  
 * @author Zofia Tunova
 *
 */

public class AppMain {

	public static void main(String[] args) {
		Banka banka= new Banka(8.5, 10);
		Registracia reg= new Registracia(banka);
		reg.zobrazRegistraciu();

	}

}
