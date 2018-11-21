<<<<<MonitorHub>>>>>
To start monitoring your application (here a spring-boot application) following needs to be done:
> Add this agent as a dependency in your pom.xml file for maven dependency

					<dependency>
			         <groupId>com.stackroute</groupId>
						<artifactId>agent</artifactId>
						<version>0.0.1-SNAPSHOT</version>
					</dependency>

> Along with this distribution manager

					<distributionManagement>
              	   <snapshotRepository>
               		<id>snapshots</id>
                  	<name>e8c44ebf2442-snapshots</name>
                  	<url>http://localhost:8081/artifactory/libs-snapshot-local</url>
                  </snapshotRepository>
        			</distributionManagement>

> Add this setting.xml file to your local repository(m2 folder) from ""

> Then repackage/rebuild your application

> Provide ip along with port number where your application is running

> Then the data metrics can be displayed on UI "http://52.66.184.4:4200"\