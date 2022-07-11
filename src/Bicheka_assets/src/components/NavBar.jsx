import React from 'react';
import { BrowserRouter, Link , Routes, Route} from "react-router-dom";

//components
import Account from './Account';
import Home from './Home';
import Interchange from './Interchange';
import Send from './Send';

//icons
import HomeIcon from '@mui/icons-material/Home';
import SendIcon from '@mui/icons-material/Send';
import ChangeCircleOutlinedIcon from '@mui/icons-material/ChangeCircleOutlined';
import PersonIcon from '@mui/icons-material/Person';

function NavBar(props){
    return (
        <BrowserRouter forceRefresh = {true}>
            <div className="navBar">
                <div className="navBarItem">
                    <button>
                        <Link to = "/" className = "navBarButton">
                            <HomeIcon/>
                            <p>Home</p>
                        </Link>
                    </button>
                </div>
                <div className="navBarItem">
                    <button>
                        <Link to = "/interchange" className = "navBarButton">
                            <ChangeCircleOutlinedIcon/>
                            <p>Interchange</p>
                        </Link>
                    </button>
                </div>
                <div className="navBarItem">
                    <button>
                        <Link to = "/send" className = "navBarButton">
                            <SendIcon/>
                            <p>Send</p>
                        </Link>
                    </button>
                </div>
                <div className="navBarItem">
                    <button>
                        <Link to = "/account" className = "navBarButton">
                            <PersonIcon className = "accountIcon" />
                            <p>Account</p>
                        </Link>
                    </button>
                </div>
            </div>
            <Routes>
                <Route exact path='/' element = {<Home/>}/>
                <Route exact path='/interchange' element={<Interchange />}/>
                <Route exact path='/send' element={<Send/>}/>
                <Route exact path='/account' element={<Account/>}/>
            </Routes>
        </BrowserRouter>
        
    );
}

export default NavBar;