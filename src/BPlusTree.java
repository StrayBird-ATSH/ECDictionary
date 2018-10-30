import java.util.AbstractMap;
import java.util.Set;

public class BPlusTree<K, V> extends AbstractMap<String, String> {
    private Node root;
    private int order;

    void setRoot(Node root) {
        this.root = root;
    }

    int getOrder() {
        return order;
    }


    public Object get(Comparable key) {
        return root.get(key);
    }


    void remove(Comparable key) {
        root.remove(key, this);
    }

    void insertOrUpdate(Comparable key, Object obj) {
        root.insertOrUpdate(key, obj, this);
    }


    private BPlusTree(int order) {
        if (order < 3) {
            System.out.print("order must be greater than 2");
            System.exit(0);
        }
        this.order = order;
        root = new Node(true, true);
    }

    public Set<Entry<String, String>> entrySet() {
        return null;
    }

    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(6);
        long current = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < 100; i++) {
                int randomNumber = (int) (Math.random() * 1000);
                tree.insertOrUpdate(randomNumber, randomNumber);
            }
            for (int i = 0; i < 100; i++) {
                int randomNumber = (int) (Math.random() * 1000);
                tree.remove(randomNumber);
            }
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
        int search = 33;
        System.out.print(tree.get(search));
    }
}
