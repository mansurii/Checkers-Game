# Checkers Game

Welcome to the Checkers game, implemented using the Model-View-Controller (MVC) design pattern. This game allows for one mode of play : human vs. human.
## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Game Rules](#game-rules)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- **Human vs. Human**: Play with a friend in a local multiplayer setting.
- **MVC Architecture**: The game is designed using the MVC pattern for a clean separation of concerns.
- **Intuitive UI**: Easy-to-use interface for a seamless gaming experience.

## Installation

### Prerequisites

- Ensure you have **JDK 21 or above** (Java Development Kit) installed. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html).
- Ensure you have **Java** installed on your machine. You can verify the installation by running `java -version` in your command line.

### Steps using bash
1. ** Clone the repository:** git clone https://github.com/mansurii/othello-game.git
2. ** Navigate to the project directory:** cd Checker-game
3. ** Complile the checkers game:** javac -d bin src/*.java
4.  ** Run the checkers game:** Java -cp bin Main

## Usage
### Starting the Game:
- Run the game using the instructions in the Installation section.
- Follow the on-screen instructions to make your moves.
### Game controls:
- Use the mouse to click on the board to place your pieces.
- The game will automatically switch turns between the players.

## Game Rules
### Objective:
- The objective of Checkers is to capture all of your opponent's pieces or block them so they cannot move.
### Game Play:
- The game is played on an 8x8 board with alternating dark and light squares. Each player starts with 12 pieces placed on the dark squares of the three rows closest to them.
- Players take turns moving their pieces diagonally to an adjacent empty square.
- If a player's piece can capture an opponent's piece by jumping over it to an empty square, they must do so. Multiple jumps are allowed if available.
- When a piece reaches the opposite side of the board, it is crowned and becomes a "king," which can move diagonally both forward and backward.
- The game ends when one player captures all of the opponent's pieces or blocks them so they cannot move.

## Contributing
- Contributions are welcome! If you have any suggestions or improvements, please create a pull request or open an issue.
- Fork the repository.
- Create your feature branch (git checkout -b feature/AmazingFeature).
- Commit your changes (git commit -m 'Add some AmazingFeature').
- Push to the branch (git push origin feature/AmazingFeature).
- Open a pull request.

## License
- This project is licensed under the MIT License.

## Contact
If you have any questions or need further assistance, feel free to contact me:
- Email: mansurii60@outlook.com
- LinkedIn: Ismail Mansuri

Feel free to check out all my projects on https://github.com/mansurii

    
