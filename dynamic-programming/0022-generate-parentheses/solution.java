// 22. Generate Parentheses
// https://leetcode.com/problems/generate-parentheses/
// Medium | Java | Accepted 2025-10-27
// Runtime 2 ms | Memory 43.8 MB

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        recursion(ans, 0, 0, "", n);
        return ans;
    }

    public void recursion(List<String> ans, int left, int right, String temp, int n)
    {
        if(temp.length()==n*2)
        {
            ans.add(temp);
            return;
        }
        if(left<n)
        {
            recursion(ans, left+1, right, temp+"(", n);
        }
        if(right<left)
        {
            recursion(ans, left, right+1, temp+")", n);
        }

    }
}
