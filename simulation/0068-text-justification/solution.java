// 68. Text Justification
// https://leetcode.com/problems/text-justification/
// Hard | Java | Accepted 2026-08-11
// Runtime 1 ms | Memory 43.2 MB

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
       int i = 0;
       List<String> ans = new ArrayList<>();
       while(i<words.length)
       {
        int count = words[i].length();
        List<String> temp = new ArrayList<>();
        temp.add(words[i]);
        while(i+1<words.length && count+words[i+1].length()<maxWidth && maxWidth-count-words[i+1].length()>=temp.size())
        {
            temp.add(words[i+1]); //This, is, an  3 exam, 12 
            count+=words[i+1].length(); //8 
            i++;
        }
        StringBuilder tt = new StringBuilder();
        int res = maxWidth-count; //16-12 = 4
        int size = temp.size()-1; //2;
        if(i==words.length-1)
        {
            for(String p : temp)
            {
                tt.append(p);
                if(!temp.get(temp.size()-1).equals(p))
                {
                tt.append(" ");
                }
            }
            int rest = maxWidth - tt.length();
            tt.append(" ".repeat(rest));
        }
        else if(size==0)
        {
            tt.append(temp.get(0));
            int rest = maxWidth-temp.get(0).length();
            tt.append(" ".repeat(rest));
        }
        else
        {
        for(String t : temp)
        {
            tt.append(t); //This   is
            int numSpaces = 0; 
            if(res>0&&size>0)
            {
            if(res%size!=0)
            {
                numSpaces = res/size+1; //2
            }
            else
            {
                numSpaces = res/size; //1
            }
             res-=numSpaces;
            tt.append(" ".repeat(numSpaces));
            } //2
            size--;
        }
        }
        ans.add(tt.toString());
        i++;
       }
       return ans;
    }
}
