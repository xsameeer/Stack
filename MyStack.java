package cmsc256;
/*************
 *Class name: MyStack
 *Class Description: This class implements a Stack Interface and a linked node Stack using a generic
 * Node<E> object. It runs a static method that detects whether an html file has balanced tags.
 --------------------------------------------------------------------------------------------
 *Name: Sameer Ali
 *Version date: 03/22/2024
 *CMSC 256 901
 ***************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

public class MyStack<E> implements StackInterface<E>{
    private Node top;
    private int size;

    public MyStack(){
        clear();
    }

    /** Adds a new entry to the top of this stack.

     @param newEntry  an object to be added to the stack. */
    public void push(E newEntry){
        if (newEntry==null){
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(newEntry);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /** Removes and returns this stack's top entry.

     @return  the object at the top of the stack.

     @throws  java.util.EmptyStackException if the stack is empty. */

    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E data = top.getData();
        top = top.getNextNode();
        size--;
        return data;
    }

    /** Retrieves this stack's top entry.

     @return  the object at the top of the stack.

     @throws  java.util.EmptyStackException if the stack is empty.  */

    public E peek(){
    if (isEmpty()){
        throw new EmptyStackException();
    }
    return top.getData();
    }

    /** Detects whether this stack is empty.

     @return  True if the stack is empty. */

    public boolean isEmpty(){
        if(top == null){
            return true;
        }else if(size == 0){
            return true;
        }
        return false;
    }

    /** Removes all entries from this stack. */

    public void clear(){
        top = null;
        size = 0;
    }

    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        //throws exception if file does not exist or pathname does not exist
        if (!webpage.exists()) {
            throw new FileNotFoundException();
        }
        //throws exception if webpage is empty or file can't be read
        if (webpage.length() == 0 || !webpage.canRead()) {
            throw new IllegalArgumentException("Unable to open the file.");
        }

        //Create an instance of MyStack called stack
        MyStack<String> stack = new MyStack<>();
        //Creates a scanner object to read input from the file
        Scanner in = new Scanner(webpage);
        // Checks whether file is empty and continues loop when there is a next line
        while (in.hasNextLine()) {
            //initializes a String called line with the next line in the file
            String line = in.nextLine().trim();
            char[] charArray = line.toCharArray();
            // Runs through each character in the String line
            for (int index = 0; index < charArray.length; index++) {
                char c = charArray[index];
                if (c == '<') {
                    String htmlTag = "<";
                    // when a beginning tag is found, a for loop is created to add all the values between the opening and closing tag to the string htmlTag
                    for (int i = index + 1; i < line.length(); i++) {
                        //each character after the beginning tag character is stored in c as the loop iterates
                        c = line.charAt(i);
                        //the tag is stored in String htmlTag
                        htmlTag += c;
                        //when the end tag is reached, it breaks out of the for loop
                        if (c == '>') {
                            break;
                        }
                    }
                    // checks if htmlTag doesn't contain a '/', denoting that it is not a closing tag
                    if (!htmlTag.contains("/")) {
                        //if htmlTag is a opening tag, it is pushed onto the stack while removing the beginning and end tag
                        stack.push(htmlTag.substring(1, htmlTag.length() - 1));
                    //if htmlTag contains a '/'
                    } else {
                        // This checks if stack is empty which denotes that the opening tag is missing
                        if (stack.isEmpty()) {
                            System.out.println("Stack is empty, HTML file is unbalanced.");
                            return false;
                        }
                        //removes the top opening tag
                        String removedData = stack.pop();
                        //beginning and end of HTML tags are added to the opening tag
                        String closingTag = "</" + removedData + ">";
                        // comparing if html tag name are the same to make sure tags are not intersecting or unbalanced
                        if (!htmlTag.equals(closingTag)) {
                            System.out.println("Tags do not match, unbalanced.");
                            return false;
                        }
                    }
                }
            }

        }
        return stack.isEmpty();
    }
    private class Node
    {
        private E data; // Entry in stack
        private Node next; // Link to next node
        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }
        private Node(E dataPortion, Node linkPortion)
        {
            data = dataPortion;
            next = linkPortion;
        }
        private E getData()
        {
            return data;
        }
        private void setData(E newData)
        {
            data = newData;
        }
        private Node getNextNode()
        {
            return next;
        }
        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        }
    }
}

