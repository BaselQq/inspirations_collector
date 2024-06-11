import React from 'react';
import { Container, Group, Button, Text, Title } from '@mantine/core';
import { Link } from 'react-router-dom';
import axios from "axios";

const Header: React.FC = () => {

    function login(){
        const host = window.location.host == 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + "/oauth2/authorization/github", "_self")
    }

    function getUser() {
        axios.get("http://localhost:8080/api/users/me")
            .then(response => {
                console.log(response.data)
            })
    }

    return (
        <Container>
            <Group position="apart" spacing="xs" align="center">
                <Link to="/">
                    <Title order={2}>Home</Title>
                </Link>
                <Group>
                    <Link to="/add-inspiration">
                        <Button variant="default">add new inspiration</Button>
                    </Link>
                    <Link to="/signin">
                        <Button variant="default" onClick={login}>Sign In</Button>
                    </Link>
                    <Button variant="default" onClick={getUser}>Me</Button>
                </Group>
            </Group>
        </Container>
    );
};

export default Header;
