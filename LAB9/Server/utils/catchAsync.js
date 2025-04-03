module.exports = (fn) => {
  return (req, res, next) => {
    fn(req, res, next).catch(next); // Catch errors from async functions
  };
};
