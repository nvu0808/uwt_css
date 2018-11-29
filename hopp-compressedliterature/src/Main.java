/*
 * TCSS 342 - Compressed Literature
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The simulation controller.
 * @author Hop Pham.
 * @version May 2018.
 */
public final class Main {    
    /** Shift to the right. */
    private static final int SHIFTRIGHT = 3;
    
    /** The factor.     */
    private static final double FACTOR = 100.0;
    
    /** The lenght of buffer.*/
    private static final int BUFFER = 100;
    
    /**
     * Private constructor to prevent construction of instances.
     */
    private Main() { }
    
    /**
     * The main method.
     * @param theArgs Command line arguments.
     * @throws IOException 
     */
    @SuppressWarnings("resource")
    public static void main(final String[] theArgs) throws IOException {
        final String file = "WarAndPeace.txt";
        final String content = readFile(file);
        final long startTime = System.currentTimeMillis();
        final CodingTree codingTree = new CodingTree(content);
        try {            
            PrintStream codesSet = new PrintStream("codes.txt");
            codesSet.print(codingTree.codes); //Output to txt file.
            codesSet.close();
            //To output binary file.
            codesSet = new PrintStream("compressed.txt");            
            final String compressedS = codingTree.bits;
            final int bitsLength = compressedS.length();
            final int compressedSize = (bitsLength >> SHIFTRIGHT) + 1;
            
            final byte[] resultBits = new byte[(bitsLength + Byte.SIZE - 1) / Byte.SIZE];
            char c;
            for (int i = 0; i < bitsLength; i++) {
                c = compressedS.charAt(i);
                if (c == '1') {
                    resultBits[i / Byte.SIZE] = (byte) (resultBits[i / Byte.SIZE]
                                                | (0x80 >>> (i % Byte.SIZE)));
                } else if (c != '0') {
                    throw new IllegalArgumentException();
                }
            }
            codesSet.write(resultBits);
            codesSet.close();
            final long time = System.currentTimeMillis() - startTime;
            final double ratio = FACTOR * compressedSize / content.length();
            System.out.printf("The original size: " + content.length() + " byte\n");
            System.out.printf("The compressed size: " + compressedSize + " byte \n");
            System.out.printf("The compression ratio: %.2f%%\n", ratio);
            System.out.printf("The elapsed time: " + time + "ms\n"); 
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
       
        testCodingTree();
        decodeFile();
    }

    /**
     * Read the file then return it's content as a string.
     * @param theFile the file
     * @return the string content
     * @throws FileNotFoundException 
     */
    private static String readFile(final String theFile) throws FileNotFoundException {
        final StringBuilder sb = new StringBuilder();
        final FileReader fr = new FileReader(theFile);
        final BufferedReader br = new BufferedReader(fr);            
        final char[] buf = new char[BUFFER];
        try {            
            int read;
            while ((read = br.read(buf)) > 0) {
                sb.append(buf, 0, read);
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * This method will test for the CodingTree class.
     * @throws IOException 
     */
    public static void testCodingTree() throws IOException { //NOPMD
        System.out.println("\n------------ Test CodingTree ----------\n");
        final String original = "I like Data Structures course.";
        final CodingTree codingTree = new CodingTree(original);
        final String decoded = codingTree.decode(codingTree.bits, codingTree.codes);
        System.out.println("Encoded String:");
        System.out.println(codingTree.bits);
        System.out.println("Decoded String: " + decoded);
        System.out.println("Compared result: " + decoded.equals(original));        
    }
    
    /**
     * This method will read the compressed file and the code file then
     * call the CodingTree to decode the file.
     * Write the decoded content to the file.
     * @throws IOException the exception
     */
    private static void decodeFile() throws IOException {
        System.out.println("\n---------------Decode the compressed file.------------");
        final String file = "codes.txt";
        final String content = readFile(file);
        
        final String[] code = content.substring(1, content.length() - 1).split(", ");        
        
        final Map<Character, String> codes = new HashMap<>();
//        final Map<String, Character> codes1 = new HashMap<>();
        for (int i = 0; i < code.length; i++) {
            codes.put(code[i].charAt(0), code[i].substring(2, code[i].length()));
//            codes1.put(code[i].substring(2, code[i].length()), code[i].charAt(0));
        }

        final Path path = Paths.get("compressed.txt");
        final byte[] fileContents =  Files.readAllBytes(path);

        final StringBuilder sb = new StringBuilder(fileContents.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * fileContents.length; i++) {
            sb.append((fileContents[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        final CodingTree codingTree1 = new CodingTree("a");
        final PrintStream codesSet = new PrintStream("decoded.txt");
        System.out.println("The output file: decoded.txt");
        codesSet.print(codingTree1.decode(sb.toString(), codes)); //Output to txt file.
        codesSet.close(); 
       
    }    
}
