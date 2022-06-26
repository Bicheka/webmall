import React from 'react';
import Balance from './Balance';

function Home(){
    return (
        <div className="home">
        <div className="title">
            <h1>Bicheka</h1>
        </div>
        <Balance balanceName = "usdBalance" currencyName = "US Dollar" logo = "../usdLogo.png"/>
        <Balance balanceName = "bichekaBalance" currencyName = "Bicheka" logo = "../bichekaLogo.png"/>  
    </div>
    );
}

export default Home;