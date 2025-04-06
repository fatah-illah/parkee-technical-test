# Data Structures

This project implements a basic Single Linked List data structure in Java without using the built-in List library.

## Features

The implementation includes:
- Node class representing a single node in the linked list
- SingleLinkedList class with the following operations:
    1. `insertAtEnd(int data)`: Adds a new node at the end of the list
    2. `insertAtBeginning(int data)`: Adds a new node at the beginning of the list
    3. `deleteByValue(int data)`: Removes the first node with the specified value
    4. `display()`: Prints all elements in the list

## How to Build and Run

### Using IntelliJ IDEA with Maven
1. Clone or download this repository
2. Open the project in IntelliJ IDEA
3. Navigate to `SingleLinkedListTest.java`
4. Run the main method by clicking the green play button

### Using Command Line with Maven
1. Navigate to the project root directory
2. Compile the Java files:
   ```
   mvn clean package
   ```
3. Run the program using Maven:
   ```
   mvn exec:java
   ```
4. Alternatively, run the JAR file manually:
   ```
   java -cp target/1_data_structures-1.0-SNAPSHOT.jar com.example.SingleLinkedListTest
   ```

## Author

Fatahillah