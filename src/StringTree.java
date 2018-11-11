import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Scanner;

abstract class StringTree extends AbstractMap<String, String> {
    StringTree() {
    }

    void importData(File file) {
        try {
            Scanner scanner = new Scanner(file, "GB18030");
            if (!scanner.hasNext())
                return;
            String option = scanner.nextLine();
            if (option.equals("INSERT"))
                while (scanner.hasNextLine()) {
                    String key = scanner.nextLine();
                    String value = scanner.nextLine();
                    this.put(key, value);
                }
            else while (scanner.hasNextLine()) {
                String key = scanner.nextLine();
                this.remove(key);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The specified file cannot be found");
        }
    }

    class EntrySet extends AbstractSet<Entry<String, String>> {
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

    abstract static class AbstractEntry extends SimpleEntry<String, String> {
        AbstractEntry(String key, String value) {
            super(key, value);
        }
    }
}