# ELEC377 Lab 2: Testing
## Viraj Bangari and Curtis Shewchuk

### Testing plan

In our module file, we created a proc entry called "lab2". To make sure our proc entry was created, we used:
```
ls /proc/lab2
```
to see if our entry was created.

Once that was verified, we wanted to see that our callback was being used. This was done by calling:

```
cat /proc/lab2
```

and by inspecting the outputs. We worked incrementally, by first only writing a short string, then only the headers, then all the proc entries (including pid = 0), and finally all the valid proc entries. When this was done, we verified our program's output by comparing the output of:

```
ps -lax
```
