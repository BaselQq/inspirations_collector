import React from 'react';
import { Container, Group, Button, Text, Title } from '@mantine/core';
import { Link } from 'react-router-dom';
import { useAuth } from "../Hooks/useAuth.ts";

const Header: React.FC = () => {
    const user = useAuth();

    function login(){
        const host = window.location.host == 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + "/oauth2/authorization/github", "_self")
    }

    return (
        <Container>
            <Group position="apart" spacing="xs" align="center">
                <Link to="/">
                    <Title order={2}>Home</Title>
                </Link>
                <Group>
                    {user && (
                        <>
                            <Link to="/add-inspiration">
                                <Button variant="default">Add New Inspiration</Button>
                            </Link>
                        </>)}
                    {!user && (
                    <Link to="/signin">
                        <Button variant="default" onClick={login}>Sign In</Button>
                    </Link>)}
                </Group>
            </Group>
        </Container>
    );
};

export default Header;
