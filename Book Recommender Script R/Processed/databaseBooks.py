import pandas as pd
import numpy as np
import pymysql

try:
    db = pymysql.connect("localhost","root","2309","recommender")
    cursor = db.cursor();
except:
    print("Connection Error")

df_books = pd.read_csv("processed_books.csv")

# insert query
insert = "INSERT INTO books(bookID,title,authors,ratings,imageURL) VALUES(%s,%s,%s,%s,%s)"

for index, row in df_books.iterrows():
    bookID = row['book_id']
    title = row['title']
    authors = row['authors']
    ratings = row['average_rating']
    imageURL = row['image_url']

    ins = (bookID, title, authors, ratings, imageURL)

    try:
        cursor.execute(insert, ins)
        db.commit()
        print("Successful")
    except:
        print(ins)
        db.rollback()
        print(TypeError)
        print(ValueError)
        print("Exception Occured ")

db.close()
