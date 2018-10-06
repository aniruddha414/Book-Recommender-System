library(dplyr)
df_books <-  read.csv(
    file = "E:/Book Recommender Script R/Processed/processed_books.csv",
    header = TRUE,
    sep = ",",
    quote = "\""
  )
df_ratings  <-  read.csv(
  file = "E:/Book Recommender Script R/Processed/processed_ratings.csv",
  header = TRUE,
  sep = ",",
  quote = "\""
) 
# present books ids
presentBookId <- df_books[df_books$book_id,c(1)]

# drop the book ratings that are not in PresentBookId
df_ratings <- df_ratings[-which(!(df_ratings$book_id %in% presentBookId )),]

df_ratings <- arrange(
  .data = df_ratings,
  user_id
)

# users data frame with frequency of book rated
df_booksPerUser <- as.data.frame(table(df_ratings[,c(1)])) # check for users read <3
# rename  columns
colnames(df_booksPerUser)[1] <- "user_id"
colnames(df_booksPerUser)[2] <- "freq_of_books_read"

# write users,books and ratings to csv files

write.csv(
  x = df_books,
  file = "E:/Book Recommender Script R/Processed/processed_books.csv",
  row.names = FALSE
)

write.csv(
  x = df_booksPerUser,
  file = "E:/Book Recommender Script R/Processed/processed_users.csv",
  row.names = FALSE
)

write.csv(
  x = df_ratings,
  file = "E:/Book Recommender Script R/Processed/processed_ratings.csv",
  row.names = FALSE
)

