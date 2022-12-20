package com.cherrysoft.manics.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class KeyUtils {
  private final Environment environment;

  @Value("${access-token.private}")
  private String accessTokenPrivateKeyPath;

  @Value("${access-token.public}")
  private String accessTokenPublicKeyPath;

  @Value("${refresh-token.private}")
  private String refreshTokenPrivateKeyPath;

  @Value("${refresh-token.public}")
  private String refreshTokenPublicKeyPath;

  private KeyPair accessTokenKeyPair;
  private KeyPair refreshTokenKeyPair;

  private KeyPair getAccessTokenKeyPair() {
    if (isNull(accessTokenKeyPair)) {
      KeyPairProvider keyPairProvider = new KeyPairProvider(accessTokenPublicKeyPath, accessTokenPrivateKeyPath);
      accessTokenKeyPair = keyPairProvider.getPairKey();
    }
    return accessTokenKeyPair;
  }

  private KeyPair getRefreshTokenKeyPair() {
    if (isNull(refreshTokenKeyPair)) {
      KeyPairProvider keyPairProvider = new KeyPairProvider(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath);
      refreshTokenKeyPair = keyPairProvider.getPairKey();
    }
    return refreshTokenKeyPair;
  }

  public RSAPublicKey getAccessTokenPublicKey() {
    return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
  }

  public RSAPrivateKey getAccessTokenPrivateKey() {
    return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
  }

  public RSAPublicKey getRefreshTokenPublicKey() {
    return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
  }

  public RSAPrivateKey getRefreshTokenPrivateKey() {
    return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
  }

  public static class KeyPairProvider {
    private final KeyPairFileDAO keyPairFileDao;

    public KeyPairProvider(String publicKeysPath, String privateKeysPath) {
      this.keyPairFileDao = new KeyPairFileDAO(publicKeysPath, privateKeysPath);
    }

    public KeyPair getPairKey() {
      if (keyPairFileDao.bothKeyPairsFilesExists()) {
        return generateKeyPairsFromFiles();
      }
      return generateAndSaveNewKeyPairs();
    }

    private KeyPair generateKeyPairsFromFiles() {
      try {
        KeyFactory keyFactory = getRSAKeyFactory();
        EncodedKeySpec publicKeySpec = keyPairFileDao.readPublicKey();
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        EncodedKeySpec privateKeySpec = keyPairFileDao.readPrivateKey();
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
      } catch (InvalidKeySpecException e) {
        throw new RuntimeException(e);
      }
    }

    private KeyPair generateAndSaveNewKeyPairs() {
      keyPairFileDao.generateKeyPairsDirectory();
      KeyPair newKeyPairs = generateNewKeyPairs();
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(newKeyPairs.getPublic().getEncoded());
      keyPairFileDao.savePublicKey(publicKeySpec);
      EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(newKeyPairs.getPrivate().getEncoded());
      keyPairFileDao.savePrivateKey(privateKeySpec);
      return newKeyPairs;
    }

    private KeyPair generateNewKeyPairs() {
      KeyPairGenerator keyPairGenerator = getRSAKeyPairGenerator();
      keyPairGenerator.initialize(2048);
      return keyPairGenerator.generateKeyPair();
    }

    private KeyFactory getRSAKeyFactory() {
      try {
        return KeyFactory.getInstance("RSA");
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
    }

    private KeyPairGenerator getRSAKeyPairGenerator() {
      try {
        return KeyPairGenerator.getInstance("RSA");
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
    }

  }

}
