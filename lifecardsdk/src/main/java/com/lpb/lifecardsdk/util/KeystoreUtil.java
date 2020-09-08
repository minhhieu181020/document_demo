package com.lpb.lifecardsdk.util;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

//import com.ViViet.Login.AESEnDecryption;

@SuppressWarnings("deprecation")
public class KeystoreUtil {

    Context context;
    private KeyStore keyStore;

    private String fileNameString = "priKey_v1";

    public void initKeyStore(Context ct) {
        this.context = ct;
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void refreshKeys(String TAG) {
        ArrayList<String> keyAliases = new ArrayList<>();
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                keyAliases.add(aliases.nextElement());
            }
        } catch (Exception e) {
        }
        //Log.e(TAG, keyAliases.toString());
    }

    public void createNewKeys(String alias) {
        try {
            if (android.os.Build.VERSION.SDK_INT < 18) {
                // API < 18
                //KeyPairVV.generateKeyPair();
                //String encodedPrivateKey = AESEnDecryption.encrypt(seedString, KeyPairVV.privateKey);
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair pair = kpg.generateKeyPair();

                PKCS8EncodedKeySpec keyspec = new PKCS8EncodedKeySpec(pair.getPrivate().getEncoded());
                PKCS8EncodedKeySpec pubKeySpec = new PKCS8EncodedKeySpec(pair.getPublic().getEncoded());

                String private18Key = new String(Base64.encode(keyspec.getEncoded(),Base64.DEFAULT));
                String public18Key = new String(Base64.encode(pubKeySpec.getEncoded(),Base64.DEFAULT));

                String encodedPrivateKey = encryptKey(private18Key);

                // lưu cặp key vào SharedPreferencesUtils
                SharedPreferencesUtils.setString(context,alias + fileNameString, encodedPrivateKey);
                SharedPreferencesUtils.setString(context,alias + "_publicKey", public18Key);


            } else if (android.os.Build.VERSION.SDK_INT == 18) {
                // API 18 - Co ho tro KeyPairGeneratorSpec nhung chua ho tro method setKeySize()
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 33);

                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=ViVietCertificate, O=LienVietTech, L=Hanoi"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        //.setKeySize(2048)
                        .build();

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();

            } else if (android.os.Build.VERSION.SDK_INT < 23) {
                //Log.e("DEMO","API tu 18 den 23");
                // API 18 - 23
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 33);

                KeyPairGeneratorSpec spec = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    spec = new KeyPairGeneratorSpec.Builder(context)
                            .setAlias(alias)
                            .setSubject(new X500Principal("CN=ViVietCertificate, O=LienVietTech, L=Hanoi"))
                            .setSerialNumber(BigInteger.ONE)
                            .setStartDate(start.getTime())
                            .setEndDate(end.getTime())
                            .setKeySize(2048)
                            .build();
                }

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();

            } else {
                //Log.e("DEMO","API >= 23 ");
                // // API > 23
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,"AndroidKeyStore");

                KeyGenParameterSpec spec23 = new KeyGenParameterSpec.Builder(alias,KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setDigests(KeyProperties.DIGEST_SHA256 , KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                        .setKeySize(2048).build();

                keyPairGenerator.initialize(spec23);

                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                //Log.e("KEYPAIR" , keyPair.toString());

            }
        } catch (Exception e) {
            //Log.e("DEMO", Log.getStackTraceString(e));
        }

    }

    public void deleteKey(String alias) {
        try {
            keyStore.deleteEntry(alias);
            refreshKeys("DELETE KEY");
        } catch (KeyStoreException e) {
            Log.e("DELETE KEY", Log.getStackTraceString(e));
        }

    }

    public boolean isKeyExisted(String alias) {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            // API < 18
            String privateKey = SharedPreferencesUtils.getString(context, alias
                    + fileNameString, "");

            if (privateKey.trim().length() > 0) {
                return true;
            } else {
                return false;
            }
        } else if (android.os.Build.VERSION.SDK_INT >= 18) {
            // API >= 18
            try {
                if (keyStore.containsAlias(alias)) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public String sign(String alias, String textToSign) {
        String result = "";
        if (android.os.Build.VERSION.SDK_INT < 18) {
            try {
                String key = SharedPreferencesUtils.getString(context,alias + fileNameString,"");
                //String decodedPrivateKey = AESEnDecryption.decrypt(seedString, key);
                String decodedPrivateKey = decryptKey(key);

                result = signSHA256RSA(textToSign, decodedPrivateKey);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Log.e("SIGN DATA ERR", Log.getStackTraceString(e));
                result = "";
            }
        } else {
            try {
                KeyStore.Entry entry = keyStore.getEntry(alias, null);
                if (!(entry instanceof PrivateKeyEntry)) {
                    //Log.w("SIGN", "Not an instance of a PrivateKeyEntry");
                    return null;
                }
                Signature s1 = Signature.getInstance("SHA256withRSA");
                PrivateKey pk = ((PrivateKeyEntry) entry).getPrivateKey();
                s1.initSign(pk);
                s1.update(textToSign.getBytes("UTF-8"));
                byte[] signature = s1.sign();
                result = Base64.encodeToString(signature, Base64.DEFAULT);
            } catch (Exception e) {
                Log.e("SIGN DATA ERR", Log.getStackTraceString(e));
                result = "";
            }
        }

        return result;
    }

    // get string of public key
    public String getPublicKeyString(String alias) throws CertificateException,
            NoSuchAlgorithmException, IOException, KeyStoreException,
            UnrecoverableEntryException {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            String key = SharedPreferencesUtils.getString(context,alias + "_publicKey","");
            return key;
        } else {
            if (keyStore == null) {
                keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
            }
            PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
            try {
                return Base64.encodeToString(publicKey.getEncoded(),Base64.DEFAULT);
            } catch (Exception e) {
                // e.printStackTrace();
                Log.e("Get key string err: ", e.getMessage());
            }
            return "";
        }
    }

    public String signSHA256RSA(String input, String privatekey)
            throws Exception {
        // Remove markers and new line characters in private key
        String realPK = privatekey;
        // byte[] b1 = Base64.getDecoder().decode(realPK);
        byte[] b1 = Base64.decode(realPK, Base64.DEFAULT);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(input.getBytes("UTF-8"));
        byte[] s = privateSignature.sign();
        return Base64.encodeToString(s, Base64.DEFAULT);
    }
    /*
     * Encrypt data
     */
    public String encryptData(String stringToEncrypt, String alias) throws Exception {
        String result = "";
        /**/
        String publicKey = getPublicKeyString(alias);
        //get the Key from the publicKey String
        byte[] keyBytes = Base64.decode(publicKey.getBytes("utf-8"),Base64.DEFAULT);
        //PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(spec);

        // get an RSA cipher object and print the provider
        final Cipher cipher = Cipher.getInstance("RSA");
        // encrypt the plain text using the public key
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(stringToEncrypt.getBytes("UTF-8"));
        result = new String(Base64.encode(encryptedBytes, Base64.DEFAULT));

        /**/
        return result;
    }

    public String decryptData(String alias, String stringToDecrypt) throws Exception {
        String result = "";
        //return result;
        if(android.os.Build.VERSION.SDK_INT >= 18){
            if (keyStore == null) {
                keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
            }
            KeyStore.Entry entry = keyStore.getEntry(alias, null);
            if (!(entry instanceof PrivateKeyEntry)) {
                Log.w("DEMO2", "Not an instance of a PrivateKeyEntry");
                return "Error";
            }
            PrivateKey pk = ((PrivateKeyEntry) entry).getPrivateKey();

            Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher1.init(Cipher.DECRYPT_MODE, pk);

            byte[] decryptedBytes = cipher1.doFinal(Base64.decode(stringToDecrypt, Base64.DEFAULT));
            result = new String(decryptedBytes);
        }
        else{ //Android < 18
            //Lay ra privatekey dang String
            String key = SharedPreferencesUtils.getString(context,alias + fileNameString,"");
            //String decodedPrivateKey = AESEnDecryption.decrypt(seedString, key);
            String privateKeyString = decryptKey(key);

            //Chuyen sang privateKey dang PrivateKey
            byte[] keyBytes = Base64.decode(privateKeyString.getBytes("utf-8"), Base64.DEFAULT);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            //X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA/ECB/PKCS1Padding");

            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            //Khoi tao ma hoa
            Cipher cipher1 = Cipher.getInstance("RSA");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);

            //Ma hoa
            byte[] decryptedBytes = cipher1.doFinal(stringToDecrypt.getBytes());

            //Chuyen sang dang string
            result = new String(decryptedBytes);
            System.out.println("DDecrypted?????" + result);
        }

        return result;
    }

    /*
     *  kiem tra co can tao moi cap key khong
     */
    public Boolean isNeedToReCreateKeyPair(){
        String checkStr = SharedPreferencesUtils.getString(context,"NeedReCreateKeyPair","NO");
        if (checkStr.equals("YES"))
            return true;
        return false;
    }
    /*
     *  danh dau trang thai Can tao moi cap key
     */
    public void markAsNeededToReCreateKeyPair(){
        SharedPreferencesUtils.setString(context,"NeedReCreateKeyPair","YES");
    }
    public void markAsDoNotNeededToReCreateKeyPair(){
        SharedPreferencesUtils.setString(context,"NeedReCreateKeyPair","NO");
    }
    /*
     * Ham kiem tra device da dang ky key chua
     */
    public Boolean isKeyPairRegistered(){
        String checkStr = SharedPreferencesUtils.getString(context,"KeyPairRegistered","NO");
        //Log.e("DEMO","Trang thai dang ky key: " + checkStr);
        if (checkStr.equals("YES"))
            return true;
        return false;
    }
    /*
     * Ham danh dau da dang ky cap key
     */
    public void markAsKeyPairRegistered(){
        SharedPreferencesUtils.setString(context,"KeyPairRegistered","YES");
        //Log.e("DEMO","Danh dau Da dang ky KEY");
        //String checkStr = SharedPreferencesUtils.getString(context,"KeyPairRegistered","NO");
        //Log.e("DEMO","Kiem tra Trang thai dang ky key: " + checkStr);
    }
    public void markAsKeyPairNotRegistered(){
        SharedPreferencesUtils.setString(context,"KeyPairRegistered","NO");
        //Log.e("DEMO","Danh dau Chua dang ky KEY");
        //String checkStr = SharedPreferencesUtils.getString(context,"KeyPairRegistered","NO");
        //Log.e("DEMO","Kiem tra Trang thai dang ky key: " + checkStr);
    }
    /*
     * Ham ma hoa, giai ma
     */
    public String encryptKey(String str){

        return str + "1";
    }

    public String decryptKey(String str){

        return str.substring(0, str.length() - 1);
    }
}