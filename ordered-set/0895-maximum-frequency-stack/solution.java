// 895. Maximum Frequency Stack
// https://leetcode.com/problems/maximum-frequency-stack/
// Hard | Java | Accepted 2026-08-30
// Runtime 35 ms | Memory 70.5 MB

class FreqStack {
    Map<Integer, Integer> freq = new HashMap<>();
    Map<Integer, Stack<Integer>> groupFreq = new HashMap<>();
    int mostFrequent = 0;
    public FreqStack() {
        
    }
     // freq map (5, 1) (7, 1) (4, 0)
     // Group freq map  (1, [5, 7) (2, []) (3, [])
     //mostFrequent 1
    public void push(int val) {
        freq.put(val, freq.getOrDefault(val, 0)+1);
        groupFreq.computeIfAbsent(freq.get(val), k -> new Stack<>()).push(val);
        mostFrequent = Math.max(mostFrequent, freq.get(val));
    }
    
    public int pop() {
        int mostFreq = groupFreq.get(mostFrequent).pop();
        if(groupFreq.get(mostFrequent).isEmpty())
        {
            mostFrequent--;
        }
        freq.put(mostFreq, freq.get(mostFreq)-1);
        return mostFreq;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
