<b>RU01[Run application]</b> As a USER I want to run application so that I can connect to database and view and edit data.<br>
RU01.1[Application runs successfully]  I see welcoming message from application <br>
RU01.2 [List of commands] As a USER I want to see list of commands. I use command ‘help’ .I see list of commands

<b>CO01 [Connect to database]</b> As a USER I want to connect to database so that I can view and edit data.<br>
CO01.1 [Successful] I see welcoming message from application. I use command ’connect’ and input database name, user name and password. I see confirmation that I connected to database<br>
CO01.1 [Wrong password or name] I see welcoming message from application. I use command ’connect’ and input database name, user name and password (wrong). I see message that password or user name is wrong. Application requests to enter correct name/password. <br>
I use command ’connect’ and input database name, user name and password(correct). I see confirmation that I connected to database
CO01.2 [Wrong database] I see welcoming message from application I use command ’connect’ and input database name (wrong), user name and password. I see message that database doesn’t exist . Application requests to enter existent database name. 

<b>LI01 [List of tables]</b> As a USER I want to see list of tables in database so that I can view and edit data.<br>
LI01.1 [List of tables] See that I have connection to database. I use command ‘tables’. I see list of tables in the format "[tableName1, tableName2, tableName3]"<br>
LI01.2 [Wrong command] See that I have connection to database. I use command ‘tbl’. I see message about wrong command. 

<b>VI01 [View table data]</b> As a USER I want to see data of table so that I can view and edit data.<br>
VI01.1 [View all data of the table] See that I have connection to database. I use command ‘find’ <tablename>. I see data in formatted view.<br>
VI01.2 [Wrong table name] See that I have connection to database. I use command ‘find’ <tablename>(wrong). I see message that table doesn’t exist. 

<b>CR01 [Create table] </b>As a USER I want to create table so that I can store data.<br>
CR01.1 [Create table] See that I have connection to database. I use command ‘create’ <tablename> with list of columns. I see confirmation that table is created.<br>
CR01.2 [Create table with existent name] See that I have connection to database. I use command ‘create’ <tablename>(wrong). I see message that table can’t be created 

<b>DR01 [Delete table] </b>As a USER I want to delete table so that I can erase data.<br>
DR01.1 [Delete table] See that I have connection to database. I use command ‘drop’ <tablename>. I see confirmation that table is deleted.<br>
DR01.2 [Delete non-existent table] See that I have connection to database. I use command ‘drop’ <tablename>(wrong). I see message that table doesn’t exist

<b>CL01 [Clear table]</b> As a USER I want to clear table data so that I can have empty table.<br>
CL01.1 [Delete table] See that I have connection to database. I use command ‘clear’ <tablename>. I see confirmation that table is cleared.<br>
CL01.2 [Delete data in non-existent table] See that I have connection to database. I use command ‘clear’ <tablename>(wrong). I see message that table doesn’t exist

<b>UP01 [Update table] </b>As a USER I want to update table data so that I can have corrected data.<br>
UP01.1 [Update existent table] See that I have connection to database. I use command ‘update’ <tablename>. I see confirmation that table is updated.<br>
UP01.2 [Update data in non-existent table] See that I have connection to database. I use command ‘update’ <tablename>(wrong). I see message that table doesn’t exist

<b>IN01 [Insert data in table] </b>As a USER I want to add data to table so that I can have data stored.<br>
IN01.1 [Insert data in existent table] See that I have connection to database. I use command ‘insert’ <tablename>. I see confirmation that data is inserted.<br>
IN01.2 [Update data in non-existent table] See that I have connection to database. I use command ‘insert’ <tablename>(wrong). I see message that table doesn’t exist

<b>DE01 [Delete data from table]</b> As a USER I want to delete some data from table so that I can have correct data.<br>
DE01.1 [Delete data from table] See that I have connection to database. I use command ‘delete’ <tablename>. I see confirmation that data is deleted.<br>
DE01.2 [Delete data from non-existent table] See that I have connection to database. I use command ‘delete’ <tablename>(wrong). I see message that table doesn’t exist

<b>EX01[Exit application]</b> As a USER I want to exit application.<br>
EX01.1[Exit application]  I use command ‘exit’ . I see confirmation that I exit application.







