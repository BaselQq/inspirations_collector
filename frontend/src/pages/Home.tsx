import React, { useEffect, useState } from 'react';
import { Container, SimpleGrid, Card, Image, Text, Group, Badge, TextInput, ActionIcon } from '@mantine/core';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { AiOutlineClose } from 'react-icons/ai';
import { useAuth } from "../Hooks/useAuth.ts";

const Home: React.FC = () => {
    const [inspirations, setInspirations] = useState<any[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const user = useAuth();

    const fetchInspirations = () => {
        axios.get('http://localhost:8080/inspirations').then((response) => {
            setInspirations(response.data);
        });
    };

    useEffect(() => {
        fetchInspirations();
    }, []);

    useEffect(() => {
        if (searchTerm === '') {
            fetchInspirations();
        } else {
            axios.get(`http://localhost:8080/search/inspirations?searchTerm=${searchTerm}`).then((response) => {
                setInspirations(response.data);
            });
        }
    }, [searchTerm]);

    const handleDelete = (id: string) => {
        axios.delete(`http://localhost:8080/inspiration/${id}`, {
            withCredentials: true
        })
            .then(() => {
                fetchInspirations();
            })
            .catch(error => {
                console.error('Error deleting inspiration:', error);
            });
    };

    return (
        <Container>
            <Group position="center" style={{ marginBottom: '20px' }}>
                <Text size="xl">Search</Text>
            </Group>
            <Group position="center" style={{ marginBottom: '20px' }}>
                <TextInput
                    placeholder="Search for inspirations"
                    value={searchTerm}
                    onChange={(event) => setSearchTerm(event.currentTarget.value)}
                />
            </Group>
            <SimpleGrid cols={3} spacing="lg">
                {inspirations.map((inspiration) => (
                    <Card key={inspiration.id} shadow="sm" padding="lg" radius="md">
                        <Card.Section component={Link} to={`/details/${inspiration.id}`}>
                            <Image src={inspiration.heroImage} height={160} alt={inspiration.name} radius="md" />
                        </Card.Section>
                        <Group position="apart" style={{ marginBottom: 5, marginTop: 'md' }}>
                            <Text weight={500}>{inspiration.name}</Text>
                            <Badge color="pink" variant="light">
                                {inspiration.tags.join(', ')}
                            </Badge>
                            {user && (
                                <ActionIcon color="red" onClick={() => handleDelete(inspiration.id)}>
                                    <AiOutlineClose size={16} />
                                </ActionIcon>
                            )}
                        </Group>
                        <Text size="sm" color="dimmed">
                            {inspiration.description}
                        </Text>
                    </Card>
                ))}
            </SimpleGrid>
        </Container>
    );
};

export default Home;
