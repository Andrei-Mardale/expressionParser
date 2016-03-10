JC = javac

.SUFFIXES: .java .class
.java.class:
		$(JC) $*.java

CLASSES = \
	ExpressionTree/Addition.java \
	ExpressionTree/Assignment.java \
	ExpressionTree/Constant.java \
	ExpressionTree/ExpressionTree.java \
	ExpressionTree/Multiplication.java \
	ExpressionTree/Node.java \
	ExpressionTree/Operand.java \
	ExpressionTree/Operator.java \
	ExpressionTree/Ternary.java \
	ExpressionTree/Unary.java \
	ExpressionTree/Variable.java \
	ExpressionParser/ExpressionParser.java \
	ExpressionTreePrinter/ExpressionTreePrinter.java \
	Main/Main.java

build: classes

classes: $(CLASSES:.java=.class)

run: build 
	java -Xmx512m Main.Main

clean: 
	cd ExpressionTreePrinter; rm -f *.class
	cd ExpressionParser; rm -f *.class
	cd ExpressionTree; rm -f *.class
	cd Main; rm -f *.class


