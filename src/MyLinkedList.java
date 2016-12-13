import java.util.Iterator;

/**
 * Created by lWeRl on 13.12.2016.
 */
public class MyLinkedList<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int length;
    private int half_length;

    MyLinkedList() {
        first = null;
        last = null;
        length = 0;
        half_length = 0;
    }

    private Node<T> search(int pos) {
        Node<T> temp;
        if (pos <= half_length) {
            temp = first;
            for (int i = 0; i < pos; i++) {
                temp = temp.getNext();
            }
        } else {
            temp = last;
            for (int i = length - 1; i > pos; i--) {
                temp = temp.getPrev();
            }
        }
        return temp;
    }

    public T getFirst() {
        return first.getValue();
    }

    public T getLast() {
        return last.getValue();
    }

    public int length() {
        return length;
    }

    public void add(T value) {
        Node<T> temp = new Node<>(value);
        if (first == null) {
            temp.setNext(null);
            temp.setPrev(null);
            first = temp;
            last = temp;
            length++;
        } else {
            temp.setPrev(last);
            temp.setNext(null);
            last.setNext(temp);
            last = temp;
            length++;
            half_length = length / 2;
        }
    }

    public void insert(T value, int pos) {
        if (pos < 0 || pos > length - 1) throw new IndexOutOfBoundsException(("" + pos));
        Node<T> temp = new Node<>(value);
        Node<T> curr = search(pos);
        temp.setPrev(curr.getPrev());
        temp.setNext(curr);
        if (curr != first) curr.getPrev().setNext(temp);
        else first = temp;
        curr.setPrev(temp);
        length++;
        half_length = length / 2;
    }

    public void delete(int pos) {
        if (pos < 0 || pos > length - 1) throw new IndexOutOfBoundsException(("" + pos));
        Node<T> temp = search(pos);
        if (temp == first) {
            first = temp.getNext();
            first.setPrev(null);
            temp.setNext(null); //destroy all links
        } else if (temp == last) {
            last = temp.getPrev();
            last.setNext(null);
            temp.setPrev(null);//destroy all links
        } else {
            temp.getPrev().setNext(temp.getNext());
            temp.getNext().setPrev(temp.getPrev());
            temp.setNext(null);//destroy all links
            temp.setPrev(null);
        }
    }

    public void reverse() {
        if (length == 0) return;
        Node<T> curr = first;
        Node<T> temp;
        last = curr;
        while (true) {
            temp = curr.getNext();
            curr.setNext(curr.getPrev());
            curr.setPrev(temp);
            if (curr.getPrev() == null) break;
            curr = temp;
        }
        first = curr;
    }

    public T get(int pos) {
        if (pos < 0 || pos > length - 1) throw new IndexOutOfBoundsException(("" + pos));
        return search(pos).getValue();
    }

    public void update(T value, int pos) {
        if (pos < 0 || pos > length - 1) throw new IndexOutOfBoundsException(("" + pos));
        search(pos).setValue(value);
    }

    @Override
    public Iterator iterator() {
        return new MyLinkedListIterator(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MyLinkedListIterator implements Iterator<T> {
        MyLinkedList<T> list;
        boolean newFlag = true;


        MyLinkedListIterator(MyLinkedList<T> list) {
            this.list = list;
        }

        private Node<T> temp;

        @Override
        public boolean hasNext() {
            if (length == 0) return false;
            if (newFlag) return true;
            return (temp.getNext() != null);
        }

        @Override
        public T next() {
            if (newFlag) {
                newFlag = false;
                temp = first;
            } else {
                temp = temp.getNext();
            }
            return temp.getValue();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.value = value;
            next = null;
            prev = null;
        }

        T getValue() {
            return value;
        }

        void setValue(T value) {
            this.value = value;
        }

        Node<T> getNext() {
            return next;
        }

        void setNext(Node<T> next) {
            this.next = next;
        }

        Node<T> getPrev() {
            return prev;
        }

        void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}
