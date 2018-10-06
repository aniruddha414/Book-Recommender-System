# import needed libraries

library(dplyr)
library(tidyr)
library(stringr)

# Read books.csv and ratings.csv from raw data folder

books <- read.csv(
  file = "E:/Book Recommender Script R/Raw/books.csv",
  header = TRUE,
  sep = ",",
  quote = "\""
)

#head(books)

# Read ratings.csv from raw data folder

df_ratings <- read.csv(
  file = "E:/Book Recommender Script R/Raw/ratings.csv",
  header = TRUE,
  sep = ",",
  quote = "\""
)
#head(df_ratings)

#cleaning books.csv

# Clean Title and Authors

books <- books %>% 
  mutate(title = str_trim(str_extract(title, '([0-9a-zA-Z]| |\'|,|\\.|\\*)*')),
         title_length = str_count(title, " ") + 1) %>%
  mutate(authors = str_trim(str_extract(authors, '([a-zA-Z]| |\'|,|\\.|\\*)*')),
         authors_length = str_count(title, " ")+1)

df_books <- select(
  .data = books,
  book_id,
  title,
  authors,
  language_code,
  books_count,
  goodreads_book_id,
  average_rating,
  ratings_count,
  image_url,
  small_image_url
)

#head(df_books) #// invalid characters removed from title and authors

# remove duplicate entries

#str(df_books) #10k entries

df_books <- df_books[-which(duplicated(df_books$title)),]
str(df_books) #9544 entries
# changing the language code

engCode <- c("","en","en-CA","en-GB","en-US","eng")

df_books[df_books$language_code %in% engCode,c(4)] <- "en"

# remove books of other languages . Considering only english ! 
df_books <- df_books[-which(!(df_books$language_code %in% engCode)),] # 

#str(df_books) # 9427 books are present

#remove books whose ratings are less than 3.50

df_books <- df_books[-which(df_books$average_rating < 3.50) , ]

# present books ids
presentBookId <- df_books[df_books$book_id,c(1)]

# drop the book ratings that are not in PresentBookId
df_ratings <- df_ratings[-which(!(df_ratings$book_id %in% presentBookId )),]

str(df_ratings)

# sort ratings on basis of user id

df_ratings <- arrange(
  .data = df_ratings,
  user_id
)

# users data frame with frequency of book rated
df_booksPerUser <- as.data.frame(table(df_ratings[,c(1)])) # check for users read <3
# rename  columns
colnames(df_booksPerUser)[1] <- "user_id"
colnames(df_booksPerUser)[2] <- "freq_of_books_read"

# for recducing time consider 5% of users
set.seed(1)
user_fraction <- 0.05
users <- unique(df_ratings$user_id)
sample_users <- sample(users, round(user_fraction * length(users)))

is.vector(sample_users)

# remove those entries from df_booksPerUser which are not present in sample users
df_booksPerUser <- df_booksPerUser[-which(!(df_booksPerUser$user_id %in% sample_users)),]
df_booksPerUser <- arrange(.data = df_booksPerUser,df_booksPerUser$user_id)

#remove those entries from df_ratings which are not present in sample users
df_ratings <- df_ratings[-which(!(df_ratings$user_id %in% sample_users)),]
df_ratings <- arrange(.data = df_ratings,
                      df_ratings$user_id)

# remove books not present in df_ratings from df_books

presentBooksRatings <- unique(df_ratings$book_id) # 7751 books present
df_books <- df_books[-which(!(df_books$book_id %in% presentBooksRatings)),]

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


