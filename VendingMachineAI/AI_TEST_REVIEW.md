Lauren Conrad  
COSC-442-001  
Fall 2026

**Task 2: AI Test Coverage Analysis**  
**Due:** October 16, 2026, 11:59pm

***Note:** When following up with the AI to ask for better coverage, I never ended up using that code because when I reran the tests I got 100% despite not implementing any changes.* 

**Link to conversation:** [https\://claude.ai/share/800ecabb-83ae-4690-9b09-105f80ec6a08](https://claude.ai/share/800ecabb-83ae-4690-9b09-105f80ec6a08) 

---

**Reflection:**

Claude was much better than I was at creating test cases, and got 100% coverage in a much smaller number of methods than I did. Something notable is that the AI tests won’t always have explicit tests for every method (ex. getItem()), but they will include the target method in other related tests, so it’s still covered. There was also a lot of testing of similar values in one method. So even though there isn’t an explicit parameterized test, similar concepts are going on (for ex. eachCodeMapsToDistinctSlot()).  

What was really beneficial about the AI tests is that the Display Name feature made it much easier to understand the code. The method titles were also easier to understand since they specified what the expected result should be. It got 100% on the first try (although VSCode did not register it that way initially- I feel like the values fluctuate every once in a while for not reason).

 I also did notice that Claude used some unnecessary duplication. For example, regarding the insertMoney method, they repeat code to test for a value of 0 and 0.25, and 5\. There was also a lot of unnecessary importing that could have been done with a single line of Assertions.\*

After completing this exercise, I have a better understanding of AI’s capabilities when creating tests. I think a good, competent AI can produce accurate and beneficial results very quickly. This can be a great starting point for drawing up tests to then compare to VSCode’s test coverage feature. I can not speak on how well the code gets corrected when a user asks for the LLM to make editorial changes. This is because I did not have to follow up.

