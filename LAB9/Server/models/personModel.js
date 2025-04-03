const mongoose = require("mongoose");

const personSchema = new mongoose.Schema({
  firstName: {
    type: String,
    required: [true, "Please provide a first name"],
  },
  lastName: {
    type: String,
    required: [true, "Please provide a last name"],
  },
  email: {
    type: String,
    required: [true, "Please provide an email address"],
    unique: true,
    lowercase: true,
  },
  age: {
    type: Number,
    required: [true, "Please provide an age"],
  },
  active: {
    type: Boolean,
    default: true,
  },
});

const Person = mongoose.model("Person", personSchema);

module.exports = Person;
