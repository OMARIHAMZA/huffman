import huffman.Huffman;

public class Main {
    public static void main(String[] args) {
        String s = "MHD HAMZA AL OMARI";
        String compressedString = Huffman.getInstance().compressString(s);
        String decompressedString = Huffman.getInstance().decompressString(compressedString);
        System.out.println(compressedString);
        System.out.println(decompressedString);
    }
}
