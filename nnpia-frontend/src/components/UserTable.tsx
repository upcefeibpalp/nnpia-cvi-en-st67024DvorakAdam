import * as React from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Paper } from '@mui/material';

interface User {
    id: number;
    email: string;
    active: boolean;
}

interface UserTableProps {
    users: User[];
    onToggleActive: (id: number, currentStatus: boolean) => void;  // Opravený typ
}

const UserTable: React.FC<UserTableProps> = ({ users, onToggleActive }) => {
    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map(user => (
                        <TableRow key={user.id}>
                            <TableCell>{user.id}</TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell>{user.active ? 'Active' : 'Inactive'}</TableCell>
                            <TableCell>
                                <Button
                                    variant="contained"
                                    color={user.active ? 'secondary' : 'primary'}
                                    onClick={() => onToggleActive(user.id, user.active)} // Opravené předání dvou argumentů
                                >
                                    {user.active ? 'Deactivate' : 'Activate'}
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default UserTable;
