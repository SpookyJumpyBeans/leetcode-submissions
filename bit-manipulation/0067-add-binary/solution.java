// 67. Add Binary
// https://leetcode.com/problems/add-binary/
// Easy | Java | Accepted 2026-08-24
// Runtime 2 ms | Memory 42.9 MB

class Solution {
    public String addBinary(String a, String b) {
        StringBuilder str = new StringBuilder();
        int startA = a.length()-1;
        int startB = b.length()-1;
        int carry = 0;
        while(startA>=0 && startB>=0)
        {
            if(a.charAt(startA)=='1' && b.charAt(startB)=='1')
            {
                int temp = 2+carry;
                temp%=2;
                str.append(temp);
                carry = 1;
            }
            else if(a.charAt(startA)=='0' && b.charAt(startB)=='0')
            {
                str.append(carry);
                carry = 0;
            }
            else 
            {
                int temp = 1+carry;
                temp%=2;
                str.append(temp);
                if(temp==0)
                {
                    carry = 1;
                }
                else
                {
                    carry = 0;
                }
            }
            startA--;
            startB--;
        }
        if(startB<0)
        {
            while(startA>=0)
            {
                if(a.charAt(startA)=='1')
                {
                    int temp = 1+carry;
                    temp%=2;
                    str.append(temp);
                    if(temp==0)
                    {
                        carry = 1;
                    }
                    else
                    {
                        carry = 0;
                    }
                }
                else
                {
                    str.append(carry);
                    carry = 0;
                }
                startA--;
            }
        }
        if(startA<0)
        {
            while(startB>=0)
            {
                if(b.charAt(startB)=='1')
                {
                    int temp = 1+carry;
                    temp%=2;
                    str.append(temp);
                    if(temp==0)
                    {
                        carry = 1;
                    }
                    else
                    {
                        carry = 0;
                    }
                }
                else
                {
                    str.append(carry);
                    carry = 0;
                }
                startB--;
            }
        }
        return carry == 1 ? str.append(carry).reverse().toString() : str.reverse().toString();
    }
}
