import java.util.Set;

public class BPlusTree extends StringTree {
    private Node root;
    private int minDegree;

    private BPlusTree(int minDegree) {
        if (minDegree < 2) {
            System.out.print("minDegree must be at least 2");
            System.exit(0);
        }
        this.minDegree = minDegree;
        root = new Node(true, true);
    }

    void setRoot(Node root) {
        this.root = root;
    }

    int getMinDegree() {
        return minDegree;
    }

    @Override
    public String get(Object key) {
        return root.get(key.toString());
    }

    @Override
    public String put(String key, String value) {
        root.insertOrUpdate(key, value, this);
        return "Key = " + key + ", Value = " + value + " has been put into the tree";
    }

    @Override
    public String remove(Object key) {
        root.remove(key.toString(), this);
        return "Entry key = " + key + " has been removed";
    }


    public Set<Entry<String, String>> entrySet() {
        return null;
    }

    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(2);
        tree.put(Integer.toString(22), "22");
        tree.put(Integer.toString(31), "31");
        tree.put(Integer.toString(51), "51");
        tree.put(Integer.toString(11), "11");
        tree.put(Integer.toString(61), "61");
        tree.put(Integer.toString(71), "71");
        tree.put(Integer.toString(32), "32");
        tree.put(Integer.toString(52), "52");
        tree.put(Integer.toString(12), "12");
        tree.put(Integer.toString(62), "62");
        tree.put(Integer.toString(72), "72");
        tree.put(Integer.toString(33), "33");
        tree.put(Integer.toString(53), "53");
        tree.put(Integer.toString(13), "13");
        tree.put(Integer.toString(63), "63");
        tree.put(Integer.toString(73), "73");
        tree.put(Integer.toString(34), "34");
        tree.put(Integer.toString(54), "54");
        tree.put(Integer.toString(14), "14");
        tree.put(Integer.toString(64), "64");
//        tree.remove(Integer.toString(14));
        System.out.print(tree.get(Integer.toString(14)));
    }
}
