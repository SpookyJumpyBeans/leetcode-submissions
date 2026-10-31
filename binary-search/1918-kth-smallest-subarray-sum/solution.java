// 1918. Kth Smallest Subarray Sum
// https://leetcode.com/problems/kth-smallest-subarray-sum/
// Medium | Java | Accepted 2026-10-30
// Runtime 26 ms | Memory 48.2 MB

/**
 * STEP-BY-STEP EXPLANATION OF THE ALGORITHM:
 * 
 * 1. The Core Idea (Binary Search on Answer):
 *    Instead of generating all possible subarrays and sorting them (which takes too much memory), 
 *    we guess what the kth smallest sum might be. 
 *    The smallest possible subarray sum is the smallest single element in the array.
 *    The largest possible subarray sum is the sum of every element in the array.
 *    Our true answer must lie somewhere between these two values.
 * 
 * 2. Making a Guess:
 *    We use Binary Search to pick a "mid" value between our min and max possible sums.
 *    We then ask a question: "How many subarrays have a sum less than or equal to this 'mid'?"
 * 
 * 3. Counting Efficiently (Sliding Window):
 *    To answer that question quickly, we use a Sliding Window (the 'countLess' helper method).
 *    Because all numbers in the array are positive, adding elements always increases the sum, 
 *    and removing elements always decreases it. 
 *    We expand our window to the right. If the sum gets bigger than our guessed 'mid', 
 *    we shrink it from the left until it's valid again. 
 *    The number of valid subarrays ending at the right pointer is exactly the size of the window.
 * 
 * 4. Adjusting our Guess:
 *    If the total count of valid subarrays is >= k, it means our guessed 'mid' is large enough 
 *    to hold the kth smallest sum. We record it as a potential answer, but search lower (left half) 
 *    to see if an even smaller sum also works.
 *    If the total count is < k, our guessed 'mid' is too small. We must guess a larger sum (right half).
 */
class Solution {
    public int kthSmallestSubarraySum(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        
        // STEP 1: Define the search space boundaries.
        // Find the lowest possible sum (min element) and highest possible sum (total array sum).
        for (int i : nums) {
            sum += i;
            min = Math.min(min, i);
        }
        
        // p1 is our lower bound, p2 is our upper bound for the binary search.
        int p1 = min;
        int p2 = sum;
        int ans = 0;
        
        // STEP 2 & 4: Binary search for the kth smallest sum.
        while (p1 <= p2) {   
            int mid = p1 + (p2 - p1) / 2; // 'mid' is our guessed sum
            
            // We call our sliding window helper to see how many subarrays are <= mid.
            // If there are at least 'k' valid subarrays, this 'mid' might be our answer.
            if (countLess(mid, nums) >= k) {
                ans = mid;     // Record this as a potential answer
                p2 = mid - 1;  // Try to find a tighter (smaller) valid sum
            } else {
                p1 = mid + 1;  // Our guess was too small, search the upper half
            }
        }
        return ans; // Once the binary search is exhausted, 'ans' holds the exact kth smallest sum.
    }

    // STEP 3: Helper method using Sliding Window to count subarrays with sum <= mid.
    public int countLess(int mid, int[] nums) {
        int sum = 0;      // Running sum of the current window
        int left = 0;     // Left pointer of the window
        int count = 0;    // Total number of valid subarrays
        
        // 'i' acts as the right pointer, expanding the window one element at a time
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i]; // Add the new element to our current window's sum
            
            // If the window's sum is too big, shrink it from the left until it is valid.
            // (Because all numbers are positive, subtracting from the left guarantees a smaller sum).
            while (sum > mid) {
                sum -= nums[left];
                left++;
            }
            
            // Crucial step: Once the window is valid, the number of valid subarrays 
            // that end exactly at index 'i' is equal to the current length of the window.
            // E.g., if window is [1, 2, 3], valid subarrays ending at '3' are [3], [2, 3], and [1, 2, 3].
            count += (i - left + 1); 
        }
        return count;
    }
}
