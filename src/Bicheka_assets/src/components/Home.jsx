import React from 'react';
import Balance from './Balance';

function Home(props){
    return (
        <div className="home">
        <div className="title">
            <h1>Bicheka</h1>
        </div>
        <Balance balanceName = "USD" currencyName = "US Dollar" logo = "../usdLogo.png"/>
        <Balance balanceName = "Bicheka" currencyName = "Bicheka" logo = "../bichekaLogo.png"/>  
    </div>
    );
}

export default Home;