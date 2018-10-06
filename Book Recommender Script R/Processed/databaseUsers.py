import pandas as pd
import numpy as np
import pymysql

try:
    db = pymysql.connect("localhost","root","2309","recommender")
    cursor = db.cursor();
except:
    print("Connection Error")

# Read users csv
df_users = pd.read_csv("processed_users.csv")

# insert query for users

insert = "INSERT INTO users(userID,frequency,auth_key) VALUES(%s,%s,%s)"

for index, row in df_users.iterrows():
    userID = row['user_id']
    frequency = row['freq_of_books_read']
    authKey = 'abc12345'

    ins = (int(userID), int(frequency), authKey)

    try:
        cursor.execute(insert, ins)
        db.commit()
        print('Sucessfull')
    except:
        print(ins)
        db.rollback()
        print(TypeError)
        print(ValueError)
        print('Exception Occurred')
db.close()
