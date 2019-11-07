# Lab 3 Testing 
## Curtis Shewchuk SN: 10189026
## Viraj Bangari   SN: 10186046


For our testing plan, we have four input files, one for each producer. The files are `proc{PID}.txt`

The contents of each file contain a unique set of seven contigious alphabet in increasing order. This allows us to check that all data was sent (the contents from each file must be consumed by the consumer at some point) and that the order is correct (there must be four sets of increasing subsequences, one for each producer). The producers were created using the `start_producers` script which contains the following code:

```
./meminit
./producer 0 < proc0.txt &
./producer 1 < proc1.txt &
./producer 2 < proc2.txt &
./producer 3 < proc3.txt &
```

After that, the command `./consumer 4` was ran. The output of this command was:

```
abcdefg
hijklmn
ABCDEFG
HIJKLMN
```

Since each character from the input files were printed, we know that all the data was sent. Since the printed characters are in order, we know that the data was recieved in order. Therefore, we can say that our producers and consumer are working as expected.
