// 1405. Longest Happy String
// https://leetcode.com/problems/longest-happy-string/
// Medium | Java | Accepted 2026-08-22
// Runtime 2 ms | Memory 42.5 MB

class Solution {
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((d, e)->Integer.compare(e[0], d[0]));
        if(a>0)
        {
        pq.add(new int[]{a, 0});
        }
        if(b>0)
        {
        pq.add(new int[]{b, 1});
        }
        if(c>0)
        {
        pq.add(new int[]{c, 2});
        }
        StringBuilder ans = new StringBuilder();
        while(!pq.isEmpty())
        {
            int[] temp = pq.poll();
            int n = ans.length();
            if(n>=2 && ans.charAt(n-1)==ans.charAt(n-2)&&ans.charAt(n-1)==(temp[1]+'a'))
            {
                if(pq.isEmpty())
                {
                    break;
                }
                int[] temp1 = pq.poll();
                ans.repeat(temp1[1]+'a', 1);
                temp1[0]--;
                if(temp1[0]>0)
                {
                    pq.add(temp1);
                }
                pq.add(temp);
            }
            else
            {
                ans.repeat(temp[1]+'a', temp[0]>1 ? 2 : 1);
                temp[0]-= temp[0]>1 ? 2 : 1;
                if(temp[0]>0)
                {
                    pq.add(temp);
                }
            }
        }
        return ans.toString();
    }
}
