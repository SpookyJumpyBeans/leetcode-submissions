// 621. Task Scheduler
// https://leetcode.com/problems/task-scheduler/
// Medium | Java | Accepted 2025-12-04
// Runtime 54 ms | Memory 48.1 MB

class Solution {
    class Task implements Comparable<Task>
    {
        public int freq;
        public char task;
        public Task(int freq, char task) 
        {
            this.freq = freq;
            this.task = task;
        }

         public int compareTo(Task other) {
            return Integer.compare(this.freq, other.freq);
        }

        public String toString()
        {
            return freq + " " + task;
        }
    }

    class Cooling{
        int timeReady;
        Task task;
        
        public Cooling(int t, Task task)
        {
            timeReady = t;
            this.task = task;
        }
    }
    public int leastInterval(char[] tasks, int n) {
        PriorityQueue<Task> queue = new PriorityQueue<>(Collections.reverseOrder());
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<tasks.length; i++)
        {
            if(!map.containsKey(tasks[i]))
            {
                map.put(tasks[i], 1);
            }
            else
            {
                map.put(tasks[i], map.get(tasks[i])+1);
            }
        }
        for(char key : map.keySet())
        {
            queue.add(new Task(map.get(key), key));
        }
        int time = 0;
        Queue<Cooling> cooldownQueue = new LinkedList<>();
        while(!queue.isEmpty() || !cooldownQueue.isEmpty())
        {
            while(!cooldownQueue.isEmpty() && cooldownQueue.peek().timeReady <= time)
            {
                queue.add(cooldownQueue.poll().task);
            }
            if(!queue.isEmpty())
            {
            
            Task temp = queue.poll();
            temp.freq--;
            if(temp.freq!=0)
            {
                cooldownQueue.add(new Cooling(time+n+1, temp));
            }
        }
            time++;
        }
        return time;
    }
}
