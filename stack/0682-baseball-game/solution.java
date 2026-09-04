// 682. Baseball Game
// https://leetcode.com/problems/baseball-game/
// Easy | Java | Accepted 2026-09-01
// Runtime 3 ms | Memory 43.4 MB

class Solution {
    public int calPoints(String[] operations) {
        Stack<Integer> temp = new Stack<>();
        for(String op : operations)
        {
            if(op.equals("+"))
            {
                int t1 = temp.pop();
                int t2 = temp.pop();
                temp.push(t2);
                temp.push(t1);
                temp.push(t1+t2);
            }
            else if(op.equals("C"))
            {
                temp.pop();
            }
            else if(op.equals("D"))
            {
                temp.push(2*temp.peek());
            }
            else
            {
                temp.push(Integer.parseInt(op));
            }
        }
        int sum = 0;
        while(!temp.isEmpty())
        {
            sum+=temp.pop();
        }
        return sum;
    }
}
