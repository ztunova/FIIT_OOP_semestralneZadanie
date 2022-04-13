package ucty;

/**
 * Vlastna vynimka ktora sa vyhadzuje ak je zostatok na ucte mensi ako 50.
 *
 */
public class NedostatocnyZostatokException extends Exception{
	
	public NedostatocnyZostatokException()
    {
        super();
    }
    public NedostatocnyZostatokException(double kolko)
    {
        super("Nedostatocny zostatok na ucte: " + kolko);
    }
    
}
