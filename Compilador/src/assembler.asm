.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib.data
.code
START:
MOV EAX, _2
MOV _e, EAX
MOV EAX, _5
MOV _f, EAX
MOV EAX, _e
ADD EAX, _f
MOV _a, EAX
MOV EAX, _f
ADD EAX, _e
MOV _b, EAX
CMP _a, _b
JNE IF_CMP1
MOV _d, 1
FOR_INICIO1:
MOV EAX, _d
MUL EAX, _e
CMP _a, EAX
JA FOR_CMP1
CMP _a, _b
JE IF_CMP2
MOV EAX, _d
ADD EAX, _1
MOV _d, EAX
JMP FOR_INICIO1
FOR_CMP1:
invoke ExitProcess, 0
END START