// 2429. Minimize XOR
// https://leetcode.com/problems/minimize-xor/
// Medium | Java | Accepted 2026-11-03
// Runtime 1 ms | Memory 41.7 MB

class Solution {
    public int minimizeXor(int num1, int num2) {
        //Intuition:
        //Count number of set bits in both num1 and num2
        //3 Cases:
        //
        //1. Number of set bits in num1 > num2
        // This means we can't make all the set bits in num1 0
        // Greedily start at the most significant set bits in num1 and mark them in x until we've set enough bits that reach the number of set bits in num2, thereby minimizing the value of x
        //2. Number of set bits in num1 < num2
        // This means we have extra bits that we have to use
        // First mark all bits in num1 that are 1, then do another pass and mark the 0 bits, starting at the least significant 0 bit so we minimize the value that x can be
        //3. Number of set bits in num1 == num2
        // This means we have the perfect number of bits
        // Go through num1 and mark all its set bits in x
        String n2 = Integer.toBinaryString(num2);
        int count2 = 0; 
        for(int i = 0; i<n2.length(); i++) //Counts the number of set bits in n1 and n2
        {
            count2+=n2.charAt(i)=='1' ? 1 : 0;
        }
        String n1 = Integer.toBinaryString(num1);
        int count1 = 0;
        for(int i = 0; i<n1.length(); i++)
        {
            count1+=n1.charAt(i)=='1' ? 1 : 0;
        }
        int x = 0; //Resulting value
        if(count1==count2) //The counts are equal
        {
            int i = 0;
            while(count2>0)
            {
                if((num1&(1<<i))!=0) //(1<<i) is a bit mask that extracts the bit at index i
                //If num1 & (1<<i) != 0, that means the bit at index i in num1 is 1, meaning we need to mark that in x too at index i
                {
                    x |= (1<<i);
                    count2--; 
                }
                i++;
            }
        }
        else if(count1<count2)
        {
            count2-=count1;
            int i = 0;
            while(count1>0) //Same thing as equal case
            {
                if((num1&(1<<i))!=0)
                {
                    x |= (1<<i);
                    count1--;
                }
                i++;
            }
            i = 0;
            while(count2>0) //Now do another pass and use up the remaining count2 set bits
            {
                if((x&(1<<i))==0) //Now we check if the bit at index i is 0, we compromise turning a bit from 0 to 1 by starting at the LSB and working up
                {
                    x |= (1<<i);
                    count2--;
                }
                i++;
            }
        }
        else //Case where we have less bits in num2 than num1
        {
            int i = 31 - Integer.numberOfLeadingZeros(num1); //We have to compromise by starting at the MSB
            while(count2>0)
            {
                if((num1&(1<<i))!=0) //Same check to see if the bit at index i is 1
                {
                    x|=(1<<i);
                    count2--;
                }
                i--; //Go down to the LSB while count2 > 0
            }
        }
        return x;
    }
}
