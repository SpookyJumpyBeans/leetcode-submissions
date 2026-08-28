// 2276. Count Integers in Intervals
// https://leetcode.com/problems/count-integers-in-intervals/
// Hard | Java | Accepted 2026-08-13
// Runtime 80 ms | Memory 126 MB

class CountIntervals {
    TreeMap<Integer, Integer> treemap = new TreeMap<>();
    int count = 0;
    public CountIntervals() {
    }
    
    public void add(int left, int right) {
        // Find any interval that starts at or before `right` 
        // and could potentially overlap with [left, right]
        Map.Entry<Integer, Integer> entry = treemap.floorEntry(right);
        
        while (entry != null && entry.getValue() >= left) {
            int start = entry.getKey();
            int end = entry.getValue();
            
            // Expand our new interval to absorb the overlapping one
            left = Math.min(left, start);
            right = Math.max(right, end);
            
            // Subtract the old interval's length from total count and remove it
            count -= (end - start + 1);
            treemap.remove(start);
            
            // Look for the next potential overlap
            entry = treemap.floorEntry(right);
        }
        
        // Insert the fully merged interval and add its length
        treemap.put(left, right);
        count += (right - left + 1);
    }
    
    public int count() {
       return count;
    }
}

/**
 * Your CountIntervals object will be instantiated and called as such:
 * CountIntervals obj = new CountIntervals();
 * obj.add(left,right);
 * int param_2 = obj.count();
 */
