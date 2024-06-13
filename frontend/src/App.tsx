import { useAuth0 } from "@auth0/auth0-react";

import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import { Login } from "./pages/Login.tsx";
import Home from "./pages/Home.tsx";
import DetailsPage from "./pages/DetailsPage.tsx";
import AddImagePage from "./pages/AddImagePage.tsx";
import Footer from "./components/Footer.tsx";
import Header from "./components/Header.tsx";
import AddInspirationPage from "./pages/AddInspirationPage.tsx";
import { useAuth } from "./Hooks/useAuth.ts";

function App() {
  const user = useAuth();

  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
          {!user && (
            <Route path="/login" element={<Login />} />
          )}
        <Route path="/details/:id" element={<DetailsPage />} />
        {user && (
            <Route path="/add-inspiration" element={<AddInspirationPage />} />
        )}
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
