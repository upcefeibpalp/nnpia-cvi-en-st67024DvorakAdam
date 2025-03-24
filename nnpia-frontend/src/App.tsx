import './App.css'
//import User from "./components/User.tsx"
import UserTable from './components/UserTable';
import {useState} from "react";

function App() {
/*
  return (
    <>
        <h1>Users</h1>
        <User id={0} email={"pavel@upce.cz"} active={true} />
    </>
  )*/

    // Pole uživatelů
    const [users, setUsers] = useState([
        { id: 1, email: 'pavel@upce.cz', active: true },
        { id: 2, email: 'josef@upce.cz', active: false },
        { id: 3, email: 'jana@upce.cz', active: true },
    ]);

    // Funkce pro přepnutí stavu uživatele
    const toggleActiveStatus = (id: number) => {
        setUsers(users.map(user =>
            user.id === id ? { ...user, active: !user.active } : user
        ));
    };

    return (
        <div className="App">
            <h1>Users</h1>
            <UserTable users={users} onToggleActive={toggleActiveStatus} />
        </div>
    );
}

export default App
