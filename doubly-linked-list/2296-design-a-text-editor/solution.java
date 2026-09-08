// 2296. Design a Text Editor
// https://leetcode.com/problems/design-a-text-editor/
// Hard | Java | Accepted 2026-09-07
// Runtime 215 ms | Memory 89.8 MB

class TextEditor {
    StringBuilder t = new StringBuilder();
    int cursor = 0;
    public TextEditor() {
        
    }
    
    public void addText(String text) {
        t.insert(cursor, text);
        cursor+=text.length();
    }
    
    public int deleteText(int k) {
        int delete = Math.max(cursor-k, 0);
        t.delete(delete, cursor);
        int deleted = cursor - delete;
        cursor = delete;
        return deleted;
    }
    
    public String cursorLeft(int k) {
        cursor = Math.max(cursor-k, 0);
        int left = Math.max(cursor-10, 0);
        return t.substring(left, cursor);
    }
    
    public String cursorRight(int k) {
        cursor = Math.min(cursor+k, t.length());
        int left = Math.max(cursor-10, 0);
        return t.substring(left, cursor);
    }
}

/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */
