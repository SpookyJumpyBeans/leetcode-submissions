// 706. Design HashMap
// https://leetcode.com/problems/design-hashmap/
// Easy | Java | Accepted 2026-09-01
// Runtime 33 ms | Memory 57.7 MB

class MyHashMap {
    int[] map = new int[1000001];
    public MyHashMap() {
        Arrays.fill(map, -1);
    }
    
    public void put(int key, int value) {
        map[key] = value;
    }
    
    public int get(int key) {
        return map[key] == -1 ? -1 : map[key];
    }
    
    public void remove(int key) {
        map[key] = -1;
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
