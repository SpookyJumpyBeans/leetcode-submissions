// 2131. Longest Palindrome by Concatenating Two Letter Words
// https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/
// Medium | Java | Accepted 2026-08-10
// Runtime 59 ms | Memory 92.8 MB

class Solution {
    public int longestPalindrome(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for(String w : words)
        {
          map.put(w, map.getOrDefault(w, 0)+1);  //Use map to get counts of every word
        }
        int ans = 0;
        boolean flag = false;
        Set<String> used = new HashSet<>(); //Use set to keep track of used pairs
        for(Map.Entry<String, Integer> m : map.entrySet())
        {
            String temp  = m.getKey();
            if(temp.charAt(0)!=temp.charAt(1))
            {
                int count = map.get(temp);
                String reverse = temp.charAt(1) + "";
                reverse+=temp.charAt(0);
                if(used.contains(reverse) || !map.containsKey(reverse))
                {
                    continue;
                }
                int count2 = map.get(reverse);
                ans+=Math.min(count, count2)*4;
                used.add(reverse);
            }
            else
            {
                if(map.get(temp)>=1 && map.get(temp)%2!=0 && !flag)
                {
                flag = true;
                }
                ans+=(map.get(temp)/2)*4;
            }
            used.add(temp);
        }
        if(flag)
        {
            ans+=2;
        }
        return ans;
    }
}
