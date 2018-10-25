import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;

abstract class StringTree<K, V> extends AbstractMap<String, String> {
    StringTree() {
    }

    class EntrySet<T> extends AbstractSet<Entry<String, String>> {
        @NotNull
        public Iterator<Entry<String, String>> iterator() {
            return new EntryIterator();
        }


        public int size() {
            return StringTree.this.size();
        }
    }

    class EntryIterator implements Iterator<Entry<String, String>> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Entry<String, String> next() {
            return null;
        }
    }
}