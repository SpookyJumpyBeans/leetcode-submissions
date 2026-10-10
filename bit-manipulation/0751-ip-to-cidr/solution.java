// 751. IP to CIDR
// https://leetcode.com/problems/ip-to-cidr/
// Medium | Java | Accepted 2026-10-09
// Runtime 2 ms | Memory 43.9 MB

class Solution {
    public List<String> ipToCIDR(String ip, int n) {
        int IP = 0; 
        String[] temp = ip.split("\\.");
        for(int i = 0; i<temp.length; i++)
        {
            IP = (IP<<8)+Integer.parseInt(temp[i]);
        }   
        List<String> ans = new ArrayList<>();
        while(n>0)
        {
            int lowestBit = IP & (-IP);
            int index = Integer.numberOfTrailingZeros(lowestBit);
            if(lowestBit==0)
            {
                index = 31 - Integer.numberOfLeadingZeros(n);
            }
            int blockSize = 1 << index;
            while(blockSize>n)
            {
                index--;
                blockSize = 1 << index;
            }
            ans.add(toString(IP, 32-index));
            IP += blockSize;
            n-=blockSize;
        }
        return ans;
    }

    public String toString(int IP, int end)
    {
        StringBuilder cidr = new StringBuilder();
        int mask = (1 << 8)-1;
        for(int i = 3; i>=0; i--)
        {
            cidr.append(IP>>(i*8)&mask);
            if(i>0)
            {
                cidr.append(".");
            }
        }
        return cidr.append("/").append(end).toString();
    }
}
