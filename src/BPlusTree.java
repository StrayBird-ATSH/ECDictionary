import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

    @Override
    public String get(Object key) {
        return root.get(key.toString());
    }

    @Override
    public String put(String key, String value) {
        root.putElement(key, value, this);
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

    public class Node {
        private boolean isLeaf;
        private boolean isRoot;
        private Node parent;
        private Node previous;
        private Node next;
        private ArrayList<SimpleEntry<String, String>> entries;
        private ArrayList<Node> children;

        private Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            entries = new ArrayList<>();
            if (!isLeaf)
                children = new ArrayList<>();
        }

        Node(boolean isLeaf, boolean isRoot) {
            this(isLeaf);
            this.isRoot = isRoot;
        }

        String get(String key) {
            if (isLeaf) {
                for (SimpleEntry<String, String> entry : entries)
                    if (entry.getKey().compareTo(key) == 0)
                        return entry.getValue();
                return null;
            } else if (key.compareTo(entries.get(0).getKey()) <= 0)
                return children.get(0).get(key);
            else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0)
                return children.get(children.size() - 1).get(key);
            else
                for (int i = 0; i < entries.size(); i++)
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i + 1).getKey().compareTo(key) > 0)
                        return children.get(i).get(key);
            return null;
        }

        void putElement(String key, String obj, BPlusTree tree) {
            if (isLeaf)
                if (contains(key) || entries.size() < (2 * tree.minDegree - 1)) {
                    insertEntry(key, obj);
                    if (parent != null)
                        parent.fixAfterInsert();
                } else {
                    Node left = new Node(true);
                    Node right = new Node(true);
                    if (previous != null) {
                        previous.next = left;
                        left.previous = previous;
                    }
                    if (next != null) {
                        next.previous = right;
                        right.next = next;
                    }
                    left.next = right;
                    right.previous = left;
                    previous = null;
                    next = null;
                    int leftSize = tree.minDegree + (2 * tree.minDegree) % 2;
                    int rightSize = tree.minDegree;
                    insertEntry(key, obj);
                    for (int i = 0; i < leftSize; i++)
                        left.entries.add(entries.get(i));
                    for (int i = 0; i < rightSize; i++)
                        right.entries.add(entries.get(leftSize + i));
                    splitNode(left, right);
                }
            else {
                int i = 0;
                while (i < entries.size() && key.compareTo(entries.get(i).getKey()) > 0)
                    i++;
                children.get(i - 1).putElement(key, obj, tree);
            }
        }

        private void insertEntry(String key, String obj) {
            SimpleEntry<String, String> entry = new SimpleEntry<>(key, obj);
            if (entries.size() == 0) {
                entries.add(entry);
                return;
            }
            for (int i = 0; i < entries.size(); i++)
                if (entries.get(i).getKey().compareTo(key) == 0) {
                    entries.get(i).setValue(obj);
                    return;
                } else if (entries.get(i).getKey().compareTo(key) > 0)
                    if (i == 0) {
                        entries.add(0, entry);
                        return;
                    } else {
                        entries.add(i, entry);
                        return;
                    }
            entries.add(entries.size(), entry);
        }

        private void splitNode(Node left, Node right) {
            if (parent != null) {
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(index, left);
                parent.children.add(index + 1, right);
                this.entries = null;
                this.children = null;
                parent.fixAfterInsert();
                this.parent = null;
            } else {
                isRoot = false;
                Node parent = new Node(false, true);
                root = parent;
                left.parent = parent;
                right.parent = parent;
                parent.children.add(left);
                parent.children.add(right);
                this.entries = null;
                this.children = null;
                parent.fixAfterInsert();
            }
        }

        private void fixAfterInsert() {
            validate(this);
            if (children.size() > (2 * minDegree - 1)) {
                Node left = new Node(false);
                Node right = new Node(false);
                int leftSize = minDegree + (2 * minDegree) % 2;
                int rightSize = minDegree;
                for (int i = 0; i < leftSize; i++) {
                    left.children.add(children.get(i));
                    left.entries.add(new SimpleEntry<>(children.get(i).entries.get(0).getKey(), null));
                    children.get(i).parent = left;
                }
                for (int i = 0; i < rightSize; i++) {
                    right.children.add(children.get(leftSize + i));
                    right.entries.add(new SimpleEntry<>(children.get(leftSize + i).entries.get(0).getKey(), null));
                    children.get(leftSize + i).parent = right;
                }
                splitNode(left, right);
            }
        }

        private void validate(@NotNull Node node) {
            if (node.entries.size() == node.children.size())
                for (int i = 0; i < node.entries.size(); i++) {
                    String key = node.children.get(i).entries.get(0).getKey();
                    if (node.entries.get(i).getKey().compareTo(key) != 0) {
                        node.entries.remove(i);
                        node.entries.add(i, new SimpleEntry<>(key, null));
                        if (!node.isRoot)
                            validate(node.parent);
                        i--;
                    }
                }
            else if (node.isRoot && node.children.size() >= 2
                    || node.children.size() >= (2 * minDegree - 1) / 2
                    && node.children.size() <= (2 * minDegree - 1)
                    && node.children.size() >= 2) {
                node.entries.clear();
                for (int i = 0; i < node.children.size(); i++) {
                    String key = node.children.get(i).entries.get(0).getKey();
                    node.entries.add(new SimpleEntry<>(key, null));
                    if (!node.isRoot)
                        validate(node.parent);
                }
            }
        }

        private void updateRemove(@NotNull BPlusTree tree) {
            validate(this);
            if (children.size() < (2 * tree.minDegree - 1) / 2 || children.size() < 2) {
                if (isRoot) {
                    if (children.size() < 2) {
                        Node root = children.get(0);
                        tree.root = root;
                        root.parent = null;
                        root.isRoot = true;
                        this.entries = null;
                        this.children = null;
                    }
                } else {
                    int currIdx = parent.children.indexOf(this);
                    int prevIdx = currIdx - 1;
                    int nextIdx = currIdx + 1;
                    Node previous = null, next = null;
                    if (prevIdx >= 0)
                        previous = parent.children.get(prevIdx);

                    if (nextIdx < parent.children.size())
                        next = parent.children.get(nextIdx);

                    if (previous != null
                            && previous.children.size() > (2 * tree.minDegree - 1) / 2
                            && previous.children.size() > 2) {
                        int idx = previous.children.size() - 1;
                        Node borrow = previous.children.get(idx);
                        previous.children.remove(idx);
                        borrow.parent = this;
                        children.add(0, borrow);
                        validate(previous);
                        validate(this);
                        parent.updateRemove(tree);
                    } else if (next != null
                            && next.children.size() > (2 * tree.minDegree - 1) / 2
                            && next.children.size() > 2) {
                        Node borrow = next.children.get(0);
                        next.children.remove(0);
                        borrow.parent = this;
                        children.add(borrow);
                        validate(next);
                        validate(this);
                        parent.updateRemove(tree);
                    } else {
                        if (previous != null
                                && (previous.children.size() <= (2 * tree.minDegree - 1) / 2 ||
                                previous.children.size() <= 2)) {

                            for (int i = previous.children.size() - 1; i >= 0; i--) {
                                Node child = previous.children.get(i);
                                children.add(0, child);
                                child.parent = this;
                            }
                            previous.children = null;
                            previous.entries = null;
                            previous.parent = null;
                            parent.children.remove(previous);
                            validate(this);
                            parent.updateRemove(tree);
                        } else if (next != null
                                && (next.children.size() <= (2 * tree.minDegree - 1) / 2 ||
                                next.children.size() <= 2)) {
                            for (int i = 0; i < next.children.size(); i++) {
                                Node child = next.children.get(i);
                                children.add(child);
                                child.parent = this;
                            }
                            next.children = null;
                            next.entries = null;
                            next.parent = null;
                            parent.children.remove(next);
                            validate(this);
                            parent.updateRemove(tree);
                        }
                    }
                }
            }
        }

        void remove(String key, BPlusTree tree) {
            if (isLeaf) {
                if (!contains(key))
                    return;
                if (isRoot)
                    remove(key);
                else {
                    if (entries.size() > (2 * tree.minDegree - 1) / 2 && entries.size() > 2)
                        remove(key);
                    else {
                        if (previous != null
                                && previous.entries.size() > (2 * tree.minDegree - 1) / 2
                                && previous.entries.size() > 2
                                && previous.parent == parent) {
                            int size = previous.entries.size();
                            SimpleEntry<String, String> entry = previous.entries.get(size - 1);
                            previous.entries.remove(entry);
                            entries.add(0, entry);
                            remove(key);
                        } else if (next != null
                                && next.entries.size() > (2 * tree.minDegree - 1) / 2
                                && next.entries.size() > 2
                                && next.parent == parent) {
                            SimpleEntry<String, String> entry = next.entries.get(0);
                            next.entries.remove(entry);
                            entries.add(entry);
                            remove(key);
                        } else if (previous != null
                                && (previous.entries.size() <= (2 * tree.minDegree - 1) / 2
                                || previous.entries.size() <= 2)
                                && previous.parent == parent) {
                            for (int i = previous.entries.size() - 1; i >= 0; i--)
                                entries.add(0, previous.entries.get(i));
                            remove(key);
                            previous.parent = null;
                            previous.entries = null;
                            parent.children.remove(previous);
                            if (previous.previous != null) {
                                Node temp = previous;
                                temp.previous.next = this;
                                previous = temp.previous;
                                temp.previous = null;
                                temp.next = null;
                            } else {
                                previous.next = null;
                                previous = null;
                            }
                        } else if (next != null
                                && (next.entries.size() <= (2 * tree.minDegree - 1) / 2
                                || next.entries.size() <= 2)
                                && next.parent == parent) {
                            entries.addAll(next.entries);
                            remove(key);
                            next.parent = null;
                            next.entries = null;
                            parent.children.remove(next);
                            if (next.next != null) {
                                Node temp = next;
                                temp.next.previous = this;
                                next = temp.next;
                                temp.previous = null;
                                temp.next = null;
                            } else {
                                next.previous = null;
                                next = null;
                            }
                        }
                    }
                    parent.updateRemove(tree);
                }
            } else if (key.compareTo(entries.get(0).getKey()) <= 0)
                children.get(0).remove(key, tree);
            else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0)
                children.get(children.size() - 1).remove(key, tree);
            else
                for (int i = 0; i < entries.size(); i++)
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i + 1).getKey().compareTo(key) > 0) {
                        children.get(i).remove(key, tree);
                        break;
                    }
        }

        private boolean contains(String key) {
            for (SimpleEntry<String, String> entry : entries)
                if (entry.getKey().compareTo(key) == 0)
                    return true;
            return false;
        }

        private void remove(String key) {
            int index = -1;
            for (int i = 0; i < entries.size(); i++)
                if (entries.get(i).getKey().compareTo(key) == 0) {
                    index = i;
                    break;
                }
            if (index != -1)
                entries.remove(index);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("isRoot: ");
            sb.append(isRoot);
            sb.append(", ");
            sb.append("isLeaf: ");
            sb.append(isLeaf);
            sb.append(", ");
            sb.append("keys: ");
            for (SimpleEntry entry : entries) {
                sb.append(entry.getKey());
                sb.append(", ");
            }
            sb.append(", ");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(5);
        for (int i = 10; i < 100; i++)
            tree.put(Integer.toString(i), Integer.toString(i));
        for (int j = 20; j < 30; j++)
            tree.remove(Integer.toString(j));
        for (int i = 0; i < 130; i += 6)
            System.out.println(tree.get(Integer.toString(i)));
    }
}
