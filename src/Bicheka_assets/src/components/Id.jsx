import React from 'react';
import ContentCopyIcon from '@mui/icons-material/ContentCopy';

function Id(props){
    return (
        <div className = "ID">
            <div className = "idHeader" >
             <h3 className="idOwner">{props.idOwner}</h3>
             <button className="copyIcon">
                <ContentCopyIcon/>
             </button>
            </div>
            <div className="id">
                <p>{props.id}</p>
                
            </div>
           
        </div>
    );
}

export default Id;