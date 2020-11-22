.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
_errorCero DB "Error division por cero", 0
_errorNegativo DB "Error valor negativo en la resta" , 0
_ceroDOUBLE DQ 0.0
_ceroULONGINT DD 0
_3 DD 3
_2 DD 2
_1 DD 1
_0 DD 0
_id@main DD ?
.code
START:
Error_Resta_Negativa:
invoke MessageBox, NULL, addr Message Error, addr _errorNegativo, MB_OK
invoke ExitProcess, 0
Error_Division_Cero:
invoke MessageBox, NULL, addr Message Error, addr _errorCero, MB_OK
invoke ExitProcess, 0
MOV EAX, _0
CMP EAX, _1
JAE IF_CMP1
MOV EAX, _2
ADD EAX, _3
MOV _id@main, EAX
JMP IF_THEN1
IF_CMP1:
IF_THEN1:
MOV EAX, _0
CMP EAX, _1
JB IF_CMP2
MOV ECX, _null
MOV EAX, _2
MOV EDX, 0
MUL EAX, null
MOV _id@main, EAX
JMP IF_THEN2
IF_CMP2:
IF_THEN2:
MOV EAX, _0
CMP EAX, _1
JBE IF_CMP3
MOV ECX, _3
CMP 3, _ceroULONGINT
JE Error_Division_Cero
MOV EAX, _2
MOV EDX, 0
DIV ECX
MOV _id@main, EAX
JMP IF_THEN3
IF_CMP3:
IF_THEN3:
MOV EAX, _0
CMP EAX, _1
JA IF_CMP4
MOV EAX, _2
SUB EAX, _3
CMP 2, 3
JB Error_Resta_Negativa
MOV _id@main, EAX
JMP IF_THEN4
IF_CMP4:
IF_THEN4:
invoke ExitProcess, 0
END START