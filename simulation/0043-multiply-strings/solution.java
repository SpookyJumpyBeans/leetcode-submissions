// 43. Multiply Strings
// https://leetcode.com/problems/multiply-strings/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0"))
        {
            return "0";
        }
        int[] res = new int[num1.length() + num2.length()];

        for(int i = num1.length()-1;i>=0; i--)
        {
                int mult1 = num1.charAt(i) - '0';
            for(int j = num2.length()-1; j>=0; j--)
            {
                int mult2 = num2.charAt(j) - '0';
                int mult = mult1*mult2;
                int sum = mult+res[i+j+1];
                res[i+j+1] = sum%10;
                res[i+j] += sum/10;
            }
        } 
        String ans = "";
        boolean zero = false;
        System.out.println(Arrays.toString(res));
        for(int k = 0; k<res.length; k++)
        {
            if(res[k]==0 && !zero)
            {
                continue;
            }
            zero = true;
            ans+=res[k];
        }
        return ans;
    }
}
