// 1472. Design Browser History
// https://leetcode.com/problems/design-browser-history/
// Medium | Java | Accepted 2026-09-11
// Runtime 46 ms | Memory 53.6 MB

class BrowserHistory {
    String[] browser = new String[5001];
    int front = -1;
    int curr = -1; 
    //Use a DLL or 2 stacks (One stack is the backpages and contains the current page on the top and another is the frontpage stack. Everytime we go back, we pop the top of the back stack and add it to the frontpage stack and vice versa)
    
    //I used an array and 2 pointers to simulate a DLL
    //One pointer keeps track of the index associated with the very front of the array
    //The other is the current page we're viewing
    
    public BrowserHistory(String homepage) {
        browser[++curr] = homepage; //Increment the curr pointer and then set the value at index 0 equal to the homepage
        front = curr; //Make the front the curr 
    }
    
    public void visit(String url) {
        browser[++curr] = url; //Do the exact same thing as the constructor but now with the url
        front = curr; 
        //Making the front the curr is important because when we visit, we clear the forward history
        //By making the front equal to curr, we now make it impossible to access any elements past curr (which means we can't access any of the forward web pages)
        //This is lazy deletion by just moving the pointer
    }
    
    public String back(int steps) {
        int limit = Math.max(0, curr-steps); //Get the limit of how far we can go back
        //This is the maximum of 0 or curr-steps
        curr = limit; //Set curr to whatever index limit is at
        return browser[limit];
    } 
    
    public String forward(int steps)
    {
        int limit = Math.min(curr+steps, front); //Do the same for forwad except it's the minimum of the front and curr+steps
        curr = limit;
        return browser[limit];
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */
