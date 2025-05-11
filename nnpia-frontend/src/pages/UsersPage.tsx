import { useState, useEffect } from "react";
import axios from 'axios';
import UserTable from '../components/UserTable';

const UsersPage = () => {
    const [users, setUsers] = useState<any[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const fetchUsers = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get('http://localhost:9000/api/v1/users/all');
            setUsers(response.data);
        } catch (err) {
            setError('Error fetching users');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const activateUser = async (id: number) => {
        try {
            await axios.post(`http://localhost:9000/api/v1/users/${id}/activate`);
            setUsers(users.map(user =>
                user.id === id ? { ...user, active: true } : user
            ));
        } catch (err) {
            setError('Error activating user');
        }
    };

    const deactivateUser = async (id: number) => {
        try {
            await axios.post(`http://localhost:9000/api/v1/users/${id}/deactivate`);
            setUsers(users.map(user =>
                user.id === id ? { ...user, active: false } : user
            ));
        } catch (err) {
            setError('Error deactivating user');
        }
    };

    const toggleActiveStatus = (id: number, currentStatus: boolean) => {
        if (currentStatus) {
            deactivateUser(id);
        } else {
            activateUser(id);
        }
    };

    return (
        <div className="page-container">
            <h1>Users</h1>
            {loading && <p>Loading users...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!loading && !error && users.length > 0 && (
                <UserTable users={users} onToggleActive={toggleActiveStatus} />
            )}
        </div>
    );
};

export default UsersPage;
