# My Personal Project

## Two-player board game

This is a board game collection with two games, chess and ticktacktoe. Each person control their own pieces and try to win.

People who want a simple game of chess or ticktacktoe with their friend could use it.

There are two reasons that I want to create this application:
- I really like to play board games with my friends on the spare time
- I like to play chess in particular
- ticktacktoe is such a simple and common game to play anywhere, anytime.
- I think this will be a challenging experience and a great learning opportunity for me.


## User Story Phase 1
- As a user, I want to be able to move one of my pieces to a square.
- As a user, I want to be able to capture an opponent's piece and add it to list of pieces captured.
- As a user, I want to be able to see which pieces were captured by my opponent and myself.
- As a user, I want to be able to see the all the moves my opponent and I performed.
- As a user, I want to pick the space to place my X or O

## User Story Phase 2
- As a user, I want to save my chess/ticktacktoe game when close the application
- As a user, I want to have the option to reload my saved chess/ticktacktoe game when i reopen the application


## "Phase 4: Task 2"
Fri Apr 01 14:27:05 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:08 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:09 PDT 2022  
bP added to black and all captured  
Fri Apr 01 14:27:09 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:11 PDT 2022  
loaded Black Pieces Captured  
Fri Apr 01 14:27:15 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:17 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:18 PDT 2022  
wP added to white and all captured  
Fri Apr 01 14:27:18 PDT 2022  
new move added to moves  
Fri Apr 01 14:27:20 PDT 2022  
loaded White Pieces Captured  

## "Phase 4: Task 3"
I felt like my overall class design was pretty good.  
If I wanted to improve my design, I would add  
a connection between BoardGame and   
ChessGame, TickTackToeGame. Right now   
there isn't one since BoardGame only creates  
an instance of ChessGame and TickTackToeGame  
when the button is clicked. Therefore, I have no  
way of going back to the BoardGame (main menu)  
after one of the games are finished. Secondly,  
I have a lot of repetitive code in the constructors of  
ChessGame and TickTackToeGame when reading  
and newly constructing. Lastly, ChessBoard class  
is doing a lot more than just being a chessboard,  
I could take the part of checking if a move is valid,  
and the part of making a move out into two separate  
classes.
