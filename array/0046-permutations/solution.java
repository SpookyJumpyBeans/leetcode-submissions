// 46. Permutations
// https://leetcode.com/problems/permutations/
// Medium | Java | Accepted 2025-12-14
// Runtime 2 ms | Memory 45.4 MB

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    int[] num;
    public List<List<Integer>> permute(int[] nums) {
        num = nums;
        recurse(0, new ArrayList<>());
        return ans;
    }
    
    public void recurse(int ind, List<Integer> temp)
    {
        if(ind>=num.length)
        {
            if(temp.size()==num.length)
            {
                ans.add(new ArrayList<>(temp));
            }
            return;
        }
      for(int i = 0; i<num.length; i++)
      {
        if(temp.contains(num[i]))
        {
            continue;
        }
        temp.add(num[i]);
        recurse(ind+1, temp);
        temp.remove(temp.size()-1);
      }
      return;
    }
}
