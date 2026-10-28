// 1166. Design File System
// https://leetcode.com/problems/design-file-system/
// Medium | Java | Accepted 2026-10-28
// Runtime 118 ms | Memory 68.1 MB

class FileSystem {
    Map<String, Integer> fileSys = new HashMap<>();
    public FileSystem() {
        
    }
    
    public boolean createPath(String path, int value) {
        StringBuilder temp = new StringBuilder();
        String[] paths = path.split("/");
        if(paths.length>2)
        {
            for(int i = 1; i<paths.length-1; i++)
            {
                temp.append(paths[i]);
                if(i!=paths.length-2)
                {
                    temp.append("+");
                }
            }
            if(!fileSys.containsKey(temp.toString()) || fileSys.containsKey(temp.append("+").append(paths[paths.length-1]).toString()))
            {
                return false;
            }
            fileSys.put(temp.toString(), value);
        }   
        else
        {
            if(fileSys.containsKey(paths[paths.length-1]))
            {
                return false;
            }
            fileSys.put(paths[paths.length-1], value);
        }
        return true;
    }
    
    public int get(String path) {
        StringBuilder temp = new StringBuilder();
        String[] paths = path.split("/");
        for(int i = 1; i<paths.length; i++)
        {
            temp.append(paths[i]);
            if(i!=paths.length-1)
            {
                temp.append("+");
            }
        }
        return fileSys.containsKey(temp.toString()) ? fileSys.get(temp.toString()) : -1;
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */
