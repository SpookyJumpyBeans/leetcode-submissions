// 705. Design HashSet
// https://leetcode.com/problems/design-hashset/
// Easy | Java | Accepted 2026-09-01
// Runtime 12 ms | Memory 54.5 MB

class MyHashSet {
    boolean[] arr = new boolean[1000001];
    public MyHashSet() {

    }
    
    public void add(int key) {
        if(!arr[key])
        {
            arr[key] = true;
        }
    }
    
    public void remove(int key) {
        arr[key] = false;
    }
    
    public boolean contains(int key) {
        return arr[key];
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
