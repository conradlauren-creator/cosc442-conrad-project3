## Class: VendingMachine, Method: VendingMachine()
The observed failure:
For loop iterates out of bounds

The test that exposed it:
All tests in VendingMachineTest returned an out of bounds exception

The source-code fault that caused it:
for (int i = 0; i <= NUM_SLOTS; i++) {}

How you diagnosed the fault:
Understanding that all the methods were being affected encouraged me to identify a method that affects all code. This would be the constructor method. Looking at the VendingMachine constructor method, I saw a for loop. If starting at 0, a for loop can not iterate through all of the options while inclduing the last option. This will create an out of bounds error. 

The correction you made:
for (int i = 0; i < NUM_SLOTS; i++) {}
I removed the <= and replaced it with <

## Class: VendingMachine, Method: InsertMoney()

The observed failure:
Money greater than zero but less than one would not be accepted.

The test that exposed it:
void testInsert_LessThanOneGreaterThanZero_Money() {}

The source-code fault that caused it:
In the method insertMoney(), the if statement says if (amount < 1), throw an exception

How you diagnosed the fault:
Reading the Javadocs provided insight on how the program was supposed to function. Any amount of money greater than zero should be accepted, yet the if statement only accepted money greater than one.

The correction you made:
if (amount < 0)

## INJECTED FAULT FOR TEST VALIDATION
## Class: VendingMachine, Method: ReturnChange()

The fault you introduced:
Introduced a fault in return change, where balance is not set to 0 after change is returned.

The test or tests that failed:
@ParameterizedTest 
@ValueSource(ints = {-1000000000, -1, 0, 1, 100, 1000000000})
void testReturnChange(int nums) {
    myVending.balance = nums;
    //ASSERT
    assertEquals(nums, myVending.returnChange(), 0.01);
    assertEquals(0, myVending.balance, 0.01);
}

The relevant JUnit failure message:

AssertionFailedError: expected: [0.0] but was: [-1000000000.0]
AssertionFailedError: expected: [0.0] but was: [-1.0]
AssertionFailedError: expected: [0.0] but was: [1.0]
AssertionFailedError: expected: [0.0] but was: [100.0]
AssertionFailedError: expected: [0.0] but was: [1000000000.0]

A short explanation of why the test detected the fault:
The test detected the fault because it asserted that after the function ran, the balance was equal to 0 as the instructions specified. But due to the bug, the balance was not set to 0.
