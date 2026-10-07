package ds;

import Model.Hospital;
import Model.Location;

/**
 * AVL Tree for Hospital Index
 * Key: Hospital ID
 * Self-balancing - guarantees O(log n) for all operations
 * Used for fast hospital lookup by ID and location-based queries
 */
public class AVLTree {

    private Node root;

    private static class Node {
        Hospital data;
        Node left, right;
        int height;

        Node(Hospital data) {
            this.data = data;
            this.height = 1;
        }
    }

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(Hospital hospital) {
        root = insertRec(root, hospital);
    }

    private Node insertRec(Node node, Hospital hospital) {

        if (node == null) {
            return new Node(hospital);
        }

        if (hospital.getId() < node.data.getId()) {
            node.left = insertRec(node.left, hospital);

        } else if (hospital.getId() > node.data.getId()) {
            node.right = insertRec(node.right, hospital);

        } else {
            node.data = hospital;
            return node;
        }

        node.height = 1 + Math.max(
                height(node.left),
                height(node.right)
        );

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 &&
                hospital.getId() < node.left.data.getId()) {
            return rightRotate(node);
        }

        // RR Case
        if (balance < -1 &&
                hospital.getId() > node.right.data.getId()) {
            return leftRotate(node);
        }

        // LR Case
        if (balance > 1 &&
                hospital.getId() > node.left.data.getId()) {

            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 &&
                hospital.getId() < node.right.data.getId()) {

            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public Hospital search(int id) {
        return searchRec(root, id);
    }

    private Hospital searchRec(Node node, int id) {

        if (node == null) {
            return null;
        }

        if (id == node.data.getId()) {
            return node.data;
        }

        if (id < node.data.getId()) {
            return searchRec(node.left, id);
        }

        return searchRec(node.right, id);
    }

    // Find hospitals within radius (km)
    public void findNearby(
            Location loc,
            double radiusKm,
            java.util.List<Hospital> result) {

        findNearbyRec(root, loc, radiusKm, result);
    }

    private void findNearbyRec(
            Node node,
            Location loc,
            double radius,
            java.util.List<Hospital> result) {

        if (node == null) {
            return;
        }

        findNearbyRec(node.left, loc, radius, result);

        if (node.data.getLocation().distanceTo(loc) <= radius) {
            result.add(node.data);
        }

        findNearbyRec(node.right, loc, radius, result);
    }

    // Inorder traversal
    public void inorder() {

        System.out.println(
                "=== Hospital Index (AVL Tree - Inorder) ==="
        );

        inorderRec(root);
    }

    private void inorderRec(Node node) {

        if (node != null) {

            inorderRec(node.left);

            System.out.println(
                    node.data +
                            " [Height: " +
                            node.height +
                            ", Bal: " +
                            getBalance(node) +
                            "]"
            );

            inorderRec(node.right);
        }
    }
}