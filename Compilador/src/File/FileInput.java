package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Lexer.LexerAnalyzer;
import Lexer.Token;
import File.*;

public class FileInput extends JFrame implements ActionListener {

    private JTextField route;
    private JButton btn;
   // private FileReaderSource fileReader;
    private BufferedReader br = null;
    private String road;
    private static final int EOF = 9999999;

    public FileInput() {
        super("JFileChooser Test");
        setLayout(new FlowLayout());

        route = new JTextField(30);
        add(route);

        btn = new JButton("Buscar...");
        btn.addActionListener(this);
        add(btn);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("*.txt","txt");    
        fileChooser.setFileFilter(imgFilter);

        int result = fileChooser.showOpenDialog(this);

        if (result != JFileChooser.CANCEL_OPTION) {

            File fileName = fileChooser.getSelectedFile();

            if ((fileName == null) || (fileName.getName().equals(""))) {
            	route.setText("...");
            } else {
                route.setText(fileName.getAbsolutePath());
                this.road=fileName.getAbsolutePath();
               /* try {
					br=new BufferedReader(new FileReader(fileName.getAbsolutePath()));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
                
            }
        }
        
      
    }

    public String getRoad() {
    	return this.road;
    }
    
  public BufferedReader getBuffer() {
	 BufferedReader auxBr = new BufferedReader(br);
	  return auxBr;
  }



	public static void main(String[] args) {

        /*public static String readFile(){
            String str="";
            Charset charset = Charset.forName("US-ASCII");
            Path path = Paths.get("C:\\Users\\Camila Barreiro\\Documents\\Entrada.txt");
            try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    str+=line+'\n';
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            return str;
        }*/
    /*    FileInput test = new FileInput();
       
     
        test.setDefaultCloseOperation(EXIT_ON_CLOSE);
        test.setSize(400, 110);
        test.setVisible(true);
       
        LexerAnalyzer la = new LexerAnalyzer(test.getRoad());
        System.out.println(la.getNextLine());*/

        //JFileChooser ventanita=new JFileChooser();
        //ventanita.showOpenDialog(ventanita);

        //String path = ventanita.getSelectedFile().getAbsolutePath();//obtiene la ruta del archivo selecionado
       // Path path = Paths.get("C:\\Users\\Camila Barreiro\\Documents\\Entrada.txt");
        String path = "C:\\Users\\marti\\Desktop\\Proyecto-Compiladores\\Casos de Prueba.txt";
        //System.out.println(path);
       LexerAnalyzer la = new LexerAnalyzer(path);

        System.out.println("Source: " + la.getSource());
        for (int i = 0; i <= 20; i++) {
            Token tk = la.getNextToken();
            System.out.println("Token: " + tk.getLexema() + " Id: " + tk.getId());
        }
        System.out.println(la.getErrors());
        System.out.println(la.getWarning());
    }
}

