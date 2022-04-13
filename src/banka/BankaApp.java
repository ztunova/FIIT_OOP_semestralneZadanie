package banka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GUI.Registracia;
import ucty.SporiaciUcet;
import ucty.Ucet;
import zakaznici.Zakaznik;
import zakaznici.Zvyhodneny;

/**
 * Trieda BankaApp obsahuje konzolovu aplikaciu. K tej som ale neskor este dokoncila aj graficke rozhranie,
 * takze tato trieda a jej metody uz nie su nejako potrebne a uz sa nepouzivaju, kedze program je mozne  
 * ovladat cez pouzivatelske graficke rozhranie.
 */
public class BankaApp {
	/**
	 * Trieda obsahuje objekt typu Banka, zoznam zakaznik, ktory ziska z banky a konstanta koniec sluzi
	 * na ukoncenie programu. BufferedReader zase na vstup z klavesnice
	 */
	private final int koniec= 0;
	private Banka banka;
	BufferedReader bufferedReader;
	ArrayList <Zakaznik> z;
	
	/**
	 * konstruktor aplikacie. Vytvori banku, s pouzitim tohto konstruktoru vznikne banka s nulovym urokom aj
	 * poplatkom a ulozi si zoznam zakaznikov banky.
	 */
	public BankaApp()
	{
		banka= new Banka();
		z = banka.getZakaznici();
		bufferedReader= new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Konstruktor ktory prekonava konstruktor BankaApp(). Prostrednictvom tohot konstruktoru sa vytvori 
	 * banka so zadanou urokovou mierou a poplatkom
	 * @param urokM	je urokova miera ktoru bude mat banka
	 * @param popl	poplatok ktory si banka bude uctovat
	 */
	public BankaApp(double urokM, double popl)
	{
		banka= new Banka(urokM, popl);
		z = banka.getZakaznici();
		bufferedReader= new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Funkcia ktora zobrazuje menu az kym pouzivatel nezada 0, cize neukonci program
	 * @throws NumberFormatException 
	 * @throws IOException
	 */
	public void zobrazMenu() throws NumberFormatException, IOException {
		System.out.println("Vyber si z ponuky");
		System.out.println("1: pridat zakaznika");
		System.out.println("2: vlozit na ucet");
		System.out.println("3: vykonat platbu");
		System.out.println("4: skontrolovat zostatok");
		System.out.println("5: vypocitat urok");
		System.out.println("6: Historia uctu");
		System.out.println("7: vytvorit sporenie");
		System.out.println("8: prevod z bezneho uctu na sporiaci");
		System.out.println("9: historia sporiaceho uctu");
		System.out.println("0: koniec");
		int volba=Integer.parseInt(bufferedReader.readLine());;
		while (volba != koniec) {
			vykonajMenu(volba);
			System.out.println("Vyber si z ponuky");
			System.out.println("1: pridat zakaznika");
			System.out.println("2: vlozit na ucet");
			System.out.println("3: vykonat platbu");
			System.out.println("4: skontrolovat zostatok");
			System.out.println("5: vypocitat urok");
			System.out.println("6: Historia uctu");
			System.out.println("7: vytvorit sporenie");
			System.out.println("8: prevod z bezneho uctu na sporiaci");
			System.out.println("9: historia sporiaceho uctu");
			System.out.println("0: koniec");
			volba= Integer.parseInt(bufferedReader.readLine());
		}
	}
	
	/**
	 * Funkcia ktora vykonava samotne moznosti zvolene pouzivatelom. Rozdiel oproti funkcionalite
	 * grafickeho rozhrania je ten, ze v grafickom rozhrani sa prihlasi jeden platny zakaznik banky,
	 * ktory potom operuje len so svojim uctom. Tuto pouzivatel zadava ucty s ktorymi chce vykonavat 
	 * nejake akcie, teda ma pristup k lubovolnemu uctu.
	 * @param volba moznost z menu ktoru zadal pouzivatel
	 * @throws IOException
	 */
	private void vykonajMenu(int volba) throws IOException {
		
		String cisloU;
		switch (volba) {
		case 1: 
			System.out.println("Vytvorenie uctu pre noveho zakaznika");
			System.out.println("Vlozte pociatocnu sumu na ucet:");
			double vklad= Double.parseDouble(bufferedReader.readLine());
			System.out.println("Zadajte cislo uctu:");
			cisloU= bufferedReader.readLine();
			Ucet novyUcet= new Ucet(cisloU, vklad);
			
			System.out.println("Zadajte svoje meno:");
			String nMeno= bufferedReader.readLine();
			System.out.println("Zadajte svoje ID:");
			int nId= Integer.parseInt(bufferedReader.readLine());
			System.out.println("Zadajte svoj vek:");
			int nVek= Integer.parseInt(bufferedReader.readLine());
			
			if(nVek > 60 ) {
				banka.novyZvyhodneny(nMeno, novyUcet, nId, nVek, "Dochodca");
			}
			else if (nVek < 25) {
				banka.novyZvyhodneny(nMeno, novyUcet, nId, nVek, "Student");
			}
			else {
				banka.novyZakaznik(nMeno, novyUcet, nId, nVek);
			}
			for (int i=0; i< z.size(); i++) {
				z.get(i).zobraz();
				if(z.get(i) instanceof Zvyhodneny) {
					System.out.println("Zakaznik s vyhodami");
				}
			}
			break;
		case 2:
			System.out.println("Zadaj cislo uctu: ");
			cisloU= bufferedReader.readLine();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					if(aktCislo.equals(cisloU)) {
						System.out.println("Suma na vlozenie: ");
						double vlozit= Double.parseDouble(bufferedReader.readLine());
						aktUcet.vklad(vlozit);
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 3:
			System.out.println("Zadaj cislo vasho uctu: ");
			cisloU= bufferedReader.readLine();
			System.out.println("Zadaj cislo cieloveho uctu: ");
			String cisloUH= bufferedReader.readLine();
			double poplatok= banka.getPoplatok();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdeneM= false;
				boolean najdeneH= false;
				String poznm = null;
				double vlozit= 0;
				for (int i= 0; i<z.size(); i++) {
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					if(aktCislo.equals(cisloU)) {
						System.out.println("Suma na zaplatenie: ");
						double zaplatit= Double.parseDouble(bufferedReader.readLine());
						vlozit= zaplatit;
						System.out.println("Poznamka:");
						poznm= bufferedReader.readLine();
						if(z.get(i) instanceof Zvyhodneny) {
							aktUcet.platba(zaplatit, poznm);
							System.out.println("Zvyhodneny zakaznik bez poplatku ");
						}
						else {
							aktUcet.platba(zaplatit, poznm, poplatok);
						}
						najdeneM= true;
					}
					if(aktCislo.equals(cisloUH)) {
						najdeneH= true;
					}
				}
				if(najdeneH == true) {
					for (int i= 0; i<z.size(); i++) {
						Ucet aktUcet= z.get(i).getUcet();
						String aktCislo= aktUcet.getCisloUctu();
						if(aktCislo.equals(cisloUH)) {
							System.out.println("Suma na vlozenie: ");
							aktUcet.vklad(vlozit, poznm);
						}
					}
				}
				if(najdeneM== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 4:
			System.out.println("Zadaj cislo uctu: ");
			cisloU= bufferedReader.readLine();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					if(aktCislo.equals(cisloU)) {
						System.out.println("Zostatok na ucte: "+ aktUcet.getZostatok());
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 5:
			System.out.println("Zadaj cislo vasho sporiaceho uctu: ");
			cisloU= bufferedReader.readLine();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					SporiaciUcet aktSporenie= z.get(i).getSporiaciUcet();
					String aktCislo= aktSporenie.getCisloUctu();
					if(aktCislo.equals(cisloU)) {
						banka.vypocitajUrok(z.get(i));
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 6:
			System.out.println("Zadaj cislo uctu: ");
			cisloU= bufferedReader.readLine();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					if(aktCislo.equals(cisloU)) {
						aktUcet.vypisPlatby();
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 7:
			System.out.println("Zadaj cislo uctu ku ktoremu chcete vytvorit sporenie: ");
			String cisloUctu= bufferedReader.readLine();
			
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					Zakaznik aktZakaznik= z.get(i);
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					if(aktCislo.equals(cisloUctu)) {
						if(aktZakaznik.getVytvoreneSporenie() == false) {
							aktZakaznik.setVytvoreneSporenie(true);
							System.out.println("Zadaj cislo sporiaceho uctu: ");
							String cisloSporenia= bufferedReader.readLine();
							SporiaciUcet nSporenie= new SporiaciUcet(cisloSporenia, 0);
							aktZakaznik.setSporenie(nSporenie);
							System.out.println("Sporenie vytvorene: majitel "+ aktZakaznik.getMeno() + " cislo sporenia:"+ aktZakaznik.getSporiaciUcet().getCisloUctu()+ " zostatok:"+ aktZakaznik.getSporiaciUcet().getZostatok());
						}
						else {
							System.out.println("Sporenie ku tomuto uctu je uz vytvorene");
						}
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 8:
			System.out.println("Zadaj vase cislo bezneho uctu: ");
			cisloUctu= bufferedReader.readLine();
			
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					
					Zakaznik aktZakaznik= z.get(i);
					Ucet aktUcet= z.get(i).getUcet();
					String aktCislo= aktUcet.getCisloUctu();
					
					if(aktCislo.equals(cisloUctu)) {
						System.out.println("Zostatok na beznom ucte: "+ aktUcet.getZostatok());
						if(aktZakaznik.getVytvoreneSporenie() == true) {
							System.out.println("Zadaj cislo vasho sporiaceho uctu: ");
							String cisloSporenia= bufferedReader.readLine();
							SporiaciUcet aktSporenie= aktZakaznik.getSporiaciUcet();
							String aktSpCislo= aktSporenie.getCisloUctu();
							
							if(aktSpCislo.equals(cisloSporenia)) {
								System.out.println("Suma na vlozenie: ");
								double vlozit= Double.parseDouble(bufferedReader.readLine());
								aktSporenie.vklad(vlozit);
								aktUcet.platba(vlozit, "Prevod na sporiaci ucet", 0);
								System.out.println("Prevod uspesny: majitel "+ aktZakaznik.getMeno() + " cislo sporenia:"+ aktZakaznik.getSporiaciUcet().getCisloUctu()+ " zostatok:"+ aktZakaznik.getSporiaciUcet().getZostatok());
							}
							
						}
						najdene= true;
					}
				}
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 9:
			System.out.println("Zadaj cislo vasho sporiaceho uctu: ");
			String cisloSp= bufferedReader.readLine();
			if(banka.getPocetZakaznikov() == 0) {
				System.out.println("Ziadny zakaznik s uctom tu nie je");
			}
			else {
				boolean najdene= false;
				for (int i= 0; i<z.size(); i++) {
					Zakaznik aktZakaznik= z.get(i);
					if (aktZakaznik.getVytvoreneSporenie() == true) {
						SporiaciUcet aktSp= z.get(i).getSporiaciUcet();
						String aktCisloSp= aktSp.getCisloUctu();
						if(aktCisloSp.equals(cisloSp)) {
							aktSp.vypisPlatby();
							najdene= true;
						}
					}
				}	
				if(najdene== false) {
					System.out.println("Ucet sa nenasiel");
				}
			}
			break;
		case 0:
			System.out.println("Dakujeme ze vyuzivate nase sluzby");
			System.exit(0);
			break;
		default:
			System.out.println("Neplatny vstup ");
			break;
		}
	}
}
