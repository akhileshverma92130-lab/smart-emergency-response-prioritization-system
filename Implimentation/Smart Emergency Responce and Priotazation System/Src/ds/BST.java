package ds;

import Model.Ambulance;

/**
 * Binary Search Tree for Ambulance Registry
 * Key: Ambulance ID
 * Operations: Insert, Search, Delete, Inorder Traversal
 */
public class BST {
    private Node root;

    private static class Node {
        Ambulance data;
        Node left, right;

        Node(Ambulance data) {
            this.data = data;
            left = right = null;
        }
    }

    // Insert - O(h) where h = height
    public void insert(Ambulance ambulance) {
        root = insertRec(root, ambulance);
    }

    private Node insertRec(Node node, Ambulance ambulance) {
        if (node == null) {
            return new Node(ambulance);
        }
        if (ambulance.getId() < node.data.getId()) {
            node.left = insertRec(node.left, ambulance);
        } else if (ambulance.getId() > node.data.getId()) {
            node.right = insertRec(node.right, ambulance);
        } else {
            // Duplicate ID - update data
            node.data = ambulance;
        }
        return node;
    }

    // Search - O(h)
    public Ambulance search(int id) {
        return searchRec(root, id);
    }

    private Ambulance searchRec(Node node, int id) {
        if (node == null) return null;
        if (id == node.data.getId()) return node.data;
        if (id < node.data.getId()) return searchRec(node.left, id);
        return searchRec(node.right, id);
    }

    // Delete - O(h)
    public void delete(int id) {
        root = deleteRec(root, id);
    }

    private Node deleteRec(Node node, int id) {
        if (node == null) return null;

        if (id < node.data.getId()) {
            node.left = deleteRec(node.left, id);
        } else if (id > node.data.getId()) {
            node.right = deleteRec(node.right, id);
        } else {
            // Found the node to delete
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Two children - get inorder successor (min in right subtree)
            node.data = minValue(node.right);
            node.right = deleteRec(node.right, node.data.getId());
        }
        return node;
    }

    private Ambulance minValue(Node node) {
        Ambulance min = node.data;
        while (node.left != null) {
            node = node.left;
            min = node.data;
        }
        return min;
    }

    // Inorder traversal - sorted by ID
    public void inorder() {
        System.out.println("=== Ambulance Registry (Inorder - Sorted by ID) ===");
        inorderRec(root);
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println(node.data);
            inorderRec(node.right);
        }
    }

    // Find available ambulances
    public void findAvailable() {
        System.out.println("=== Available Ambulances ===");
        findAvailableRec(root);
    }

    private void findAvailableRec(Node node) {
        if (node != null) {
            findAvailableRec(node.left);
            if (node.data.isAvailable()) {
                System.out.println(node.data);
            }
            findAvailableRec(node.right);
        }
    }

    public int countNodes() {
        return countRec(root);
    }

    private int countRec(Node node) {
        if (node == null) return 0;
        return 1 + countRec(node.left) + countRec(node.right);
    }
}