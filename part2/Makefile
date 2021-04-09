all:
	jflex scanner.flex
	java -jar java-cup-11b.jar -interface -parser Parser parser.cup
	javac -cp java-cup-11b-runtime.jar *.java

run: execute
execute:
	@java -cp java-cup-11b-runtime.jar:. Driver

clean:
	$(RM) *.class *~ Main.java Parser.java Scanner.java sym.java

.PHONY: all execute clean
