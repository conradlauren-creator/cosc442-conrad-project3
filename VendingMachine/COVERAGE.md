Lauren Conrad  
COSC-442-001  
Fall 2026

**Task 1: Test Coverage Analysis**  
**Due:** October 16, 2026, 11:59pm

**Initial Code Coverage Results**

| Source File | Initial Statement/Line Coverage | Initial Branch Coverage |
| :---- | ----- | ----- |
| VendingMachine.java | 97.67% (42/43) | 90.91% (18/20) |
| VendingMachineItem.java | 100% | 100% |

**Final Code Coverage Results**

| Source File | Final Statement/Line Coverage | Final Branch Coverage |
| :---- | ----- | ----- |
| VendingMachine.java | 100% | 100% |
| VendingMachineItem.java | 100% | 100% |

**Tests/Methods Added to Improve Coverage**

1. **Method added to validate item \!= null:**

   public void addItem(VendingMachineItem item, String code)

               throws VendingMachineException {

           int slotIndex \= getSlotIndex(code);

           if (itemArray\[slotIndex\] \!= null) {

               throw new VendingMachineException(SLOT\_MESSAGE \+ code

                       \+ ALREADY\_OCCUPIED\_MESSAGE);

           } else if (item \== null) {

               throw new VendingMachineException("Invalid: Item is empty");

           } else {

               itemArray\[slotIndex\] \= item;

           }

       }

2. **Test added to guarantee item that \== null throws exception**

    @Test  
       void testAddNullItemName() {  
           //ASSERT  
           assertThrows(  
                VendingMachineException.class,  
           () \-\> myVending.addItem(null, "B"));  
       }  
     
       @Test  
       void testAddNullItemCode() {  
           //ASSERT  
           assertThrows(  
               java.lang.NullPointerException.class,  
           () \-\> myVending.addItem(myItem, null));  
       }  
     
3. **Test to make sure slot C is covered (which was never covered in the method getSlotIndex)**  
   @Test  
       void testAddNullItem() {  
           //ASSERT  
           assertThrows(  
               VendingMachineException.class,  
           () \-\> myVending.addItem(null, "C"));  
       }  
     
4. **Corrected makePurchase method to include else statement, so that it returns false**  
   public boolean makePurchase(String code) {  
           boolean returnCode \= false;  
           VendingMachineItem item \= getItem(code);  
               if ((item \!= null) && (this.balance \>= item.getPrice())) {  
               removeItem(code);  
               this.balance \-= item.getPrice();  
               returnCode \= true;  
           } else {  
               returnCode \= false;  
           }  
           return returnCode;  
       }

**Injected Fault**

1. **Balance greater than price**

Original: if ((item \!= null) && (this.balance \>= item.getPrice()))   
After Injected Fault: if ((item \!= null) && (this.balance \> item.getPrice()))

This did not return an error because I did not test what would happen if the price is the same as the balance.

Note: I accidentally put the // INJECTED FAULT FOR COVERAGE EXPERIMENT in two locations. I only injected a single fault as stated above.