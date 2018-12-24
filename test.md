cd ./src/resources
antlr4 MiniJava.g4
cd ../MiniJava
javac -d ../../bin *.java
cd ../Compiler
javac -d ../../bin/ -cp .:../../bin:/usr/local/lib/antlr-4.7.1-complete.jar Compiler.java
cd ../../bin
java Compiler

[^_^]:test1
class Factorial {
    public static void main(String[] a)
    {
        System.out.println();
    }
}

[^_^]:test2
class Factorial {
    public static void main(String[] a) {
        System.out.println(new Fac().ComputeFac(10));
    }
    
}
class Fac {
    public int ComputeFac(int num) {
        int num_aux;
        num_aux = 1;
        return num_aux;
    }
}
