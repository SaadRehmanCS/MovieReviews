# MovieReviews
This project was part of an assignment I received in a first year CS course where I was required to implement 
hashMaps and hashSets to create an algorithm capable of running the program in terms of big O notation O(n). 
While I also implemented try-catch methods, a Comparable interface, and a File reader to read a local file, the real challenge 
was improving the efficiency of this program to be able to read and plot roughly 8000 reviews made up of about 180,000 words
in total, into a hash table that simultaneously stored the total occurrences of each word, along with the average score of each
word.
The average score of each word is given by the totalled scores of the sentence in which this particular word would appear,
divided by the total occurrences of that word.
