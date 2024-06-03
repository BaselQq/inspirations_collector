import { useAuth0 } from "@auth0/auth0-react";

const LOGOUT_LINK = import.meta.env.VITE_CONFIG_AUTH_LOGOUT_LINK;

export const Logout: React.FC = () => {
  const handleLogout = () => {
    window.location.href = LOGOUT_LINK;
  }

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
      }}
    >
      <button onClick={handleLogout}>Sign out</button>
    </div>
  );
};
