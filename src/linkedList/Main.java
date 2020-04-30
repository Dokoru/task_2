package linkedList;

import linkedList.util.SwingUtils;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
        Test();
    }

    public static void Test() throws Exception {
        LinkedList l = new LinkedList();
        l.addLast(2);
        l.addLast(9);
        l.addLast(0);
        l.addLast(5);
        l.addLast(2);
        l.addLast(5);
        l.addLast(2);
        System.out.println(l.toStringBuffer());
        System.out.println(l.getSize());
        l.addFirst(7);
        System.out.println(l.toStringBuffer());
        l.addAtIndex(9,1);
        l.addAtIndex(3,6);
        System.out.println(l.toStringBuffer());
        l.deleteFirst(7);
        System.out.println(l.toStringBuffer());
        l.deleteLast(9);
        System.out.println(l.toStringBuffer());
        l.deleteAtIndex(3);
        System.out.println(l.toStringBuffer());
        System.out.println(l.getFirst());
        System.out.println(l.getLast());
        System.out.println(l.getAtIndex(2));
        System.out.println(l.contains(3));
        System.out.println(l.contains(7));
        System.out.println(l.firstIndexOf(2));
        System.out.println(l.lastIndexOf(2));
        l.set(4, 4);
        System.out.println(l.toStringBuffer());
        l.replaceFirstMinLastMax();
        System.out.println(l.toStringBuffer());
        l.clear();
        System.out.println(l.toStringBuffer());
    }
}
