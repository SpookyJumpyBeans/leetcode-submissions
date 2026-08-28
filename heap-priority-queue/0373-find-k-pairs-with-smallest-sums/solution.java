// 373. Find K Pairs with Smallest Sums
// https://leetcode.com/problems/find-k-pairs-with-smallest-sums/
// Medium | Java | Accepted 2026-02-08
// Runtime 156 ms | Memory 121.6 MB

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        PriorityQueue<List<Integer>> q = new PriorityQueue<>((a, b) -> (a.get(0)-b.get(0)));
        Set<List<Integer>> set = new HashSet<>();
        q.add(new ArrayList<>(Arrays.asList(nums1[0]+nums2[0], 0, 0)));
        while(k-->0)
        {
            List<Integer> temp = q.poll();
            if(temp.get(2)+1<nums2.length&&set.add(new ArrayList<>(Arrays.asList(nums1[temp.get(1)]+nums2[temp.get(2)+1], temp.get(1), temp.get(2)+1))))
            {
                q.add(new ArrayList<>(Arrays.asList(nums1[temp.get(1)]+nums2[temp.get(2)+1], temp.get(1), temp.get(2)+1)));
            }
            if(temp.get(1)+1<nums1.length && set.add( new ArrayList<>(Arrays.asList(nums1[temp.get(1)+1]+nums2[temp.get(2)], temp.get(1)+1, temp.get(2)))))
            {
                q.add(new ArrayList<>(Arrays.asList(nums1[temp.get(1)+1]+nums2[temp.get(2)], temp.get(1)+1, temp.get(2))));
            }
            ans.add(new ArrayList<>(Arrays.asList(nums1[temp.get(1)], nums2[temp.get(2)])));
        }
        return ans;
    }
}
