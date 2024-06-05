// src/pages/Home.tsx
import React, { useEffect, useState } from 'react';
import { Container, SimpleGrid, Card, Image, Text, Group, Badge } from '@mantine/core';
import axios from 'axios';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
    const [inspirations, setInspirations] = useState<any[]>([]);

    useEffect(() => {
        axios.get('http://localhost:8080/inspirations').then((response) => {
            setInspirations(response.data);
        });
    }, []);

    return (
        <Container>
            <Group position="center" style={{ marginBottom: '20px' }}>
                <Text size="xl">Search</Text>
            </Group>
            <SimpleGrid cols={3}>
                {inspirations.map((inspiration) => (
                    <Card key={inspiration.id} shadow="sm" padding="lg" component={Link} to={`/details/${inspiration.id}`}>
                        <Card.Section>
                            <Image src={inspiration.heroImage} height={160} alt={inspiration.name} />
                        </Card.Section>
                        <Group position="apart" style={{ marginBottom: 5, marginTop: 'md' }}>
                            <Text weight={500}>{inspiration.name}</Text>
                            <Badge color="pink" variant="light">
                                {inspiration.tags.join(', ')}
                            </Badge>
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
