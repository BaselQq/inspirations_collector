// src/pages/DetailsPage.tsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Image, Text, Badge, Group, SimpleGrid } from '@mantine/core';
import axios from 'axios';

const DetailsPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [inspiration, setInspiration] = useState<any>(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/inspiration/${id}`).then((response) => {
            setInspiration(response.data);
        });
    }, [id]);

    if (!inspiration) {
        return <Text>Loading...</Text>;
    }

    return (
        <Container>
            <SimpleGrid cols={2}>
                {inspiration.detailImageUrls.map((url: string, index: number) => (
                    <Image key={index} src={url} height={400} alt={inspiration.name} />
                ))}
            </SimpleGrid>
            <Text size="xl">{inspiration.name}</Text>
            <Group>
                {inspiration.tags.map((tag: string, index: number) => (
                    <Badge key={index} color="pink" variant="light">
                        {tag}
                    </Badge>
                ))}
            </Group>
            <Text>{inspiration.description}</Text>
        </Container>
    );
};

export default DetailsPage;
