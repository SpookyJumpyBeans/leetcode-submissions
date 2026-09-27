// 149. Max Points on a Line
// https://leetcode.com/problems/max-points-on-a-line/
// Hard | Java | Accepted 2026-09-26
// Runtime 40 ms | Memory 47.2 MB

class Solution {
    public int maxPoints(int[][] points) {
        //Representing the slope as a floating point double will lead to rounding errors
        //Instead calculate delta x and delta y between the two points and divide them by their GCD to get a key
        //Points in the same line will have the same key
        Map<String, Integer> slopeCount = new HashMap<>(); 
        int max = 1; //By default a single point is considered a point on a line 
        for(int i = 0; i<points.length; i++) //Go through every combination
        {
            for(int j = 0; j<points.length; j++)
            {
                if(i==j) //Skip if this is the point we're currently evaluating
                {
                    continue;
                }
                int mx = points[i][0]-points[j][0]; //Get the delta x and delta y
                int my = points[i][1]-points[j][1];
                if(my!=0 && mx!=0) //If they don't equal 0 then that means they aren't on a vertical/horizontal line
                {
                    int gcd = gcd(Math.abs(mx), Math.abs(my)); //Make both numbers positive just in case they're negative
                    //GCD of a negative number is calculated by ignoring the sign
                    mx/=gcd; //Divide both deltas by the GCD
                    my/=gcd;
                }
                else
                {
                    if(mx==0) //If mx is 0, then this means that these points are on a vertical line
                    //Make the key unique for vertical numbers
                    {
                        mx = 10000;
                        my = 10000;
                    }
                    else
                    {
                        //This is for a horizontal line
                        mx = 0;
                        my = 0;
                    }
                }
                String key = mx + " " + my; //Make the key
                slopeCount.put(key, slopeCount.getOrDefault(key, 0)+1); //Add it to the map
            }
        for(String keys : slopeCount.keySet()) //Since the map is going to be calulcating the mx my pairs for every point from the point we're evaluating, we need to clear the map at the end of every iteration
        //Go through the frequency count for this specific point and find the max
        {
            max = Math.max(slopeCount.get(keys)+1, max);
        }
        slopeCount.clear(); //Clear the map
        }
        return max; //Return the max
    }

    public int gcd(int num1, int num2)
    {
        if(num1<num2)
        {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int remain = num1%num2;
        while(remain>0)
        {
            num1 = num2;
            num2 = remain;
            remain = num1%num2;
        }
        return num2;
    }
}
