import { useAuth0 } from "@auth0/auth0-react";

import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import { Login } from "./pages/Login.tsx";
import Home from "./pages/Home.tsx";
import DetailsPage from "./pages/DetailsPage.tsx";
import AddImagePage from "./pages/AddImagePage.tsx";
import Footer from "./components/Footer.tsx";
import Header from "./components/Header.tsx";
import AddInspirationPage from "./pages/AddInspirationPage.tsx";

function App() {
  const { isLoading, user, logout, getIdTokenClaims } = useAuth0();

  if (isLoading) {
    return <span>loading...</span>;
  }

  const authProvider: AuthBindings = {
    login: async () => {
      return {
        success: true,
      };
    },
    logout: async () => {
      logout({ returnTo: window.location.origin });
      return {
        success: true,
      };
    },
    onError: async (error) => {
      console.error(error);
      return { error };
    },
    check: async () => {
      try {
        const token = await getIdTokenClaims();
        if (token) {
          axios.defaults.headers.common = {
            Authorization: `Bearer ${token.__raw}`,
          };
          return {
            authenticated: true,
          };
        } else {
          return {
            authenticated: false,
            error: {
              message: "Check failed",
              name: "Token not found",
            },
            redirectTo: "/login",
            logout: true,
          };
        }
      } catch (error: any) {
        return {
          authenticated: false,
          error: new Error(error),
          redirectTo: "/login",
          logout: true,
        };
      }
    },
    getPermissions: async () => null,
    getIdentity: async () => {
      if (user) {
        return {
          ...user,
          avatar: user.picture,
        };
      }
      return null;
    },
  };

  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        {/* TODO: add react error boundary to catch fetching error or 404 error from rest-api  <Route path="*" element={<ErrorComponent />} /> */}
        <Route path="/details/:id" element={<DetailsPage />} />
        <Route path="/add-inspiration" element={<AddInspirationPage />} />

      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
