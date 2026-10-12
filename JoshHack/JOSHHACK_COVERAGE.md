Lauren Conrad  
COSC-442-001  
Fall 2026

**Task 3: JoshHack Test Coverage Analysis**  
**Due:** October 16, 2026, 11:59pm

Link to Conversation: [https\://claude.ai/share/4951c8b2-6062-4fd1-a89a-7612df5ebc56](https://claude.ai/share/4951c8b2-6062-4fd1-a89a-7612df5ebc56) 

**Baseline/Final Coverage**:  
For Inventory.java, I ran it through the LLM to test its ability to create test cases, and it succeeded in making them with 100% statement and branch coverage on the first try. 

Since I felt that was too simple, I analyzed the coverage of the 5 test cases I made for the 2 methods in Creature.java (methods: modifyAttackValue() and isPlayer()).In this case, the creation of a Creature object was fully covered. In the method modifyAttackValue(), all of the method’s content was covered except for the return statement. That was only partially executed.   
The isPlayer() method was fully covered.

**Description of Cases Added (Inventory.java):**  
Although the AI created test cases that have full coverage, I honestly believe it went a little overboard because of just how many methods and possible variations it created. It created 33 functions, even though there were only 7 in the original Inventory.java. And 3 of the 33 were not even reached. It also tests for things that are not specified (due to a lack of commenting), such as whether the same item can be added twice. Although these could be necessary, I don’t know if it is beneficial to test for cases where the requirements are not fully specified yet.  
There were also some tests created that I did not think were necessary, such as checking if isFull is false for an item that was never added and also in cases where an item has been removed.   
This should already be covered conceptually in removeItem tests.

In my Inventory.java test cases, I covered constructing inventory items, as well as returning items at specific indices. For every test I created, including the rest I am going to discuss, I designed both valid and invalid test cases. I tested adding items to both full and empty inventories, as well as testing the removal of items. When there is a failure in these cases, both adding and removal result in nothing happening. I’m not sure if this is expected or not, since Inventory.java does not specify what to do in invalid cases.

My code tests fullness for empty, partially full, zero-capacity, and full inventories.  
My test cases validate whether an item exists in the cases of an empty list, lists where it does exist, where it doesn’t, and when the targeted item is at the end of the list. My final method tests that when a null item is added, nothing happens.

**Coverage Gaps:** None