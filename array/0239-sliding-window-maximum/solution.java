// 239. Sliding Window Maximum
// https://leetcode.com/problems/sliding-window-maximum/
// Hard | Java | Accepted 2025-10-27
// Runtime 31 ms | Memory 59.6 MB

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length-k+1];
        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i<nums.length; i++)
        {
            while(!dq.isEmpty() && dq.peekFirst() < i - k + 1)
            {
                dq.pollFirst();
            }

            while(!dq.isEmpty() && nums[dq.peekLast()] <=nums[i])
            {
                dq.pollLast();
            }

            dq.offerLast(i);

            if(i >= k-1)
            {
                ans[i-k+1] = nums[dq.peekFirst()];
            }

        }
        return ans;
    }
}
