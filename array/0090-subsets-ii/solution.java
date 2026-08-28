// 90. Subsets II
// https://leetcode.com/problems/subsets-ii/
// Medium | Java | Accepted 2025-12-14
// Runtime 2 ms | Memory 45.2 MB

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    int[] num;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        num = nums;
        recurse(0, new ArrayList<>());
        return ans;
    }

    public void recurse(int ind, List<Integer> temp)
    {
       ans.add(new ArrayList<>(temp));
       for(int i = ind; i<num.length; i++)
       {
        if(i>ind && num[i] == num[i-1])
            continue;
        temp.add(num[i]);
        recurse(i+1, temp);
        temp.remove(temp.size()-1);
       }
       return;
    }
}
