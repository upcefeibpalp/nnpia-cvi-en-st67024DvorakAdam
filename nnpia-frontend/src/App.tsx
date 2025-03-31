import './App.css'
import { useState, useEffect } from "react";
import UserTable from './components/UserTable';

function App() {
    const [users, setUsers] = useState<any[]>([]); // Stav pro uživatele
    const [loading, setLoading] = useState<boolean>(false); // Stav pro načítání
    const [error, setError] = useState<string | null>(null); // Stav pro chybu

    // Funkce pro načítání uživatelů z backendu
    const fetchUsers = async () => {
        setLoading(true); // Začneme načítat
        setError(null); // Resetujeme chybu
        try {
            const response = await fetch('http://localhost:9000/api/v1/users/all', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Chyba při načítání uživatelů');
            }

            const data = await response.json();
            setUsers(data); // Uložíme uživatele do stavu
        } catch (err) {
            setError('Chyba při volání endpointu');
        } finally {
            setLoading(false); // Načítání skončilo
        }
    };

    // Načteme uživatele při prvním renderování komponenty (componentDidMount)
    useEffect(() => {
        fetchUsers();
    }, []);

    // Funkce pro přepnutí stavu uživatele
    const toggleActiveStatus = (id: number) => {
        setUsers(users.map(user =>
            user.id === id ? { ...user, active: !user.active } : user
        ));
    };

    return (
        <div className="App">
            <h1>Users</h1>
            {loading && <p>Načítám uživatele...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!loading && !error && users.length > 0 && (
                <UserTable users={users} onToggleActive={toggleActiveStatus} />
            )}
        </div>
    );
}

export default App;
