import React, {useDebugValue, useState} from "react";
import {Principal} from '@dfinity/principal';
import { Bicheka} from "../../../declarations/Bicheka/index";

function Balance(props) {
  
  const [balance, setBalance] = useState(0);
 
  async function handleChange(){
    const id = await Bicheka.getId();
    console.log(String(id));
    const value = await Bicheka.balanceOf(Principal.fromText("nixvz-oy2nx-gyo47-oyrwa-3bzsl-7sssq-7k6tk-wislm-4zwuf-u3dhq-mae"), props.balanceName);
    console.log(value);
    setBalance(value);
    console.log("your balance is "+ balance);
    
    
  }

  async function handleClick(event){
  
    // const authClient = await AuthClient.create();
    // const identity = await authClient.getIdentity();
    // const authenticatedCanister = createActor(canisterId, {
    //   agentOptions: {
    //     identity,
    //   },
    // });
    // const balances = await authenticatedCanister.balanceOf(identity, props.balanceName);
    // setBalance(balances);
  }
  

  return (
    
    <div className = "balance">
        <img className="currencyLogo" src={props.logo} alt="usdLogo"/>
        <h5 className="currencyName ">{props.currencyName}</h5>
        <h6 className="rightElement"
        onClick={handleChange}
        >${balance}</h6>
    </div>

  );
}

export default Balance;
