import ReactDOM from 'react-dom/client';
import React from 'react';
import App from "./components/App";
import { Bicheka } from '../../declarations/Bicheka/index';
import {Actor, HttpAgent} from "@dfinity/agent";
import { AuthClient } from '@dfinity/auth-client';

const webapp_id = process.env.WHOAMI_CANISTER_ID;

const webapp_idl = ({IDL}) => {
    return IDL.Service({whoami: IDL.Func([], [IDL.Principal], ["query"])});
};


const init = async () => {

    const authClient = await AuthClient.create();

    if (await authClient.isAuthenticated()){
        handleAuthenticated(authClient);
    }
    else{
        await authClient.login({
            identityProvider: 'https://identity.ic0.app/#authorize',
            onSuccess: () => {
               handleAuthenticated(authClient);
            }
        });
    }

    
}

async function handleAuthenticated(authClient){
    const root = ReactDOM.createRoot(document.getElementById("root"));

    const identity = authClient.getIdentity();
    const agent = new HttpAgent({identity});
    const webapp = Actor.createActor(webapp_idl, {
        agent,
        canisterId: webapp_id,
    });

    const principal = await webapp.whoami();
    console.log(principal.toText());
    Bicheka.addUser(principal);
    
    root.render(<App/>);

    
}

init();
