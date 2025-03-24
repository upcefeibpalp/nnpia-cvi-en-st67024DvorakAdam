import {useState} from "react";
import * as React from "react";

interface UserProps {
  id:number,
  email:string,
  active: boolean
}

const User=({id,email,active}:UserProps)=>{

  const [activeState, setActiveState] = useState<boolean>(active);

  const buttonClickHandle = (event: React.MouseEvent)=>{
    event.preventDefault();
    setActiveState(!activeState)
  };

  return <div>
    <h2>User {id}</h2>
    <ul>
      <li>Email: {email}</li>
      <li>{(activeState) ? "Aktivní" : "Zablokován"}</li>
    </ul>
    <button onClick={buttonClickHandle}>Aktivovat/Zablokovat</button>
  </div>
};

export default User;