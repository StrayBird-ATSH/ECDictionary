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

        bPlusTreeWC.put("22", "一");
        bPlusTreeWC.put("31", "二");
        bPlusTreeWC.put("51", "一");
        bPlusTreeWC.put("11", "三");
        bPlusTreeWC.put("61", "一");
        bPlusTreeWC.put("71", "一");
        bPlusTreeWC.put("32", "二");
        bPlusTreeWC.put("52", "一");
        bPlusTreeWC.put("12", "三");
        bPlusTreeWC.put("62", "一");
        bPlusTreeWC.put("72", "一");
        bPlusTreeWC.put("33", "二");
        bPlusTreeWC.put("53", "一");
        bPlusTreeWC.put("13", "三");
        bPlusTreeWC.put("63", "一");
        bPlusTreeWC.put("73", "啦啦啦");
        bPlusTreeWC.put("34", "二");
        bPlusTreeWC.put("54", "一");
        bPlusTreeWC.put("14", "三");
        bPlusTreeWC.put("64", "一");


        System.out.println(bPlusTreeWC.get("7"));
        System.out.println(bPlusTreeWC.get("3"));
        System.out.println(bPlusTreeWC.get("73"));
        System.out.println(bPlusTreeWC.get("1"));
        System.out.println(bPlusTreeWC.get("6"));
    }
}
