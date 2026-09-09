// 641. Design Circular Deque
// https://leetcode.com/problems/design-circular-deque/
// Medium | Java | Accepted 2026-09-08
// Runtime 4 ms | Memory 46.5 MB

class MyCircularDeque {
    private final int[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int size;

    public MyCircularDeque(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.front = 0; //Front is inclusive, this pointer points to the exact front element of the deque
        this.rear = 0; //Rear is exclusive, this pointer points to the element before the actual rear of the deque
        this.size = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) return false; //If the deque is full, immediately return false
        // Move front backward circularly
        front = (front - 1 + capacity) % capacity; //Basically, we want to move the front and rear pointers away from each other to simulate the wrapping around of the circular deque
        //Since the front pointer pointers to the exact value that's the front of the deque, we first move it back, since now whatever index we set equal to value is going to be the index that front points to
        //The array is only full once the front and rear pointers wrap around and touch each other again
        data[front] = value;
        size++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) return false;
        // Place at rear, then advance rear circularly
        data[rear] = value; //For rear, we put the value in first at rear, since rear is going to point to the position right before the value at the rear
        //So setting the value at the rear and then incrementing it does this exclusivity
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) return false; //To delete at the front, just move the front pointer forward, which is lazy deletion
        //This doesn't actually replace the value at the front pointer, but simply makes it so that the value at the front pointer is inaccessible
        //After incrementing the front pointer, we can't access the value that's at front, unless we add another value at front (which will overwrite this outdated value)
        front = (front + 1) % capacity;
        size--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) return false;
        rear = (rear - 1 + capacity) % capacity; //For deleting from the rear, do the same thing
        //Since the rear is exclusive, it will end back up on the value that it just set, but since its exclusive, when we add to the rear, it will first overwrite this value and then move the pointer forward
        size--;
        return true;
    }
    
    public int getFront() {
        return isEmpty() ? -1 : data[front]; //Get the value at the front pointer
    }
    
    public int getRear() {
        if (isEmpty()) return -1;
        // Rear points to the next insertion slot, so the last element is at (rear - 1)
        return data[(rear - 1 + capacity) % capacity]; //Account for rear's exclusivity
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean isFull() {
        return size == capacity;
    }
}
