package com.security;

import javafx.stage.Stage;

public class EncryptionProgress extends Stage implements Runnable {

    private FileEncryption fe;
    private boolean finish;

    @Override
    public void run() {
        startEncryption();
    }

    private void startEncryption() {
        new Thread(() -> fe.start()).start();
    }
}
