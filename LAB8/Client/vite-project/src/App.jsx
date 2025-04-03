import { useEffect, useState } from "react";
import { io } from "socket.io-client";
import { TextField, Button, Paper, Typography, Box } from "@mui/material";

const socket = io("http://localhost:3000/");

function ChatApp() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    socket.on("connect", () => {
      console.log("Connected: ", socket.id);
    });

    socket.on("MessageToCoClients", (msg) => {
      setMessages((prevMessages) => [...prevMessages, msg]);
    });

    return () => {
      socket.off("MessageToCoClients");
    };
  }, []);

  const sendMessage = () => {
    if (message.trim() !== "") {
      socket.emit("sendMessage", message);
      setMessages((prevMessages) => [...prevMessages, `You: ${message}`]);
      setMessage("");
    }
  };

  return (
    <Paper elevation={3} sx={{ p: 2, maxWidth: 400, mx: "auto", mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        Chat Room
      </Typography>
      <Box sx={{ height: 300, overflowY: "auto", border: 1, p: 1, mb: 2 }}>
        {messages.map((msg, index) => (
          <Typography
            key={index}
            variant="body1"
            sx={{ p: 0.5, borderBottom: 1 }}
          >
            {msg}
          </Typography>
        ))}
      </Box>
      <Box sx={{ display: "flex", gap: 1 }}>
        <TextField
          fullWidth
          variant="outlined"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Type a message..."
        />
        <Button variant="contained" color="primary" onClick={sendMessage}>
          Send
        </Button>
      </Box>
    </Paper>
  );
}

export default ChatApp;
