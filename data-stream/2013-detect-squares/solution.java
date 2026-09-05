// 2013. Detect Squares
// https://leetcode.com/problems/detect-squares/
// Medium | java | Accepted on NeetCode 2026-09-04

class CountSquares {
    private HashMap<String, Integer> map;

    public CountSquares() {
        map = new HashMap<>();
    }
    
    public void add(int[] point) {
        if(map.containsKey(new String(point[0] + "," + point[1])))
        {
            map.put(new String(point[0] + ","+  point[1]), map.get(new String(point[0] + "," + point[1]))+1);
        }
        else
        {
            map.put(new String(point[0] + ","+  point[1]), 1);
        }
    }

    public int count(int[] point) {
        int count = 0;
        for(String temp : map.keySet())
        {
            String[] split = temp.split(",");
            int x1 = point[0];
            int x2 = Integer.parseInt(split[0]);
            int y1 = point[1];
            int y2 = Integer.parseInt(split[1]);
            if(Math.abs(x1-x2)==Math.abs(y1-y2) && Math.abs(x1-x2)!=0 && Math.abs(y1-y2)!=0)
            {
                if(map.containsKey(new String(x1 + "," + y2)) && map.containsKey(new String(x2 + "," + y1)))
                {
                    count+=map.get(new String(x1 + "," + y2)) * map.get(new String(x2 + "," + y1)) * map.get(temp);
                }
            }
        }
        return count;
    }
}
