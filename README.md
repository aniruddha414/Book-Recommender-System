# Book-Recommender-System
1. Create Database names recommender in mysql.
2. Create table users books and users.(check for schema in server.java code present in src folder of Book Recommender)
3. Then run python script files(databaseUsers.py and databaseBooks.py) present processed folder in Book Recommender Script R. (make sure you have pandas,numoy,pymysql python packages installed)
4. import Rserve library in R console and call Rserve() in console
5.Run server.java
6.Run TrialUI and enter userId and password (password : abc12345)
7.If credentials are valid than recommendations are displayed in next window.
8.Check for valid userIDs from processed_users.csv or database.

Dependencies:
  make sure java and R are installed on your machine.
  
  Packages needed in R:
  1. dplyr
  2. tidyr
  3. recommenderlab
  4. Rserve
  5. Matrix
  6. stringr
  
  note: 5% users of original dataset are used due to computational limitation of machine.
  
  This was simple recommmender based on concept of user based collaborative filtering.
  
  Dataset credits : https://github.com/zygmuntz/goodbooks-10k
                    visit above url for original dataset.Only books.csv and ratings.csv were used in this project. 
