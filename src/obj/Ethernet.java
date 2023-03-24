package obj;
import java.io.*;
import java.util.*;

public class Ethernet {

	private List<String> l;
	private String dest_MAC;
	private String src_MAC;
	private String type;
	private int port;
	private IP p;
	
	private final int num;

	
	public Ethernet(List<String> l) {
		this.l=l;
		dest_MAC= new String();
		src_MAC=new String();
		type= new String("type : ");
		type="0x";
		num = Trace.getCpt();		
		
		//Dest MAC
		for(int i=0 ; i< 6 ;i++) {
			dest_MAC+=l.get(i);
			if( i<5) {
				dest_MAC+=":";
			}
		}
		if(dest_MAC.equals("ff:ff:ff:ff:ff:ff")) {
			dest_MAC="Destination : Broadcast (ff:ff:ff:ff:ff:ff)";
		}

		
		//Source MAC
		for(int i = 6; i < 12 ; i++) {
			src_MAC+=l.get(i);
			if( i<11) {
				src_MAC+=":";
			}
		}

		
		//Type
		p=null;
		String tmp= Convertor.strtobyte(l.get(14));
		tmp=tmp.substring(0,4);
		int i = Convertor.strbintoint(tmp);
		type +="IPv"+i+" (0x"+l.get(12)+l.get(13)+")";

		
		if(l.get(12).equals("08") && l.get(13).equals("00")) {
			p=new IP(l, this);
		}
		else {
			System.out.println("Protocole non traité pour la trame "+num);
			type+=" Protocole Ethernet non traité";
		}
		
	}
	

	
	public String getDest_mac() {
		return dest_MAC;
	}
	
	
	public String getSrc_mac() {
		return src_MAC;
	}
	
	
	public String getType() {
		return type;
	}
	

	public IP getP() {
		return p;
	}
	
	
	public List<String> getL(){
		return l;
	}

		

	
	public void afficheTrame() {
		int taille=0;
		for (String s: getL()) {
			System.out.print(s+" ");
			taille++;
		}
		System.out.println("taille : "+taille);
	}
	
	
	public void afficheEthernet() {
		System.out.println("\nEthernet\nDestination : "+this.getDest_mac()+"\nSource : "+this.getSrc_mac()+"\n"+this.getType());
		if( getP()!=null)
			getP().lire();
	}



	public int getNum() {
		return num;
	}
	
	
	public String toString() {
		if( p!=null )
			return getP().toString();
		else
			return "Destination MAC: "+getDest_mac()+" Source MAC: "+getSrc_mac()+" Type: "+getType() ;
	}
	
	public String getComm() {
		if( p!= null)
			return getP().getComm();
		else
			return "Destination MAC: "+getDest_mac()+" Source MAC: "+getSrc_mac()+" Type: "+getType() ;
	}
	

}
