default:
	javac -cp ".:./mysql-connector.jar" *.java 

rmi:
	rmiregistry 1986&
server:
	java -Djava.security.policy=policy -cp ".:./mysql-connector.jar" MarketplaceServer 
client:
	java -Djava.security.policy=policy MarketplaceClient
clean:
	rm *.class
