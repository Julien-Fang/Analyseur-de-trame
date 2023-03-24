package obj;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.stage.Stage;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class InterfaceGraphique extends JFrame implements ActionListener {
	private JButton btnNewButton;
	private JPanel contentPane;
	private static InterfaceGraphique frame;
	private JComboBox comboBox;
	private JComboBox comboBox2;
	private String dest;
	private String src;
	private File file;
	private Trace t;
	private Trace tt;
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraphique frame= new InterfaceGraphique(11.1);
					frame.setVisible(true);
		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public InterfaceGraphique(double d) throws NumberFormatException, FileNotFoundException, IOException, ExceptionData {
		setTitle("Visualisateur");
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		btnNewButton = new JButton("File...");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(100, 100, 300,200);
		contentPane.setPreferredSize(new Dimension(1200,600));
		contentPane.setLayout(null);
		
		contentPane.add(btnNewButton);
		
		Label label = new Label("--->");
		label.setFont(new Font("Serif", Font.BOLD, 50));
		label.setBounds(10, 10, 84, 350);
		label.setForeground(new Color(255,0,0));
		contentPane.add(label);
		
		
		setContentPane(contentPane);
			
		
		
	}
	
	public InterfaceGraphique(Trace t ) throws FileNotFoundException,IOException, NumberFormatException { 
		ecrireFic();
		setTitle("Visualisateur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		this.t=t;
		
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(1200,1000*Trace.getLt().size()));
		contentPane.setLayout(null);
		Label label = new Label("Commentaires");
		label.setBounds(670, 10, 84, 21);
		contentPane.add(label);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(596, 58, 143, 443);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblNewLabel);

		
		btnNewButton = new JButton("File...");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(330, 10, 85, 21);
		contentPane.add(btnNewButton);
		int pix=0;
		try {
			t.lectureTrame();
		} catch(FileNotFoundException fne) {
			throw new FileNotFoundException("Fichier non trouve\n");
		} catch(IOException ioe) {
			throw new IOException("Probleme de fichier\n");
		}		
	
			
		Ethernet t1;
		for (int i=0;i<t.getLt().size();i++) {
		t1=t.getTrame(i);
		
		if (t1.getP()!=null) {
			src=t1.getP().getAddrSrc();
			dest=t1.getP().getAddrDest();
			break;
		}
		
		
		}
		JLabel lblNewLabel_1 = new JLabel(src);
		lblNewLabel_1.setBounds(59, 10, 84, 13);
		
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel_6 = new JLabel(dest);
		lblNewLabel_6.setBounds(539, 10, 84, 13);
		contentPane.add(lblNewLabel_6);
		
		comboBox = new JComboBox();
		comboBox.setBounds(1057, 10, 84, 21);
		
	
		comboBox.addItem("Tout");
		comboBox.addItem(dest);	
		comboBox.addItem(src);
		
		comboBox.addActionListener(this);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_8 = new JLabel("Filtre ip :");
		lblNewLabel_8.setBounds(1005, 14, 70, 13);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("Protocole :");
		lblNewLabel_10.setBounds(830, 14, 70, 13);
		contentPane.add(lblNewLabel_10);
		
		comboBox2 = new JComboBox();
		comboBox2.setBounds(900, 10, 84, 21);

		comboBox2.addActionListener(this);
		
		comboBox2.addItem("Tout");
		comboBox2.addItem("TCP");
		comboBox2.addItem("HTTP");
		comboBox2.addItem("IP");
		contentPane.add(comboBox2);

		for (int i=0;i<t.getLt().size();i++) {
			t1=t.getTrame(i);
	
				if (t1.getP()==null) {
					
					JLabel lblNewLabel_3 = new JLabel("    [WARNING]     Protocole non traité " );
					lblNewLabel_3.setBounds(150, 35+pix, 500, 13);
					contentPane.add(lblNewLabel_3);
				}
				
				
				if (t1.getP()!=null) {
				String tiret="-------------------------------------------------------------------------------------------------------------------------------";
				JLabel lblNewLabel_5;
			
		
				if (src.compareTo(t1.getP().getAddrSrc())==0) {
				
					lblNewLabel_5 = new JLabel(tiret+">");
	
				} else {
					lblNewLabel_5 = new JLabel("<"+tiret);
				}
				
			
				
				if(t1.getP().getTCP()!=null) {
					JLabel lblNewLabel_3 = new JLabel(t1.getP().getTCP().getSrcport());
					lblNewLabel_3.setBounds(4, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_3);
					JLabel lblNewLabel_7 = new JLabel(t1.getP().getTCP().getDestport());
					lblNewLabel_7.setBounds(580, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_7);
					if (t1.getP().getTCP().getHttp()==null) {		
				JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
				lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
				lblNewLabel_2.setOpaque(true);
				lblNewLabel_2.setBackground(new Color(255, 218, 185));
				lblNewLabel.setBackground(new Color(255, 228, 196));
				JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
				lblNewLabel_4.setBounds(640, 40+pix, 800, 13);	
				lblNewLabel_5.setOpaque(true);
				lblNewLabel_5.setBackground(new Color(255, 218, 185));
				contentPane.add(lblNewLabel_2);
				lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
				contentPane.add(lblNewLabel_5);
				contentPane.add(lblNewLabel_4);
				}
				else {
					if (t1.getP().getTCP().getHttp()=="TCP Previous segment not captured") {
			
						JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
						lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
						lblNewLabel_2.setOpaque(true);
						lblNewLabel_2.setBackground(new Color(101, 60, 206));
		
						JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
						lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
						
						lblNewLabel_5.setOpaque(true);
						lblNewLabel_5.setBackground(new Color(101, 60, 206));
						contentPane.add(lblNewLabel_2);
						contentPane.add(lblNewLabel_4);		
						lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
						contentPane.add(lblNewLabel_5);
						}
						else {
							JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
							lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
							lblNewLabel_2.setOpaque(true);
							lblNewLabel_2.setBackground(new Color(224, 255, 255));
							
							lblNewLabel.setBackground(new Color(224, 255, 255));
							JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
							lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
							
							lblNewLabel_5.setOpaque(true);
							lblNewLabel_5.setBackground(new Color(224, 255, 255));
							contentPane.add(lblNewLabel_2);
							contentPane.add(lblNewLabel_4);		
							lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
							contentPane.add(lblNewLabel_5);
							
						}
					}
				}
				else if (t1.getP() != null){
					
					JLabel lblNewLabel_2 = new JLabel(t1.getP().toString());
					lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
					contentPane.add(lblNewLabel_2);
					lblNewLabel_2.setOpaque(true);
					lblNewLabel_2.setBackground(new Color(152, 251, 152));
				
					contentPane.add(lblNewLabel_2);
		
					JLabel lblNewLabel_4 = new JLabel(t1.getP().getComm());
					lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
					contentPane.add(lblNewLabel_4);
					
					lblNewLabel_5.setOpaque(true);
					lblNewLabel_5.setBackground(new Color(152, 251, 152));
					lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
					contentPane.add(lblNewLabel_5);
				}
			
				}

				pix+=25;
					
		}
		JScrollPane scroll = new JScrollPane(contentPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setContentPane(scroll);
		
		
	}
	

	
	
	
	public InterfaceGraphique(boolean boo,Trace t ) throws FileNotFoundException,IOException,  NumberFormatException { 
		setTitle("Visualisateur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		this.t=t;
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(1200,200*Trace.getLt().size()));
		contentPane.setLayout(null);
		Label label = new Label("Commentaires");
		label.setBounds(670, 10, 84, 21);
		contentPane.add(label);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(596, 58, 143, 443);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblNewLabel);

		
		
		btnNewButton = new JButton("File...");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(330, 10, 85, 21);
		contentPane.add(btnNewButton);
		int pix=0;
		try {
			t.lectureTrame();
		} catch(FileNotFoundException fne) {
			throw new FileNotFoundException("Fichier non trouve\n");
		} catch(IOException ioe) {
			throw new IOException("Probleme de fichier\n");
		}		
	
			
			
		Ethernet t1;
		for (int i=0;i<t.getLt().size();i++) {
		t1=t.getTrame(i);
		
		if (t1.getP()!=null) {
			src=t1.getP().getAddrSrc();
			dest=t1.getP().getAddrDest();
			break;
		}
		
		
		}
		JLabel lblNewLabel_1 = new JLabel(src);
		lblNewLabel_1.setBounds(59, 10, 84, 13);
		
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel_6 = new JLabel(dest);
		lblNewLabel_6.setBounds(539, 10, 84, 13);
		contentPane.add(lblNewLabel_6);
		
		comboBox = new JComboBox();
		comboBox.setBounds(1057, 10, 84, 21);
		
		if (boo) {
			comboBox.addItem(src);
			if (src!= dest) {
			comboBox.addItem(dest);	
		}
		}
		else if (src != dest && boo==false) {
			comboBox.addItem(dest);	
			comboBox.addItem(src);
			
		}
		comboBox.addItem("Tout");
	
		
		comboBox.addActionListener(this);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_8 = new JLabel("Filtre ip :");
		lblNewLabel_8.setBounds(1005, 14, 70, 13);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("Protocole :");
		lblNewLabel_10.setBounds(830, 14, 70, 13);
		contentPane.add(lblNewLabel_10);
		
		comboBox2 = new JComboBox();
		comboBox2.setBounds(900, 10, 84, 21);

		comboBox2.addActionListener(this);
		
		comboBox2.addItem("Tout");
		comboBox2.addItem("TCP");
		comboBox2.addItem("HTTP");
		comboBox2.addItem("IP");
		contentPane.add(comboBox2);
		
		for (int i=0;i<t.getLt().size();i++) {
			t1=t.getTrame(i);
			
	
			if (t1.getP()!=null) {
			if (src.compareTo(t1.getP().getAddrSrc())==0 && boo) {
				
				String tiret="-------------------------------------------------------------------------------------------------------------------------------";
				JLabel lblNewLabel_5;
				if (src.compareTo(t1.getP().getAddrSrc())==0) {
	
					lblNewLabel_5 = new JLabel(tiret+">");
	
				} else {
					lblNewLabel_5 = new JLabel("<"+tiret);
				}
				
			
		
				if(t1.getP().getTCP()!=null) {
					JLabel lblNewLabel_3 = new JLabel(t1.getP().getTCP().getSrcport());
					lblNewLabel_3.setBounds(4, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_3);
					JLabel lblNewLabel_7 = new JLabel(t1.getP().getTCP().getDestport());
					lblNewLabel_7.setBounds(580, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_7);
					if (t1.getP().getTCP().getHttp()==null) {		
				JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
				lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
				lblNewLabel_2.setOpaque(true);
				lblNewLabel_2.setBackground(new Color(255, 218, 185));
				lblNewLabel.setBackground(new Color(255, 228, 196));
				JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
				lblNewLabel_4.setBounds(640, 40+pix, 800, 13);	
				lblNewLabel_5.setOpaque(true);
				lblNewLabel_5.setBackground(new Color(255, 218, 185));
				contentPane.add(lblNewLabel_2);
				lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
				contentPane.add(lblNewLabel_5);
				contentPane.add(lblNewLabel_4);
				}
				else {
					if (t1.getP().getTCP().getHttp()=="TCP Previous segment not captured") {
			
						JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
						lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
						lblNewLabel_2.setOpaque(true);
						lblNewLabel_2.setBackground(new Color(101, 60, 206));
		
						JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
						lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
						
						lblNewLabel_5.setOpaque(true);
						lblNewLabel_5.setBackground(new Color(101, 60, 206));
						contentPane.add(lblNewLabel_2);
						contentPane.add(lblNewLabel_4);		
						lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
						contentPane.add(lblNewLabel_5);
						}
						else {
							JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
							lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
							lblNewLabel_2.setOpaque(true);
							lblNewLabel_2.setBackground(new Color(224, 255, 255));
							
							lblNewLabel.setBackground(new Color(224, 255, 255));
							JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
							lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
							
							lblNewLabel_5.setOpaque(true);
							lblNewLabel_5.setBackground(new Color(224, 255, 255));
							contentPane.add(lblNewLabel_2);
							contentPane.add(lblNewLabel_4);		
							lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
							contentPane.add(lblNewLabel_5);
							
						}
					}
				}
				else if (t1.getP() != null){
					
					JLabel lblNewLabel_2 = new JLabel(t1.getP().toString());
					lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
					contentPane.add(lblNewLabel_2);
					lblNewLabel_2.setOpaque(true);
					lblNewLabel_2.setBackground(new Color(152, 251, 152));
				
					contentPane.add(lblNewLabel_2);
		
					JLabel lblNewLabel_4 = new JLabel(t1.getP().getComm());
					lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
					contentPane.add(lblNewLabel_4);
					
					lblNewLabel_5.setOpaque(true);
					lblNewLabel_5.setBackground(new Color(152, 251, 152));
					lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
					contentPane.add(lblNewLabel_5);
				}
				pix+=25;
			}
			
			else if (src.compareTo(t1.getP().getAddrSrc())!=0 && boo==false) {

				String tiret="-------------------------------------------------------------------------------------------------------------------------------";
				JLabel lblNewLabel_5;
				if (src.compareTo(t1.getP().getAddrSrc())==0) {
				
					lblNewLabel_5 = new JLabel(tiret+">");
	
				} else {
					lblNewLabel_5 = new JLabel("<"+tiret);
				}
				
				
				
				
				if(t1.getP().getTCP()!=null) {
					JLabel lblNewLabel_3 = new JLabel(t1.getP().getTCP().getSrcport());
					lblNewLabel_3.setBounds(4, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_3);
					JLabel lblNewLabel_7 = new JLabel(t1.getP().getTCP().getDestport());
					lblNewLabel_7.setBounds(580, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_7);
					if (t1.getP().getTCP().getHttp()==null) {		
				JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
				lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
				lblNewLabel_2.setOpaque(true);
				lblNewLabel_2.setBackground(new Color(255, 218, 185));
				lblNewLabel.setBackground(new Color(255, 228, 196));
				JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
				lblNewLabel_4.setBounds(640, 40+pix, 800, 13);	
				lblNewLabel_5.setOpaque(true);
				lblNewLabel_5.setBackground(new Color(255, 218, 185));
				contentPane.add(lblNewLabel_2);
				lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
				contentPane.add(lblNewLabel_5);
				contentPane.add(lblNewLabel_4);
				}
				else {
					if (t1.getP().getTCP().getHttp()=="TCP Previous segment not captured") {
			
						JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
						lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
						lblNewLabel_2.setOpaque(true);
						lblNewLabel_2.setBackground(new Color(101, 60, 206));
		
						JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
						lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
						
						lblNewLabel_5.setOpaque(true);
						lblNewLabel_5.setBackground(new Color(101, 60, 206));
						contentPane.add(lblNewLabel_2);
						contentPane.add(lblNewLabel_4);		
						lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
						contentPane.add(lblNewLabel_5);
						}
						else {
							JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
							lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
							lblNewLabel_2.setOpaque(true);
							lblNewLabel_2.setBackground(new Color(224, 255, 255));
							
							lblNewLabel.setBackground(new Color(224, 255, 255));
							JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
							lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
							
							lblNewLabel_5.setOpaque(true);
							lblNewLabel_5.setBackground(new Color(224, 255, 255));
							contentPane.add(lblNewLabel_2);
							contentPane.add(lblNewLabel_4);		
							lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
							contentPane.add(lblNewLabel_5);
							
						}
					}
				}
				else if (t1.getP() != null){
					
					JLabel lblNewLabel_2 = new JLabel(t1.getP().toString());
					lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
					contentPane.add(lblNewLabel_2);
					lblNewLabel_2.setOpaque(true);
					lblNewLabel_2.setBackground(new Color(152, 251, 152));
				
					contentPane.add(lblNewLabel_2);
		
					JLabel lblNewLabel_4 = new JLabel(t1.getP().getComm());
					lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
					contentPane.add(lblNewLabel_4);
					
					lblNewLabel_5.setOpaque(true);
					lblNewLabel_5.setBackground(new Color(152, 251, 152));
					lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
					contentPane.add(lblNewLabel_5);
				}
				
				pix+=25;
			}
			}
				
			
		}
		JScrollPane scroll = new JScrollPane(contentPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setContentPane(scroll);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==btnNewButton ) {
		
			this.setVisible(false);
			this.dispose();

			JFileChooser choose = new JFileChooser(
			        FileSystemView
			        .getFileSystemView()
			        .getHomeDirectory()
			    );
			int res = choose.showOpenDialog(null);
			if (res == JFileChooser.APPROVE_OPTION) {
			      file = choose.getSelectedFile();
			     
	
			      Trace t= new Trace(file);
			      EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								//TOUT
								
								frame = new InterfaceGraphique(t); 
								frame.setVisible(true);
								
							
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
			    }
			
	
			
		}
	
			
		
		
		if (e.getSource()==comboBox) {
		
		
			this.setVisible(false);
			this.dispose();
		if (comboBox.getSelectedItem()==src) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//pour que prendre -----------> src
						
						frame = new InterfaceGraphique(true,t); 
						frame.setVisible(true);
			
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	
		}
	
		else if (comboBox.getSelectedItem()==dest){
		
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//TOUT
						
						frame = new InterfaceGraphique(false,t); 
						frame.setVisible(true);
						
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			}
		
		else if (comboBox.getSelectedItem()=="Tout") {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			
					frame = new InterfaceGraphique(t); 
					frame.setVisible(true);
		
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		}
		
		}
		if(e.getSource()==comboBox2) {
			this.setVisible(false);
			this.dispose();
			
		if (comboBox2.getSelectedItem()=="TCP"){

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// <--------------- DEST
							frame = new InterfaceGraphique("tcp",t); 
							frame.setVisible(true);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				}
		
		else if (comboBox2.getSelectedItem()=="IP"){
			
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// <--------------- DEST
							
							frame = new InterfaceGraphique("ip",t); 
							frame.setVisible(true);
						
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		
		
		else if (comboBox2.getSelectedItem()=="HTTP"){

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						// <--------------- DEST
						frame = new InterfaceGraphique("http",t); 
						frame.setVisible(true);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		else if (comboBox2.getSelectedItem()=="Tout ") {
			EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new InterfaceGraphique(t); 
					frame.setVisible(true);
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		}
		
		}
		

	}
	
	
	

    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public InterfaceGraphique(String choix,Trace t ) throws FileNotFoundException,IOException, ExceptionData, NumberFormatException { 
		
		setTitle("Visualisateur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(1200,200*Trace.getLt().size()));
		contentPane.setLayout(null);
		Label label = new Label("Commentaires");
		label.setBounds(670, 10, 84, 21);
		contentPane.add(label);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(596, 58, 143, 443);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("File...");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(330, 10, 85, 21);
		contentPane.add(btnNewButton);
		this.t=t;
	
		int pix=0;
		try {
			t.lectureTrame();
		} catch(FileNotFoundException fne) {
			throw new FileNotFoundException("Fichier non trouve\n");
		} catch(IOException ioe) {
			throw new IOException("Probleme de fichier\n");
		}		
	
		Ethernet t1;
		for (int i=0;i<t.getLt().size();i++) {
		t1=t.getTrame(i);
		
		if (t1.getP()!=null) {
			src=t1.getP().getAddrSrc();
			dest=t1.getP().getAddrDest();
			break;
		}
		
		
		}
		JLabel lblNewLabel_1 = new JLabel(src);
		lblNewLabel_1.setBounds(59, 10, 84, 13);
		
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel_6 = new JLabel(dest);
		lblNewLabel_6.setBounds(539, 10, 84, 13);
		contentPane.add(lblNewLabel_6);
		
		comboBox = new JComboBox();
		comboBox.setBounds(1057, 10, 84, 21);
		comboBox.addItem("Tout");
		comboBox.addItem(src);
		
		if (src != dest) {
		comboBox.addItem(dest);	
		}
		
		comboBox.addActionListener(this);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_8 = new JLabel("Filtre ip :");
		lblNewLabel_8.setBounds(1005, 14, 70, 13);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("Protocole :");
		lblNewLabel_10.setBounds(830, 14, 70, 13);
		contentPane.add(lblNewLabel_10);
		
		comboBox2 = new JComboBox();
		comboBox2.setBounds(900, 10, 84, 21);

		
		if (choix=="tcp") {
			comboBox2.addItem("TCP");
			comboBox2.addItem("HTTP");
			comboBox2.addItem("IP");
		}
		
		if (choix=="http") {
			
			comboBox2.addItem("HTTP");
			comboBox2.addItem("TCP");
			comboBox2.addItem("IP");
		}
		
		if (choix=="ip") {
			comboBox2.addItem("IP");
			comboBox2.addItem("TCP");
			comboBox2.addItem("HTTP");
			
		}
		
		comboBox2.addItem("Tout ");
		contentPane.add(comboBox2);
		comboBox2.addActionListener(this);
		for (int i=0;i<t.getLt().size();i++) {

			t1=t.getTrame(i);
			String tiret="-------------------------------------------------------------------------------------------------------------------------------";
			JLabel lblNewLabel_5;
		
			lblNewLabel_5 =new JLabel("");
		
			
			
			if (t1.getP()!=null) { 
				
					
					if (src.compareTo(t1.getP().getAddrSrc())==0) {
				
						lblNewLabel_5 = new JLabel(tiret+">");
		
					} else {
						lblNewLabel_5 = new JLabel("<"+tiret);
					
					}
		
			
		
			if (t1.getP().getTCP()!=null) {
				if (t1.getP().getTCP().getHttp()==null && choix=="tcp") {
					JLabel lblNewLabel_3 = new JLabel(t1.getP().getTCP().getSrcport());
					lblNewLabel_3.setBounds(4, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_3);
					JLabel lblNewLabel_7 = new JLabel(t1.getP().getTCP().getDestport());
					lblNewLabel_7.setBounds(580, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_7);
			JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
			lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
			lblNewLabel_2.setOpaque(true);
			lblNewLabel_2.setBackground(new Color(255, 218, 185));
			

			lblNewLabel.setBackground(new Color(255, 228, 196));
			JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
			lblNewLabel_4.setBounds(640, 40+pix, 800, 13);
			
			lblNewLabel_5.setOpaque(true);
			lblNewLabel_5.setBackground(new Color(255, 218, 185));
			contentPane.add(lblNewLabel_2);
			contentPane.add(lblNewLabel_4);
			lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
			contentPane.add(lblNewLabel_5);
				
			pix+=25;
			
			
				}
			
			else if (choix=="http" && t1.getP().getTCP().getHttp()!=null){
							if (t1.getP().getTCP().getHttp()=="TCP Previous segment not captured") {
						
								JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
								lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
								lblNewLabel_2.setOpaque(true);
								lblNewLabel_2.setBackground(new Color(101, 60, 206));
				
								JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
								lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
								
								lblNewLabel_5.setOpaque(true);
								lblNewLabel_5.setBackground(new Color(101, 60, 206));
								contentPane.add(lblNewLabel_2);
								contentPane.add(lblNewLabel_4);		
								lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
								contentPane.add(lblNewLabel_5);
								}
							else {
								JLabel lblNewLabel_2 = new JLabel(t1.getP().getTCP().toString());
								lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
								lblNewLabel_2.setOpaque(true);
								lblNewLabel_2.setBackground(new Color(224, 255, 255));
								
								lblNewLabel.setBackground(new Color(224, 255, 255));
								JLabel lblNewLabel_4 = new JLabel(t1.getP().getTCP().getComm());
								lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
								
								lblNewLabel_5.setOpaque(true);
								lblNewLabel_5.setBackground(new Color(224, 255, 255));
								contentPane.add(lblNewLabel_2);
								contentPane.add(lblNewLabel_4);		
								lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
								contentPane.add(lblNewLabel_5);
								
							}
				pix+=25;
			
			
			}
				else if (t1.getP() != null && choix=="ip" && t1.getP().getTCP()==null){
					
					JLabel lblNewLabel_2 = new JLabel(t1.getP().toString());
					lblNewLabel_2.setBounds(45, 35+pix, 515, 13);
					contentPane.add(lblNewLabel_2);
					lblNewLabel_2.setOpaque(true);
					lblNewLabel_2.setBackground(new Color(152, 251, 152));
				
					contentPane.add(lblNewLabel_2);
		
					JLabel lblNewLabel_4 = new JLabel(t1.getP().getComm());
					lblNewLabel_4.setBounds(640, 40+pix, 357, 13);
					contentPane.add(lblNewLabel_4);
					
					lblNewLabel_5.setOpaque(true);
					lblNewLabel_5.setBackground(new Color(152, 251, 152));
					lblNewLabel_5.setBounds(45, 45+pix, 515, 15);
					contentPane.add(lblNewLabel_5);
					JLabel lblNewLabel_3 = new JLabel(t1.getP().getTCP().getSrcport());
					lblNewLabel_3.setBounds(4, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_3);
					JLabel lblNewLabel_7 = new JLabel(t1.getP().getTCP().getDestport());
					lblNewLabel_7.setBounds(580, 35+pix, 45, 13);
					contentPane.add(lblNewLabel_7);
				
					pix+=25;
					}
			}
			
			}
			
		}
		JScrollPane scroll = new JScrollPane(contentPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		setContentPane(scroll);
		
	}
	
	
	

	
	
	
	
	
	
	
	
	public  void ecrireFic() throws IOException{
		String s="";

		Trace tt = new Trace(new File("data/Trace1.txt"));
		try {
			tt.lectureTrame();
		} catch(FileNotFoundException fne) {
			throw new FileNotFoundException("Fichier non trouve\n");
		} catch(IOException ioe) {
			throw new IOException("Probleme de fichier\n");
		}		
	
		Ethernet t1=tt.getTrame(0);
	
		if (t1.getP()!=null) {
			src=t1.getP().getAddrSrc();
			dest=t1.getP().getAddrDest();
		}
		
		s+=src+"            "+dest+"      "+"Commentaires\n";
	
		for (int i=0;i<tt.getLt().size();i++) {
			Ethernet eth=tt.getTrame(i);
			String tiret="-------------------------------------";
	

			if (src.compareTo(eth.getP().getAddrSrc())==0) {
				tiret+=">";

			} else {
				tiret="<"+tiret;
	  
			}
			
			
			if(eth.getP().getTCP()!=null) {
				s+=eth.getP().getTCP().getSrcport();
				s+=tiret;
				s+=eth.getP().getTCP().getDestport()+"  ";
				s+=eth.getP().getTCP().getComm();
			
			}
			else {
				s+=tiret;
				s+=eth.getP().getComm();
			}

		}
	
	
		
	
		File file = new File("visualisateur.txt");

	 
	 FileWriter fw = new FileWriter(file.getAbsoluteFile());
	 BufferedWriter bw = new BufferedWriter(fw);
	 bw.write(s);
	 bw.close();

}
}





























