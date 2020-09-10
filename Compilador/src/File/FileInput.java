package File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class FileInput extends JFrame implements ActionListener {

    private JTextField route;
    private JButton btn;

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
                FileReaderSource fileReader = new FileReaderSource(this.getRoute().getText());
            }
        }
        
      
    }

    public JTextField getRoute() {
		return route;
	}


	public static void main(String[] args) {
        FileInput test = new FileInput();
        test.setDefaultCloseOperation(EXIT_ON_CLOSE);
        test.setSize(400, 110);
        test.setVisible(true);
       
    }
}

