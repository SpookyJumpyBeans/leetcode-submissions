// 93. Restore IP Addresses
// https://leetcode.com/problems/restore-ip-addresses/
// Medium | Java | Accepted 2026-09-20
// Runtime 2 ms | Memory 44.3 MB

class Solution {
    List<String> valid = new ArrayList<>();
    String ip;
    public List<String> restoreIpAddresses(String s) {
        //Use backtracking to find a valid ip
        ip = s;
        recurse(0, 0, new StringBuilder()); //Keep track of how many dots we've used, the index in the original ip string and the current ip address we've built thus far
        return valid;
    }
    
    public void recurse(int dotsCount, int ind, StringBuilder temp)
    {
        if(dotsCount==4 && ind==ip.length()) //If we have used 4 dots and the index in the original ip string is equal to the ip length, we've used all the numbers in this ip (This filters out ip addresses that slip through when we are at the final part of the ip address and we use a substring that doesn't go all the way to the end of the ip string)
        {
            valid.add(temp.toString()); //Add to the list of valid ip addresses
            return;
        }
        for(int i = ind; i<ip.length(); i++) //The loop starts at where the previous ip string left off, so directly after the part we just added to our ip address with the dot
        {
            int extract = Integer.parseInt(ip.substring(ind, i+1)); //Extract the integer value of the ip we're currently trying out
            if(extract>255) //If it's greater than 255, we know that any future i value will also not work, cut this branch off immediately
            {
                break;
            }
            if(dotsCount+1==4 && (i+1!=ip.length() || Integer.parseInt(ip.substring(ind, ip.length()))>255) || (ip.charAt(ind)=='0' && i+1>ind+1)) //If we're on the number that goes after the third dot, we have to ensure that the number is the entire rest of the string, so if ip.length()!=i+1 that means we aren't utilizing the entire rest of the string
            //If it does go to the rest of the string and the result is greater than 255, also continue
            //The last condition is to ensure that none of the ip addresses we make have leading zeroes, by only allowing zeroes to be added on their own
            {
                continue;
            }
            StringBuilder tempp = new StringBuilder(temp);
            temp.append(ip.substring(ind, i+1)); //Append the chosen ip substring
            if(dotsCount<3)//Only add a period if the number of dots is less than 3
            {
                temp.append(".");
            }
            recurse(dotsCount+1, i+1, temp); //Increment the number of dots added, the new starting point in the ip address string is now i+1 (where the last substring ended)
            temp = tempp; //Backtrack and restore previous state
        }
    }
}
