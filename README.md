# Fetch Take-Home Test - Backend

This README will help you run this simple java project.

Assumptions made about the project:
- file transactions.csv will be provided by you in the correct location
- Negative points in the transactions.csv file equals spending.
  - example : "DANNON",-200,"2020-10-31T15:00:00Z" means that user is spending 200 points and all the rules for spending points will apply to this transaction



## STEP 1 (Only if you have don't have Java installed):
Follow the following tutorial pages to install Java in your PC:  
Windows - https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-do-I-install-Java-on-Windows  
Ubuntu - https://itsfoss.com/run-java-program-ubuntu/  
Mac - https://www.java.com/en/download/help/mac_install.html  

## STEP 2 (cloning the project in your device):  
Assumption : You need to have git setup on your device. If you do not have one you can follow this tutorial : https://www.simplilearn.com/tutorials/git-tutorial/git-installation-on-windows  
- open git bash  
type the following commands in git bash:  
- git clone https://github.com/TakshSoni123/Fetch_take_home_test-backend.git  


## STEP 3 (To setup transactions.csv file in the project):
- Delete the existing dummy transactions.csv file from the project folder  
- Add your test transactions.csv file  

## STEP 4 (To run the program):
- Open command prompt/terminal  
- navigate inside the project folder  
- run command : javac PointsHandler.java  
- run command : java PointsHandler \<Points to be spent\>  


