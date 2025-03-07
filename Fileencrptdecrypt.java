package Github;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Fileencrptdecrypt {
    private static final String AES_ALGORITHM = "AES";
    private static final String ENCRYPTED_FILE_EXTENSION = ".enc";

    public static void main(String[] args){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("enter the file path");
            String filepath = reader.readLine();

            System.out.println("Enter encrypt key: ");
            String encryptkey = reader.readLine();

            System.out.println("Encrypt(E) or Decryt(D): ");
            String mode = reader.readLine();

            if(mode.equalsIgnoreCase("E")){
                encryptFile(filepath,encryptkey);
                System.out.println("File encryted successfully");
            }else if(mode.equalsIgnoreCase("D")){
                decryptFile(filepath,encryptkey);
                System.out.println("File decrypted successfully");
            }else{
                System.out.println("Invalid");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void encryptFile(String filepath,String encryptkey) throws Exception{
        byte[] fileContent = Files.readAllBytes(Paths.get(filepath));

        Key key =generatekey(encryptkey);
        Cipher cipher =Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte [] encryptedContent=cipher.doFinal(fileContent);

        String encryptedFilePath = filepath + ENCRYPTED_FILE_EXTENSION;
        FileOutputStream outputStream = new FileOutputStream(encryptedFilePath);
        outputStream.write(encryptedContent);
        outputStream.close();
        

    }
    public static void decryptFile(String filepath,String encryptkey) throws Exception{
        byte [] encrytedContent = Files.readAllBytes(Paths.get(filepath));

        Key key = generatekey(encryptkey);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte [] decryptedContent = cipher.doFinal(encrytedContent);

        String decryptedFilePath =filepath.endsWith(ENCRYPTED_FILE_EXTENSION) 
        ? filepath.substring(0, filepath.length() - ENCRYPTED_FILE_EXTENSION.length()) 
        : filepath + ".decrypted";
        FileOutputStream outputStream = new FileOutputStream(decryptedFilePath);
        outputStream.write(decryptedContent);
        outputStream.close(); 
    }
    public static Key generatekey(String encryptkey)throws Exception{
        byte [] keybytes =new byte[16];
        byte [] inputkeyBytes =encryptkey.getBytes();
        System.arraycopy(inputkeyBytes, 0, keybytes, 0, Math.min(inputkeyBytes.length, keybytes.length));
        return new SecretKeySpec(keybytes, AES_ALGORITHM);
    } 
}
