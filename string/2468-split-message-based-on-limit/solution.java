// 2468. Split Message Based on Limit
// https://leetcode.com/problems/split-message-based-on-limit/
// Hard | Java | Accepted 2026-08-11
// Runtime 77 ms | Memory 53.7 MB

class Solution {
    String m;
    int l;
    public String[] splitMessage(String message, int limit) {
        l = limit; //Set global limt var
        m = message; //Set global message var
        int possible = 5; //There are 5 possible digits, 1-9, 10-99, 100-999, 1000-9999, and 10000 that b could be in <a/b>
        int start = 1; //The first range of digits 1-9
        int end = 9;
        String[] ans = new String[0]; //Initialize default answer to nothing
        while(possible>0)
        {
            int a = -1; //a represents a possible number of messages, the second a is set to something other than -1, we know we've found the minimum number of messages possible
            if(possible==1) //If possible = 1, we know this is the last range: 10000.
            { 
                boolean isVal = isValid(10000);
                if(isVal) //If 10000 is valid, set a to 10000.
                {
                    a = 10000;
                    break;
                }
                return ans; //Else return empty array
            }
            else
            {
                int l = start; //If we aren't in the last range, we want to perform binary search to find the smallest valid number of messages
                int r = end;
                while(l<=r)
                {
                    int mid = (l+r)/2;
                    if(isValid(mid)) //If this current mid is valid, we want to set a to that mid and make right bound smaller to see if we can find a smaller valid mid
                    { 
                        r = mid-1;
                        a = mid;
                    }
                    else
                    {
                        l = mid+1;
                    }
                }
            }
            if(a!=-1) //After binary search for the specific digit range, see if we've found a valid num of messages
                {
                    ans  = new String[a]; //If we have instantiate new array of size num of messages
                    int indd = 0; //To track indexes in the ans array
                    int aa = 1; //Keeps track of current message
                    int st = 0; //Keeps track of position in the message 
                    int digBB = String.valueOf(a).length(); //Keeps track of the fixed number of digits in b
                    while(aa<=a)
                    {
                        StringBuilder temp = new StringBuilder(); 
                        int digAA = String.valueOf(aa).length(); //Gets dynamic number of digits in a
                        int rest = digAA+digBB+3; //Adds the 3 additional characters: </>
                        rest = limit-rest; //Sees how many chars in message we can fit after using the sufix
                        if(aa==a) //If we're at the last message, then see what's smaller, the number of chars we have left or the end of the message
                        {
                            int smaller = Math.min(st+rest, message.length());
                            temp.append(message, st, smaller); //Ensures we don't go out of bounds
                        }
                        else
                        {
                        temp.append(message, st, st+rest); //If not last message, just extract message.substring(st, st+rest)
                        }
                        temp.append("<");
                        temp.append(aa);
                        temp.append("/");
                        temp.append(a);
                        temp.append(">");
                        ans[indd] = temp.toString(); //Set the ans array at this index to the message that matches it
                        aa++;
                        indd++;
                        st = st+rest;
                    }
                    return ans;
                }
            start*=10; //Change start to the next range's start
            end*=10; //Change end to next range's end
            possible--;
            end+=9;
        }
        return ans;
    }

    public boolean isValid(int parts)
    {
        //Checks to see if given the suffixes for the number of messages allowed, we have enough space to fit the entire message
     int totalSuffixLen = 0; 
     int bLen = String.valueOf(parts).length(); //Static number of digits for b
     for(int i = 1; i<=parts; i++) //Iterate from 1 to b
     {
        int aLen = String.valueOf(i).length(); //Get dynamic number of digits for a
        int total = 3 + bLen + aLen; //Add the </>
        if(l<=total) //If the suffix itself is already longer or equal to the max size of the message, return false
        {
            return false;
        }
        totalSuffixLen+=total; //Add to total amount of space taken up by suffixes only
     }
     return l*parts - totalSuffixLen>=m.length() ? true : false; //Subtract total amount of space taken up by suffixes from the total amount of space available. If this is greater than message length, this is a valid number of messages
    } 
}
