// 460. LFU Cache
// https://leetcode.com/problems/lfu-cache/
// Hard | Java | Accepted 2026-08-22
// Runtime 72 ms | Memory 137.1 MB

class LFUCache {
    //Use the same node class from LRU Cache, but add an additional field, the frequency field, which keeps track of how many times this key value node has been accessed
    class Node
    {
        Node prev;
        Node next;
        int key;
        int val;
        int freq;
        public Node(int key, int value)
        {
            this.key = key;
            this.val = value;
            this.freq = 1; //Set it by default to 1 as when creating the node, this is the first time accessing it
        }
    }

    class DoublyLinkedList //In LFU cache, to ensure we can invalidate the least frequently used key while also maintaining the least recently used order, we can make a doubly linked list class that is mapped to every possible frequency
    {
        Node head; //Keep the head and tail nodes of the doubly linked list
        Node tail;
        public DoublyLinkedList() //Instantiate the doubly linked list 
        {
            head = new Node(0,0);
            tail = new Node(0,0);
            head.next = tail;
            tail.prev = head;
        }

        public Node addNode(Node newNode) //Have add and remove node methods like LRU cache but in the DLL class
        //addNode adds the node to the front of the DLL, thereby making it the most recently accessed element
        {
            newNode.next = head.next;
            head.next.prev = newNode;
            head.next = newNode;
            newNode.prev = head;
            return newNode;
        }

        public void removeNode(Node node) //Remove the node when we want to update the recency of a node or to delete a node due to the eviction policy
        //First remove it from its old recency position and then add it to the front 
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    int capacity; //The capacity of the cache
    int minFreq; //This keeps track of what the least frequently used frequency is so that we can easily call the map with this key and retrieve the DLL for that specific frequency
    //Easily remove the least recently used node from that list with the tail.prev
    Map<Integer, Node> map; //Maps key to a key value node
    Map<Integer, DoublyLinkedList> fre; //Maps a frequency to a DLL of nodes that have been accessed that many times

    public LFUCache(int capacity) { 
        this.capacity = capacity; //Set capacity to the capacity given
        map = new HashMap<>();
        fre = new HashMap<>();
    }
    
    public int get(int key) { 
        if(!map.containsKey(key)) //First check if the map has the key
        //If it doesn't immediately return -1
        {
            return -1;
        }
            Node temp = map.get(key); //Get the key value node from the map with the key
            removeIfEmpty(temp); //Remove if the DLL only contains this one node
            //This means that since we just accessed this node, its frequency increases by 1
            //If the DLL associated with the frequency before only had this one node, removing this node means the DLL is empty
            //We need to remove the key value pair of frequency and DLL from the map if the DLL is empty
            temp.freq++; //Add 1 to the node's frequency var
            fre.computeIfAbsent(temp.freq, k -> new DoublyLinkedList()).addNode(temp); //This basically creates a new frequency DLL entry if this is a frequency that hasn't been reached yet or simply adds the node to the start of the DLL associated with this new frequency since this node is now the most recently accessed node
            return temp.val;
    }
    
    public void put(int key, int value) {
        Node temp;
        if(map.size()==capacity && !map.containsKey(key)) //First check if the cache is full and make sure the cache doesn't already contain the key
        //If it does, we don't have to evict
        {
            temp = fre.get(minFreq).tail.prev; //Get the last node in the DLL associated with the minimum frequency as it's the least recently/frequently accessed element
            map.remove(temp.key); //Remove it from the map
            removeIfEmpty(temp); //If this was the only node in the DLL, then also remove increase the minimum frequency by 1 and remove it from the frequency map
        }
        if(!map.containsKey(key)) //If the map doesn't contain this key value pair
        {
            temp = new Node(key, value); //Make a new node
            map.put(key, temp); //Put it in the map
            fre.computeIfAbsent(temp.freq, k -> new DoublyLinkedList()).addNode(temp); //Add it to the right frequency key or create it if it doesn't exist and add that node to the front
            minFreq = 1; //Set the minFreq to 1 since adding a new node to the map always makes the minFreq 1
            return;
        }
        temp = map.get(key); //Else if the map already contains this node
        temp.val = value; //Set the node's value to this new value //Add it back to the map
        removeIfEmpty(temp);
        temp.freq++;
        fre.computeIfAbsent(temp.freq, k -> new DoublyLinkedList()).addNode(temp);
    }

    public void removeIfEmpty(Node temp) //Removes a frequency DLL pair if the DLL is empty
    {
        fre.get(temp.freq).removeNode(temp); //First remove the node from the DLL associated with the node's frequency
        DoublyLinkedList t = fre.get(temp.freq);
        if(t.head.next==t.tail) //If the DLL's head's next pointer points to the DLL's tail, we know this DLL is empty
        {
            if(temp.freq==minFreq) //If this node's frequency was the minimum frequency before, we need to add 1 to the minimum frequency as the temp.freq is incrasing by 1
            {
                minFreq++;
            }
            fre.remove(temp.freq); //Now remove this pair from the frequency map
        }
    }
}


/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
