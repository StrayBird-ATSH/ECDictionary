import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

abstract class StringTree<K, V> extends AbstractMap<String, String> {
    StringTree() {
    }

    class EntrySet<T> extends AbstractSet<Map.Entry<String, String>> {
        @NotNull
        public Iterator<Map.Entry<String, String>> iterator() {
            return new EntryIterator();
        }

        public int size() {
            return StringTree.this.size();
        }
    }

    class EntryIterator implements Iterator<Map.Entry<String, String>> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Map.Entry<String, String> next() {
            return null;
        }
    }

    abstract static class AbstractEntry<K, V> extends AbstractMap.SimpleEntry<String, String> {
        AbstractEntry(String key, String value) {
            super(key, value);
        }
    }
}