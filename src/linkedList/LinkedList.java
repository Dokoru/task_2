package linkedList;

public class LinkedList {

    public LinkedList(Node head) {
        this.head = head;
        head.next = null;
        this.last = head;
        this.size = 0;
    }

    public LinkedList() {
        this.head = null;
        this.last = null;
        this.size = -1;
    }

    private class Node {
        private int value;
        private Node next;

        Node(int value) {
            this.value = value;
            next = null;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node head;
    private Node last;
    private int size;

    public void addLast(int value) {
        Node newNode = new Node (value);
        newNode.next = null;
        if (head == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size ++;
    }

    public void addFirst(int value) {
        Node newNode = new Node (value);
        if (head == null) {
            last = newNode;
        }
        newNode.next= head;
        head = newNode;
        size++;
    }

    public void addAtIndex(int value, int index) throws Exception {
        if (index > size + 1) {
            throw new Exception("IncorrectLargeIndex");
        }
        if (index < 0) {
            throw new Exception("IncorrectSmallIndex");
        }
        Node newNode = new Node(value);
        if (index == size) {
            addLast(value);
        }
        if (index == 0) {
            addFirst(value);
        }
        else {
            Node current = head;
            int i = 0;
            while (i != index - 1) {
                current = current.next;
                i++;
            }
            Node next = current.next;
            current.next = newNode;
            newNode.next = next;
        }
        size++;
    }

    public void deleteFirst(int value) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                if (previous == null) {
                    head = head.next;
                    if (head == null) {
                        last = null;
                    }
                } else {
                    previous.next = current.next;
                    if (current.next == null) {
                        last = previous;
                    }
                }
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    public void deleteLast(int value) {
        Node previous = null;
        Node current = head;
        Node currentTemp = null;
        Node previousTemp = null;
        while (current != null) {
            if (current.value == value) {
                currentTemp = current;
                previousTemp = previous;
            }
            previous = current;
            current = current.next;
        }
        if (previousTemp == null) {
            head = head.next;
            if (head == null) {
                last = null;
            }
        } else {
            previousTemp.next = currentTemp.next;
            if (currentTemp.next == null) {
                last = previousTemp;
            }
        }
    }

    public void deleteAtIndex(int index) throws Exception {
        if (index > size) {
            throw new Exception("IncorrectLargeIndex");
        }
        if (index < 1) {
            throw new Exception("IncorrectSmallIndex");
        }
        Node previous = null;
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                previous.next = current.next;
            }
            previous = current;
            current = current.next;
        }
        size--;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        head = null;
        last = null;
        size = -1;
    }

    public int getFirst() {
        return head.value;
    }

    public int getLast() {
        return last.value;
    }

    public int getAtIndex(int index) throws Exception {
        if (index > size) {
            throw new Exception("IncorrectLargeIndex");
        }
        if (index < 0) {
            throw new Exception("IncorrectSmallIndex");
        }
        Node current = head;
        int i = 0;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current.value;
    }

    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int firstIndexOf(int value) {
        Node current = head;
        for (int i = 0; i <= size; i++) {
            if (current.value == value) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    public int lastIndexOf(int value) {
        Node current = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (current.value == value) {
                index = i;
            }
            current = current.next;
        }
        return index;
    }

    public void set(int value, int index) throws Exception {
        if (index > size) {
            throw new Exception("IncorrectLargeIndex");
        }
        if (index < 0) {
            throw new Exception("IncorrectSmallIndex");
        }
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                current.value = value;
            }
            current = current.next;
        }
    }

    public int[] toArray() {
        int[] arr = new int [size + 1];
        Node current = head;
        if (head == null) {
            return arr;
        }
        for (int i = 0; i <= size; i++) {
            arr[i] = current.value;
            current = current.next;
        }
        return arr;
    }

    public StringBuffer toStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        Node current = head;
        if (head == null) {
            return stringBuffer;
        }
        while (current != null) {
            stringBuffer.append(current.value);
            current = current.next;
        }
        return stringBuffer;
    }

    public void toLinkedList(int[] array) {
        clear();
        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                addLast(array[i]);
            }
        }
    }

    public void replaceFirstMinLastMax() {
        if (head == null) {
            return;
        }
        Node current = head.next, previous = head, max = head, previousMax = null, nextMax = max.next,
                min = head, previousMin = null, nextMin = min.next;
        while (current != null) {
            if (current.value < min.value) {
                min = current;
                previousMin = previous;
                nextMin = current.next;
            }
            if (current.value >= max.value) {
                max = current;
                previousMax = previous;
                nextMax = current.next;
            }
            previous = current;
            current = current.next;
        }
        if (previousMax == null) {
            head = min;
        } else {
            previousMax.next = min;
        }
        if (previousMin == null) {
            head = max;
        } else {
            previousMin.next = max;
        }
        max.next = nextMin;
        min.next = nextMax;
    }
}
