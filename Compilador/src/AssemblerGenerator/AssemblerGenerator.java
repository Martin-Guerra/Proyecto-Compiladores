package AssemblerGenerator;

//ULONGINT: registros del procesador (EAX, EBX, ECX Y EDX o AX, BX, CX y DX) y seguimiento de registros.
//DOUBLE: co-procesador 80X87, y variables auxiliares.

import SymbolTable.SymbolTable;
import SymbolTable.Use;
import SyntacticTree.SyntacticTree;

import java.util.List;

public class AssemblerGenerator {

    private SyntacticTree tree;
    private String assemblerHeader = "";
    private String assemblerData = "";
    private String assemblerCode = "";

    public AssemblerGenerator(SyntacticTree tree) {
        this.tree = tree;
    }

    //Variables auxiliares
    //Recorre el subárbol de más a la izquierda con hijos hojas
    //(Si se trata de un operador unario, será un subárbol con un solo hijo hoja)
    //Se genera código para el subárbol, creando una variable auxiliar
    //Guarda en la tabla de símbolos la variable auxiliar
    //Se reemplaza el subárbol por la variable auxiliar donde quedó el resultado de la operación.

//Registros
    //Previo a la generación de código para un subárbol, se debe verificar qué registro está disponible
    //Se utiliza una tabla de ocupación de registros
    //Luego de generar código para el subárbol, se marcará el registro que quedó ocupado por el resultado
    //de la operación
    //Se reemplaza el subárbol por el registro donde quedó el resultado.

    private void concatenateMainHeader() {
        this.assemblerHeader += ".386" + '\n' + ".model flat, stdcall" + '\n' + "option casemap :none" + '\n' +
        "include \\masm32\\include\\windows.inc" + '\n' + "include \\masm32\\include\\kernel32.inc" + '\n' +
        "include \\masm32\\include\\user32.inc" + '\n' + "includelib \\masm32\\lib\\kernel32.lib" + '\n' +
        "includelib \\masm32\\lib\\user32.lib" + '\n';
    }

    private void concatenateDataSection(SymbolTable st) {
        this.assemblerData += ".data" + '\n';
        this.assemblerData += "_errorCero" + " DB " + "\"Error division por cero\", 0" + '\n';
        this.assemblerData += "_errorNegativo" + " DB " + "\"Error valor negativo en la resta\" , 0" + '\n';
        this.assemblerData += "_ceroDOUBLE DQ 0.0" + '\n';
        this.assemblerData += "_ceroULONGINT DD 0" + '\n';
        this.assemblerData += st.generateAssemblerCode();
    }
    public void concatenatePROCAssembler(List<SyntacticTree> PROCtrees, RegisterContainer registerContainer) {
        for (SyntacticTree root : PROCtrees) {
            this.assemblerCode += root.getAttribute().getScope() + ": \n";
            this.getMostLeftTreePROC(root, registerContainer);
            this.assemblerCode += "END\nRET\n";
        }
    }

    private void concatenateCodeSection(List<SyntacticTree> PROCtrees, SyntacticTree root, RegisterContainer registerContainer) {
        this.assemblerCode += ".code" + '\n' + "START:" + '\n';
        this.concatenatePROCAssembler(PROCtrees, registerContainer);
        this.assemblerCode += "Error_Resta_Negativa:"+ '\n';
        this.assemblerCode += "invoke MessageBox, NULL, addr Message Error, addr _errorNegativo, MB_OK"+ '\n';
        this.assemblerCode += "invoke ExitProcess, 0" + '\n';
        this.assemblerCode += "Error_Division_Cero:"+ '\n';
        this.assemblerCode += "invoke MessageBox, NULL, addr Message Error, addr _errorCero, MB_OK"+ '\n';
        this.assemblerCode += "invoke ExitProcess, 0" + '\n';
        this.getMostLeftTree(root, registerContainer);
        this.assemblerCode += "invoke ExitProcess, 0" + '\n' + "END START";
    }


    public void getMostLeftTree(SyntacticTree root, RegisterContainer registerContainer) {
        if (root != null && !root.isLeaf()) {
            if ((root.getRight() != null)) {
                if (root.getLeft().isLeaf() && root.getRight().isLeaf()) {
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                } else {
                    this.getMostLeftTree(root.getLeft(), registerContainer);
                    this.getMostLeftTree(root.getRight(), registerContainer);
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                }
            } else {
                if (root.getLeft().isLeaf())
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                else{
                    this.getMostLeftTree(root.getLeft(), registerContainer);
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                }
            }
        }else{
            if(root.isLeaf() && root.getAttribute().getUse().equals(Use.llamado_procedimiento))
                this.assemblerCode += root.assemblerTechnique(registerContainer);
        }
    }

    public void getMostLeftTreePROC(SyntacticTree root, RegisterContainer registerContainer) {
        if (root != null && !root.isLeaf()) {
            if ((root.getRight() != null) && (root.getLeft() != null)) {
                if (root.getLeft().isLeaf() && root.getRight().isLeaf())
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                else {
                    this.getMostLeftTreePROC(root.getLeft(), registerContainer);
                    this.getMostLeftTreePROC(root.getRight(), registerContainer);
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                }
            } else {
                if (root.getRight() != null) {
                    this.getMostLeftTreePROC(root.getRight(), registerContainer);
                    this.assemblerCode += root.assemblerTechnique(registerContainer);
                }else{
                    if(root.getLeft() != null){
                        this.getMostLeftTreePROC(root.getLeft(), registerContainer);
                        this.assemblerCode += root.assemblerTechnique(registerContainer);
                    }
                }
            }
        }else{
            if(root.isLeaf() && root.getAttribute().getUse().equals(Use.llamado_procedimiento))
                this.assemblerCode += root.assemblerTechnique(registerContainer);
        }
    }

    public String printAssembler(List<SyntacticTree> PROCtrees, SyntacticTree root, RegisterContainer registerContainer, SymbolTable st){
        concatenateMainHeader();
        concatenateCodeSection(PROCtrees, root, registerContainer);
        concatenateDataSection(st);
        this.assemblerData += root.getAssemblerData();
        String assembler = this.assemblerHeader + this.assemblerData + this.assemblerCode;
        System.out.println(assembler);
        return assembler;
    }

}
