// 146. LRU Cache
// https://leetcode.com/problems/lru-cache/
// Medium | Java | Accepted 2026-08-20
// Runtime 16 ms | Memory 58.8 MB

class LRUCache {
    private class Node { //Create a custom Node class because the intution is that we keep a map that maps a key to a node 
    //Store the key and value of the node so we can remove nodes using the key
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int value) { //Construct the node
            this.key = key;
            val = value;
        }
    }

    int capacity; 
    Map<Integer, Node> map;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        //The intuition is that we keep a doubly linked list of the key value nodes in the cache
        //The order of the doubly linked list is the order of recency
        //Head of list is most recently accessed node
        //Based on whether we get or put, we want to move nodes around and remove nodes
        this.capacity = capacity; //Set capacity to the capacity of the cache
        map = new HashMap<>();
        head = new Node(0, 0); //Make dummy head and tail nodes so we don't have to deal with null pointers
        tail = new Node(0, 0);
        head.next = tail; //Initially set head next to tail and vice verse
        tail.prev = head;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) //First check if the cache has this key, if it doesn't return -1
        {
            return -1;
        }
        Node node = map.get(key); //Else, we want to get the node by calling the map with its key
        remove(node); //We want to remove the node from wherever it was before in the doubly linked list
        return moveNodeFront(node).val; //Add it to the very beginning since it's most recently used
    }
    
    public void put(int key, int value) { 
        if(map.containsKey(key)) //For put, first check if the map contains the key, if it does, we just want to update the nodes value and then remove the node from wherever it was in the list before and move it to the front of the order
        { 
            Node node = map.get(key);
            node.val = value;
            remove(node);
            moveNodeFront(node);
            return;
        }
        if(map.size()==capacity) //If the cache is currently at max capacity, we need to evict the least recently used node
        {
            map.remove(tail.prev.key); //Do this by removing the node associated with the tail.prev's key first from the map
          remove(tail.prev); //Then remove the node from the doubly linked list
        }
        Node node = new Node(key, value); //Now that we definitely have room for this node, make the new node
        map.put(key, node); //Put it in the map
        moveNodeFront(node); //And then move it to the front as its the most recently accessed 
    }

    public Node moveNodeFront(Node node) 
    {
        node.next = head.next; //First set the node's next to be the current first node
        head.next.prev = node; //Set the current first node's prev to be the new recent
        head.next = node; //Set the head's next to point to this new recent
        node.prev = head; //Set the node's prev to point to the head
        return node;
    }

    public void remove(Node node)
    {
        node.prev.next = node.next; //Simply set the pointers of the nodes directly before and after the node we want to remove to each other
        node.next.prev = node.prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */ 
