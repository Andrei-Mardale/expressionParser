#!/bin/bash
if [ $# -eq 0 ] 
then
	make clean &> /dev/null
else
	echo "Compiling..."
	make &> /dev/null
	echo "Compiling finished!"

	echo "Running..."

	java Main.Main $1

	echo "Finished!"
fi
