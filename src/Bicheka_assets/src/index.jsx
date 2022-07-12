import ReactDOM from 'react-dom/client';
import React from 'react';
import App from "./components/App";
import { Bicheka, canisterId, createActor } from '../../declarations/Bicheka/index';
import {Actor, HttpAgent} from "@dfinity/agent";
import { AuthClient } from '@dfinity/auth-client';

const init = async () => {

    const webapp_id = process.env.WHOAMI_CANISTER_ID;

    // The interface of the whoami canister
    const webapp_idl = ({ IDL }) => {
        return IDL.Service({ whoami: IDL.Func([], [IDL.Principal], ["query"]) });
    };

    const authClient = await AuthClient.create();

    if (await authClient.isAuthenticated()){
        handleAuthenticated(authClient, webapp_id, webapp_idl);
    }
    else{

        await authClient.login({
            identityProvider: 'https://identity.ic0.app/#authorize',
            onSuccess: () => {
               handleAuthenticated(authClient, webapp_id, webapp_idl);
            }
        });
    }
    
}


async function handleAuthenticated(authClient, webapp_id, webapp_idl){

    const identity = await authClient.getIdentity();
    const authenticatedCanister = createActor(canisterId, {
      agentOptions: {
        identity,
      },
    });
    console.log("webapp = " + authenticatedCanister);

    // Call whoami which returns the principal (user id) of the current user.
    const principal = await authenticatedCanister.getId();
    console.log(principal.toText());

    const root = ReactDOM.createRoot(document.getElementById("root"));
    root.render(<App/>);

}

init();
