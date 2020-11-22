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
_h@main DD ?
_f@main DD ?
_e@main DD ?
_d@main DD ?
_c@main DD ?
_z@main DD ?
_y@main DD ?
_b@main DD ?
_x@main DD ?
_a@main DD ?
_1 DD 1
.code
START:
Error_Resta_Negativa:
invoke MessageBox, NULL, addr Message Error, addr _errorNegativo, MB_OK
invoke ExitProcess, 0
Error_Division_Cero:
invoke MessageBox, NULL, addr Message Error, addr _errorCero, MB_OK
invoke ExitProcess, 0
MOV EAX, _a@main
SUB EAX, _b@main
CMP a@main, b@main
JB Error_Resta_Negativa
MOV EBX, _c@main
ADD EBX, _1
CMP EAX, EBX
JBE IF_CMP1
MOV EAX, _b@main
ADD EAX, _c@main
MOV _a@main, EAX
JMP IF_THEN1
IF_CMP1:
MOV EAX, _b@main
SUB EAX, _c@main
CMP b@main, c@main
JB Error_Resta_Negativa
MOV _a@main, EAX
IF_THEN1:
invoke ExitProcess, 0
END START