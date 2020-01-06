package com.example.test.demo.impl;

import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.service.TokenProvider;
import com.example.test.demo.sto.UserSTO;
import com.example.test.demo.utils.JavaKeyStoreUtilities;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenProviderImpl implements TokenProvider {

    private static final String AUTHORITIES_KEY = "AUTHORITIES";
    private static final String path = "/home/container-data/security/AppKS.p12";
    private static final String type = "PKCS12";
    private static final String pass = "prismaapp01AB";
    private static final String alias = "prismaapp";
    private final RSAPrivateKey PRIVATE_KEY;
    private final RSAPublicKey PUBLIC_KEY;

    @Autowired
    public TokenProviderImpl() {
        this.PUBLIC_KEY = getPublicKey();
        this.PRIVATE_KEY = getPrivateKey();
    }


    @Override
    public String generateToken(UserSTO userSTO) {
        // Genera el token con roles, issuer, fecha, expiración (8h)
        /*final String authorities = userSTO.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()).getAuthority())
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(userSTO.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(ISSUER_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();*/
        return test(userSTO);
    }

    public String test(UserSTO userSTO) {
        // Compose the JWT claims set

        Date now = new Date();
        final String authorities = userSTO.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()).getAuthority())
                .collect(Collectors.joining(","));
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                .issuer("Claro")
                .subject(userSTO.getEmail())
                .audience(authorities)
                .expirationTime(new Date(now.getTime() + 1000 * 60 * 10)) // expires in 10 minutes
                .claim("name", "sarasa")
                .notBeforeTime(now)
                .issueTime(now)
                .jwtID(UUID.randomUUID().toString())
                .build();

        System.out.println(jwtClaims.toJSONObject());
        // Produces
        // {
        //   "iss" : "https:\/\/openid.net",
        //   "sub" : "alice",
        //   "aud" : [ "https:\/\/app-one.com" , "https:\/\/app-two.com" ],
        //   "exp" : 1364293137871,
        //   "nbf" : 1364292537871,
        //   "iat" : 1364292537871,
        //   "jti" : "165a7bab-de06-4695-a2dd-9d8d6b40e443"
        // }

        // Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

        // Create the encrypted JWT object
        EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

        // Create an encrypter with the specified public RSA key
        RSAEncrypter encrypter = new RSAEncrypter(PUBLIC_KEY);

        // Do the actual encryption
        try {
            jwt.encrypt(encrypter);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        // Serialise to JWT compact form
        String jwtString = jwt.serialize();

        System.out.println(jwtString);
        // Produces
        //
        // eyJhbGciOiJSU0EtT0FFUCIsImVuYyI6IkExMjhHQ00ifQ.K52jFwAQJH-
        // DxMhtaq7sg5tMuot_mT5dm1DR_01wj6ZUQQhJFO02vPI44W5nDjC5C_v4p
        // W1UiJa3cwb5y2Rd9kSvb0ZxAqGX9c4Z4zouRU57729ML3V05UArUhck9Zv
        // ssfkDW1VclingL8LfagRUs2z95UkwhiZyaKpmrgqpKX8azQFGNLBvEjXnx
        // -xoDFZIYwHOno290HOpig3aUsDxhsioweiXbeLXxLeRsivaLwUWRUZfHRC
        // _HGAo8KSF4gQZmeJtRgai5mz6qgbVkg7jPQyZFtM5_ul0UKHE2y0AtWm8I
        // zDE_rbAV14OCRZJ6n38X5urVFFE5sdphdGsNlA.gjI_RIFWZXJwaO9R.oa
        // E5a-z0N1MW9FBkhKeKeFa5e7hxVXOuANZsNmBYYT8G_xlXkMD0nz4fIaGt
        // uWd3t9Xp-kufvvfD-xOnAs2SBX_Y1kYGPto4mibBjIrXQEjDsKyKwndxzr
        // utN9csmFwqWhx1sLHMpJkgsnfLTi9yWBPKH5Krx23IhoDGoSfqOquuhxn0
        // y0WkuqH1R3z-fluUs6sxx9qx6NFVS1NRQ-LVn9sWT5yx8m9AQ_ng8MBWz2
        // BfBTV0tjliV74ogNDikNXTAkD9rsWFV0IX4IpA.sOLijuVySaKI-FYUaBy
        // wpg

        return jwtString;
    }

    public String readToken(String token) {
        // Parse back
        EncryptedJWT jwt = null;
        try {
            jwt = EncryptedJWT.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create a decrypter with the specified private RSA key
        RSADecrypter decrypter = new RSADecrypter(PRIVATE_KEY);

        // Decrypt
        try {
            jwt.decrypt(decrypter);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        // Retrieve JWT claims
        try {
            System.out.println(jwt.getJWTClaimsSet().getIssuer());
            System.out.println(jwt.getJWTClaimsSet().getSubject());
            System.out.println(jwt.getJWTClaimsSet().getAudience().size());
            System.out.println(jwt.getJWTClaimsSet().getExpirationTime());
            System.out.println(jwt.getJWTClaimsSet().getNotBeforeTime());
            System.out.println(jwt.getJWTClaimsSet().getIssueTime());
            System.out.println(jwt.getJWTClaimsSet().getJWTID());
            return jwt.getJWTClaimsSet().toJSONObject().toJSONString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static RSAPublicKey getPublicKey() {
        KeyStore keystore = JavaKeyStoreUtilities.getKeyStoreFromPath(path, type, pass);
        try {
            return (RSAPublicKey) keystore.getCertificate(alias).getPublicKey();
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static RSAPrivateKey getPrivateKey() {
        KeyStore keystore = JavaKeyStoreUtilities.getKeyStoreFromPath(path, type, pass);
        try {
            return (RSAPrivateKey) keystore.getKey(alias, pass.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
