package com.example.test.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class JavaKeyStoreUtilities {

    private static final Logger logger = LoggerFactory.getLogger("TEST");

    private JavaKeyStoreUtilities() {
    }

    public static KeyStore getKeyStoreFromPath(String path, String keystoreType, String password) {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            KeyStore keyStore = KeyStore.getInstance(keystoreType);
            keyStore.load(is, password.toCharArray());
            return keyStore;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException
                | NullPointerException e) {
            logger.error("Could not load: " + path + ", with type: " + keystoreType + "and password: " + password);
            logger.error(e.toString());
            return null;
        }
    }

}
