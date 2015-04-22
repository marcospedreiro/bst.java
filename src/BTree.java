/*
 * Created by Marcos on 4/21/15.
 *
 * A simple Binary Search Tree implementation
 * Not thread safe
 *
 */

/* TODO:
    1) Add tests to main for dfs and bfs
    2) Add method descriptions
  */

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BTree {

    private Node root;
    private int size;

    public static void main(String[] args) {
        BTree B = new BTree(5);
        System.out.println("Number of nodes: " + B.getSize());
        System.out.println("Height of Tree: " + B.getHeight());
        System.out.println("Inserting values");
        B.insert(6);
        B.insert(4);
        B.insert(7);
        B.insert(2);
        System.out.println("Height of Tree: " + B.getHeight());
        System.out.println("Number of nodes: " + B.getSize());
        //
        System.out.println(B.toString());
        System.out.println("Sum of elements: " + B.sumValues());
        //
        System.out.println("Search for 2: " + B.search(2));
        System.out.println("Search for 9: " + B.search(9));
        //
        B.remove(2);
        System.out.println(B.toString());
        System.out.println("Height of Tree: " + B.getHeight());
        System.out.println("Sum of elements: " + B.sumValues());
        System.out.println("Search for 2: " + B.search(2));
        System.out.println("Number of nodes: " + B.getSize());
        //
        B.remove(5);
        System.out.println(B.toString());
        System.out.println("Height of Tree: " + B.getHeight());
        System.out.println("Sum of elements: " + B.sumValues());
        System.out.println("Search for 5: " + B.search(5));
        System.out.println("Number of nodes: " + B.getSize());
    }

    public BTree() {
        root = null;
        size = 0;
    }

    public BTree(double v) {
        root = new Node(v);
        size = 1;
    }

    public int getHeight() {
        return heightHelper(root);
    }

    private int heightHelper(Node n) {
        if(n == null) {
            return 0;
        } else {
            return 1 + Math.max(heightHelper(n.getLeft()), heightHelper(n.getRight()));
        }
    }

    public int getSize() {
        return size;
    }

    public void insert(double v) {
        if(root == null) {
            root = new Node(v);
        } else {
            insertHelper(root, v);
        }
        size += 1;
    }

    private void insertHelper(Node n, double v) {
        if(v != n.getValue()) {
            if (v < n.getValue()) {
                if (n.getLeft() == null) {
                    Node lchild = new Node(v);
                    n.setLeft(lchild);
                } else {
                    insertHelper(n.getLeft(), v);
                }
            } else {
                if (n.getRight() == null) {
                    Node rchild = new Node(v);
                    n.setRight(rchild);
                } else {
                    insertHelper(n.getRight(), v);
                }
            }
        }
    }

    public void remove(double v) {
        if(root != null) {
            if(root.getValue() == v) {
                Node tempRoot = new Node(0);
                tempRoot.setLeft(root);
                root.removeNode(v, tempRoot);
                root = tempRoot.getLeft();
            } else {
                root.removeNode(v, null);
            }
            size -= 1;
        }
    }

    public boolean search(double v) {
        return searchHelper(root, v);
    }

    private boolean searchHelper(Node n, double v) {
        if(n == null) {
            return false;
        }

        if(v == n.getValue()) {
            return true;
        } else {
            if(v < n.getValue()) {
                return searchHelper(n.getLeft(), v);
            } else {
                return searchHelper(n.getRight(), v);
            }
        }
    }

    public String toString() {
        return stringHelper(root);
    }

    private String stringHelper(Node n) {
        String result= "";
        if(n == null) {
            return "";
        } else {
            if(n.getLeft() != null) {
                result += stringHelper(n.getLeft()) + ", ";
            }
            if(n.getRight() != null) {
                result += stringHelper(n.getRight()) + ", ";
            }
            result += n.getValue();
            return "["+ result +"]";
        }
    }

    public double sumValues() { return summer(root); }

    private double summer(Node n) {
        if(n == null) {
            return 0;
        } else {
            return summer(n.getLeft()) + summer(n.getRight()) + n.getValue();
        }
    }

    public boolean dfs_preOrder(double v, boolean useIterative) {
        if(useIterative) {
            return preOrder_Iter(root, v);
        }
        return preOrder(root, v);
    }
    private boolean preOrder(Node n, double v) {
        if(n == null) {
            return false;
        } else {
            if(n.getValue() == v) { return true; }

            boolean searchLeft = preOrder(n.getLeft(), v);
            boolean searchRight = preOrder(n.getRight(), v);
            return searchLeft || searchRight;
        }
    }
    private boolean preOrder_Iter(Node n, double v) {
        Stack<Node> parent = new Stack<Node>();
        while(!parent.isEmpty() || n != null) {
            if(n != null) {
                if(n.getValue() == v) { return true; }
                if(n.getRight() != null) { parent.push(n.getRight()); }
                n = n.getLeft();
            } else {
                n = parent.pop();
            }
        }
        return false;
    }

    public boolean dfs_inOrder(double v, boolean useIterative) {
        if(useIterative) {
            return inOrder_Iter(root, v);
        }
        return inOrder(root, v);
    }
    private boolean inOrder(Node n, double v) {
        if(n == null) {
            return false;
        } else {
            boolean searchLeft = preOrder(n.getLeft(), v);

            if(n.getValue() == v) { return true; }

            boolean searchRight = preOrder(n.getRight(), v);
            return searchLeft || searchRight;
        }
    }
    private boolean inOrder_Iter(Node n, double v) {
        Stack<Node> parent = new Stack<Node>();
        while(!parent.isEmpty() || n != null) {
            if(n != null) {
                parent.push(n);
                n = n.getLeft();
            } else {
                n = parent.pop();
                if(n.getValue() == v) { return true; }
                n = n.getRight();
            }
        }
        return false;
    }

    public boolean dfs_postOrder(double v, boolean useIterative) {
        if(useIterative) {
            return postOrder_Iter(root, v);
        }
        return postOrder(root, v);
    }
    private boolean postOrder(Node n, double v) {
        if(n == null) {
            return false;
        } else {
            boolean searchLeft = preOrder(n.getLeft(), v);
            boolean searchRight = preOrder(n.getRight(), v);

            if(n.getValue() == v) { return true; }

            return searchLeft || searchRight;
        }
    }
    private boolean postOrder_Iter(Node n, double v) {
        Stack<Node> parent = new Stack<Node>();
        Node lastVisited = null;
        while(!parent.isEmpty() || n != null) {
            if (n != null) {
                parent.push(n);
                n = n.getLeft();
            } else {
                Node peeked = parent.peek();
                if(peeked.getRight() != null && lastVisited != peeked.getRight()) {
                    // if right child exists AND traversing node from left child, move right
                    n = peeked.getRight();
                } else {
                    if(peeked.getValue() == v) { return true; }
                    lastVisited = parent.pop();
                }
            }
        }
        return false;
    }

    public boolean bfs(double v) {
        return bfsHelper(root, v);
    }
    public boolean bfsHelper(Node n, double v) {
        Queue<Node> q = new ArrayDeque<Node>();
        q.add(n);
        while(!q.isEmpty()) {
            Node curr = q.remove();
            if(curr.getValue() == v) { return true; }

            if(curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if(curr.getRight() != null) {
                q.add(curr.getRight());
            }

        }
        return false;
    }


    public class Node {
        private double value;
        private Node left;
        private Node right;

        public Node() {}

        public Node(double v) { value = v; }

        public Node(double v, Node l, Node r) {
            value = v;
            left = l;
            right = r;
        }

        public void setValue(double v) {
            value = v;
        }
        public void setLeft(Node l) {
            left = l;
        }
        public void setRight(Node r) {
            right = r;
        }
        public double getValue() {
            return value;
        }
        public Node getLeft() {
            return left;
        }
        public Node getRight() {
            return right;
        }

        private boolean removeNode(double v, Node parent) {
            if(v < this.value) {
                if(this.left != null) {
                    return this.left.removeNode(v, this);
                } else {
                    return false;
                }
            } else if(v > this.value) {
                if(this.right != null) {
                    return this.left.removeNode(v, this);
                } else {
                    return false;
                }
            } else {
                if(this.left != null && this.right != null) {
                    this.value = right.minValue();
                    right.removeNode(this.value, this);
                } else if(parent.getLeft() == this) {
                    parent.left = (left != null) ? left : right;
                } else if(parent.right == this) {
                    parent.right = (left != null) ? left : right;
                }
                return true;
            }
        }

        private double minValue() {
            if(this.left == null) {
                return this.value;
            } else {
                return this.left.minValue();
            }
        }


    } // END NODE
} // END BTREE
