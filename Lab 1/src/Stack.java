
/**class Stack creates an array-based data structure that will be used to process Strings
 * from a prefix to postfix expression.
 * 
 * Reference: https://www.youtube.com/watch?v=myve2r_RY1A
 */
public class Stack {
	

	/**Stack constructor for a String array
	 * 
	 * @param height
	 */
	public Stack(int height) {
		maxItems = height;
		arrayStack = new String[maxItems];
		stackTop = 0;
	}
	
	/**boolean test to see if stack is empty.
	 * @return true if empty; false if not empty
	 */
	public boolean isEmpty() {
		
		if (stackTop == 0)
			return true;
		else
			return false;
	}
	
	/**boolean test to see if stack is full
	 * 
	 * @return true if full; false if empty
	 */
	public boolean isFull() {
		if (stackTop == maxItems)
			return true;
		else
			return false;
	}
	
	/**pop top item off the stack and return it; decrement stackTop
	 * 
	 * @return itemOut
	 */
	public String pop() {
		String itemOut = "";
		
		if(stackTop > 0) {
			
			stackTop--;
			itemOut = arrayStack[stackTop];
		}
		
		return itemOut;
	}
	/**push new item to top of the stack; increment stackTop
	 * 
	 *@param itemIn  //new value to add to stack
	 */
	public void push(String itemIn) {
		
		arrayStack[stackTop] = itemIn;
		stackTop++;	
	}
	
	/**get the value of the top item in the stack; stack remains unchanged
	 * 
	 * @return topItem //value of the top item
	 */
	public String peek() {
		String topItem = "";
		
		if (stackTop > 0)
			topItem = arrayStack[stackTop-1];
		
		return topItem;
	}
	
	
	/**determine the index of the top item in the stack
	 * @return stackTop  //int value of top index
	 */
	public int getStackTop() {
		return stackTop;
	}
	
	/**provides stack contents for error checking and debugging
	 *@return s  //String of stack values
	 */
	public String toString() {
		
		String s = "";
		for(int i = stackTop; i > 0; i--) {
			s += arrayStack[i-1] + " ";
		}
		
		return s;
	}
	
	//initialize variables used by Stack class
	private String[] arrayStack;
	private int maxItems;
	private int stackTop;

}
