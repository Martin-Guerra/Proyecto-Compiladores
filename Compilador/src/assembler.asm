.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.STACK 200h
.data
_errorCero DB "Error division", 0
_errorNegativo DB "Error resta" , 0
_ceroDOUBLE DQ 0.0
_ceroULONGINT DD 0
_r@main DQ ?
_m@main@z@c DQ ?
_g@main@z DQ ?
_D0 DQ 2.0
_a@main DQ ?
_a@main@z@c DQ ?
.code
Error_Resta_Negativa:
invoke MessageBox, NULL, addr _errorNegativo, addr _errorNegativo, MB_OK
invoke ExitProcess, 0
Error_Division_Cero:
invoke MessageBox, NULL, addr _errorCero, addr _errorCero, MB_OK
invoke ExitProcess, 0
START:
FNINIT
d@main@z@c: 
FLD _r@main
FSTP _a@main
END
RET
c@main@z: 
FLD _D0
FSTP _a@main
END
RET
d@main@z: 
FLD _r@main
FSTP _a@main
END
RET
z@main: 
END
RET
d@main: 
FLD _r@main
FSTP _a@main
END
RET
FLD _a@main
FSTP _g@main@z
CALL z@main
invoke ExitProcess, 0
END START