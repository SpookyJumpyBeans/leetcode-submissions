// 2182. Construct String With Repeat Limit
// https://leetcode.com/problems/construct-string-with-repeat-limit/
// Medium | Java | Accepted 2026-10-12
// Runtime 17 ms | Memory 47.8 MB

class Solution {
    public String repeatLimitedString(String s, int repeatLimit) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a)); //Use a prioirty queue because we want to sort the characters lexographically and always use the lexographically greatest character
        int[] count = new int[26];
        for(int i = 0; i<s.length(); i++)
        {
            count[s.charAt(i)-'a']++; //Count how many of every letter appears
        }
        for(int i = 0; i<26; i++)
        {
            if(count[i]>0) //Any letter that appears gets added to the priority queue
            {
                pq.add(i);
            }
        }
        StringBuilder ans = new StringBuilder();
        while(!pq.isEmpty())
        {
            int top = pq.poll(); //First get the top letter from the pq (the lexographically largest)
            if(!pq.isEmpty()) //If the priority queue isn't empty, we want to get the second largest letter after this top one 
            {
                int next = pq.poll();
                while(count[top]>0) //To get the lexographically largest string, you need to interweave the largest and second largest letters until we run out of the largest letter to use
                {
                    int repeat = Math.min(count[top], repeatLimit); //See how many letters we use up of the largest letter's total count
                    count[top]-=repeat; 
                    ans.repeat(top+'a', repeat); //Repeat the largest letter as many times as we can
                    if(count[top]==0) //If the count of the largest letter is now 0, add back the second largest letter since we didn't have to use it as a reset
                    {
                        pq.add(next);
                        break;
                    }
                    count[next]--; //Else, we use up 1 of the second largest letter (we use up just 1 because we want to reset the total amount of largest letters in the row by breaking the streak with this 1 second largest letter)
                    //Only use 1 so we can save more second largest letters for later
                    ans.append((char)(next+'a'));
                    if(count[next]==0) //If we run out of the second largest letter
                    {
                        if(pq.isEmpty()) //If there are no more letters available, we add the largest letter back onto the pq since it's count isn't 0
                        {
                            pq.add(top);
                            break;
                        }
                        next = pq.poll(); //Else, we set the second largest letter to the next value on the heap
                    }
                }
                //After we can't add any more of the largest letter to the heap (either we used all of the largest letters available or we ran out of reset letters) we repeat the process with the new largest letter on the heap
            }
            else //If there's only just one letter on the heap, then just add as many of this letter as we can, and then don't add it back to the heap since we can't do anything else
            {
                ans.repeat(top+'a', Math.min(repeatLimit, count[top]));
            }
        }
        return ans.toString(); //Return the answer as a string
    }
}
