import React from 'react';
import AddFunds from './AddFunds';
import Balance from './Balance';
import NavBar from './NavBar';
function Interchange(props){
    return (
        <div className="home">
            <div className="title">
                <h1>Interchange</h1>
            </div>
            <div>
                <AddFunds currencyName = "Add USD Funds" logo = "./usdLogo.png" />
            </div>
            <div>
                <h1>Here goes the payment method</h1>
            </div>
            <div>
                <AddFunds currencyName = "USD for Bicheka" logo = "./bichekaLogo.png" />
            </div>

            <div>
                <AddFunds currencyName = "Bicheka for USD" logo = "./bichekaLogo.png" />
            </div>
         </div>
    );
}

export default Interchange;