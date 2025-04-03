import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function Home() {
  return <h1 className="text-2xl">Home Page</h1>;
}

function About() {
  return <h1 className="text-2xl">About Page</h1>;
}

function Contact() {
  return <h1 className="text-2xl">Contact Page</h1>;
}

export default function App() {
  return (
    <Router>
      <div className="flex flex-col items-center p-4">
        <nav className="flex gap-4 mb-4">
          <Link to="/" className="text-blue-500">
            Home
          </Link>
          <Link to="/" className="text-blue-500">
            About
          </Link>
          <Link to="/" className="text-blue-500">
            Contact
          </Link>
        </nav>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
        </Routes>
      </div>
    </Router>
  );
}
