import java.util.Random;

//-----------------------------------------------------------------
// Agatha Malinowski
// Spring’20 CS 284QB
// Homework Assignment 2
//-----------------------------------------------------------------

//-----------------------------------------------------------------
// A treap is a binary search tree (BST) which additionally 
// maintains heap priorities. A treap can perform the methods
// defined below and takes in the data fields Random
// priorityGenerator and Node<E> root.
//-----------------------------------------------------------------
public class Treap<E extends Comparable>
{
    private Random priorityGenerator;
    private Node<E> root;
    protected boolean addReturn;
    protected E deleteReturn;
    protected E findReturn;

    //-----------------------------------------------------------------
    //    Initialize priorityGenerator, root, and deleteReturn
    //-----------------------------------------------------------------
    public Treap()
    {
        priorityGenerator = new Random();
        root = null;
        deleteReturn = null;
        addReturn = true;
        findReturn = null;

    }

    //-----------------------------------------------------------------
    //    Initialize priorityGenerator(seed), root, and deleteReturn
    //-----------------------------------------------------------------
    public Treap(long seed)
    {
        priorityGenerator = new Random(seed);
        root = null;
        deleteReturn = null;
        addReturn = true;
        findReturn = null;
    }

    //-----------------------------------------------------------------
    //    Adds a node with a given key and randomly generated priority 
    //    into the Treap. Returns addReturn.
    //-----------------------------------------------------------------
    public boolean add(E key)
    {
        root = add(this.root, key, priorityGenerator.nextInt(1000));

        return addReturn;  
    }
    
    //-----------------------------------------------------------------
    //    Adds a node with a given key and priority into the Treap.
    //    Returns addReturn.
    //-----------------------------------------------------------------
    public boolean add(E key, int priority)
    {
        root = add(this.root, key, priority);

        return addReturn;  
    }

    //-----------------------------------------------------------------
    //    Adds a node with a given key and priority to the Treap.
    //    Finds where the node should be added and uses rotateRight and
    //    rotateLeft to place the node into the Treap based on the
    //    given priority
    //
    //    boolean addReturn is set to false only when a node with a 
    //    given key already exists within the Treap. Returns true 
    //    otherwise.
    //-----------------------------------------------------------------
    private Node<E> add(Node<E> localroot,E key, int priority)
    {
        if (localroot == null) {
            // key is not in the tree, insert it.
            addReturn = true;
            return new Node<E>(key, priority);
        } else if (key.compareTo(localroot.data) == 0) 
        {
            // key is equal to localroot.data
            addReturn = false;
            return localroot;
        } else if (key.compareTo(localroot.data) < 0) 
        {
            // key is less than localroot.data
            localroot.left = add(localroot.left, key, priority);
            if (localroot.left.priority < localroot.priority ) {
                localroot = localroot.rotateRight();
            }
            return localroot;
        } else 
        {
            // key is greater than localroot.data
            localroot.right = add(localroot.right, key, priority);
            if (localroot.right.priority < localroot.priority ) {
                localroot = localroot.rotateLeft();
            }
        }
        return localroot;

    }

    //-----------------------------------------------------------------
    //    Remove a node with the given key from the Treap. Does nothing 
    //    to the Treap if a node with the given key is not found.
    //-----------------------------------------------------------------
    public E delete( E key )
    {
        this.root = delete( this.root, key );

        return deleteReturn;
    }

    //-----------------------------------------------------------------
    //    Deletes a node with a given key. Finds the node and uses
    //    rotateRight anf rotateLeft to make that node a leaf, then
    //    deletes the node.
    //
    //    deleteReturn is only set to null only when the gieven key is
    //    not located inside the Treap. deleteReturn equals 
    //    localroot.data otherwise.
    //-----------------------------------------------------------------
    private Node<E> delete(Node<E> localroot,E key)
    {
        int rightPriority;
        int leftPriority;

        if( localroot != null )
        {
            int compareResult = key.compareTo( localroot.data );
            
            // If key is less than localroot.data
            if( compareResult < 0 )
                    localroot.left = delete( localroot.left, key );
                // If key is greater than localroot.data
                else if( compareResult > 0 )
                    localroot.right = delete( localroot.right, key );
                // Key is equal to localroot.data
                else
                {
                    deleteReturn = localroot.data;

                    // If localroot.right exists, rightPriority = localroot.right.priority
                    // Else, localroot.right.priority does not exist and will equal 0
                    if ( localroot.right != null) rightPriority = localroot.right.priority;
                        else rightPriority = 0;

                    // If localroot.left exists, leftPriority = localroot.left.priority
                    // Else, localroot.left.priority does not exist and will equal 0
                    if ( localroot.left != null) leftPriority = localroot.left.priority;
                        else leftPriority = 0;

                    // Rotate to make deleted node a leaf
                    if ( leftPriority < rightPriority ) localroot = localroot.rotateLeft();
                        else  localroot = localroot.rotateRight();
                     
                    if( localroot != null )
                                localroot = delete( localroot, key );
                        else  if( localroot != null) localroot.left = null;
                }
        }
        else deleteReturn = null;

        return localroot;
    }

