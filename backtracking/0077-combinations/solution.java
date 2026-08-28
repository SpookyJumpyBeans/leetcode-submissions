// 77. Combinations
// https://leetcode.com/problems/combinations/
// Medium | Java | Accepted 2026-08-22
// Runtime 14 ms | Memory 97.9 MB

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    int kk;
    int nn;
    public List<List<Integer>> combine(int n, int k) {
        nn = n;
        kk = k;
        List<Integer> temp = new ArrayList<>();
        backtrack(temp, 1);
        return ans;
    }

    public void backtrack(List<Integer> temp, int start)
    {
        if(temp.size()==kk)
        {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for(int i = start; i<=nn-(kk-temp.size())+1; i++)
        {
            temp.add(i);
            backtrack(temp, i+1);
            temp.remove(temp.size()-1);
        }
    }
}
