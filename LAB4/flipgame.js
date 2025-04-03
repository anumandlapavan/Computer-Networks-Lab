const grid = document.getElementById("grid");
const timerEl = document.getElementById("timer");
const player1ScoreEl = document.getElementById("player1-score");
const player2ScoreEl = document.getElementById("player2-score");

let level = 3;
let gridSize = level * level;
let cards = [];
let flippedCards = [];
let currentPlayer = 1;
let timer;
let player1Score = 0;
let player2Score = 0;
let timeLeft = 60;
let canFlip = true;

function createGrid(size) {
  grid.innerHTML = "";
  grid.style.gridTemplateColumns = `repeat(${level}, 1fr)`;
  gridSize = size;

  const images = Array.from({ length: 9 }, (_, i) => `img${i + 1}.jpg`);
  let selectedImages = [];

  // Select the required number of images to form pairs
  while (selectedImages.length < size / 2) {
    let img = images[Math.floor(Math.random() * images.length)];
    if (!selectedImages.includes(img)) {
      selectedImages.push(img);
    }
  }

  // Duplicate images to make pairs
  cards = [...selectedImages, ...selectedImages];

  // Ensure grid is completely filled by shuffling
  while (cards.length < size) {
    let extraImage = images[Math.floor(Math.random() * images.length)];
    if (cards.filter((img) => img === extraImage).length < 2) {
      cards.push(extraImage);
    }
  }

  // Shuffle final card list
  cards.sort(() => Math.random() - 0.5);

  cards.forEach((card) => {
    const cardElement = document.createElement("div");
    cardElement.classList.add("grid-item");
    cardElement.dataset.value = card;
    cardElement.style.backgroundColor = "#ccc";
    cardElement.addEventListener("click", () => handleCardClick(cardElement));
    grid.appendChild(cardElement);
  });
}

function handleCardClick(card) {
  if (
    !canFlip ||
    card.classList.contains("flipped") ||
    flippedCards.length === 2
  )
    return;
  card.style.backgroundImage = `url(images/${card.dataset.value})`;
  card.style.backgroundSize = "cover";
  card.classList.add("flipped");
  flippedCards.push(card);

  if (flippedCards.length === 2) {
    canFlip = false;
    setTimeout(checkMatch, 1000);
  }
}

function checkMatch() {
  const [card1, card2] = flippedCards;

  if (card1.dataset.value === card2.dataset.value) {
    card1.classList.add("matched");
    card2.classList.add("matched");

    if (currentPlayer === 1) {
      player1Score++;
      player1ScoreEl.textContent = player1Score;
    } else {
      player2Score++;
      player2ScoreEl.textContent = player2Score;
    }
    checkGameOver();
  } else {
    setTimeout(() => {
      card1.style.backgroundImage = "";
      card2.style.backgroundImage = "";
      card1.classList.remove("flipped");
      card2.classList.remove("flipped");
    }, 1000);
  }

  flippedCards = [];
  canFlip = true;
  switchPlayer();
}

function checkGameOver() {
  const remainingCards = document.querySelectorAll(".grid-item:not(.matched)");
  if (remainingCards.length === 0) {
    announceWinner();
  }
}

function announceWinner() {
  clearInterval(timer);
  let winnerMessage = "";
  if (player1Score > player2Score) {
    winnerMessage = "Player 1 Wins!";
  } else if (player2Score > player1Score) {
    winnerMessage = "Player 2 Wins!";
  } else {
    winnerMessage = "It's a Tie!";
  }
  setTimeout(() => {
    alert(
      `${winnerMessage}\nPlayer 1 Score: ${player1Score}\nPlayer 2 Score: ${player2Score}`
    );
    resetGame();
  }, 500);
}

function switchPlayer() {
  currentPlayer = currentPlayer === 1 ? 2 : 1;
  canFlip = true;
  timeLeft = 60;
  startTimer();
}

function startTimer() {
  clearInterval(timer);
  timer = setInterval(() => {
    timeLeft--;
    timerEl.textContent = timeLeft;

    if (timeLeft === 0) {
      clearInterval(timer);
      if (flippedCards.length === 1) {
        randomlyFlipOneCard();
      }
      switchPlayer();
    }
  }, 1000);
}

function randomlyFlipOneCard() {
  const unflippedCards = [
    ...document.querySelectorAll(".grid-item:not(.flipped):not(.matched)"),
  ];
  if (unflippedCards.length > 0) {
    const randomCard =
      unflippedCards[Math.floor(Math.random() * unflippedCards.length)];
    handleCardClick(randomCard);
  }
}

function startGame(selectedLevel) {
  level = selectedLevel;
  createGrid(level * level);
  currentPlayer = 1;
  player1Score = 0;
  player2Score = 0;
  player1ScoreEl.textContent = player1Score;
  player2ScoreEl.textContent = player2Score;
  canFlip = true;
  clearInterval(timer);
  timeLeft = 60;
  timerEl.textContent = timeLeft;
  startTimer();
}

function resetGame() {
  startGame(level);
}

document.getElementById("easy").addEventListener("click", () => startGame(3));
document.getElementById("medium").addEventListener("click", () => startGame(4));
document.getElementById("hard").addEventListener("click", () => startGame(6));

startGame(level);
