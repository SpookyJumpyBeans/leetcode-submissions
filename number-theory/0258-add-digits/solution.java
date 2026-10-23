// 258. Add Digits
// https://leetcode.com/problems/add-digits/
// Easy | Java | Accepted 2026-10-22
// Runtime 9 ms | Memory 44.7 MB

class Solution {
    public int addDigits(int num) {
        String curr = String.valueOf(num);
        while(curr.length()>1)
        {
            String[] temp = curr.split("");
            int sum = 0;
            for(int i = 0; i<temp.length; i++)
            {
                sum+=Integer.parseInt(temp[i]);
            }
            curr = String.valueOf(sum);
        }
        return Integer.parseInt(curr);
    }
}
