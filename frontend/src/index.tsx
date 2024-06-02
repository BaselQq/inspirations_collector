import { Auth0Provider } from "@auth0/auth0-react";
import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";

const container = document.getElementById("root") as HTMLElement;
const root = createRoot(container);

const DOMAIN = import.meta.env.VITE_CONFIG_AUTH_CONFIG_DOMAIN;
const CLIENT_ID = import.meta.env.VITE_CONFIG_AUTH_CONFIG_CLIENT_ID;

root.render(
  <React.StrictMode>
    <Auth0Provider
      //TODO: add our values to have our own authentication account connected to our FRONTEND
      domain={DOMAIN}
      clientId={CLIENT_ID}
      redirectUri={window.location.origin}
    >
      <App />
    </Auth0Provider>
  </React.StrictMode>
);
