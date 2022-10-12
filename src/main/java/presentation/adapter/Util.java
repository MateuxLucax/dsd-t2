package presentation.adapter;

import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Util {
    public static void centerFrame(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }
    
    public static void message(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public static JFileChooser loadFileSelector(JFrame view) throws HeadlessException {
        String cwd = new File("").getAbsolutePath().concat("/malhas");
        JFileChooser jFile = new JFileChooser(cwd);
        jFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFile.showOpenDialog(view);
        return jFile;
    }
    
    public static void init(JFrame frame) {
        frame.setVisible(true);
        Util.centerFrame(frame);
    }
}
