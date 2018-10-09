/*
 * TCSS 305 - Tetris
 */
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * This is class access file.
 * @author Hop Pham
 * @version Feb 28 2018
 */
public class SaveLoadGame {

    /** Path file. */
    private static final String PATH = "./data/score.src";

//    private static final String ALGO = "AES";
//    private static final byte[] keyValue =
//              new byte[]{'T', 'h', 'e', 'B', 'e',
//    's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    /**
     * Save the highest score.
     * @param theName the name
     * @param theScore the score
     */
    public void updateScore(final String theName, final int theScore) {
        saveScore(theName, theScore);
    }
    
    /** 
     * Return the highest score.
     * @return the data
     */
    public String loadScore() {
        List<String> data = new LinkedList<>();
        try {            
            data = Files.readAllLines(Paths.get(PATH));

        //catches input/output exceptions and all subclasses exceptions
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(null,
                                "Can't Processing File:" + ex.getMessage());
        }
        String result = "";
        if (data.isEmpty()) {
            result = ":0";
        } else {
            result = data.get(0);
        }
        return result;
    }
    
    /** 
     * Access the file.
     * @param theName the name
     * @param theScore the score
     */
    private void saveScore(final String theName, final int theScore) {
        try {
            final String content = theName + ":" + theScore;
            Files.write(Paths.get(PATH), content.getBytes());

        //catches input/output exceptions and all subclasses exceptions
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(null,
                                "Error Processing File:" + ex.getMessage());
        }
    }
    
//      /**
//       * Encrypt a string with AES algorithm.
//       *
//       * @param theData is a string
//       * @return the encrypted string
//       */
//      private String encrypt(String theData) throws Exception {
//          Key key = generateKey();
//          Cipher c = Cipher.getInstance(ALGO);
//          c.init(Cipher.ENCRYPT_MODE, key);
//          byte[] encVal = c.doFinal(theData.getBytes());
//          return Base64.getEncoder().encodeToString(encVal);
//      }
//
//      /**
//       * Decrypt a string with AES algorithm.
//       *
//       * @param encryptedData is a string
//       * @return the decrypted string
//       */
//      public static String decrypt(String encryptedData) throws Exception {
//          Key key = generateKey();
//          Cipher c = Cipher.getInstance(ALGO);
//          c.init(Cipher.DECRYPT_MODE, key);
//          byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
//          byte[] decValue = c.doFinal(decordedValue);
//          return new String(decValue);
//      }
//
//      /**
//       * Generate a new encryption key.
//       */
//      private static Key generateKey() throws Exception {
//          return new SecretKeySpec(keyValue, ALGO);
//      }
}
