public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public class LinkedList {
        private Node head;
        private Node tail;
        private int size = 0;
        private class Node {
            private Object data;
            private Node next;
            public Node (Object data) {
                this.data = data;
                this.next = null;
            }
            public String toString(){
                return String.valueOf(data);
            }

        }
    }
}
