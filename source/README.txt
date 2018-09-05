							Online-Market-Place
							
The following steps must be involved in order for a RMI program to work properly:
1. In this project RMI Registry will run on port 1986; that is specified by
	String name = "//10.234.136.55:1986/MarketplaceServer";

2. Compile all of the Java files - you can accomplish this through the following command: make

3. Run the RMI Registry in the background - this will run at the port 1986:  make rmi

4. Run the MarketplaceServer by executing the following command:  make server

5. Open a second instance of putty

6. Run the MarketplaceClient by executing the following command:  make client

7. Once you have completed this work please remember to clean up by terminating the RMI Registry. You 
   can bring this process to the foreground through the following command:  % fg
   At which point you can kill the process.

8. To clean the all class files execute the following command:  make clean




- Given Machine for Server is: in-csci-rrpc01.cs.iupui.edu - 10.234.136.55

  others are for customer/admin to access the remote services are: 
	• in-csci-rrpc02.cs.iupui.edu - 10.234.136.56
	• in-csci-rrpc03.cs.iupui.edu - 10.234.136.57
	• in-csci-rrpc04.cs.iupui.edu - 10.234.136.58
	• in-csci-rrpc05.cs.iupui.edu - 10.234.136.59
	• in-csci-rrpc06.cs.iupui.edu - 10.234.136.60

- The server will listen always, will never terminate. To force terminate server please press clt+c.



* For all customer commor password is: 123 and ids are need to take a look in customer table of nschowdh_db (such as: yz, ab, df and so on)
* In DB there are three admin define are:  qw, er, ty ; password: 123
