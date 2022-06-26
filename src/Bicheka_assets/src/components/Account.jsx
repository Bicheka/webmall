import React from 'react';
import AddIcon from '@mui/icons-material/Add';
import Friends from './Friends';
function Account(){
    return (
        <div className="home">
        <div className="title">
        <h1>Account</h1>
            <button>
                <div className = "addContact" >
                    <AddIcon id = "addContact" />
                    <p>Add Contact</p>
                </div>
            </button>
        </div>
        <Friends/>
    </div>
    );
}

export default Account;