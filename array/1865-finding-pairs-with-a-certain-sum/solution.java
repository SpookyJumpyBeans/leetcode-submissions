// 1865. Finding Pairs With a Certain Sum
// https://leetcode.com/problems/finding-pairs-with-a-certain-sum/
// Medium | Java | Accepted 2026-08-13
// Runtime 158 ms | Memory 109.7 MB

class FindSumPairs {
    Map<Integer, Integer> map2;
    int[] n1;
    int[] n2;
    public FindSumPairs(int[] nums1, int[] nums2) {
        map2 = new HashMap<>();
        for(int j : nums2)
        {
            map2.put(j, map2.getOrDefault(j, 0)+1);
        }
        n1 = nums1;
        n2 = nums2;
    }
    
    public void add(int index, int val) {
        map2.put(n2[index], map2.get(n2[index])-1);
        if(map2.get(n2[index])==0)
        {
            map2.remove(n2[index]);
        }
        n2[index]+=val;
        map2.put(n2[index], map2.getOrDefault(n2[index], 0)+1);
    }
    
    public int count(int tot) {
        int count = 0;
        for(int i : n1)
        {
            int compl = tot-i;
            Integer freq = map2.get(compl);
            if(freq!=null)
            {
                count+=map2.get(compl);
            }
        }
        return count;
    }
}

/**
 * Your FindSumPairs object will be instantiated and called as such:
 * FindSumPairs obj = new FindSumPairs(nums1, nums2);
 * obj.add(index,val);
 * int param_2 = obj.count(tot);
 */
