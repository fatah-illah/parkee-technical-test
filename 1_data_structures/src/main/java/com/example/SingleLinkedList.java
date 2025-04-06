package com.example;

class SingleLinkedList {
    Node head;      // Stores the reference to the head of the linked list

    // Constructor
    public SingleLinkedList() {
        this.head = null;
    }

    // 1. Insert a new node with given data at the end of the linked list
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);

        // If the list is empty, make the new node as head
        if (head == null) {
            head = newNode;
            return;
        }

        // Traverse to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        // Append the new node at the end
        current.next = newNode;
    }

    // 2. Insert a new node with given data at the beginning of the linked list
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);

        // Make the new node point to the current head
        newNode.next = head;

        // Update the head to point to the new node
        head = newNode;
    }

    // 3. Delete the first node with the given value from the linked list
    public void deleteByValue(int data) {
        // If list is empty
        if (head == null) {
            return;
        }

        // If head node itself holds the data to be deleted
        if (head.data == data) {
            head = head.next;
            return;
        }

        // Search for the node to be deleted
        Node current = head;
        Node prev = null;

        while (current != null && current.data != data) {
            prev = current;
            current = current.next;
        }

        // If data was not found in the linked list
        if (current == null) {
            return;
        }

        // Unlink the node from the linked list
        prev.next = current.next;
    }

    // 4. Display all elements of the linked list
    public void display() {
        Node current = head;

        if (current == null) {
            System.out.println("List is empty");
            return;
        }

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
