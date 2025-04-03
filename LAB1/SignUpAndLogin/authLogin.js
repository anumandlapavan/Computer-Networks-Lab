const loginForm = document.getElementById("loginForm");
const toLogin = document.getElementById("toLogin");

loginForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const email = document.getElementById("loginEmail").value;
  const password = document.getElementById("loginPassword").value;

  if (!email || !password) {
    alert("Please fill in all fields.");
    return;
  }
  alert("Login successful!");
});
