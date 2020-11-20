package AssemblerGenerator;

//ULONGINT: registros del procesador (EAX, EBX, ECX Y EDX o AX, BX, CX y DX) y seguimiento de registros.
//DOUBLE: co-procesador 80X87, y variables auxiliares.

import SymbolTable.SymbolTable;
import SymbolTable.Use;
import SyntacticTree.SyntacticTree;

import java.util.List;

public class AssemblerGenerator {

    private SyntacticTree tree;
    private String assembler = "";

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
        this.assembler += ".386" + '\n' + ".model flat, stdcall" + '\n' + "option casemap :none" + '\n' +
        "include \\masm32\\include\\windows.inc" + '\n' + "include \\masm32\\include\\kernel32.inc" + '\n' +
        "include \\masm32\\include\\user32.inc" + '\n' + "includelib \\masm32\\lib\\kernel32.lib" + '\n' +
        "includelib \\masm32\\lib\\user32.lib" + '\n';
    }

    private void concatenateDataSection(SymbolTable st) {
        this.assembler += ".data" + '\n';
        this.assembler += st.generateAssemblerCode();

    }
    public void concatenatePROCAssembler(List<SyntacticTree> PROCtrees, RegisterContainer registerContainer) {
        for (SyntacticTree root : PROCtrees) {
            this.assembler += root.getAttribute().getScope() + ": \n";
            this.getMostLeftTreePROC(root, registerContainer);
            this.assembler += "END\nRET\n";
        }
    }

    private void concatenateCodeSection(List<SyntacticTree> PROCtrees, SyntacticTree root, RegisterContainer registerContainer) {
        this.assembler += ".code" + '\n' + "START:" + '\n';
        this.concatenatePROCAssembler(PROCtrees, registerContainer);
        this.getMostLeftTree(root, registerContainer);
        this.assembler += "invoke ExitProcess, 0" + '\n' + "END START";
    }


    public void getMostLeftTree(SyntacticTree root, RegisterContainer registerContainer) {
        if (root != null && !root.isLeaf()) {
            if ((root.getRight() != null)) {
                if (root.getLeft().isLeaf() && root.getRight().isLeaf()) {
                    this.assembler += root.generateAssemblerCode(registerContainer);
                } else {
                    this.getMostLeftTree(root.getLeft(), registerContainer);
                    this.getMostLeftTree(root.getRight(), registerContainer);
                    this.assembler += root.generateAssemblerCode(registerContainer);
                }
            } else {
                if (root.getLeft().isLeaf())
                    this.assembler += root.generateAssemblerCode(registerContainer);
                else{
                    this.getMostLeftTree(root.getLeft(), registerContainer);
                    this.assembler += root.generateAssemblerCode(registerContainer);
                }
            }
        }else{
            if(root.isLeaf() && root.getAttribute().getUse().equals(Use.llamado_procedimiento))
                this.assembler += root.generateAssemblerCode(registerContainer);
        }
    }

    public void getMostLeftTreePROC(SyntacticTree root, RegisterContainer registerContainer) {
        if (root != null && !root.isLeaf()) {
            if ((root.getRight() != null) && (root.getLeft() != null)) {
                if (root.getLeft().isLeaf() && root.getRight().isLeaf())
                    this.assembler += root.generateAssemblerCode(registerContainer);
                else {
                    this.getMostLeftTreePROC(root.getLeft(), registerContainer);
                    this.getMostLeftTreePROC(root.getRight(), registerContainer);
                    this.assembler += root.generateAssemblerCode(registerContainer);
                }
            } else {
                if (root.getRight() != null) {
                    this.getMostLeftTreePROC(root.getRight(), registerContainer);
                    this.assembler += root.generateAssemblerCode(registerContainer);
                }else{
                    if(root.getLeft() != null){
                        this.getMostLeftTreePROC(root.getLeft(), registerContainer);
                        this.assembler += root.generateAssemblerCode(registerContainer);
                    }
                }
            }
        }else{
            if(root.isLeaf() && root.getAttribute().getUse().equals(Use.llamado_procedimiento))
                this.assembler += root.generateAssemblerCode(registerContainer);
        }
    }

    public String printAssembler(List<SyntacticTree> PROCtrees, SyntacticTree root, RegisterContainer registerContainer, SymbolTable st){
        concatenateMainHeader();
        concatenateDataSection(st);
        concatenateCodeSection(PROCtrees, root, registerContainer);
        System.out.println(this.assembler);
        return this.assembler;
    }

}
