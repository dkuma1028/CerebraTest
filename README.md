## Demo Challenge

#### Instructions
1. Complete the project setup as listed below
2. Complete the Excerise
3. Email a synopsis of your work and the link to your git repo containing the completed exercise to: sqedemonstrationchallenge@nbcuni.com


#### Expectations
We will be evaluating
1. Naming conventions
2. Code readability
3. Code encapsulation
4. Code structure and organization
5. Quality of test cases
6. Variety  of testing types (examples: boundary, happy path, negative, etc) 


#### Technologies
1. Java
2. Selenium
3. TestNG
4. Any other technologies you see fit.
5. Please do not use a BDD framework.

#### Project Setup
1. Clone this project to your git account in a public repo
2. Setup the project in your IDE
3. Open the index.html file from src/test/resource/files in a browser
4. Copy the url from the browser and update the url value in src/test/resource/config.properties to be the copied url.
5. In src/test/resources update the config.properties file platform for your OS.
6. From command line run mvn clean install -U -DskipTests
7. Make sure you can run the DemoTest and chrome launches.  You may need to update the chromedriver in /src/test/resources/chromedriver/ to the version that works with your browser
   https://chromedriver.chromium.org/


#### Exercise
1. Use the site at the index.html
2. There are helper locators provided for you in the src/test/resource/files/locators.txt file.
3. In the Test Cases section below:
  - List all of the test cases you think are necessary to test the sample page
  - Note any defects or issues observed
4. Code up a few examples of:
  - At least one happy path case placing an order
  - At least one error case
5. When complete please check your code into your public git repo

#### Test Cases

1. Place order with different pizza types and validate order amount is calculated correctly
2. Validate order confirmation dialog box for correct ordered pizza name and cost
3. Validate user is able to select 2 different types of toppings based on pizza type selected
4. Validate amount is correctly calculated for order with quantity greater than 1
4. Validate user is able to place order by selecting any one of payment types
5. Place order without entering mandatory fields(name and phone) and validate error message
6. Validate all the entered field values get reset to default after clicking on reset button
7. validate that the maximum quantity that can be ordered is 99999
8. Validate that user can not place order with negative quantity
9. valdiate that quanity value should be numeric. 
10. Validate that cost value is non editable
11. Validate that user can select Pizza and toping only from drop down and can not enter custom values
13. Validate that Name,Phone,Email field allow maximum of 100 characters
14. Validate that email and name field accepts only alphanumeric charcters
15. Validate that phone field accepts only numeric characters
16. Validate that user can select only one payment type at a time
 	


#####  Issues Observed:
1> User is able to select both the payment option and place order. User should not be able to select both radio buttons at same time
2> Reset button is not resetting the topping selected
3> User is able to place order just by entering the name and phone number
4> Able to select toppings for Pizza which does not have any toppings. Similarly, able to select multiple topping for pizza that has just one topping assigned as per the pizza name
5> Phine number field accepts alphabets

####  Automated scenarios
1> Place and order without entering the mandatory field and valdiate the error message
2> Place order with different Pizza type and toppings combination>Validate cost> validate the confirmation dialog box

Note: Have used POM to write locators and initialize them. Would have used locators.txt if it was a properties file
      Have created 2 new packages:
		>Page script which has the actual code(logic and locators) for order creation page
		>Utils package which has common functions
      Have used data provider method to create order with different pizza type, toppings combination
      	
	



