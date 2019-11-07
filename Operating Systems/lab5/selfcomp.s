	.file	"selfcomp.c"
	.text
.globl main
	.type	main, @function
main:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$8, %esp
	andl	$-16, %esp
	movl	$0, %eax
	subl	%eax, %esp
	call	doTest
	subl	$12, %esp
	pushl	$0
	call	exit
	.size	main, .-main
.globl compromise
	.data
	.align 32
	.type	compromise, @object
	.size	compromise, 45
compromise:
	.byte	104
	.byte	105
	.zero	43
	.section	.rodata
	.align 32
.LC0:
	.string	"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxWXYZ"
.globl compromise1
	.data
	.align 4
	.type	compromise1, @object
	.size	compromise1, 4
compromise1:
	.long	.LC0
	.text
.globl doTest
	.type	doTest, @function
doTest:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$136, %esp
	movl	$0, i
.L3:
	movl	i, %eax
	addl	compromise1, %eax
	cmpb	$0, (%eax)
	jne	.L6
	jmp	.L2
.L6:
	leal	-136(%ebp), %eax
	movl	%eax, %edx
	addl	i, %edx
	movl	i, %eax
	addl	compromise1, %eax
	movb	(%eax), %al
	movb	%al, (%edx)
	incl	i
	jmp	.L3
.L2:
	leave
	ret
	.size	doTest, .-doTest
	.comm	i,4,4
	.section	.note.GNU-stack,"",@progbits
	.ident	"GCC: (GNU) 3.3.4"
