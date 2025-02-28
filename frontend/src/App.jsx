import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import PropertyManagement from "./components/PropertyManagement";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <h1>Property Management System</h1>
        <PropertyManagement />
      </div>
    </>
  );
}

export default App;
