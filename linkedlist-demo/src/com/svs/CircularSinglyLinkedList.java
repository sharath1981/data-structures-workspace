package com.svs;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class CircularSinglyLinkedList<E> {
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
        nodeAt(size).ifPresent(last->{
            last.setNext(first);
            size++;
        });
	}

    public void addLast(final E element) {
        if(isEmpty()) {
            addFirst(element);
            return;
        }
        nodeAt(size-1).ifPresent(last->{
            last.setNext(new Node<>(element, first));
            size++;
        });
    }

    public E getFirst() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return first.getElement();
    }

    public E getLast() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return nodeAt(size-1).map(Node::getElement)
                             .orElse(null);
    }

    public E removeFirst() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        final var element = getFirst();
        first = first.getNext();
        size--;
        if(size>1) {
            nodeAt(size).ifPresent(last->{
                last.setNext(first);
            });
        }
        return element;
    }

    public E removeLast() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        if(size<2) {
            return removeFirst();
        }
        final var element = getLast();
        nodeAt(size-2).ifPresent(last->{
            last.setNext(first);
            size--;
        });
        return element;
    }

    private Optional<Node<E>> nodeAt(final int index) {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return Stream.iterate(first, Objects::nonNull, Node::getNext)
                     .skip(index)
                     .findFirst();
    }

    public void printAll() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        Stream.iterate(first, Node::getNext)
              .limit(size)
              .map(Node::getElement)
              .forEach(System.out::println);
    }

    public static void main(String[] args) {
        final var list = new CircularSinglyLinkedList<String>();
        list.addFirst("A");
        list.addFirst("B");
        list.addFirst("C");
        list.addFirst("D");
        list.addFirst("E");
        list.printAll();
        System.out.println("==========");
        System.out.println(list.removeFirst());
        System.out.println(list.removeLast());
        System.out.println("==========");
        list.printAll();
        System.out.println("==========");
    }

    private static final class Node<E> {
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

}
