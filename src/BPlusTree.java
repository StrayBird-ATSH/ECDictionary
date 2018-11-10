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
            if (!isLeaf) {
                children = new ArrayList<>();
            }
        }

        Node(boolean isLeaf, boolean isRoot) {
            this(isLeaf);
            this.isRoot = isRoot;
        }

        String get(String key) {
            if (isLeaf) {
                for (SimpleEntry<String, String> entry : entries) {
                    if (entry.getKey().compareTo(key) == 0) {
                        return entry.getValue();
                    }
                }
                return null;
            } else {
                if (key.compareTo(entries.get(0).getKey()) <= 0) {
                    return children.get(0).get(key);
                } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
                    return children.get(children.size() - 1).get(key);
                } else {
                    for (int i = 0; i < entries.size(); i++) {
                        if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i + 1).getKey().compareTo(key) > 0) {
                            return children.get(i).get(key);
                        }
                    }
                }
            }
            return null;
        }

        void insertOrUpdate(String key, String obj, BPlusTree tree) {
            if (isLeaf) {
                if (contains(key) || entries.size() < (2 * tree.minDegree - 1)) {
                    insertOrUpdate(key, obj);
                    if (parent != null) {
                        parent.updateInsert(tree);
                    }
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
                    insertOrUpdate(key, obj);
                    for (int i = 0; i < leftSize; i++)
                        left.getEntries().add(entries.get(i));
                    for (int i = 0; i < rightSize; i++)
                        right.getEntries().add(entries.get(leftSize + i));
                    if (parent != null) {
                        int index = parent.getChildren().indexOf(this);
                        parent.getChildren().remove(this);
                        left.setParent(parent);
                        right.setParent(parent);
                        parent.getChildren().add(index, left);
                        parent.getChildren().add(index + 1, right);
                        setEntriesNull();
                        setChildrenNull();
                        parent.updateInsert(tree);
                        setParent(null);
                    } else {
                        isRoot = false;
                        Node parent = new Node(false, true);
                        tree.root = parent;
                        left.setParent(parent);
                        right.setParent(parent);
                        parent.getChildren().add(left);
                        parent.getChildren().add(right);
                        setEntriesNull();
                        setChildrenNull();
                        parent.updateInsert(tree);
                    }
                }
            } else {
                if (key.compareTo(entries.get(0).getKey()) <= 0) {
                    children.get(0).insertOrUpdate(key, obj, tree);
                } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
                    children.get(children.size() - 1).insertOrUpdate(key, obj, tree);
                } else {
                    for (int i = 0; i < entries.size(); i++) {
                        if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i + 1).getKey().compareTo(key) > 0) {
                            children.get(i).insertOrUpdate(key, obj, tree);
                            break;
                        }
                    }
                }
            }
        }


        private void updateInsert(BPlusTree tree) {
            validate(this, tree);
            if (children.size() > (2 * tree.minDegree - 1)) {
                Node left = new Node(false);
                Node right = new Node(false);
                int leftSize = tree.minDegree + (2 * tree.minDegree) % 2;
                int rightSize = tree.minDegree;
                for (int i = 0; i < leftSize; i++) {
                    left.getChildren().add(children.get(i));
                    left.getEntries().add(new SimpleEntry<>(children.get(i).getEntries().get(0).getKey(), null));
                    children.get(i).setParent(left);
                }
                for (int i = 0; i < rightSize; i++) {
                    right.getChildren().add(children.get(leftSize + i));
                    right.getEntries().add(new SimpleEntry<>(children.get(leftSize + i).getEntries().get(0).getKey(), null));
                    children.get(leftSize + i).setParent(right);
                }
                if (parent != null) {
                    int index = parent.getChildren().indexOf(this);
                    parent.getChildren().remove(this);
                    left.setParent(parent);
                    right.setParent(parent);
                    parent.getChildren().add(index, left);
                    parent.getChildren().add(index + 1, right);
                    setEntriesNull();
                    setChildrenNull();
                    parent.updateInsert(tree);
                    setParent(null);
                } else {
                    isRoot = false;
                    Node parent = new Node(false, true);
                    tree.root = parent;
                    left.setParent(parent);
                    right.setParent(parent);
                    parent.getChildren().add(left);
                    parent.getChildren().add(right);
                    setEntriesNull();
                    setChildrenNull();
                    parent.updateInsert(tree);
                }
            }
        }


        private void validate(@NotNull Node node, BPlusTree tree) {
            if (node.getEntries().size() == node.getChildren().size()) {
                for (int i = 0; i < node.getEntries().size(); i++) {
                    String key = node.getChildren().get(i).getEntries().get(0).getKey();
                    if (node.getEntries().get(i).getKey().compareTo(key) != 0) {
                        node.getEntries().remove(i);
                        node.getEntries().add(i, new SimpleEntry<>(key, null));
                        if (!node.isRoot()) {
                            validate(node.parent, tree);
                        }
                    }
                }
            } else if (node.isRoot() && node.getChildren().size() >= 2
                    || node.getChildren().size() >= (2 * tree.minDegree - 1) / 2
                    && node.getChildren().size() <= (2 * tree.minDegree - 1)
                    && node.getChildren().size() >= 2) {
                node.getEntries().clear();
                for (int i = 0; i < node.getChildren().size(); i++) {
                    String key = node.getChildren().get(i).getEntries().get(0).getKey();
                    node.getEntries().add(new SimpleEntry<>(key, null));
                    if (!node.isRoot()) {
                        validate(node.parent, tree);
                    }
                }
            }
        }


        private void updateRemove(BPlusTree tree) {
            validate(this, tree);
            if (children.size() < (2 * tree.minDegree - 1) / 2 || children.size() < 2) {
                if (isRoot) {
                    if (children.size() < 2) {
                        Node root = children.get(0);
                        tree.root = root;
                        root.setParent(null);
                        root.setRoot();
                        setEntriesNull();
                        setChildrenNull();
                    }
                } else {
                    int currIdx = parent.getChildren().indexOf(this);
                    int prevIdx = currIdx - 1;
                    int nextIdx = currIdx + 1;
                    Node previous = null, next = null;
                    if (prevIdx >= 0) {
                        previous = parent.getChildren().get(prevIdx);
                    }
                    if (nextIdx < parent.getChildren().size()) {
                        next = parent.getChildren().get(nextIdx);
                    }
                    if (previous != null
                            && previous.getChildren().size() > (2 * tree.minDegree - 1) / 2
                            && previous.getChildren().size() > 2) {
                        int idx = previous.getChildren().size() - 1;
                        Node borrow = previous.getChildren().get(idx);
                        previous.getChildren().remove(idx);
                        borrow.setParent(this);
                        children.add(0, borrow);
                        validate(previous, tree);
                        validate(this, tree);
                        parent.updateRemove(tree);
                    } else if (next != null
                            && next.getChildren().size() > (2 * tree.minDegree - 1) / 2
                            && next.getChildren().size() > 2) {
                        Node borrow = next.getChildren().get(0);
                        next.getChildren().remove(0);
                        borrow.setParent(this);
                        children.add(borrow);
                        validate(next, tree);
                        validate(this, tree);
                        parent.updateRemove(tree);
                    } else {
                        if (previous != null
                                && (previous.getChildren().size() <= (2 * tree.minDegree - 1) / 2 ||
                                previous.getChildren().size() <= 2)) {

                            for (int i = previous.getChildren().size() - 1; i >= 0; i--) {
                                Node child = previous.getChildren().get(i);
                                children.add(0, child);
                                child.setParent(this);
                            }
                            previous.setChildrenNull();
                            previous.setEntriesNull();
                            previous.setParent(null);
                            parent.getChildren().remove(previous);
                            validate(this, tree);
                            parent.updateRemove(tree);
                        } else if (next != null
                                && (next.getChildren().size() <= (2 * tree.minDegree - 1) / 2 ||
                                next.getChildren().size() <= 2)) {

                            for (int i = 0; i < next.getChildren().size(); i++) {
                                Node child = next.getChildren().get(i);
                                children.add(child);
                                child.setParent(this);
                            }
                            next.setChildrenNull();
                            next.setEntriesNull();
                            next.setParent(null);
                            parent.getChildren().remove(next);
                            validate(this, tree);
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
                    if (entries.size() > (2 * tree.minDegree - 1) / 2 && entries.size() > 2) {
                        remove(key);
                    } else {
                        if (previous != null
                                && previous.getEntries().size() > (2 * tree.minDegree - 1) / 2
                                && previous.getEntries().size() > 2
                                && previous.parent == parent) {
                            int size = previous.getEntries().size();
                            SimpleEntry<String, String> entry = previous.getEntries().get(size - 1);
                            previous.getEntries().remove(entry);
                            entries.add(0, entry);
                            remove(key);
                        } else if (next != null
                                && next.getEntries().size() > (2 * tree.minDegree - 1) / 2
                                && next.getEntries().size() > 2
                                && next.parent == parent) {
                            SimpleEntry<String, String> entry = next.getEntries().get(0);
                            next.getEntries().remove(entry);
                            entries.add(entry);
                            remove(key);
                        } else {
                            if (previous != null
                                    && (previous.getEntries().size() <= (2 * tree.minDegree - 1) / 2
                                    || previous.getEntries().size() <= 2)
                                    && previous.parent == parent) {
                                for (int i = previous.getEntries().size() - 1; i >= 0; i--) {
                                    entries.add(0, previous.getEntries().get(i));
                                }
                                remove(key);
                                previous.setParent(null);
                                previous.setEntriesNull();
                                parent.getChildren().remove(previous);
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
                                    && (next.getEntries().size() <= (2 * tree.minDegree - 1) / 2
                                    || next.getEntries().size() <= 2)
                                    && next.parent == parent) {
                                for (int i = 0; i < next.getEntries().size(); i++) {
                                    entries.add(next.getEntries().get(i));
                                }
                                remove(key);
                                next.setParent(null);
                                next.setEntriesNull();
                                parent.getChildren().remove(next);
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
                    }
                    parent.updateRemove(tree);
                }
            } else {
                if (key.compareTo(entries.get(0).getKey()) <= 0) {
                    children.get(0).remove(key, tree);
                } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
                    children.get(children.size() - 1).remove(key, tree);
                } else {
                    for (int i = 0; i < entries.size(); i++) {
                        if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i + 1).getKey().compareTo(key) > 0) {
                            children.get(i).remove(key, tree);
                            break;
                        }
                    }
                }
            }
        }


        private boolean contains(String key) {
            for (SimpleEntry<String, String> entry : entries) {
                if (entry.getKey().compareTo(key) == 0) {
                    return true;
                }
            }
            return false;
        }


        private void insertOrUpdate(String key, String obj) {
            SimpleEntry<String, String> entry = new SimpleEntry<>(key, obj);
            if (entries.size() == 0) {
                entries.add(entry);
                return;
            }
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).getKey().compareTo(key) == 0) {
                    entries.get(i).setValue(obj);
                    return;
                } else if (entries.get(i).getKey().compareTo(key) > 0) {
                    if (i == 0) {
                        entries.add(0, entry);
                        return;
                    } else {
                        entries.add(i, entry);
                        return;
                    }
                }
            }
            entries.add(entries.size(), entry);
        }

        private void remove(String key) {
            int index = -1;
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).getKey().compareTo(key) == 0) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                entries.remove(index);
            }
        }

        private void setParent(Node parent) {
            this.parent = parent;
        }

        private ArrayList<SimpleEntry<String, String>> getEntries() {
            return entries;
        }

        private void setEntriesNull() {
            this.entries = null;
        }

        private ArrayList<Node> getChildren() {
            return children;
        }

        private void setChildrenNull() {
            this.children = null;
        }

        private boolean isRoot() {
            return isRoot;
        }

        private void setRoot() {
            this.isRoot = true;
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
        BPlusTree tree = new BPlusTree(2);
        for (int i = 10; i < 100; i++)
            tree.put(Integer.toString(i), Integer.toString(i));
        for (int j = 20; j < 30; j++)
            tree.remove(Integer.toString(j));
        for (int i = 0; i < 130; i += 6)
            System.out.println(tree.get(Integer.toString(i)));
    }
}
