# synchrony-test

There is an API works as an interface for application 
used to do CRUD operations on employee database.

<br>
Here, we add employees there are two classes to manage employee, 
one we can say legacy manager '<b>EmployeeManager</b>' which manages employee in brute force and in simple manner.
Simple retrieval everytime from database.

<br>
<br>

Then, we have one '<b>EmployeeSmartManager</b>'. It uses multithreading for 
adding employees, we use <b>threadPoolExecutor</b> (AsyncConfig Class) along with <b>async</b> provided 
by spring. Used <b>CompletableFuture</b> and <b>countDownLatch</b> to match with different threads 
to return response only when all employees added to database. For retrieval, we use redis here.

Using redis for caching: When employees info retrieved, first time it fetches from database and after
that we get it from redis cache only.

We can also create unit test and integration test to check that our functionality working fine or not
by specifying them in test package.

We can separated out code into package and modules for more robust and maintainable code.


