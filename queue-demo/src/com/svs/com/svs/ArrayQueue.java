package com.svs;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayQueue<E> {

    private E[] queue;
    private int index;

    public ArrayQueue(final int initialCapacity) {
        queue = (E[]) new Object[initialCapacity];
        index = -1;
    }

    public int size() {
        return index + 1;
    }

    public boolean isEmpty() {
        return index == -1;
    }

    public boolean isFull() {
        return queue.length == size();
    }

    public void enqueue(final E element) {
        ensureCapacity();
        queue[++index] = element;
    }

    public E dequeue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        final var element = queue[0];
        queue = Arrays.copyOfRange(queue, 1, queue.length+1);
        queue[index--] = null;
        return element;
    }

    public void clearAll() {
        Stream.generate(this::dequeue)
              .limit(size())
              .forEach(System.out::println);
    }

    
    public boolean contains(final E element) {
        return Objects.nonNull(element) && Arrays.stream(queue)
                                                 .anyMatch(element::equals);
    }

    public int indexOf(final E element) {
        if (Objects.isNull(element)) {
            return -1;
        }
        return IntStream.range(0, size())
                        .filter(i->element.equals(queue[i]))
                        .findFirst()
                        .orElse(-1);
    }

    public E elementAt(final int index) {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        if (index < 0 || index > this.index) {
            throw new IndexOutOfBoundsException();
        }
        return Arrays.stream(queue)
                     .skip(index)
                     .findFirst()
                     .orElseThrow(IndexOutOfBoundsException::new);
    }

    public void printAll() {
        System.out.println(Arrays.toString(queue));
    }

    private void ensureCapacity() {
        if (isFull()) {
            queue = Arrays.copyOf(queue, 2*size());
        }
    }

    public static void main(String[] args) throws Exception {
        final var queue = new ArrayQueue<>(5);
		System.out.println("size => " + queue.size());
		System.out.println("isEmpty => " + queue.isEmpty());
		System.out.println("isFull => " + queue.isFull());
		System.out.println("===========================");
		queue.enqueue("sharath");
		queue.enqueue("vijay");
		queue.enqueue("vivek");
		queue.enqueue("ravi");
		queue.enqueue("gopal");
		queue.printAll();
		System.out.println("size => " + queue.size());
		System.out.println("isEmpty => " + queue.isEmpty());
		System.out.println("isFull => " + queue.isFull());
		System.out.println("contains => " + queue.contains("vivek"));
		System.out.println("===========================");
        System.out.println("dequeue => " + queue.dequeue());
        queue.printAll();
        System.out.println("===========================");
		queue.clearAll();
        queue.printAll();
		System.out.println("size => " + queue.size());
		System.out.println("isEmpty => " + queue.isEmpty());
		System.out.println("isFull => " + queue.isFull());
    }
}
