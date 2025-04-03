const Person = require("../models/personModel");
const catchAsync = require("../utils/catchAsync"); // Utility for handling async errors

exports.getAllPersons = catchAsync(async (req, res, next) => {
  const persons = await Person.find();
  res.status(200).json({
    status: "success",
    results: persons.length,
    data: { persons },
  });
});

exports.createPerson = catchAsync(async (req, res, next) => {
  const newPerson = await Person.create(req.body);
  res.status(201).json({
    status: "success",
    data: { newPerson },
  });
});