    //-----------------------------------------------------------------
    //    Searches the Treap to see if a node with the given key is
    //    in the Treap.
    //-----------------------------------------------------------------
    public E find(E key)
    {
        find(this.root, key);
        return findReturn;
    }

    //-----------------------------------------------------------------
    //    Searches through the Treap if the given key is inside of the
    //    Treap. Compares the given key to root.data
    //
    //    findReturn is only set to null when the given key does not
    //    exist within the Treap. Returns root.data otherwise.
    //-----------------------------------------------------------------
    private Node<E> find(Node<E> root, E key)
    {
        if( root != null )
        {
            int compareResult = key.compareTo( root.data );
            
            // If key is less than root.data
            if( compareResult < 0 )
                root.left = find( root.left, key );
            // If key is greater then root.data
            else if( compareResult > 0 )
                root.right = find( root.right, key );
            // If key is equat to root.data
            else
                findReturn = root.data; //Found a node with the same key!
        }
        else findReturn = null;

        return root;
    }
    
    //-----------------------------------------------------------------
    //    The protected static class Node<E> uses the following data
    //    fields to create new nodes and perform right/left rotations 
    //    as nessesary.
    //-----------------------------------------------------------------
    protected static class Node<E extends Comparable>
    {
        public E data;
        public int priority;
        public Node<E> left;
        public Node<E> right;
        
        //-----------------------------------------------------------------
        //    Initialize data fields and check is the value of data is null.
        //    If so, throw an exception.
        //-----------------------------------------------------------------
         public Node(E data, int priority)
         {
             if ((E)data == null)
             {
                 throw new IllegalArgumentException("Data cannot be empty");
             }
             else
             {
                this.data = data;
                this.priority = priority;
                this.left = null;
                this.right = null;
             }
         }

        //-----------------------------------------------------------------
        //    Performs a right roation between nodes within the Treap. This
        //    is used when adding or deleting nodes to reorder the Treap 
        //    correctly.
        //-----------------------------------------------------------------
        public Node<E> rotateLeft()
        {
            Node<E> tmpNode;     

            tmpNode = this.right;
            if (tmpNode != null) 
            {   
                this.right = tmpNode.left;
                tmpNode.left = this;
            }

            return (tmpNode);          
        }

        //-----------------------------------------------------------------
        //    Performs a left roation between nodes within the Treap. This
        //    is used when adding or deleting nodes to reorder the Treap 
        //    correctly.
        //-----------------------------------------------------------------
        public Node<E> rotateRight()
        {
            Node<E> tmpNode;     

            tmpNode = this.left;
            if (tmpNode != null) 
            {
                this.left = tmpNode.right;
                tmpNode.right = this;
            }


            return (tmpNode);   
        }

        //-----------------------------------------------------------------
        //    toString Node Class
        //-----------------------------------------------------------------
        public String toString()
        {
            String keyPriority = "(key = " + data.toString() + ", priority = " + priority + ")";
            return keyPriority;
        }
    }
    //-----------------------------------------------------------------
    //    End Node Class
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //    toString Treap Class
    //-----------------------------------------------------------------
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            preOrderTraverse(root, 1, sb);
            return sb.toString();
        }
        private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb)
        {
            for(int i = 1; i < depth; i++)
            {
                sb.append("  ");
            }
            if (node == null)
            {
                sb.append("null\n");
            } else 
            {
                sb.append(node.toString());
                sb.append("\n");
                preOrderTraverse(node.left, depth + 1, sb);
                preOrderTraverse(node.right, depth + 1, sb);
            }
        }

    //-----------------------------------------------------------------
    //    Main
    //-----------------------------------------------------------------
    public static void main(String[] args) 
    {
        Treap testTree = new Treap();
        
        testTree.add (4,81);
        testTree.add (2,69);
        testTree.add (6,30);
        testTree.add (1,16);
        testTree.add (3,88);
        testTree.add (5,17);
        testTree.add (7,74);
        System.out.println(testTree);
    }
}

