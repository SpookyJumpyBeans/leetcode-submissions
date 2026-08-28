// 981. Time Based Key-Value Store
// https://leetcode.com/problems/time-based-key-value-store/
// Medium | Java | Accepted 2026-08-10
// Runtime 164 ms | Memory 108.9 MB

class TimeMap {
    private Map<String, TreeMap<Integer, String>> map;
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
       map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        TreeMap<Integer, String> tm = map.get(key);
        if (tm == null) return "";
        Integer e = tm.floorKey(timestamp);
        return e == null ? "" : tm.get(e);
        }
    }


/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
