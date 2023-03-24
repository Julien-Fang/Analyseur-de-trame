package obj;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

	
	//Test d'une erreur de caractere OK
	//Test de l'offset PAS ENCORE
	//Test sur les octets de taille 2 PAS BSN C SUPPOSé DANS L ENONCE
	public static void main (String [] args) throws FileNotFoundException,IOException, NumberFormatException, IndexOutOfBoundsException {
		try {		
		Trace t = new Trace(new File("data/Trace2.txt"));
		
		t.lectureTrame();
		System.out.println("afficher");
		t.afficher();
		System.out.println("nombre de trame : "+Trace.getCpt());
		//Ethernet
		
		//Ethernet t1=t.getTrame(3);

		//t1.afficheEthernet();

		//System.out.println("petit test du long :"+Convertor.hexlongtoint("05a3"));
		
		} catch(FileNotFoundException fne) {
			throw new FileNotFoundException("Fichier non trouve\n");
		} catch(IOException ioe) {
			throw new IOException("Probleme de fichier\n");
		}		
		catch (NumberFormatException nfe) {
			throw new NumberFormatException("Erreur de conversion");
		}
		catch(IndexOutOfBoundsException ibe) {
			throw new IndexOutOfBoundsException("Vous souhaitez voir une trame invalide ou, se trouvant apres une trame invalide");
		}

	}
	
}
