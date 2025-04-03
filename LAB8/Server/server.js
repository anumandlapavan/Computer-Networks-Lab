import express from "express";
import { Server } from "socket.io";
import { createServer } from "http";
import cors from "cors";

const app = express();
const port = 3000;

const server = createServer(app);
const io = new Server(server, {
  cors: {
    origin: "http://localhost:5173",
    methods: ["GET", "POST"],
    credentials: true,
  },
});

app.use(
  cors({
    origin: "http://localhost:5173",
    methods: ["GET", "POST"],
    credentials: true,
  })
);

io.on("connection", (socket) => {
  console.log("User connected");
  console.log("Id :", socket.id);

  socket.emit("welcome", "Welcome to the server");

  socket.on("sendMessage", (message) => {
    console.log(`Message from ${socket.id}: ${message}`);

    // Send message to all clients except sender
    socket.broadcast.emit(
      "MessageToCoClients",
      `User-(${socket.id}): ${message}`
    );
  });

  // socket.broadcast.emit("MessageToCoClients", `Hello from socket ${socket.id}`);

  socket.on("disconnect", () => {
    console.log("User disconnected");
  });
});

app.get("/", (req, res) => {
  res.send("Hello world");
});

server.listen(port, () => {
  console.log(`Server is running at port ${port}`);
});
