/******************************************************************************
 *  Name:     Henry Langmack
 *
 *  Partner Name:        N/A
 *
 ******************************************************************************/

Final Project Name:  Calcy

/******************************************************************************
 *  Describe how decided to implement this project. Is it original? or a spin-off
 *  a similar project? If it is the latter, submit a link or documentation to it.
 *****************************************************************************/

It is an original, and it came from inspiration about wondering how calculators can parse complex expressions and come back with correct answers,
precedence included. I wanted to create a calculator "emulator" of sorts which could be like a TI calculator but powered by my own software and algorithms.

/******************************************************************************
 *  Describe step by step how to execute your project successfully.
 * If multiple conditions result in different outputs, describe the steps
* to achieve the different outcomes.
*
******************************************************************************/

1. Download IntelliJ
2. Unzip the .zip folder
3. Run Calcy.java (main) through Intellij IDE

/******************************************************************************
 *  Describe the data types you used to implement  your project
 *
 *****************************************************************************/

Abstract syntax tree - Node interface that I created that different node types inherit from, including binary node, function call node, etc.
Stack - used for the application stack to store the running application at the top of the stack, can pop it off when it is done
Lists - used everywhere in the code, one example is storing the history of the evaluated expressions under the currently typed in one
/******************************************************************************
 *  Known bugs/limitations.
 *****************************************************************************/

One known bug is that sometimes when entering in a very malformed expression the calculator will crash, however this is very rare and I've only experienced it
one or two times. Also, at one other point the recursion depth limit was exceeded and the program crashed then too.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course classmates, and friends)
 *  and attribute them by name.
 *****************************************************************************/

I learned about abstract syntax trees a while ago with the help of my friend Ayush, but no help was given to me for this project in particular.