// import { useAuth0 } from "@auth0/auth0-react";

export const Login: React.FC = () => {
  // const { loginWithRedirect } = useAuth0();

  function login(){
    const host = window.location.host == 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
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
      {/*<button onClick={() => loginWithRedirect()}>Sign in</button>*/}
      <button onClick={login}>Sign in</button>
    </div>
  );
};
