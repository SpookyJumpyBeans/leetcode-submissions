// 715. Range Module
// https://leetcode.com/problems/range-module/
// Hard | Java | Accepted 2026-10-13
// Runtime 41 ms | Memory 55.8 MB

class RangeModule {
    // TreeMap is used to keep the intervals sorted by their start times (keys).
    // Key: left bound of the interval.
    // Value: right bound of the interval.
    // The intervals stored are strictly disjoint (non-overlapping).
    TreeMap<Integer, Integer> intervals = new TreeMap<>();
    
    public RangeModule() {
        
    }
    
    // HARDEST PROBLEM EVER
    // SO MANY EDGE CASES
    
    public void addRange(int left, int right) {
        // Step 1: Handle potential overlap with the interval that starts just before or exactly at 'left'.
        // floorKey(left) returns the greatest key <= left, or null if no such key exists.
        Integer lower = intervals.floorKey(left); 
        
        // If such an interval exists and its right bound extends into or beyond our new 'left', they overlap.
        if(lower != null && intervals.get(lower) >= left) {
            // Merge them: update 'right' to the maximum of both right bounds.
            right = Math.max(intervals.get(lower), right);
            // Remove the old interval since we are merging it into our new [left, right) range.
            intervals.remove(lower);
            // Expand our new range's start point to include the lower interval.
            left = lower;
        }
        
        // Step 2: Handle overlaps with intervals that start at or after our (potentially updated) 'left'.
        // ceilingKey(left) returns the least key >= left.
        Integer higher = intervals.ceilingKey(left);
        
        // While there is a next interval and it starts before or exactly at our current 'right' boundary...
        while(higher != null && right >= higher) {
            // Merge them by extending our 'right' bound if the existing interval goes further.
            right = Math.max(right, intervals.get(higher));
            // Remove the overlapping interval since it's now subsumed.
            intervals.remove(higher);
            // Look for the next interval to potentially merge.
            higher = intervals.ceilingKey(left);
        }
        
        // Step 3: Insert the final, fully merged, disjoint interval into the map.
        intervals.put(left, right);
    }
    
    public boolean queryRange(int left, int right) {
        // Find the interval that starts just before or at 'left'.
        // Because our intervals are disjoint and fully merged, if the requested [left, right) 
        // range is fully covered, it MUST be contained entirely within this specific interval.
        Integer lower = intervals.floorKey(left);
        
        // Return true if such an interval exists AND its end boundary is >= the queried 'right'.
        return lower != null && intervals.get(lower) >= right;
    }
    
    public void removeRange(int left, int right) {
        // Step 1: Check if the interval starting before or at 'left' needs to be truncated or split.
        Integer lower = intervals.floorKey(left);
        
        if(lower != null) {
            int further = intervals.get(lower); // The right bound of the 'lower' interval
            
            if(lower == left) {
                // Case 1A: The interval starts exactly where our removal starts.
                intervals.remove(lower);
                if(further > right) {
                    // If it extends beyond the removal zone, keep the remaining right part.
                    intervals.put(right, further);
                }
            }
            else if(further > left && further > right) {
                // Case 1B: The removal zone is completely inside the 'lower' interval.
                // We need to split the existing interval into two pieces: a left part and a right part.
                intervals.put(lower, left);         // Keep the left part
                intervals.put(right, further);      // Keep the right part
                return; // Since it completely swallowed the removal range, we are done.
            }
            else if(further > left) {
                // Case 1C: The interval overlaps with the beginning of the removal zone.
                // Truncate the right side of the interval up to 'left'.
                intervals.put(lower, left);
            }
        }
        
        // Step 2: Clean up any intervals that start AFTER 'left' but are affected by the removal zone.
        // higherKey(left) returns the least key strictly greater than 'left'.
        Integer higher = intervals.higherKey(left);
        
        // Loop while the next interval starts strictly before our removal 'right' boundary.
        while(higher != null && higher < right) {
            if(intervals.get(higher) > right) {
                // Case 2A: The interval extends beyond the removal zone's right boundary.
                // Remove the old interval and keep only the part that falls after 'right'.
                int further = intervals.get(higher);
                intervals.remove(higher);
                intervals.put(right, further);
                return; // We reached the end of the removal zone, so we can exit.
            }
            // Case 2B: The interval is completely swallowed by the removal zone.
            intervals.remove(higher);
            // Move on to check the next interval.
            higher = intervals.higherKey(left);
        }
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
