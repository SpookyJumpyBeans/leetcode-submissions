// 3408. Design Task Manager
// https://leetcode.com/problems/design-task-manager/
// Medium | Java | Accepted 2026-09-11
// Runtime 348 ms | Memory 270.4 MB

class TaskManager {
    Map<Integer, Integer> taskBelongsTo = new HashMap<>();
    Map<Integer, Integer> currentPriority = new HashMap<>();
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
    {
        if(a[0]==b[0])
        {
            return Integer.compare(b[1], a[1]);
        }
        return Integer.compare(b[0], a[0]);
    });

    //We track what users belong to each task id as well as the most up to date priorities that belong to each task id
    public TaskManager(List<List<Integer>> tasks) {
        for(int i = 0; i<tasks.size(); i++)
        {
            int userId = tasks.get(i).get(0); //Get the 3 parameters needed
            int taskId = tasks.get(i).get(1);
            int priority = tasks.get(i).get(2);
            taskBelongsTo.put(taskId, userId); //Pair every taskId with a user
            currentPriority.put(taskId, priority); //Also pair every taskId with a priority (this is to check for outdated taskIds later)
            pq.add(new int[]{priority, taskId}); //Put the priority and taskId onto the priority queue
            //This priority queue is a max heap that first checks the first element and then checks the second to sort the elements
        }
    }

    
    public void add(int userId, int taskId, int priority) {
        taskBelongsTo.put(taskId, userId); //Treat this as just another element in the original constructor
        //Pair the task id with user id and priority and add to priority queue
        pq.add(new int[]{priority, taskId});
        currentPriority.put(taskId, priority);
    }
    
    public void edit(int taskId, int newPriority) { 
        //Update the taskId and priority mapping with this new priority
        //Add this new pair onto the pq
        currentPriority.put(taskId, newPriority);
        pq.add(new int[]{newPriority, taskId});
    }
    
    public void rmv(int taskId) { 
        //Remove the associated taskId key from both user id and priority maps
        taskBelongsTo.remove(taskId);
        currentPriority.remove(taskId);
    }
    
    public int execTop() {
        while(!pq.isEmpty()) //While the pq has elements
        {
            int[] task = pq.poll(); //Poll the top element
            int priority = task[0]; //Get the priority and id of this top element
            int id = task[1];
            if(currentPriority.get(id)!=null && currentPriority.get(id)==priority) //The first check is to check to see if this taskId still exists (it could've been removed) and the second check is to see if the priority associated with the user id on the pq is up to date with the definitive pairing in the map
            //If both are true, then this is the element that needs to be executed next
            {
                int user = taskBelongsTo.get(id); //Get the user associated with this id
                rmv(id); //Call the remove function on this id
                return user; 
            }
        } 
        return -1; //Return -1 if nothing was found
    }
}

/**
 * Your TaskManager object will be instantiated and called as such:
 * TaskManager obj = new TaskManager(tasks);
 * obj.add(userId,taskId,priority);
 * obj.edit(taskId,newPriority);
 * obj.rmv(taskId);
 * int param_4 = obj.execTop();
 */
