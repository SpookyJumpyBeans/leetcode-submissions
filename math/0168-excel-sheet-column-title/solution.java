// 168. Excel Sheet Column Title
// https://leetcode.com/problems/excel-sheet-column-title/
// Easy | Java | Accepted 2026-08-24
// Runtime 0 ms | Memory 42.1 MB

class Solution {
    public String convertToTitle(int columnNumber) {
        StringBuilder ans = new StringBuilder();
        int start = 0;
        int t = columnNumber;
        while(columnNumber>0)
        {
           columnNumber--;
           char a = (char)((columnNumber%26)+'A');
           ans.append(a);
           columnNumber/=26;
        }
        return ans.reverse().toString();
    }
}
