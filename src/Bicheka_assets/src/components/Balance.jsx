import React, {useDebugValue, useState} from "react";
import {Principal} from '@dfinity/principal';
import { Bicheka, canisterId, createActor } from "../../../declarations/Bicheka/index";
import { AuthClient } from '@dfinity/auth-client';

function Balance(props) {
  
  const [balance, setBalance] = useState(0);
 
  async function handleChange(){
    const id = await Bicheka.getId()
    const value = await Bicheka.balanceOf(Principal.fromText(id), props.balanceName);
    setBalance(value);
  }

  async function handleClick(event){
  
    const authClient = await AuthClient.create();
    const identity = await authClient.getIdentity();
    const authenticatedCanister = createActor(canisterId, {
      agentOptions: {
        identity,
      },
    });
    const balance = await authenticatedCanister.balanceOf(props.balanceName);
  }
  // const [id, setId] = useState("");
  // const [balanceResult, setBalance] = useState("");
  // const [cryptoSymbol, setSymbol] = useState("");
  // const [isHidden, setHidden] = useState(true);

  // function handleChange(event){
  //   const value = event.target.value;
  //   setId(value);
  // }

  // async function handleClick() {
  //   console.log(id);
  //   const principal = Principal.fromText(id); //convert the id into principal data type
  //   const balance = await Bicheka.balanceOf(principal);
  //   setBalance(balance.toLocaleString());
  //   setSymbol(await Bicheka.showSymbol());
  //   setHidden(false);
  // }

  return (
    
    <div className = "balance">
        <img className="currencyLogo" src={props.logo} alt="usdLogo"/>
        <h5 className="currencyName ">{props.currencyName}</h5>
        <h6 className="rightElement"
        onLoad={handleChange}
        >${balance}</h6>
        <button className = "myButton" onClick={handleClick}><h5>Click me</h5></button>
    </div>

  );
}

export default Balance;
