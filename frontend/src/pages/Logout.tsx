import { useAuth0 } from "@auth0/auth0-react";

export const Logout: React.FC = () => {
  const handleLogout = () => {
    window.location.href = 'http://dev-2xiaxtlbepivqfdo.us.auth0.com/oidc/logout?federated';
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
