package obj;

import java.util.ArrayList;
import java.util.List;

public class IP {
  private static int compteurip;
  private String version;
  private String header;
  private String ihl;
  private String totalLength;
  private String id;
  private String flags;
  private int dontfrag;
  private int morefrag;
  private String offset;
  private String vraieoffset;
  private String ttl;
  private String protocole;
  private String checksum;
  private String addrSrc;
  private String addrDest;
  private TCP tcp;

  private Ethernet trame;

  public IP(List<String> ls, Ethernet t) {
    trame = t;

    // Pour IP version
    version = new String();
    String convertir = Convertor.strtobyte(ls.get(14));
    String[] tmp = convertir.split(" ");
    version += Convertor.strbintoint(tmp[0]);

    // Pour Header
    header = new String();
    int taille = Convertor.strbintoint(tmp[1]);
    header += ".... " + tmp[1] + " = Header Length: " + taille * 4 + " bytes (" + taille + ")";

    // IHL
    ihl = new String();
    ihl += "Differentiated Services Field: 0x" + ls.get(15);

    // Total Length
    String total = ls.get(16) + ls.get(17);
    totalLength = new String();
    totalLength += "Total Length: " + Convertor.hextoint(ls.get(16) + ls.get(17));

    // Id
    id = new String();
    id += "Identification: 0x" + ls.get(18) + ls.get(19) + " (" + Convertor.hextoint(ls.get(18) + ls.get(19)) + ")";

    // Flags ET offset
    String tmp1 = Convertor.strtobyte(ls.get(20));

    dontfrag = Integer.parseInt((String) tmp1.subSequence(1, 2));
    morefrag = Integer.parseInt((String) tmp1.substring(2, 3));

    flags = new String();
    flags += "0x" + Convertor.strbintoint(tmp1.substring(0, 3));

    tmp1 = Convertor.strtobyte(ls.get(20) + ls.get(21));

    tmp1 = tmp1.substring(3, tmp1.length() - 1).trim();

    String tmp2 = Convertor.strtobyte(ls.get(20).charAt(0) + "") + Convertor.strtobyte(ls.get(20).charAt(1) + "")
        + Convertor.strtobyte(ls.get(21).charAt(0) + "") + Convertor.strtobyte(ls.get(21).charAt(1) + "");
    tmp2 = tmp2.replaceAll(" ", "");

    tmp2 = tmp2.substring(3, tmp2.length());
    offset = new String();
    int cpt1 = Convertor.strbintoint(tmp2) * 8;
    offset = cpt1 + "";

    vraieoffset = "" + Convertor.strbintoint(tmp2);

    // TTL
    ttl = new String();
    ttl += "Time to Live: " + Convertor.hextoint(ls.get(22));

    // Protocole
    protocole = new String("Protocole:");
    tcp = null;
    int dec = Convertor.hextoint(ls.get(23));
   if (dec == 6) {
      protocole += "TCP";
      int i = taille * 4 + 14;
      tcp = new TCP(ls, i, trame);
    } else {
      protocole = "inconnu";
    }

    if (tcp != null)
      protocole += " (" + dec + ")";

    // Checksum
    checksum = new String();
    checksum += "Header Checksum: 0x" + ls.get(24) + ls.get(25) + " [validation disabled]";

    // Source Adresse
    addrSrc = new String("");
    for (int i = 26; i < 30; i++) {
      addrSrc += Convertor.hextoint(ls.get(i)) + "";
      if (i < 29) {
        addrSrc += ".";
      }
    }

    // Destination Adresse
    addrDest = new String("");
    for (int i = 30; i < 34; i++) {
      addrDest += Convertor.hextoint(ls.get(i)) + "";
      if (i < 33) {
        addrDest += ".";
      }
    }

  }

  public String getVersion() {
    return version;
  }

  public String getHeader() {
    return header;
  }

  public String getIHL() {
    return ihl;
  }

  public String getTotalLength() {
    return totalLength;
  }

  public String getId() {
    return id;
  }

  public String getFlags() {
    return flags;
  }

  public int getDontfrag() {
    return dontfrag;
  }

  public int getMorefrag() {
    return morefrag;
  }

  public String getOffset() {
    return offset;
  }

  public String getTTL() {
    return ttl;
  }

  public String getProtocole() {
    return protocole;
  }

  public String getChecksum() {
    return checksum;
  }

  public String getAddrSrc() {
    return addrSrc;
  }

  public String getAddrDest() {
    return addrDest;
  }

  public TCP getTCP() {
    return tcp;
  }

  public void lire() {
    if (getTCP() != null)
      getTCP().lire();
    else
      System.out.println("\nIP\n\n" + getVersion() + "\n" + getHeader() + "\n" + getTotalLength() + "\n" + getId()
          + "\nFLags: " + getFlags() + "\nDon't fragment: " + getDontfrag() + "\nMore fragment: " + getMorefrag() + "\n"
          + "\nOffset: " + getOffset() + "\n" + getTTL() + "\n" + getProtocole() + "\n" + getChecksum() + "\n"
          + getAddrSrc() + "\n" + getAddrDest());

  }

  public String toString() {
    if (getTCP() != null) {
      return getTCP().toString();
    } else if (getTCP() == null)
      return "protocole non traité";
    else {
      String s = "";

      if (getMorefrag() == 1) {
        s += "Fragmented IP protocol";
      }

      return s + " (" + getProtocole() + ",off=" + getOffset() + "," + getId() + ")\n";
    }
  }

  public String getComm() {
    if (getTCP() != null) {
      return getTCP().getComm();
    } else if (getTCP()==null) {
      return "protcole non traité";
    }

    else {
      String s = "";
      if (getMorefrag() == 1) {
        s += "Fragmented IP protocole";
      }

      return "IPv" + getVersion() + s + " (" + getProtocole() + ",off=" + getOffset() + "," + getId() + ")\n";
    }
  }

}
