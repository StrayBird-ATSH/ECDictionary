import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class RedBlackTree<K, V> extends StringTree<String, String> {
    private transient Entry<String, String> root;
    private transient int size = 0;
    private transient EntrySet<Entry<String, String>> entrySet;
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RedBlackTree() {
    }

    @Override
    public String put(String key, String value) {
        Entry<String, String> t = root;
        if (t == null) {
            root = new Entry<>(key, value, null);
            size = 1;
            return null;
        }
        int cmp;
        Entry<String, String> parent;

        if (key == null)
            throw new NullPointerException();
        do {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else
                return t.setValue(value);
        } while (t != null);
        Entry<String, String> e = new Entry<>(key, value, parent);
        if (cmp < 0)
            parent.left = e;
        else
            parent.right = e;
//        todo
//        fixAfterInsertion(e);
        size++;
        return null;
    }

    @NotNull
    public Set<Map.Entry<String, String>> entrySet() {
        EntrySet es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet<Entry<String, String>>());
    }

    public int size() {
        return size;
    }

    static final class Entry<K, V> implements Map.Entry<String, String> {
        String key;
        String value;
        Entry<String, String> left;
        Entry<String, String> right;
        Entry<String, String> parent;
        boolean color = BLACK;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(String key, String value, Entry<String, String> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        @Contract(pure = true)
        public String getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         * called
         */
        public String setValue(String value) {
            String oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
