import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { Auth0Provider } from "@auth0/auth0-react";
import '@mantine/core/styles.css';
import { MantineProvider } from "@mantine/core";


const DOMAIN = import.meta.env.VITE_CONFIG_AUTH_CONFIG_DOMAIN;
const CLIENT_ID = import.meta.env.VITE_CONFIG_AUTH_CONFIG_CLIENT_ID;
ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(

<React.StrictMode>
  <MantineProvider>
    <Auth0Provider
      //TODO: add our values to have our own authentication account connected to our FRONTEND
      domain={DOMAIN}
      clientId={CLIENT_ID}
      redirectUri={window.location.origin}
    >
      <App />
    </Auth0Provider>
  </MantineProvider>
  </React.StrictMode>
);
