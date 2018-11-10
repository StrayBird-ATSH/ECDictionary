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
        bPlusTreeWC.put("7", "7");
        bPlusTreeWC.put("3", "3");
        bPlusTreeWC.put("5", "5");
        bPlusTreeWC.put("1", "1");
        bPlusTreeWC.put("6", "6");

        bPlusTreeWC.put("22", "22");
        bPlusTreeWC.put("31", "31");
        bPlusTreeWC.put("51", "51");
        bPlusTreeWC.put("11", "11");
        bPlusTreeWC.put("61", "61");
        bPlusTreeWC.put("71", "71");
        bPlusTreeWC.put("32", "32");
        bPlusTreeWC.put("52", "52");
        bPlusTreeWC.put("12", "12");
        bPlusTreeWC.put("62", "62");
        bPlusTreeWC.put("72", "72");
        bPlusTreeWC.put("33", "33");
        bPlusTreeWC.put("53", "53");
        bPlusTreeWC.put("13", "13");
        bPlusTreeWC.put("63", "63");
        bPlusTreeWC.put("73", "73");
        bPlusTreeWC.put("34", "34");
        bPlusTreeWC.put("54", "54");
        bPlusTreeWC.put("14", "14");
        bPlusTreeWC.put("64", "64");


        System.out.println(bPlusTreeWC.get("7"));
        System.out.println(bPlusTreeWC.get("3"));
        System.out.println(bPlusTreeWC.get("73"));
        System.out.println(bPlusTreeWC.get("14"));
        System.out.println(bPlusTreeWC.get("6"));
        System.out.println(bPlusTreeWC.get("6"));
        System.out.println(bPlusTreeWC.predecessor("7"));
        System.out.println(bPlusTreeWC.predecessor("3"));
        System.out.println(bPlusTreeWC.predecessor("73"));
        System.out.println(bPlusTreeWC.predecessor("14"));
        System.out.println(bPlusTreeWC.predecessor("6"));
        System.out.println(bPlusTreeWC.predecessor("6"));
        System.out.println(bPlusTreeWC.successor("7"));
        System.out.println(bPlusTreeWC.successor("3"));
        System.out.println(bPlusTreeWC.successor("73"));
        System.out.println(bPlusTreeWC.successor("14"));
        System.out.println(bPlusTreeWC.successor("6"));
        System.out.println(bPlusTreeWC.successor("6"));
    }
}
