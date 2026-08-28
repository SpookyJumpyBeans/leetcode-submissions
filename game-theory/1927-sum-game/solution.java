// 1927. Sum Game
// https://leetcode.com/problems/sum-game/
// Medium | Java | Accepted 2026-08-24
// Runtime 10 ms | Memory 47.1 MB

/*
class Solution {
    public boolean sumGame(String num) {
        //My solution
        //I track both the left and right sums as well as the number of questions marks there are
        int right = 0;
        int left = 0;
        int questionsLeft = 0;
        int questionsRight = 0;
        for(int i = 0; i<num.length(); i++)
        {
            if(i<num.length()/2)
            {
                if(num.charAt(i)=='?')
                {
                    questionsLeft++;
                    continue;
                }
                left+= (num.charAt(i)-'0');
            }
            else
            {
                if(num.charAt(i)=='?')
                {
                    questionsRight++;
                    continue;
                }
                right+= (num.charAt(i)-'0');
            }
        }
        if((questionsRight+questionsLeft)%2!=0)
        {
            return true;
        }
        if(questionsLeft==questionsRight)
        {
            return left == right ? false : true;
        }
        if(questionsLeft<questionsRight)
        {
            questionsRight-=questionsLeft;
            if(right>=left)
            {
                return true;
            }
            return 9*(questionsRight/2)+right == left ? false : true;
        }
            questionsLeft-=questionsRight;
            if(left>=right)
            {
                return true;
            }
            return 9*(questionsLeft/2)+left == right ? false : true;
    }
}
*/

class Solution {
    public boolean sumGame(String num) {
        //Can simply get the net difference between the two sides
        int n = num.length();
        int diffSum = 0;
        int diffQ = 0;
        
        for (int i = 0; i < n; i++) {
            // Positive for left side, negative for right side
            int sign = (i < n / 2) ? 1 : -1; 
            
            if (num.charAt(i) == '?') {
                diffQ += sign;
            } else {
                diffSum += sign * (num.charAt(i) - '0');
            }
        }
        
        // If the total number of '?' is odd, Alice always wins.
        // (diffQ % 2 has the exact same parity as total '?' % 2)
        if (diffQ % 2 != 0) {
            return true;
        }
        
        // Bob wins ONLY if the sum difference is perfectly offset by the '?' difference.
        // We multiply diffSum by 2 to avoid floating point division of diffQ by 2.
        return (diffSum * 2 + diffQ * 9) != 0;
    }
}
