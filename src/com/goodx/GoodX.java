package com.goodx;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;

public class GoodX {
    private JTextArea desplayMsg;
    private JTextField decipherKey;
    private JButton submitButton;
    private JPanel goodMain;

    private GoodX() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You Pressed the key...");
            }
        });
    }

    private void encryptionAlgo() {
        bytesToString(new byte[16]);
        stringToBytes("hello");
    }

    /**
     *
     * @param b byte[] array
     * @return String of bytes to String
     */
    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public static byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GoodX");
        frame.setContentPane(new GoodX().goodMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(501, 501);
        frame.setVisible(true);
    }
}
