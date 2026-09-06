// 52. N-Queens II
// https://leetcode.com/problems/n-queens-ii/
// Hard | Java | Accepted 2026-09-06
// Runtime 3 ms | Memory 42.9 MB

class Solution {
    boolean[] col;
    Set<Integer> diagIncr = new HashSet<>();
    Set<Integer> diagDecr = new HashSet<>();
    int count = 0;
    int nn;
    public int totalNQueens(int n) {
        col = new boolean[n];
        nn = n;
        recurse(0);
        return count;
    }

    public void recurse(int start)
    {
            if(start==nn)
            {
                count++;
                return;
            }
            for(int st = 0; st<nn; st++)
            {
                if(col[st] || diagIncr.contains(start+st) || diagDecr.contains(start-st))
                {
                    continue;
                }
                col[st] = true;
                diagIncr.add(start+st);
                diagDecr.add(start-st);
                recurse(start+1);
                col[st] = false;
                diagDecr.remove(start-st);
                diagIncr.remove(start+st);
            }
    }
}
