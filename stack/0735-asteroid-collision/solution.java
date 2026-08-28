// 735. Asteroid Collision
// https://leetcode.com/problems/asteroid-collision/
// Medium | Java | Accepted 2026-02-14
// Runtime 5 ms | Memory 47.1 MB

class Solution {
    //[-4, 3, -6, 2,-1,4]​​​​​​​
    //-4 -6 2

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i<asteroids.length; i++)
        {
            if(asteroids[i]<0 && !stack.isEmpty())
            {
                while(!stack.isEmpty() && Math.abs(asteroids[i])>stack.peek() && stack.peek()>0)
                {
                    stack.pop();
                }
                if(stack.isEmpty())
                {
                    stack.add(asteroids[i]);
                }
                else if(stack.peek()<0)
                {
                    stack.add(asteroids[i]);
                }
                else if(Math.abs(asteroids[i])==stack.peek())
                {
                    stack.pop();
                }
            }
            else
            {
                stack.add(asteroids[i]);
            }
        }
        int[] arr = new int[stack.size()];
        int i = arr.length-1;
        while(!stack.isEmpty())
        {
            arr[i] = stack.pop();
            i--;
        }
        return arr;
    }
}
