import React from 'react';
import { Container, Group, Button, Text, Title } from '@mantine/core';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
    return (
        <Container>
            <Group position="apart" spacing="xs" align="center">
                <Link to="/">
                    <Title order={2}>Home</Title>
                </Link>
                <Group>
                    <Link to="/signup">
                        <Button variant="default">Sign Up</Button>
                    </Link>
                    <Link to="/signin">
                        <Button variant="default">Sign In</Button>
                    </Link>
                </Group>
            </Group>
        </Container>
    );
};

export default Header;
