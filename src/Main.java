public class Main {
    public static void main(String[] args) {
        RedBlackTree<String, String> redBlackTree = new RedBlackTree<>();
        redBlackTree.put("7", "一");
        redBlackTree.put("3", "一");
        redBlackTree.put("5", "一");
        redBlackTree.put("1", "一");
        redBlackTree.put("6", "一");
        redBlackTree.preOrderPrint();
    }
}
