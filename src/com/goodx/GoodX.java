package com.goodx;

import com.security.FileEncryption;
import com.security.FileEncryptionException;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author Mustafa Jamal
 * @GitHub @doct0rX
 */
public class GoodX {
    private JTextArea desplayMsg;
    private JTextField decipherKey;
    private JButton submitButton;
    private JPanel goodMain;
    private FileEncryption fe;

    private GoodX() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Check Files if Decrypted!!!");

            }
        });
    }

    public static void main(String[] args) throws FileEncryptionException {
        JFrame frame = new JFrame("GoodX");
        frame.setContentPane(new GoodX().goodMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(501, 301);
        frame.setVisible(true);
        FileEncryption file = new FileEncryption("/Users/doct0rX/Desktop/ll.txt","123", (byte) Cipher.ENCRYPT_MODE);
        file.start();
    }
}
