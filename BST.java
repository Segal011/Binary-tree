import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import static jdk.nashorn.internal.objects.NativeArray.push;


public class BST<E extends Comparable<E>>  {

    protected BstNode<E> root = null;
    protected int size = 0;

    protected Comparator<? super E> c = null;

    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<T> */
    public BST() {
        this.c = (e1, e2) -> e1.compareTo(e2);
    }

    //sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<T>
    public BST(Comparator<Object> c) {
        this.c = c;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }
        return get(element) != null;
    }

    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, root);
    }
    private BstNode addRecursive(E element, BstNode<E> root) {
        if (root == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, root.element);

        if (cmp < 0) {
            root.left = addRecursive(element, root.left);
        } else if (cmp > 0) {
            root.right = addRecursive(element, root.right);
        }

        return root;
    }

    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, root);
    }
    private BstNode removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            BstNode<E> nodeMax = getMax(node.left);
            node.element = nodeMax.element;
            node.left = removeMax(node.left);
            size--;
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    public E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException( "Element is null in get(E element)" );
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare( element, node.element );

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    //Visualization - something what I cannot and donl't want to understand
    public String toVisualizedString() {
        horizontal = term[0] + term[0];
        return root == null ? new StringBuilder().append(">").append(horizontal).toString()   //BOX DRAWINGS LIGHT HORIZONTAL
                : toTreeDraw(root, ">", "");
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                node.element.toString()).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step));
        return sb.toString();
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    protected class BstNode<N> {
        protected N element;
        protected BstNode left;
        protected BstNode right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }

    private class Iterator {
        private Stack<BstNode<E>> stack = new Stack<>();

        public Iterator( BstNode<E> root) {
            push(root);
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        public BstNode<E> next() {
            BstNode<E> node = stack.pop();
            push(node.right);
            return node;
        }
        private  void push(BstNode<E>  node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
}
