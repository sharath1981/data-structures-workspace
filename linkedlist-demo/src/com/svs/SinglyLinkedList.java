package com.svs;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class SinglyLinkedList<E extends Comparable<E>> {

    private Node<E> first;
	private int size;
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return Objects.isNull(first);
	}
	
	public void clear() {
		first = null;
		size = 0;
	}

	public void addFirst(final E element) {
		first = new Node<>(element, first);
		size++;
	}

    public void addLast(final E element) {
        if(isEmpty()) {
            addFirst(element);
            return;
        }
        getLastNode().ifPresent(last->{
                        last.setNext(new Node<>(element));
                        size++;
                     });
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return first.getElement();
    }

    public E getLast() {
        return elementAt(size-1);
    }

    public void remove(final E element) {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        if(getFirst().equals(element)){
            removeFirst();
        }
        if(Objects.nonNull(element)){
            Stream.iterate(first, node->Objects.nonNull(node) && Objects.nonNull(node.getNext()) , Node::getNext)
                  .filter(node->element.equals(node.getNext().getElement()))
                  .findFirst()
                  .ifPresent(node->{
                    node.setNext(node.getNext().getNext());
                    size--;
                  });
        } 
    }

    public E removeFirst() {
        final var element = getFirst();
        first = first.getNext();
        size--;
        return element;
    }

    public E removeLast() {
        return removeAt(size-1);
    }

    public E removeAt(final int index) {
        if (index < 1) {
            return removeFirst();
        }
        final var element = elementAt(index);
        nodeAt(index-1).ifPresent(node->{
            node.setNext(node.getNext().getNext());
            size--;
        });
        return element;
    }

    public E elementAt(final int index){
        return nodeAt(index).map(Node::getElement)
                            .orElse(null);
    }

    public int indexOf(final E element) {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        int index = -1;
        if(Objects.isNull(element)) {
            return index;
        }
        
        for (Node<E> current = first; current != null; current = current.next) {
            index++;
            if(element.equals(current.element)) {
                return index;
            }
        }
        return -1;
    }

    public boolean contains(final E element) {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return Objects.nonNull(element) && Stream.iterate(first, Node::getNext)
                                                 .map(Node::getElement)
                                                 .anyMatch(element::equals);
    }

    public void reverseIterative() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        Node<E> prev = null;
        while(Objects.nonNull(first)) {
            final var next = first.next;
            first.next = prev;
            prev = first;
            first = next;
        }
        first = prev;
    }

    public void reverseRecursive(){
        reverseRecursive(first);
    }

    private void reverseRecursive(final Node<E> node) {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        if(Objects.isNull(node.getNext())){
            first = node;
            return;
        }
        reverseRecursive(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
    }

    public void addSorted(final E element) {
        if(Objects.isNull(element)) {
            return;
        }
        if (isEmpty() || first.getElement().compareTo(element) > 0) {
			addFirst(element);
			return;
		}
        Node<E> previous = null;
        for (Node<E> current = first; 
                     Objects.nonNull(current) && current.getElement().compareTo(element) <= 0; 
                     current = current.getNext()) {
            previous = current;
        }
        if (Objects.nonNull(previous)) {
            previous.setNext(new Node<>(element, previous.getNext()));
            size++;
        }
    }

    public void printAll() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        Stream.iterate(first, Objects::nonNull, Node::getNext)
              .map(Node::getElement)
              .forEach(System.out::println); 
    }

    private Optional<Node<E>> getLastNode() {
        return nodeAt(size-1);
    }

    private Optional<Node<E>> nodeAt(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return Stream.iterate(first, Node::getNext)
                     .skip(index)
                     .findFirst();
    }

    private static final class Node<E extends Comparable<E>> {
		private Node<E> next;
		private final E element;

		private Node(final E element) {
			this.element = element;
		}
		
		private Node(final E element, final Node<E> next) {
			this(element);
			this.next = next;
		}

		private Node<E> getNext() {
			return next;
		}

		private void setNext(Node<E> next) {
			this.next = next;
		}

		private E getElement() {
			return element;
		}

		public String toString() {
			return element.toString();
		}
	}

    public static void main(String[] args) throws Exception {
        final var list = new SinglyLinkedList<String>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");
        list.addLast("E");
        list.printAll();
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        System.out.println(list.indexOf("A"));
        System.out.println(list.contains("A"));
        System.out.println(list.elementAt(0));
        System.out.println(list.removeAt(0));
        System.out.println("================");
        list.printAll();
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        System.out.println(list.indexOf("C"));
        System.out.println(list.contains("C"));
        System.out.println(list.elementAt(0));
        System.out.println("================");
        list.printAll();
        System.out.println("================");
        list.reverseIterative();
        list.printAll();
        System.out.println("================");
        list.reverseRecursive();
        list.printAll();
        System.out.println("================");
        list.clear();
        list.addSorted("X");
        list.addSorted("A");
        list.addSorted("B");
        list.addSorted("Y");
        list.addSorted("W");
        list.addSorted("M");
        list.printAll();
        System.out.println("================");
        list.remove("A");
        list.printAll();
    }
}
