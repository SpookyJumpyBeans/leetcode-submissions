// 66. Plus One
// https://leetcode.com/problems/plus-one/
// Easy | Java | Accepted 2026-01-21
// Runtime 1 ms | Memory 43.6 MB

class Solution {
    public int[] plusOne(int[] digits) {
        List<Integer> list = new ArrayList<>();
        int carry = 1;
        for(int i = digits.length-1; i>=0; i--)
        {
            int sum = digits[i]+carry;
            carry = sum/10;
            if(i>0)
            {
            list.add(0, sum%10);
            }
            else
            {
                list.add(0, sum%10);
                if(carry==1)
                {list.add(0, carry);
                }
            }
        }
        int[] ans = new int[list.size()];
        int j = 0;
        for(int n : list)
        {
            ans[j] = n;
            j++;
        }
        return ans;
    }
}
