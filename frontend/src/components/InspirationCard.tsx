import React from 'react';
import { Card, Image, Text } from '@mantine/core';

interface InspirationCardProps {
    name: string;
    description: string;
    heroImage: string;
}

const InspirationCard: React.FC<InspirationCardProps> = ({ name, description, heroImage }) => {
    return (
        <Card withBorder shadow="sm" p="lg">
            <Card.Section>
                <Image src={heroImage} alt={name} height={160} />
            </Card.Section>
            <Text weight={500} size="lg" mt="md">{name}</Text>
            <Text mt="xs">{description}</Text>
        </Card>
    );
};

export default InspirationCard;
