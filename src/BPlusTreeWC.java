import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class BPlusTreeWC extends StringTree {
    private Node root;
    private int t = 3;

    BPlusTreeWC() {
        super();
        root = new Node();
        root.leaf = true;
    }

    @Override
    public String get(Object key) {
        return BTreeSearch(root, key.toString());
    }

    @Nullable
    private String BTreeSearch(@NotNull Node x, String key) {
        int i = 0;
        while (i < x.n && key.compareTo(x.entries[i].getKey()) > 0)
            i++;
        if (i < x.n && key.equals(x.entries[i].getKey()))
            return x.entries[i].getValue();
        else if (x.leaf)
            return null;
        else return BTreeSearch(x.c[i], key);
    }

    @Override
    public String put(String key, String value) {
        String previousValue = get(key);
        Node r = this.root;
        if (root.n == 2 * t - 1) {
            root = new Node();
            root.leaf = false;
            root.n = 0;
            root.c[0] = r;
            BTreeSplitChild(root, 0);
            BTreeInsertNonFull(this.root, key, value);
        } else BTreeInsertNonFull(r, key, value);
        return previousValue;
    }

    private void BTreeSplitChild(@NotNull Node x, int i) {
        Node z = new Node();
        Node y = x.c[i];
        z.leaf = y.leaf;
        z.n = t - 1;
        System.arraycopy(y.entries, t, z.entries, 0, t - 1);
        if (!y.leaf)
            System.arraycopy(y.c, t, z.c, 0, t);
        y.n = t - 1;
        System.arraycopy(x.c, i + 1, x.c, i + 2, x.n - i);
        x.c[i + 1] = z;
        System.arraycopy(x.entries, i, x.entries, i + 1, x.n - i);
        x.entries[i].key = y.entries[t - 1].key;
        x.entries[i].setValue(y.entries[t - 1].getValue());
        x.n++;
    }

    private void BTreeInsertNonFull(@NotNull Node x, String k, String value) {
        int i = x.n;
        if (x.leaf) {
            while (i > 0 && k.compareTo(x.entries[i - 1].key) < 0) {
                x.entries[i] = x.entries[i - 1];
                i--;
            }
            x.entries[i].key = k;
            x.entries[i].setValue(value);
            x.n++;
        } else {
            while (i > 0 && k.compareTo(x.entries[i - 1].key) < 0)
                i--;
            if (x.c[i].n == 2 * t - 1) {
                BTreeSplitChild(x, i);
                if (k.compareTo(x.entries[i].key) > 0)
                    i++;
            }
            BTreeInsertNonFull(x.c[i], k, value);
        }
    }

    @Override
    public String remove(Object key) {

        return "Removed successfully";
    }


    @Override
    public Set<Entry<String, String>> entrySet() {
        return null;
    }

    private class Node {
        boolean leaf;
        int n = 0;
        BTreeEntry[] entries = new BTreeEntry[2 * t - 1];
        Node[] c = new Node[2 * t];

        Node() {
            for (int i = 0; i < 2 * t - 1; i++)
                entries[i] = new BTreeEntry(null, null);
        }
    }

    class BTreeEntry extends AbstractEntry {
        String key;

        BTreeEntry(String key, String value) {
            super(key, value);
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
}
