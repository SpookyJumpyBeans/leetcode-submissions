// 202. Happy Number
// https://leetcode.com/problems/happy-number/
// Easy | Java | Accepted 2026-01-21
// Runtime 4 ms | Memory 43.7 MB

class Solution {
    public boolean isHappy(int n) {
        String val = Integer.toString(n);
        long sum = n;
        if(n==1)
        {
            return true;
        }
        Map<Long, Integer> map = new HashMap<>();
        while(true)
        {
            val = Long.toString(sum);
            sum = 0;
             for(int i = 0; i<val.length(); i++)
        {
            sum += Long.parseLong(val.substring(i,i+1))*Long.parseLong(val.substring(i,i+1));
        }
        if(sum==1)
        {
            return true;
        }
        if(map.containsKey(sum))
        {
            return false;
        }
        map.put(sum, 1);
        }
    }
}
