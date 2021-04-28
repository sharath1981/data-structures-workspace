package com.svs;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayStack<E> {

    private E[] stack;
    private int top;

    public ArrayStack(final int initialCapacity) {
        stack = (E[]) new Object[initialCapacity];
        top = -1;
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return stack.length == size();
    }

    public void push(final E element) {
        ensureCapacity();
        stack[++top] = element;
    }

    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[top];
    }

    public E pop() {
        final var element = peek();
        stack[top--] = null;
        return element;
    }

    public void clearAll() {
        Stream.generate(this::pop)
              .limit(size())
              .forEach(System.out::println);
    }

    public boolean contains(final E element) {
        return Objects.nonNull(element) && Arrays.stream(stack)
                                                 .anyMatch(element::equals);
    }

    public int indexOf(final E element) {
        if (Objects.isNull(element)) {
            return -1;
        }
        return IntStream.range(0, size())
                        .filter(i->element.equals(stack[i]))
                        .findFirst()
                        .orElse(-1);
    }

    public E elementAt(final int index) {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        if (index < 0 || index > top) {
            throw new IndexOutOfBoundsException();
        }
        return Arrays.stream(stack)
                     .skip(index)
                     .findFirst()
                     .orElseThrow(IndexOutOfBoundsException::new);
    }

    public void printAll() {
        System.out.println(Arrays.toString(stack));
    }

    private void ensureCapacity() {
        if (isFull()) {
            stack = Arrays.copyOf(stack, 2*size());
        }
    }

    public static void main(String[] args) throws Exception {
        final var stack = new ArrayStack<>(5);

        stack.printAll();
		System.out.println("size => " + stack.size());
		System.out.println("isEmpty => " + stack.isEmpty());
		System.out.println("isFull => " + stack.isFull());
		System.out.println("===========================");
		stack.push("sharath");
		stack.push("vijay");
		stack.push("vivek");
		stack.push("ravi");
		stack.push("gopal");
        stack.printAll();
		System.out.println("size => " + stack.size());
		System.out.println("isEmpty => " + stack.isEmpty());
		System.out.println("isFull => " + stack.isFull());
		System.out.println("peek => " + stack.peek());
		System.out.println("contains => " + stack.contains("vivek"));
		System.out.println("indexOf => " + stack.indexOf("ravi"));
		System.out.println("elementAt => " + stack.elementAt(2));
        System.out.println("===========================");
		stack.push("navin");
		stack.printAll();
		System.out.println("size => " + stack.size());
		System.out.println("isEmpty => " + stack.isEmpty());
		System.out.println("isFull => " + stack.isFull());
		System.out.println("peek => " + stack.peek());
		System.out.println("contains => " + stack.contains("viveks"));
		System.out.println("indexOf => " + stack.indexOf("ravi"));
        System.out.println("indexOf => " + stack.indexOf("xyz"));
        System.out.println("elementAt => " + stack.elementAt(2));
        System.out.println("===========================");
		stack.clearAll();
		stack.printAll();
		System.out.println("size => " + stack.size());
		System.out.println("isEmpty => " + stack.isEmpty());
		System.out.println("isFull => " + stack.isFull());
 
    }
}
