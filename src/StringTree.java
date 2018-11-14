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

    abstract void preOrderPrint();

    void importData(File file) {
        try {
            int count = 0;
            Scanner scanner = new Scanner(file, "GB18030");
            if (!scanner.hasNext())
                return;
            String option = scanner.nextLine();
            long srartTime = System.currentTimeMillis();
            if (option.equals("INSERT"))
                while (scanner.hasNextLine()) {
                    count++;
                    if (count < 500 && count % 100 == 0) {
                        this.preOrderPrint();
                        System.out.println("The pre-order print of size " + count + "\n\n\n\n");
                    }
                    String key = scanner.nextLine();
                    String value = scanner.nextLine();
                    this.put(key, value);
                }
            else while (scanner.hasNextLine()) {
                count++;
                if (count < 500 && count % 100 == 0) {
                    this.preOrderPrint();
                    System.out.println("The pre-order print of size " + count);
                }
                String key = scanner.nextLine();
                this.remove(key);
            }
            System.out.println("The time consuming for the selected operation is: " +
                    (System.currentTimeMillis() - srartTime));
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