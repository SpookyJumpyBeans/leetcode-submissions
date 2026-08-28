// 978. Longest Turbulent Subarray
// https://leetcode.com/problems/longest-turbulent-subarray/
// Medium | Java | Accepted 2026-08-24
// Runtime 6 ms | Memory 51.8 MB

/*
class Solution {
    public int maxTurbulenceSize(int[] arr) {
        //My solution is optimal (beats 98%) but complicated
        //My intuition is to go through the array and find various points that fit the turbulent criteria
        //Move the left and right pointers out as far as possible to find the longest turbulent subarray
        //Default answer is 2 if there are more than 1 element and the array doesn't have all the same elements
        if(arr.length==1)
        {
            return 1;
        }
        int ans = 2;
        int ind = 1;
        int countSame = 0;
        int ind2 = 0;
        for(int i = 1; i<arr.length; i++)
        {
            if(arr[i-1]==arr[i])
            {
                countSame++;
            }
        }
        if(countSame==arr.length-1)
        {
            return 1;
        }
        while(ind<arr.length-1)
        {
            if(arr[ind-1]>arr[ind] && arr[ind+1]>arr[ind])
            {
                int left = ind-1;
                int right = ind+1;
                boolean flag = false;
                while(left-1>=0)
                {
                    if(!flag && arr[left-1]<arr[left])
                    {
                        left--;
                        flag = !flag;
                        continue;
                    }
                    else if(flag && arr[left-1]>arr[left])
                    {
                        left--;
                        flag = !flag;
                        continue;
                    }
                    break;
                }
                flag = false;
                while(right+1<arr.length)
                {
                    if(!flag && arr[right+1]<arr[right])
                    {
                        right++;
                        flag = !flag;
                        continue;
                    }
                    else if(flag && arr[right+1]>arr[right])
                    {
                        right++;
                        flag = !flag;
                        continue;
                    }
                    break;
                }

                ans = Math.max(ans, right-left+1);
                ind = right;
            }
            else if(arr[ind-1]<arr[ind] && arr[ind+1]<arr[ind])
            {
                int left = ind-1;
                int right = ind+1;
                boolean flag = false;
                while(left-1>=0)
                {
                    if(!flag && arr[left-1]>arr[left])
                    {
                        left--;
                        flag = !flag;
                        continue;
                    }
                    else if(flag && arr[left-1]<arr[left])
                    {
                        left--;
                        flag = !flag;
                        continue;
                    }
                    break;
                }
                flag = false;
                while(right+1<arr.length)
                {
                    if(!flag && arr[right+1]>arr[right])
                    {
                        right++;
                        flag = !flag;
                        continue;
                    }
                    else if(flag && arr[right+1]<arr[right])
                    {
                        right++;
                        flag = !flag;
                        continue;
                    }
                    break;
                }
                ans = Math.max(ans, right-left+1);
                ind = right;
            }
            ind++;
        }
        return ans;
    }
}
*/

class Solution {
    public int maxTurbulenceSize(int[] arr) {
        int inc = 1, dec = 1, maxLen = 1;
        for(int i = 1; i<arr.length; i++)
        {
            if(arr[i-1]<arr[i])
            {
                inc = 1 + dec;
                dec = 1;
            }
            else if(arr[i-1]>arr[i])
            {
                dec = 1 + inc;
                inc = 1;
            }
            else
            {
                inc = 1;
                dec = 1;
            }
            maxLen = Math.max(maxLen, Math.max(inc, dec));
        }
        return maxLen;
    }
}
