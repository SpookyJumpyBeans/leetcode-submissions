// 78. Subsets
// https://leetcode.com/problems/subsets/
// Medium | Java | Accepted 2025-12-04
// Runtime 1 ms | Memory 44 MB

class Solution {
    List<List<Integer>> powset = new ArrayList<>();
    int[] nums;

    public List<List<Integer>> subsets(int[] nums) {
        this.nums = nums;
        recurse(0, new ArrayList<>());
        return powset;
    }

    public void recurse(int index, ArrayList curr)
    {
        if(index==nums.length)
        {
            powset.add(new ArrayList<>(curr));
            return;
        }

        curr.add(nums[index]);
        recurse(index+1, curr);
        curr.remove(curr.size()-1);
        recurse(index+1, curr);
    }
}
