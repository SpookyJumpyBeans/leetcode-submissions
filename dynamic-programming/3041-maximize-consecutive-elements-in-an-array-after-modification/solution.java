// 3041. Maximize Consecutive Elements in an Array After Modification
// https://leetcode.com/problems/maximize-consecutive-elements-in-an-array-after-modification/
// Hard | Java | Accepted 2026-11-21
// Runtime 90 ms | Memory 74.2 MB

/*
 * Intuition (Bottom-Up Tabulation):
 * - Sort nums so smaller values are finalized before larger ones.
 * - dp[v] = length of the longest consecutive sequence ending at value v.
 * - For each num, we can either bump it (num + 1) or keep it (num).
 * - Skipping num is implicit because all other dp[v] entries stay untouched.
 *
 * Why we don't need to track indices in the DP state:
 * - Just like space-optimizing 0/1 Knapsack from 2D dp[i][v] to 1D dp[v],
 *   the array index is implicitly handled by the outer for-loop.
 * - Each element only depends on the previous iteration's table, so we can
 *   overwrite a single 1D dp array in place.
 * - Because nums is sorted, earlier elements can never be reused, and future
 *   elements only care about the best length ending at a value—not which
 *   earlier indices built it.
 * - Updating dp[num + 1] before dp[num] ensures we read the previous
 *   iteration's dp[num], preventing the current element from being used twice.
 *
 * Why direct assignment works (no Math.max needed):
 * - Every write to dp[x] uses the same formula: dp[x - 1] + 1 (either earlier
 *   when num == x - 1 was bumped, or now when num == x is kept).
 * - Since dp[x - 1] only ever increases as we scan the sorted array, the newer
 *   dp[x - 1] + 1 is always >= any previous value in dp[x].
 *
 * How duplicates are handled:
 * - Once we reach num, dp[num - 1] is frozen (no more num - 1 will appear).
 * - 1st copy sets dp[num]; 2nd copy uses it to extend dp[num + 1]; any 3rd+
 *   copy hits a fixed point and rewrites the exact same values (effectively skipped).
 *
 * Time: O(n log n) for sorting, O(n) for DP pass.
 * Space: O(max(nums)) for the dp array.
 */
class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        // Max nums[i] is 10^6, so num + 1 can reach 10^6 + 1
        int[] dp = new int[1000002];
        int max = 0;

        for (int num : nums) {
            // Update dp[num + 1] FIRST so it reads the old dp[num] (before this
            // element is counted), preventing the same element from being used twice.
            dp[num + 1] = 1 + dp[num];   // Option 1: bump num -> num + 1
            dp[num] = dp[num - 1] + 1;   // Option 2: keep num as-is

            max = Math.max(max, Math.max(dp[num + 1], dp[num]));
        }

        return max;
    }
}
