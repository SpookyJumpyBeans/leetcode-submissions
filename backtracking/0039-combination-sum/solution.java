// 39. Combination Sum
// https://leetcode.com/problems/combination-sum/
// Medium | Java | Accepted 2025-12-04
// Runtime 2 ms | Memory 45.7 MB

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    int[] cand;
    int t;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        cand = candidates;
        t = target;
        recurse(0, 0, new ArrayList<>());
        return ans;
    }

    public void recurse(int index, int sum, ArrayList<Integer> curr)
    {
        if(index >= cand.length || sum >= t)
        {
            if(sum==t)
            {
                ans.add(new ArrayList<>(curr));
            }
            return;
        }

        curr.add(cand[index]);
        recurse(index, sum+cand[index], curr);
        curr.remove(curr.size()-1);
        recurse(index+1, sum, curr);
    }
}
