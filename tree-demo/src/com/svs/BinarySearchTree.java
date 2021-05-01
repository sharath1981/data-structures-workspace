package com.svs;

import java.util.LinkedList;
import java.util.Objects;

public class BinarySearchTree<E extends Comparable<E>> {

    private Node<E> root;

	public BinarySearchTree() {
    }

    public BinarySearchTree(final E root) {
        this.root = new Node<>(root);
    }

    public boolean isEmpty() {
		return Objects.isNull(root);
	}

	public Node<E> getRoot() {
		return root;
	}

	public void insertRecursive(final E data) {
        if(Objects.isNull(data)) {
            return;
        }
		root = insertRecursive(data, root);
	}

	public Node<E> insertRecursive(final E data, final Node<E> node) {
		if (Objects.isNull(node)) {
			return new Node<>(data);
		}
		if (data.compareTo(node.data) < 0) {
			node.left = insertRecursive(data, node.left);
		} else if (data.compareTo(node.data) > 0) {
			node.right = insertRecursive(data, node.right);
		}
		return node;
	}

    public void insertIterative(final E data) {
        if(Objects.isNull(data)) {
            return;
        }
		if (isEmpty()) {
			root =  new Node<>(data);
            return;
		}
        Node<E> current = root;
        while (Objects.nonNull(current)) {
            if(data.compareTo(current.data) < 0){
                if(Objects.isNull(current.left)) {
                    current.setLeft(new Node<>(data));
                    return;
                }
                current = current.getLeft();
            } else if(data.compareTo(current.data) > 0){
                if(Objects.isNull(current.right)) {
                    current.setRight(new Node<>(data));
                    return;
                }
                current = current.getRight();
            } else {
                return;
            }
        }
	}

    public void preOrder() {
        preOrder(root);
    }
    public void inOrder() {
        inOrder(root);
    }
    public void postOrder() {
        postOrder(root);
    }
	
	public void preOrder(final Node<E> node) {
		if (Objects.nonNull(node)) {
			System.out.printf("%d ", node.data);
			preOrder(node.left);
			preOrder(node.right);
		}
	}

    public void inOrder(final Node<E> node) {
		if (Objects.nonNull(node)) {
			inOrder(node.left);
			System.out.printf("%d ", node.data);
			inOrder(node.right);
		}
	}

	public void postOrder(final Node<E> node) {
		if (Objects.nonNull(node)) {
			preOrder(node.left);
			preOrder(node.right);
			System.out.printf("%d ", node.data);
		}
	}

	public void levelOrderTraversal() {
		final var queue = new LinkedList<Node<E>>();
		queue.add(root);
		while (!queue.isEmpty()) {
			final var parent = queue.poll();
			System.out.printf("%d ", parent.data);
			if (Objects.nonNull(parent.left))
				queue.add(parent.left);
			if (Objects.nonNull(parent.right))
				queue.add(parent.right);
		}
	}

	public boolean isBST() {
		return isBST(root);
	}

	private boolean isBST(Node<E> root2) {
		// TODO Auto-generated method stub
		return false;
	}

	private static final class Node<E extends Comparable<E>> {
		private Node<E> left;
        private Node<E> right;
		private final E data;

		private Node(final E data) {
			this.data = data;
		}

        private Node<E> getLeft() {
            return left;
        }

        private void setLeft(Node<E> left) {
            this.left = left;
        }

        private Node<E> getRight() {
            return right;
        }

        private void setRight(Node<E> right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return data.toString();
        }
	}

    public static void main(String[] args) throws Exception {
        final var tree = new BinarySearchTree<Integer>(10);
        tree.insertIterative(2);
        tree.insertIterative(14);
        tree.insertIterative(4);
        tree.insertIterative(12);
        tree.insertIterative(12);
        tree.insertIterative(12);
        tree.insertIterative(1);
        tree.insertIterative(13);
        tree.insertIterative(15);
        tree.preOrder();
        System.out.println();
        tree.inOrder();
        System.out.println();
        tree.postOrder();
        System.out.println();
        tree.levelOrderTraversal();
    }
}
