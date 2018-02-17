
NAME = Chess
# May need to pass OS=win to run on windows
OS = 

all:
	@echo "Compiling..."
	javac -cp ./src/chess/*.java

run: all
# windows needs a semicolon
ifeq ($(OS),win)
		@echo "Running on windows ..."
		cd ./src/chess;java -cp . $(NAME)

# everyone else likes a colon
else
		@echo "Running ..."

		cd ./src/chess;java -cp "." $(NAME)


endif

clean:
	cd ./src/chess;rm -rf *.class