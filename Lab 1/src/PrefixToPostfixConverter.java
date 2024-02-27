import java.io.*;

/** PrefixtoPostfixConverter converts a provided prefix expression to the corresponding
 * postfix expression.  Prefix expressions are provided via an input file and the result
 * is printed to an output file.  The program checks that the input is in the correct
 * prefix form and prints errors if encountered.  The program also tracks and prints
 * the number of prefix expressions entered.
 * 
 * @version 1.0 / 26 Feb 2024
 * @author David Blossom
 * 
 */
public class PrefixToPostfixConverter {

	/** main() validates the command line input and contains the program driver.   If an
	 * incorrect number of arguments was entered, main() prints an error and prompts the
	 * user for correct input. 
	 * @param args[]  //input and output file names
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		//validate correct number of arguments was entered
		if (args.length < 2)
			System.out.println("Error: Please enter a valid input and output file.");
		
		//create PrefixToPostfixConverter object
		PrefixToPostfixConverter converter = new PrefixToPostfixConverter();
		
		//call processFiles() to process input and create output
		converter.processFiles(args[0], args[1]);
	}  //end main()
				
	/** processFiles() opens the input file, reads the input, calls processInput() to evaluate
	 * the input, and prints the result to the output file.
	 * @param inputFileName  //input file
	 * @param outputFileName  //output file
	 * @throws FileNotFoundException
	 */
	public void processFiles(String inputFileName, String outputFileName) throws FileNotFoundException {
		
		//create File object for input
		File fileIn = new File(inputFileName);
		
		//create File object for output
		File fileOut = new File(outputFileName);
		
		//initialize input and output String variables
		String newInput = null;
		String outputStr = null;
		
		//initialize counter for tracking the number of input expressions
		int counter = 0; 
		
		//verify input file exists; provide error if not found
		if (!fileIn.exists())
			System.out.println("Error: Input file not found.");
		
		//if input file exists, begin to process the file
		else
			try {
				
				//create reader and writer objects
				BufferedReader reader = new BufferedReader( new FileReader( fileIn ));
				PrintWriter writer = new PrintWriter(new FileWriter( fileOut ));
				
				//print header information to output file
				writer.printf( "%-30s %-30s \n\n", "Input Expression", "Output Expression");
				
				//begin reading input
				while ((newInput = reader.readLine()) != null) {
					
					//verify input; check that input is less than 30 characters and able to print
					if (newInput.length() > 30) {
						error = "Input is too long to process";
						writer.printf( "%-30s %-30s \n", error, "");
					}
					
					//verify input; check that left-most character is a valid operator
					else if (!checkForOperator(newInput.charAt(0))) {
						
						error = "Invalid prefix expression; invalid left-most operator";
						writer.printf( "%-30s %-30s \n", newInput, error);
					}  // end if
					
					//verify input; check that right-most character is not an operator
					else if (checkForOperator(newInput.charAt(newInput.length() - 1))) {
					
						error = "Invalid prefix expression; operator in right-most position";
						writer.printf( "%-30s %-30s \n", newInput, error);
					} //end else if

					//verify input; check that numerals are not present in the input
					else if (!verifyOperands(newInput)) {
						
						error = "Invalid numeral in the prefix expression";
						writer.printf( "%-30s %-30s \n", newInput, error);
					} //end else if
					
					//if no errors, call to processInput() and then print to output
					else {
						newInput = newInput.replaceAll("\\s", ""); //remove whitespaces
						outputStr = processInput(newInput);
						writer.printf( "%-30s %-30s \n", newInput, outputStr);		
					} //end else
					
					//track number of times the while loop executes
					counter++;
				}  //end while
				
				//print number of lines processed
				writer.print( "\n\nExpressions entered: " + counter);
				
				//close reader and writer objects
				reader.close();
				writer.close();
	
			  //Print error if unable to read or write to files
			} catch (IOException e) {
				System.out.println( "Error: Unable to read or write to file" );
			}
	}  //end processFiles()
	
