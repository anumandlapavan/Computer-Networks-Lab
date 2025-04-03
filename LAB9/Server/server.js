const mongoose = require("mongoose");
const express = require("express");
const dotenv = require("dotenv");
const cors = require("cors");
const personRoutes = require("./routes/personRoutes");
const PORT = 3001;
dotenv.config({ path: "./config.env" });

const app = require("./app");

app.use(cors());
app.use(express.json());

const DB = process.env.DATABASE.replace(
  "<PASSWORD>",
  process.env.DATABASE_PASSWORD
);

app.use("/api/persons", personRoutes);

mongoose.connect(DB).then((con) => {
  // console.log(con.connections);
  console.log("DB connection Successful");
});

const server = app.listen(PORT, () => {
  console.log(`server is listening to port ${PORT}`);
});
