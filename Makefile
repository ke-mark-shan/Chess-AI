# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = A3Basic
# you may need to pass OS=win to run on windows
OS = 

# HACK: vecmath is included regardless if needed
all:
	@echo "Compiling..."
	javac -cp vecmath.jar basic/*.java
	javac -cp vecmath.jar enhanced/*.java

run: all
# windows needs a semicolon
ifeq ($(OS),win)
		@echo "Running on windows ..."
ifeq ($(NAME),A3Basic)
		cd basic;java -cp "vecmath.jar;." $(NAME)
else
		cd enhanced;java -cp "vecmath.jar;." $(NAME)
endif
# everyone else likes a colon
else
		@echo "Running ..."
ifeq ($(NAME),A3Basic)
		cd basic;java -cp "vecmath.jar:." $(NAME)
else
		cd enhanced;java -cp "vecmath.jar:." $(NAME)
endif

endif

clean:
	cd basic;rm -rf *.class
	cd enhanced;rm -rf *.class