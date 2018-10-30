public class Main {
    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.put("7", "一");
        redBlackTree.put("3", "一");
        redBlackTree.put("5", "一");
        redBlackTree.put("1", "一");
        redBlackTree.put("6", "一");
        redBlackTree.preOrderPrint();
        BPlusTreeWC bPlusTreeWC = new BPlusTreeWC();
        bPlusTreeWC.put("7", "一");
        bPlusTreeWC.put("3", "二");
        bPlusTreeWC.put("5", "一");
        bPlusTreeWC.put("1", "三");
        bPlusTreeWC.put("6", "一");
        System.out.println(bPlusTreeWC.get("7"));
        System.out.println(bPlusTreeWC.get("3"));
        System.out.println(bPlusTreeWC.get("5"));
        System.out.println(bPlusTreeWC.get("1"));
        System.out.println(bPlusTreeWC.get("6"));
    }
}
