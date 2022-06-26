import React, {useState} from "react";
import { Bicheka, canisterId, createActor } from "../../../declarations/Bicheka";
import { AuthClient } from '@dfinity/auth-client';
function Faucet() {

  const [isDisabled, setDisabled] = useState(false)
  const [button, setButton] = useState("Gimme gimme");

  async function handleClick(event) {
    setDisabled(true);
    const authClient = await AuthClient.create();
    const identity = await authClient.getIdentity();
    const authenticatedCanister = createActor(canisterId, {
      agentOptions: {
        identity,
      },
    });
    const result = await authenticatedCanister.payOut();
    setButton(result);
    //setDisabled(false)
  }

  return (
    <div className="blue window">
      <h2>
        <span role="img" aria-label="tap emoji">
          🚰
        </span>
        Faucet
      </h2>
      <label>Get your free Bicheka tokens here! Claim 10,000 DANG coins to your account.</label>
      <p className="trade-buttons">
        <button 
          id="btn-payout"
          onClick={handleClick}
          disabled={isDisabled}
          >
          {button}
        </button>
      </p>
    </div>
  );
}

export default Faucet;
