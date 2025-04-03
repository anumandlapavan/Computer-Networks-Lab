const signupForm = document.getElementById("signupForm");
const toSignup = document.getElementById("toSignup");

signupForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const name = document.getElementById("signupName").value;
  const email = document.getElementById("signupEmail").value;
  const password = document.getElementById("signupPassword").value;
  const passwordConfirm = document.getElementById(
    "signupPasswordConfirm"
  ).value;

  if (!name || !email || !password) {
    alert("Please fill in all fields.");
    return;
  }

  if (password !== passwordConfirm) {
    alert("The password confirmation went wrong");
    return;
  }

  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  if (!passwordRegex.test(password)) {
    alert(
      "Password must be at least 8 characters long, include one uppercase letter, one lowercase letter, one number, and one special character."
    );
    return;
  }

  setTimeout(() => {
    alert("Signup successful!");
    window.location.href = "authLogin.html";
  }, 150);
});
