import './App.css';
import { useState, useEffect } from "react";
import axios from 'axios'; // Import Axios
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
            // Axios GET požadavek
            const response = await axios.get('http://localhost:9000/api/v1/users/all');

            // Uložíme uživatele do stavu
            setUsers(response.data);
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

    // Funkce pro aktivaci uživatele
    const activateUser = async (id: number) => {
        try {
            // Axios POST požadavek pro aktivaci uživatele
            await axios.post(`http://localhost:9000/api/v1/users/${id}/activate`);

            // Aktualizujeme stav uživatele na frontendové straně
            setUsers(users.map(user =>
                user.id === id ? { ...user, active: true } : user
            ));
        } catch (err) {
            setError('Chyba při aktivaci uživatele');
        }
    };

    // Funkce pro deaktivaci uživatele
    const deactivateUser = async (id: number) => {
        try {
            // Axios POST požadavek pro deaktivaci uživatele
            await axios.post(`http://localhost:9000/api/v1/users/${id}/deactivate`);

            // Aktualizujeme stav uživatele na frontendové straně
            setUsers(users.map(user =>
                user.id === id ? { ...user, active: false } : user
            ));
        } catch (err) {
            setError('Chyba při deaktivaci uživatele');
        }
    };

    // Funkce pro přepnutí stavu uživatele (pro UI)
    const toggleActiveStatus = (id: number, currentStatus: boolean) => {
        if (currentStatus) {
            deactivateUser(id); // Deaktivujeme uživatele, pokud je aktivní
        } else {
            activateUser(id); // Aktivujeme uživatele, pokud není aktivní
        }
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
