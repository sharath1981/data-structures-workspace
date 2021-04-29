package com.svs;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.stream.Stream;

public class CircularDoublyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return Objects.isNull(first) || Objects.isNull(last) || size == 0;
    }

    public void clearAll() {
        first = last = null;
        size = 0;
    }

    public void addFirst(final E item) {
        first = new Node<>(last, item, first);
        if (Objects.isNull(last)) {
            last = first;
        } else {
            first.getNext().setPrev(first);
            last.setNext(first);
        }
        size++;
    }

    public void addLast(final E item) {
        last = new Node<>(last, item, first);
        if (Objects.isNull(first)) {
            first = last;
        } else {
            last.getPrev().setNext(last);
            first.setPrev(last);
        }
        size++;
    }

    public E getFirst() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return first.getItem();
    }

    public E getLast() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return last.getItem();
    }

    public E removeFirst() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        final var item = getFirst();
        first = first.getNext();
        last.setNext(first);
        first.setPrev(last);
        size--;
        return item;
    }

    public E removeLast() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        final var item = getLast();
        last = last.getPrev();
        first.setPrev(last);
        last.setNext(first);
        size--;
        return item;
    }

    public void remove(final E item) {
        if(getFirst().equals(item)) {
            removeFirst();
            return;
        } 
        if(getLast().equals(item)) {
            removeLast();
            return;
        }
        if(Objects.nonNull(item)) {
            Stream.iterate(first, Objects::nonNull, Node::getNext)
                  .filter(node -> item.equals(node.getItem()))
                  .findFirst()
                  .ifPresent(node->{
                    final var prev = node.getPrev();
                    final var next = node.getNext();
                    node.getPrev().setNext(next);
                    node.getNext().setPrev(prev);
                    size--;
                  });
        } 
    }

    public boolean contains(final E item) {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return Objects.nonNull(item) && Stream.iterate(first, Objects::nonNull, Node::getNext)
                                              .limit(size)
                                              .map(Node::getItem)
                                              .anyMatch(item::equals);
    }

    public void printAll() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        Stream.iterate(first, Objects::nonNull, Node::getNext)
              .limit(size)
              .map(Node::getItem)
              .forEach(System.out::println); 
    }

    private static final class Node<E> {
        private Node<E> prev;
        private final E item;
        private Node<E> next;

        private Node(final E item) {
            this.item = item;
        }

        private Node(Node<E> last, final E item) {
            this(item);
            prev = last;
        }

        private Node(final E item, Node<E> first) {
            this(item);
            next = first;
        }

        private Node(Node<E> last, final E item, Node<E> first) {
            this(item);
            prev = last;
            next = first;
        }

        private Node<E> getPrev() {
            return prev;
        }

        private void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        private E getItem() {
            return item;
        }

        private Node<E> getNext() {
            return next;
        }

        private void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    public static void main(String[] args) {
        final var list = new CircularDoublyLinkedList<String>();
        list.addFirst("A");
        list.addFirst("B");
        list.addFirst("C");
        list.addFirst("D");
        list.addFirst("E");
        list.printAll();
        System.out.println("++++++++++++++");
        System.out.println(list.removeFirst());
        System.out.println(list.removeLast());
        list.remove("C");
        System.out.println("++++++++++++++");
        list.printAll();
        System.out.println("++++++++++++++");
        list.clearAll();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");
        list.addLast("E");
        list.printAll();
        System.out.println("++++++++++++++");
        System.out.println(list.contains("C"));
        
    }
}
