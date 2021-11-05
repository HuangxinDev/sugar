package com.njxm.smart.adt;

import java.nio.BufferUnderflowException;

/**
 * @author huangxin
 * @date 2021/8/7
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    private BinaryNode<AnyType> root;

    public BinarySearchTree() {
        makeEmpty();
    }

    public void makeEmpty() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMin(root).element;
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> tree) {
        if (tree == null) {
            return null;
        } else if (tree.left == null) {
            return tree;
        } else {
            return findMin(tree.left);
        }
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> tree) {
        if (tree == null) {
            return tree;
        }
        int compareResult = x.compareTo(tree.element);
        if (compareResult < 0) {
            tree.left = remove(x, tree.left);
        } else if (compareResult > 0) {
            tree.right = remove(x, tree.right);
        } else if (tree.left != null && tree.right != null) {
            tree.element = findMin(tree.right).element;
            tree.right = remove(tree.element, tree.right);
        } else {
            tree = (tree.left != null) ? tree.left : tree.right;
        }

        return tree;
    }

    public void printTree() {
    }


    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> tree) {
        if (tree == null) {
            return new BinaryNode<>(x);
        }

        int compareResult = x.compareTo(tree.element);
        if (compareResult < 0) {
            tree.left = insert(x, tree.left);
        } else if (compareResult > 0) {
            tree.right = insert(x, tree.right);
        }

        return tree;
    }


    private boolean contains(AnyType x, BinaryNode<AnyType> middle) {
        if (middle == null) {
            return false;
        }
        int compareResult = x.compareTo(middle.element);
        if (compareResult < 0) {
            return contains(x, middle.left);
        } else if (compareResult > 0) {
            return contains(x, middle.right);
        } else {
            return true;
        }
    }

    private static class BinaryNode<AnyType> {
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;

        public BinaryNode(AnyType element) {
            this(element, null, null);
        }

        public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }
}
