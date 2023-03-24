package obj;

import java.util.List;

public class TCP {

	private String srcport;
	private String destport;
	private String len;
	
	private static long seqnum2=-1; //raw
	private static long seqnum=-1; //raw
	private static boolean ackb=true;
	private static boolean seqb=false;
	
	private static String servsourceport;
	private long seqre=0;
	private long ackre=0;
	
	private String headerlength;
	
	private boolean urg;
	private boolean ack;
	private boolean psh;
	private boolean rst;
	private boolean syn;
	private boolean fin;
	
	private String window;
	private String checksumTCP;
	private String urgentpointer;
	private String options;
	
	//private HTTP http;
	private Ethernet trame;
	
	private boolean toti;
	private String http;
	
	
	public TCP( List<String> l,int indice, Ethernet t) {
		trame = t;
		
		int compteur =indice;
		
		//Source Port
		srcport=new String("");
		srcport+= Convertor.hextoint(l.get(compteur)+l.get(compteur+1));
		compteur+=2;
		
		//Destination Port
		destport=new String("");
		destport+=Convertor.hextoint(l.get(compteur)+l.get(compteur+1));
		compteur+=2;
		
		//Sequence Number
		if( seqnum2==-1 && seqb ) {
			seqb=false;
			seqnum2=Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3));
			seqre = 0;
			servsourceport=new String(getSrcport());
		}	
		else if( seqnum==-1) {
			seqb=true;
			seqnum=Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3));
			ackre=0;

			
		}
		else {
			if ( getSrcport().equals(servsourceport) ) {
				seqre =  Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3)) - seqnum2;
			}
			else {
				seqre =  Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3)) -seqnum; 

			}
		}
		compteur+=4;

		
		//Acknowledgment number
		if( ackb ) {
			ackre=0;
			ackb=false;			
		}
		else {
			if (getSrcport().equals(servsourceport)) {
				ackre = Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3)) - seqnum;
			}
			else {
				ackre = Convertor.hexlongtoint(l.get(compteur)+l.get(compteur+1)+l.get(compteur+2)+l.get(compteur+3)) - seqnum2;
			}
			
		}
		compteur+=4;
		
		
		//HeaderLength
		
		String tmp=Convertor.strtobyte(l.get(compteur));

		tmp=tmp.substring(0, 4);
		headerlength=new String();
		int i=Convertor.hextoint(l.get(compteur).substring(0, 1))*4;
		headerlength=tmp+" .... = Header Length: "+i+" bytes";
		compteur++;
		
		//Flags
		tmp=Convertor.strtobyte(l.get(compteur));
		compteur++;
		tmp=tmp.substring(2,tmp.length()-1);
		urg =  tmp.charAt(0)=='1' ? true : false;
		ack = ( tmp.charAt(1)=='1' )? true : false ;
		psh = ( tmp.charAt(3)=='1' )? true : false ;
		rst = ( tmp.charAt(4)=='1') ? true : false;
		syn = ( tmp.charAt(5)=='1') ? true : false;
		fin = ( tmp.charAt(6)=='1') ? true : false;
		
		//Window
		window=new String();
		window=""+Convertor.hextoint(l.get(compteur)+l.get(compteur+1));
		compteur+=2;
		
		//CheckSum TCP
		checksumTCP=new String("0x");
		checksumTCP+=l.get(compteur)+l.get(compteur+1);
		compteur+=2;
		
		//Urgent Pointer
		urgentpointer=new String();
		urgentpointer+=l.get(compteur)+l.get(compteur+1);
		compteur+=2;
		
		//Options
		options=new String();
		boolean b=true;
		while (b && compteur<l.size()) {
			if( l.get(compteur).equals("02") ) { 
				int taille = Convertor.hextoint(l.get(compteur+1));
				options+="MSS="+Convertor.hextoint(l.get(compteur+2)+l.get(compteur+3));
				compteur+=taille;
			}
			else if( l.get(compteur).equals("03") ) {
				int taille= Convertor.hextoint(l.get(compteur+1));
				int tmpp= Convertor.hextoint(l.get(compteur+2));
				tmpp= (int) Math.pow(2, tmpp);
				options+=" WS="+tmpp;
				compteur+=taille;				
			}
			else if( l.get(compteur).equals("04")) {
				if( l.get(compteur+1).equals("02")) {
					options+=" SACK_PERM";}
				compteur+=2;
			}
			
			
			
			else if ( l.get(compteur).equals("08")) {
				int taille= Convertor.hextoint(l.get(compteur+1));
				Long valts=Convertor.hexlongtoint(l.get(compteur+2)+l.get(compteur+3)+l.get(compteur+4)+l.get(compteur+5));
				Long valsec=Convertor.hexlongtoint(l.get(compteur+6)+l.get(compteur+7)+l.get(compteur+8)+l.get(compteur+9));
				options+=" TSval="+valts+" TSecr="+valsec;
				compteur+=taille;
			}
			
			else if( l.get(compteur).equals("01") ) {compteur+=1;}
			else 
				b=false;
			
			
		}
		
		len=new String("Len=");
		len+=l.size()-compteur;
		
		//Pour HTTP
		
		if( psh && ack) {
			toti=false;
			http=null;
			int nous=t.getNum();//notre trame
			String info1sport=srcport;
			String info2dport=destport;
			String info3window=window;
	
			toti=lfrequete(t,compteur);
			if( toti==false) {
			
				for( int j= 0; j < nous ; j++ ) { // parcours des trames avant nous
					if(j<Trace.getLt().size()) {
						Ethernet courante=  Trace.getLt().get(j);
						if( courante.getP()!=null && info1sport.equals(courante.getP().getTCP().getSrcport()) && info2dport.equals(courante.getP().getTCP().getDestport())  && info3window.equals(courante.getP().getTCP().getWindow()) ) {
							toti= lfrequete( courante,compteur );
						}
					}
				}
				if(toti == false){
					setHttp("TCP Previous segment not captured");
				}			
			}
			
		}
		
	}
		
	
	

	
	public boolean lfrequete(Ethernet t ,int cpt) {
		int i = cpt;
		List<String> l=t.getL();
		String s=new String();
		http=null;
		String type=null;
		boolean loop=true;
		
		if(cpt>l.size()-2) {return false;}
		
		while(  !(l.get(i).equals("0d")) && !(l.get(i+1).equals("0a"))  && i<l.size()-2 ) {
			s+=Convertor.asciitochar(  l.get(i)  );
			i++;	
		}
		i+=2;//sauter la fin de la requete

		while ( i+4<l.size() && loop) {	
			
			String tmp=new String();
			while( !(l.get(i).equals("0d")) && !(l.get(i+1).equals("0a"))  && i<l.size()-4) { 
				tmp+=Convertor.asciitochar(l.get(i));
				i++;
			}

			i+=2;// pour sauter 
		//	String[] tmp2 = tmp.split(" ");
		//	if( tmp2[0].equals("Content-Type:")) {
			//	type=new String();
			//	type="("+tmp2[1]+")";
		//		loop=false;
		//		String[] methode = s.split(" ");
			//}
			break;
			
		}
		
		String[] methode = s.split(" ");
		if(  methode[0].equals("HTTP/1.1") || methode[0].equals("HTTP/1.0") || methode[0].equals("GET") || methode[0].equals("POST") || methode[0].equals("HEAD") || methode[0].equals("PUT") || methode[0].equals("DELETE") ) {
			setHttp(s);
			return true;
		}
		
		return false;
	}

	
	
	
	public String getSrcport() {
		return srcport;
	}
	
	public String getDestport() {
		return destport;
	}
	
	public long getSeqre() {
		return seqre;
	}
	
	public long getAckre() {
		return ackre;
	}
	
	public String getHeaderlength() {
		return headerlength;
	}
	
	public String getFlags() {
		String f=new String();
		if( urg ) f+="URG";
		if( ack ) f+="ACK,";
		if( psh ) f+="PSH,";
		if( rst ) f+="RST,";
		if( syn ) f+="SYN,";
		if( fin ) f+="FIN,";
		return f.substring(0, f.length()-1);
		
	}
	
	public String getWindow() {
		return window;
	}
	
	public String getChecksumTCP() {
		return checksumTCP;
	}
	
	public String getUrgentpointer() {
		return urgentpointer;
	}
	
	public String getOptions() {
		return options;
	}
	
	public String getLen() {
		return len;
	}
	
	public void setHttp(String s) {
		this.http=s;
	}
	
	public String getHttp() {
		return http;
	}
	
	
	public void lire() {
		String res="\nTCP\n"+"\n"+getSrcport()+"\n"+getDestport()+"\nSeq: "+getSeqre()+"\nAck: "+getAckre()+"\n"+getHeaderlength()+"\n"+getFlags()+"\nWindow: "+getWindow()+"\nChecksum: "+getChecksumTCP()+"\nUrgent Pointer: "+getUrgentpointer()+"\n"+getOptions() +"\n"+getLen();
		if(http!=null) {
			res+="\n\nHTTP\n\n"+http;
		}
		System.out.println( res );	
	}
	
	public String toString() {
		if( getHttp()!=null)
			return getHttp()+"\n";
		else
			return getSrcport()+" -> "+getDestport()+" ("+getFlags()+") "+"Seq="+getSeqre()+" Win= "+window+" Len="+getLen()+" "+options+"\n";
    }
	
	public String getComm() {
		if( getHttp()!=null)
			return getHttp()+"\n";
		else
			return "TCP :"+getSrcport()+" -> "+getDestport()+" ("+getFlags()+") "+"Seq="+getSeqre()+" Win= "+window+" Len="+getLen()+" "+options+"\n";
    }

	

}