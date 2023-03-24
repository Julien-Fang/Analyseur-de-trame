package obj;

public class Convertor {

	public static String strtobyte(String s) {
		if(s.equals("A") || s.equals("a") ) return "1010 ";
		if(s.equals("B") || s.equals("b") ) return "1011 ";
		if(s.equals("C") || s.equals("c") ) return "1100 ";
		if(s.equals("D") || s.equals("d") ) return "1101 ";
		if(s.equals("E") || s.equals("e") ) return "1110 ";
		if(s.equals("F") || s.equals("f") ) return "1111 ";
		
		StringBuilder res= new StringBuilder();
		byte[] bytes = s.getBytes();
		for( byte b : bytes) {
			int val = b;
			for(int i=0 ;i<4 ; i++) {
				res.append((val & 8) == 0 ? 0 : 1);
				val <<=1;
			}
			res.append(" ");
		}
		return res.toString();
	
	}
	
	public static int strbintoint(String s) { //binaire sous forme string -> decimal
		return Integer.parseInt(s, 2);
	}
	
	public static int hextoint (String s) {
		return Integer.parseInt(s,16); 
	}
	
	public static long hexlongtoint(String s) {
		return Long.parseLong(s, 16);
	}
	
	public static String asciitochar(String s) {
		int i = Convertor.hextoint(s);
		return ((char)i)+"";
	}
	
}
