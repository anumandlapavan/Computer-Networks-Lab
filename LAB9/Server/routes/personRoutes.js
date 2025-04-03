const express = require("express");
const {
  getAllPersons,
  createPerson,
} = require("../controllers/personController");

const router = express.Router();

router
  .route("/")
  .get(getAllPersons) // Route for getting all persons
  .post(createPerson); // Route for creating a new person

module.exports = router;