	/** processInput() receives the input from processFiles(), initializes Stack objects
	 * that will be used for processing the input, and further validates the input.  If
	 * the input was invalid, appropriate errors are printed to output.
	 * 
	 * @param strToConvert  //String from input file in prefix
	 * @return error  //custom error message
	 * @return auxStack.peek()  //String of the input converted to postfix
	 */
	public String processInput(String strToConvert) {
		
		//create Stack objects of size equal to the length of the input String
		Stack mainStack = new Stack(strToConvert.length());
		Stack auxStack = new Stack(strToConvert.length());
		
		//check that the Stack is empty before pushing any data to it
		if (!mainStack.isEmpty()) {
			return error = "This stack cannot be used.";
		}  // end if
		
		/*Parse the input String to individual chars.  Each char is then converted back to 
		 *a String of a single character and pushed to mainStack. 
		 */
		for (int i = 0; i < strToConvert.length(); i++) {
			
			char strChar = strToConvert.charAt(i);
			String strValue = "" + strChar;
			
			mainStack.push(strValue);
		}  //end for

		/*Iterate through mainStack to convert the provided input from prefix to postfix.
		 *If an operator is encountered and there are two items in auxStack, pop the two
		 *items, pop the operator, concatenate them, and push the new String to auxStack.
		 *If those conditions are not met, pop the item from mainStack and push to auxStack.
		 *
		 *Reference: https://www.geeksforgeeks.org/prefix-postfix-conversion/ 
		 */
		while (!mainStack.isEmpty()) {	
			
			if (checkForOperator(mainStack.peek().charAt(0))
					&& auxStack.getStackTop() >= 2) {
				
				String op1 = auxStack.pop();
				String op2 = auxStack.pop();
				
				String temp = op1 + op2 + mainStack.pop();
				auxStack.push(temp);
			}  //end if
			
			else {
				
				String op = mainStack.peek();
				auxStack.push(op);
				mainStack.pop();
			}  //end else
		}  //end while
		
		/*If there are two or more items in auxStack and the top item is not an operator,
		 *there were too many operands in the prefix expression.  Provide error to print.
		 */
		if (auxStack.getStackTop() >= 2 
				&& !checkForOperator(auxStack.peek().charAt(0))) {
			error = "Invalid prefix expression; too many operands";
			return error;
		}  //end if
		
		/*If the top item in auxStack is an operator, there were too many operators in the
		 * prefix expression. Provide error to print.
		 */
		else if (checkForOperator(auxStack.peek().charAt(0))) {
			error = "Invalid prefix expression; too many operators";
			return error;
		}  //end else if
		
		/*if either of the above two conditions were not met, the prefix expression was valid
		 *and it was converted to a postfix expression.  Return auxStack.peek() which is a 
		 *String of the postfix expression.
		 */
		else
			return auxStack.peek();
	}  //end processInput()
	
	/**checkForOperator() receives a char and determines if it is a valid operator or not
	 * @param charFromStr
	 * @return true or false
	 * 
	 * Reference: https://www.geeksforgeeks.org/prefix-postfix-conversion/
	 */
	public boolean checkForOperator(char charFromStr) {
		
		switch (charFromStr) {
			case '+':
			case '-':
			case '/':
			case '*':
			case '$':
			case '^':
			return true;
		}
		return false;
	}  //end checkForOperator()
	
	/**verifyOperands() receives a String and determines if it is a numeral or not.
	 * @param inputStr
	 * @return true or false
	 */
	public boolean verifyOperands(String inputStr) {
		
		int n = inputStr.length();
		
		for (int i = 0; i < n; i++) {
			
			switch(inputStr.charAt(i) ) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				return false;
			}	
		}
		return true;
	}  //end verifyOperands()
	
	//String variable for error message
	private String error;
		
}  //end PrefixToPostfixConverter
