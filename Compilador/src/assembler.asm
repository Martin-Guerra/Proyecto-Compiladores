.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
_b@main DQ ?
_a@main DQ ?
_a@main@a DQ ?
_3.0 DQ 3.0
.code
START:
a@main: 
MOV EAX, _3.0
MOV _a@main@a, EAX
END
RET
MOV _a@main@a, _b@main
CALL a@main
invoke ExitProcess, 0
END START