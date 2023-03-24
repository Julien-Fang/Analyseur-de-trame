package obj;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trace {

	private static List<Ethernet> lt;
	private File file;
	private int nbtramevalide;
	private int nbtramejete;

	private static int cpt = 0;
	
	public Trace(File file) {
		this.file=file;
		lt=new ArrayList<>();
		nbtramevalide=0;
		nbtramejete=0;
	}
	
	public void lectureTrame()throws FileNotFoundException, IOException,IndexOutOfBoundsException{
		lt.clear();
		cpt=0;
		List<String> listemin= new ArrayList<>();
		listemin.add("0");listemin.add("1");listemin.add("2");listemin.add("3");listemin.add("4");listemin.add("5");listemin.add("6");
		listemin.add("7");listemin.add("8");listemin.add("9");listemin.add("a");listemin.add("b");listemin.add("c");listemin.add("d");
		listemin.add("e");listemin.add("f");
		

		List<String> list=new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		long ancien_offset=0;
		long cur_offset=0;
		boolean b=true;
		boolean agarder=true;
		int quantite_donnee=0;
		while (( line = br.readLine()) !=null ) {
			String[] tabs= line.split("   ");
			
			if(b==false) {
				System.out.println("Nombre de trame valide avant une trame invalide :"+ cpt);
				break;} 


			
			if(tabs[0].equals("0000") && !list.isEmpty()  ) {//&& nbtramevalide>0
				if( b==false ) {
			
					list.clear();
					nbtramejete++;
					b=true;
	
				}
				
				else {		
					
					lt.add(new Ethernet(new ArrayList<>(list)));
					list.clear();
					nbtramevalide++;
					b=true;
					cpt++;
				}
			}
						
			if(line.isEmpty()) {
				continue;
			}
			quantite_donnee=0;
			for( String word : tabs[1].split(" ") ) {
				
				String min=new String();
				if( word.length()==2 ) {
					min+=word.toLowerCase();
					if( !(listemin.contains(min.charAt(0)+"")) ||  !(listemin.contains(min.charAt(1)+""))  ) {
						// trame a supprimer
						
						b=false;
					}
				}
					list.add(word);
				//	quantite_donnee++;
			}

			//nbtramevalide++;
			
		}
	if(b==false) nbtramejete++;
		
	if(!(list.isEmpty()) && b )
		lt.add(new Ethernet(new ArrayList<>(list)));
		
	br.close();
	}
	
	


	public static int getCpt() {
		return cpt;
	}




	public static List<Ethernet> getLt(){
		return lt;
	}
	
	public Ethernet getTrame(int i) {
		return lt.get(i);
	}
	
	public void afficher() {
		for(Ethernet t : lt) {
			
			t.afficheTrame();
			System.out.println("\n");
		}
		System.out.println("trame fausse : "+nbtramejete);
		System.out.println("trame valide : "+nbtramevalide);

	}
	
	
}
