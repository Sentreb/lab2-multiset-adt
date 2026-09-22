public class Tree {

    private Node root;

    private static class Node {
        int item;
        int count;
        Node left;
        Node right;

        Node(int item) {
            this.item = item;
            this.count = 1;
        }
    }

    void insert(int item) {
        root = insertNode(root, item);
    }

    private Node insertNode(Node node, int item) {
        if (node == null) {
            return new Node(item);
        }
        if (item == node.item) {
            node.count++;
        } else if (item < node.item) {
            node.left = insertNode(node.left, item);
        } else {
            node.right = insertNode(node.right, item);
        }
        return node;
    }

    void remove(int item) {
        root = removeNode(root, item);
    }

    private Node removeNode(Node node, int item) {
        if (node == null) {
            return null;
        }
        if (item < node.item) {
            node.left = removeNode(node.left, item);
        } else if (item > node.item) {
            node.right = removeNode(node.right, item);
        } else {
            node.count--;
            if (node.count > 0) {
                return node;
            }
            // count hit 0: delete the node
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // two children: replace with in-order successor
            Node successor = minNode(node.right);
            node.item = successor.item;
            node.count = successor.count;
            successor.count = 1; // so removeNode deletes it cleanly
            node.right = removeNode(node.right, successor.item);
        }
        return node;
    }

    private Node minNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    boolean contains(int item) {
        return findNode(root, item) != null;
    }

    private Node findNode(Node node, int item) {
        if (node == null) {
            return null;
        }
        if (item == node.item) {
            return node;
        }
        if (item < node.item) {
            return findNode(node.left, item);
        }
        return findNode(node.right, item);
    }

    boolean isEmpty() {
        return root == null;
    }

    int count(int item) {
        Node node = findNode(root, item);
        return node == null ? 0 : node.count;
    }

    int size() {
        return sumCounts(root);
    }

    private int sumCounts(Node node) {
        if (node == null) {
            return 0;
        }
        return node.count + sumCounts(node.left) + sumCounts(node.right);
    }
}
