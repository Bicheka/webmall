import React, {useState} from "react";
import { Bicheka } from "../../../declarations/Bicheka/index";
import { Principal } from "@dfinity/principal";

function Transfer() {
  
  const [recipientId, setId] = useState("");
  const [amount, setAmount] = useState("");
  const [isDisabled, setDisabled] = useState(false);
  const [feedBack, setFeedBack] = useState("");
  const [isHidden, setHidden] = useState(true);
  
  async function handleClick() {
    setHidden(true);
    const recipient = Principal.fromText(recipientId);
    setDisabled(true);
    const amountToTransfer = Number(amount);
    const result = await Bicheka.transfer(recipient, amountToTransfer);
    setHidden(false);
    setFeedBack(result);
    setDisabled(false);
  }

  return (
    <div className="window white">
      <div className="transfer">
        <fieldset>
          <legend>To Account:</legend>
          <ul>
            <li>
              <input
                type="text"
                id="transfer-to-id"
                value = {recipientId}
                onChange = {(event) => setId(event.target.value)}
              />
            </li>
          </ul>
        </fieldset>
        <fieldset>
          <legend>Amount:</legend>
          <ul>
            <li>
              <input
                type="number"
                id="amount"
                value = {amount}
                onChange = {(event) => setAmount(event.target.value)}
              />
            </li>
          </ul>
        </fieldset>
        <div className="trade-buttons">
          <button 
            id="btn-transfer"
            onClick={handleClick}
            disabled={isDisabled}
             >
            Transfer
          </button>
          <p hidden = {isHidden}>{feedBack}</p>
        </div>
      </div>
    </div>
  );
}

export default Transfer;
