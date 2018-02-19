
NAME = Chess

all:
	@echo "Compiling"
	javac -cp ./src/chess/*.java

run: all
		@echo "Running"

		cd ./src/chess;java -cp "." $(NAME)

endif

clean:
	cd ./src/chess;rm -rf *.class