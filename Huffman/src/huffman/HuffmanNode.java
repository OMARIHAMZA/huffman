package huffman;

class HuffmanNode {
    String value;
    int count;
    //Binary tree pointers
    HuffmanNode left;
    HuffmanNode right;
    //Linked List pointers
    HuffmanNode nextHuffmanNode;
    HuffmanNode previousHuffmanNode;

    HuffmanNode(String value, int count) {
        this.value = value;
        this.count = count;
    }
}
