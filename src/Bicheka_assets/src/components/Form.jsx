import React from 'react';

function Form(){
    return (
       <form className = "formDiv" >
            <div className="form-floating mb-3 mt-3">
                <input type="number" className="form-control" placeholder="Enter Amount" />
                <label>Amount</label>
            </div>

            <div className="form-floating mt-3 mb-3">
                <input type="text" className="form-control" id="to:" placeholder="to:"/>
                <label>To</label>
            </div>
            <div className="form-floating mt-3 mb-3">
                <input type="text" className="form-control" id="to:" placeholder="to:"/>
                <label>From</label>
            </div>
            <button className = "formButton" type = "submit">
                Send
            </button>
       </form>
    );
} 

export default Form;