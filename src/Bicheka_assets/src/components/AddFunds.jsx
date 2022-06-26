import React from 'react';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

function AddFunds(props){
    return (
        <div className = "balance">
            <h5 className="currencyName ">{props.currencyName}</h5>
            <img className="currencyLogo" src={props.logo} alt="usdLogo"/>
            <button className="rightElement">
                <ExpandMoreIcon className= "expand" />
            </button>
        </div>
    );
}

export default AddFunds;