import { useEffect, useState } from 'react';
import axios from 'axios';

export const useAuth = () => {
    const [user, setUser] = useState<string | null | undefined>(undefined);

    useEffect(() => {
        const loadUser = async () => {
            try {
                const response = await axios.get('/api/users/me', {
                    withCredentials: true, // Ensure credentials are sent with the request
                });
                if (response.data) {
                    setUser(response.data);
                } else {
                    setUser(null);
                }
            } catch (error) {
                console.error('Error loading user:', error);
                setUser(null);
            }
        };

        loadUser();
    }, []);

    return user;
};
