// 40. Combination Sum II
// https://leetcode.com/problems/combination-sum-ii/
// Medium | Java | Accepted 2025-12-13
// Runtime 7 ms | Memory 45.2 MB

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    int[] cand;
    int targ;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        cand = candidates;
        targ = target;
        recurse(0, 0, new ArrayList<>());
        return ans;
    }

    public void recurse(int index, int sum, List<Integer> temp)
    {
        if(index == cand.length || sum >=targ)
        {
            if(sum==targ)
            {
                ans.add(new ArrayList<>(temp));
            }
            return;
        }
        temp.add(cand[index]);
        recurse(index+1, sum+cand[index], temp);
        temp.remove(temp.size()-1);
          int i = index;
        while(i<cand.length && cand[i]==cand[index])
        {
            i++;
        }
        if(i>=cand.length)
        {
            if(sum==targ)
            {
                ans.add(new ArrayList<>(temp));
            }
            return;
        }
        recurse(i, sum, temp);
    }
}
