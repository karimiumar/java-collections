package com.umar.apps.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> {
    Node<T> root;

    public void add(T value) {
        root = add(root, value);
    }

    private Node<T> add(Node<T> current, T value) {
        if(null == current) {
            return new Node<>(value);
        }

        if(value.compareTo(current.value) < 0) {
            current.left = add(current.left, value);
        } else if(value.compareTo(current.value) > 0) {
            current.right = add(current.right, value);
        } else {
            //value already exists
            return current;
        }
        return current;
    }

    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node<T> current, T value) {
        if(null == current) return false;
        if(value == current.value) return true;
        return value.compareTo(current.value) < 0 ? contains(current.left, value)
                : contains(current.right,value);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> current, T value) {
        if(null == current) {
            return null;
        }
        if(value.equals(current.value)) {
            //When a node has no children return null
            if(current.left == null && current.right == null) {
                return null;
            }
            //A node has exactly one child
            if(current.right == null) {
                return current.left;
            }
            if(current.left == null) {
                return current.right;
            }
            //When a node has two children. Determine the node that will replace the deleted node.
            //Then assign the smallest value to the node to delete, and after that,
            // we'll delete it from the right sub-tree
            T smallest = findSmallest(current.right);
            current.value = smallest;
            current.right = delete(current.right, smallest);
            return current;
        }
        if(value.compareTo(current.value) < 0) {
            current.left = delete(current.left, value);
            return current;
        }
        current.right = delete(current.right, value);
        return current;
    }

    private T findSmallest(Node<T> root) {
        return root.left == null ? root.value : findSmallest(root.left);
    }

    public void traverseInOrder(Node<T> node) {
        if(null != node) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(Node<T> node) {
        if(null != node) {
            System.out.print(" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(Node<T> node) {
        if(null != node) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(" " + node.value);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node<T> current) {
        return current == null ? 0 : size(current.left) + 1 + size(current.right);
    }

    public void traverseLevelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node<T>> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node<T> node = nodes.remove();
            System.out.print(" " + node.value);
            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.left != null) {
                nodes.add(node.right);
            }
        }
    }


    public void traverseInOrderWithoutRecursion() {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;
        stack.push(root);
        while(! stack.isEmpty()) {
            while(current.left != null) {
                current = current.left;
                stack.push(current);
            }
            current = stack.pop();
            visit(current.value);
            if(current.right != null) {
                current = current.right;
                stack.push(current);
            }
        }
    }

    public void traversePreOrderWithoutRecursion() {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;
        stack.push(root);
        while(! stack.isEmpty()) {
            current = stack.pop();
            visit(current.value);

            if(current.right != null)
                stack.push(current.right);

            if(current.left != null)
                stack.push(current.left);
        }
    }

    public void traversePostOrderWithoutRecursion() {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> prev = root;
        Node<T> current = root;
        stack.push(root);

        while (!stack.isEmpty()) {
            current = stack.peek();
            boolean hasChild = (current.left != null || current.right != null);
            boolean isPrevLastChild = (prev == current.right || (prev == current.left && current.right == null));

            if (!hasChild || isPrevLastChild) {
                current = stack.pop();
                visit(current.value);
                prev = current;
            } else {
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }
    }

    private void visit(T value) {
        System.out.print(" " + value);
    }
}
