// 17. Letter Combinations of a Phone Number
// https://leetcode.com/problems/letter-combinations-of-a-phone-number/
// Medium | Java | Accepted 2025-12-19
// Runtime 3 ms | Memory 49.3 MB

class Solution {
    String[] arr = {"", "", "abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    String dig;
    List<String> ans = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        dig = digits;
        recurse("", 0);
        return ans;
    }

    public void recurse(String temp, int index)
    {
        if(index==dig.length())
        {
            ans.add(temp);
            return;
        }
            String bruh = arr[Integer.parseInt(dig.substring(index,index+1))];
            for(int j = 0; j<bruh.length(); j++)
            {
                temp+=bruh.charAt(j);
                recurse(temp, index+1);
                temp = temp.substring(0, temp.length()-1);
            }
    }


}
