package huffman;

import java.util.ArrayList;

public class Huffman {

    private HuffmanNode huffmanNode;
    private HuffmanNode newRoot;
    private ArrayList<Character> characters = new ArrayList<>();
    private int[] characterOccurrences;
    private String dictionary = "";

    public static Huffman getInstance() {
        return new Huffman();
    }

    public String compressString(String message) {
        calculateFrequencies(message);
        char checker;
        String compressedString = "";
        for (int i = 0; i < message.length(); i++) {
            HuffmanNode current = huffmanNode;
            checker = message.charAt(i);
            String code = "";
            while (true) {
                if (current.left.value.toCharArray()[0] == checker) {
                    code += "0";
                    break;
                } else {
                    code += "1";
                    if (current.right != null) {
                        if (current.right.value.toCharArray()[0] == characters.get(characterOccurrences.length - 1))
                            break;
                        current = current.right;
                    } else {
                        break;
                    }
                }
            }
            compressedString += code;
        }
        return compressedString + "_" + dictionary;
    }

    public String decompressString(String dictionary) {
        String[] splitString = dictionary.split("_");
        String code = splitString[0];
        dictionary = splitString[1];
        ArrayList<Integer> occurs = new ArrayList<>();
        for (int i = 0; i < dictionary.length() - 1; i += 2) {
            characters.add(dictionary.charAt(i));
            occurs.add(Integer.parseInt(String.valueOf(dictionary.charAt(i + 1))));
        }

        this.characterOccurrences = new int[characters.size()];
        for (int i = 0; i < occurs.size(); i++) {
            this.characterOccurrences[i] = occurs.get(i);
        }
        occurs.clear();
        bubbleSort();
        formHuffTree();
        return decode(newRoot, code);
    }

    private void calculateFrequencies(String message) {
         /*
         Get a list of the characters that present in the string message without repeating any character
        */
        for (int i = 0; i < message.length(); i++) {
            if (!characters.contains(message.charAt(i)))
                characters.add(message.charAt(i));
        }
        characterOccurrences = new int[characters.size()];
        //Count the number of occurrences for each character
        for (int i = 0; i < characters.size(); i++) {
            for (int j = 0; j < message.length(); j++) {
                if (message.charAt(j) == characters.get(i))
                    characterOccurrences[i] += 1;
            }
        }

        for (int i = 0; i < characters.size(); i++) {
            dictionary += characters.get(i) + "" + characterOccurrences[i];
        }
        bubbleSort();
        formHuffTree();
    }

    private void constructTree(HuffmanNode root, HuffmanNode end) {
        huffmanNode = new HuffmanNode(end.previousHuffmanNode.value + end.value, end.previousHuffmanNode.count + end.count);
        huffmanNode.left = end.previousHuffmanNode;
        huffmanNode.right = end;
        end.previousHuffmanNode.previousHuffmanNode.nextHuffmanNode = huffmanNode;
        huffmanNode.previousHuffmanNode = end.previousHuffmanNode.previousHuffmanNode;
        end = huffmanNode;
        end.nextHuffmanNode = null;
        HuffmanNode current = root;

        while (current.nextHuffmanNode != null) {
            current = current.nextHuffmanNode;
        }

        if (root.nextHuffmanNode == end) {
            huffmanNode = new HuffmanNode(root.value + end.value, root.count + end.count);
            huffmanNode.left = root;
            huffmanNode.right = end;
            huffmanNode.nextHuffmanNode = null;
            huffmanNode.previousHuffmanNode = null;
            newRoot = huffmanNode;
        } else {
            constructTree(root, end);
        }
    }

    private void formHuffTree() {
        //Form the tree: create the leaf nodes and arrange them in a linked list
        HuffmanNode root = null;
        HuffmanNode current;
        HuffmanNode end = null;
        for (int i = 0; i < characterOccurrences.length; i++) {
            huffmanNode = new HuffmanNode(characters.get(i).toString(), characterOccurrences[i]);
            if (root == null) {
                root = huffmanNode;
                end = huffmanNode;
            } else {
                current = root;
                while (current.nextHuffmanNode != null) {
                    current = current.nextHuffmanNode;
                }
                current.nextHuffmanNode = huffmanNode;
                current.nextHuffmanNode.previousHuffmanNode = current;
                end = huffmanNode;
            }
        }
        assert end != null;
        constructTree(root, end);
    }

    private void bubbleSort() {
        for (int i = 0; i < characterOccurrences.length - 1; i++) {
            for (int j = 0; j < characterOccurrences.length - 1; j++) {
                if (characterOccurrences[j] < characterOccurrences[j + 1]) {
                    int temp = characterOccurrences[j];
                    characterOccurrences[j] = characterOccurrences[j + 1];
                    characterOccurrences[j + 1] = temp;
                    char tempChar = characters.get(j);
                    characters.set(j, characters.get(j + 1));
                    characters.set(j + 1, tempChar);
                }
            }
        }
    }

    private String decode(HuffmanNode root, String code) {
        String res = "";
        HuffmanNode current = root;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') current = current.left;
            else current = current.right;

            if (current.left == null && current.right == null) {
                res += current.value;
                current = root;
            }
        }
        return res;
    }


}
