package common.dataStructures.list.impl;

/**
 * Represents a node in a singly linked list (SLL).
 * <p>
 * Each node contains:
 * <ul>
 *   <li>{@code data} – the element stored in the node</li>
 *   <li>{@code next} – a reference to the next node in the list</li>
 * </ul>
 *
 * @param <E> the type of element stored in the node
 */
class SLLNode<E> {

    /** The element stored in this node */
    E data;

    /** Reference to the next node in the list */
    SLLNode<E> next;

    /**
     * Creates a node containing the specified element and pointing to the given next node.
     *
     * @param e the element stored in the node
     * @param n the next node in the list
     */
    SLLNode(E e, SLLNode<E> n) {
        data = e;
        next = n;
    }

    /**
     * Creates a node containing the specified element and no next node.
     *
     * @param e the element stored in the node
     */
    SLLNode(E e) {
        this(e, null);
    }
}
