// 732. My Calendar III
// https://leetcode.com/problems/my-calendar-iii/
// Hard | Java | Accepted 2026-09-03
// Runtime 135 ms | Memory 47 MB

class MyCalendarThree {
    TreeMap<Integer, Integer> diff = new TreeMap<>();
    public MyCalendarThree() { 
    }
    
    public int book(int startTime, int endTime) {
        diff.put(startTime, diff.getOrDefault(startTime, 0)+1);
        diff.put(endTime, diff.getOrDefault(endTime, 0)-1);
        int count = 0;
        int ans = 1;
        for(Map.Entry<Integer, Integer> e : diff.entrySet())
        {
            count += e.getValue(); 
            ans = Math.max(count, ans);
        }
        return ans;
    }
}

/**
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(startTime,endTime);
 */
