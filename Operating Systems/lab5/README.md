# Lab 5
## Viraj Bangari 10186046
## Curtis Shewchuk 10189026

# Exploit
## Description
The exploit for this lab is a buffer overflow attack. The idea is to have a function like gets()
read past the end of the allocated buffer size, which will eventually overwrite the return address
on the stack. By overwriting the return address, we can make the CPU execute any arbitrary location in memory.
If our buffer contains compiled x86 binaries, we can point the PC to the first instruction.

The code we include in our exploit echoes the contents of the `/etc/passwd` file. The trap `0x0b`
calls the `execve` syscall, which allows us to run a shell command. The shell command we want to run
is `sh -c "cat /etc/passwd; exit`. We can do this by passing `0x0b` in `eax`, a pointer to `bin/sh` in `ebx`,
the address of a array of string pointers containting `"bin/sh", "-c", "cat /etc/passwd; exit"` to `ecx` and
setting `edx` to `0`.

The full exploit is located in the file `exploit.nasm`. Some tricks were used to make sure that there were no
null or newline characters in the generated assembly. This includes using the `byte` operator, using `xor reg, reg`
to zero out a register, etc.

The exploit was compiled into a binary called `exploit`. We used the command `xxd exploit` to view the hexcodes of the binary which
was piped into sed to replace the spaces with commas, and was then saved to the file hexcode.txt. The contents of hexcode.txt were copy
pasted into our selfcomp and client files.

## Implementing the exploit in selfcomp.c

To implement the exploit, we used a buffer filled with `x` characters, terminating with `WXYZ` and a null byte.
We kept increasing the number of characters in the buffer until a segfault are core dump were generated. We looked at the registers and
kept track of the stack pointer and the program counter. The stack pointer was equal to `0xbffff420`. We kept
padding `x` characters until the program counter was equal to `0x5a595857` (`WXYZ` in little endian format). The size of the array required
to overflow return address is 145.

Since the size of our exploit was 97 bytes,
we padded out assembly code with `145 - 97 characters - 1 null byte - 4 return address bytes = 43 no-op (0x90)`
instructions. We also changed the last four bytes (not including null) of our exploit to be
`0xbffff420 - 0x61 - 0x04 = 0xbffff3bb`. This was written in reverse in the actual code due to endianness. When we
ran `selcomp`, we saw the contents of the `/etc/passwd` file printed on the screen.

## Implementing the exploit in client.c

A similar approach was used to implement the exploit in `client.c`. We ran the server at port 10000, and kept sending a string
of `x` characters terminated with `WXYZ` and a newline until the server would segfault. Once the server segfaulted, we kept track of
the stack pointer when the program counter was equal to `0x5a595857`. In this case, the stack pointer was equal to `0xbffff390` and
the size of the exploit was 129.

As before, we padded `129 - 97 characters - 1 newline byte - 4 return address bytes = 27 no-op (0x90)` instructions to the exploit.
We all set the last four bytes, not including the newline, to `0xbffff390 - 0x61 - 0x04 = 0xbffff32b`. When the client was run at
port 10000, we saw the password file printed in the console.


## Sample pseudocode buffer with exploit
```
char compromise[N] =
{
	// Padding bytes.
	0x90, 0x90, 0x90, ...
	// The exploit itself in hex. This was found 
	0x90, 0xeb, 0x29, 0x5e, ..., 0xff,
	// The new return address in little endian format.
	// In this example, we want the return address to be
	// 0xbffff3bb
	0xbb, 0xf3, 0xff, 0xbf,
	// Null terminating character. Newline can also be used here.
	0x00
};
```