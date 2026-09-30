// 2002. Maximum Product of the Length of Two Palindromic Subsequences
// https://leetcode.com/problems/maximum-product-of-the-length-of-two-palindromic-subsequences/
// Medium | Java | Accepted 2026-09-29
// Runtime 2318 ms | Memory 47.1 MB

class Solution {
    int maxProduct = 1;
    public int maxProduct(String s) {
        //Used backtracking since constraints are small
        if(s.length()==2) //Base case
        {
            return 1;
        }
        recurse("", "", s, 0); //Backtrack using two empty strings and the index starting at 0
        return maxProduct;
    }

    public void recurse(String one, String two, String s, int ind)
    {
        if(one.length()>0 && two.length()>0 && new StringBuilder(one).reverse().toString().equals(one) && new StringBuilder(two).reverse().toString().equals(two)) //Checks if the two current strings are palindromes
        {
            maxProduct = Math.max(maxProduct, one.length()*two.length()); //See if these two strings give us a new max product
        }
        if(ind==s.length()) //If the index has reached the end of the string, return immediately
        {
            return;
        }
        StringBuilder addOne = new StringBuilder(one); 
        StringBuilder addTwo = new StringBuilder(two);
        addOne.append(s.charAt(ind));
        addTwo.append(s.charAt(ind));
        //Three choices at every index
        recurse(addOne.toString(), two, s, ind+1); //Add the character to the first string
        recurse(one, addTwo.toString(), s, ind+1); //Add the character to the second string
        recurse(one, two, s, ind+1); //Skip this character altogether
    }
}
